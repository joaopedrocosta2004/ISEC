import pickle
import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.model_selection import train_test_split, RandomizedSearchCV
from sklearn.metrics import confusion_matrix, accuracy_score, recall_score, precision_score, f1_score, roc_auc_score, classification_report
from sklearn.ensemble import RandomForestClassifier
import seaborn as sns
import matplotlib.pyplot as plt
from tensorflow.keras.preprocessing.image import img_to_array, array_to_img
from tensorflow.keras.applications import MobileNetV2
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Dense, GlobalAveragePooling2D, Dropout
from tensorflow.keras.optimizers import Adam
from scikeras.wrappers import KerasClassifier

classes = [2, 8, 9, 10, 11]

# Converter as classes para índices numéricos consecutivos
class_mapping = {label: idx for idx, label in enumerate(classes)}

def preprocess_data(file, target_size=(224, 224)):

    with open(file, 'rb') as f:
        data = pickle.load(f, encoding='latin1')
        x = data['features'].astype(np.float32)
        y = data['labels']
        sizes = data['sizes']
        coords = data['coords']

    df = pd.DataFrame({
        'features': list(x),
        'labels': y,
        'sizes': list(sizes),
        'coords': list(coords)
    })

    df_filtered = df[df['labels'].isin(classes)]

    df_limited = df_filtered.groupby('labels').apply(lambda x: x.sample(n=500, random_state=42)).reset_index(drop=True)

    # Pré-processamento
    cropped_images = []
    mapped_labels = []

    for i in range(len(df_limited)):
        img = df_limited['features'].iloc[i]            # Imagem original
        x1, y1, x2, y2 = df_limited['coords'].iloc[i]
        width, height = df_limited['sizes'].iloc[i]     # Dimensões originais da imagem

        # Verificar que as coordenadas limitadoras estão dentro dos limites da imagem 
        if x1 < 0: x1 = 0
        if y1 < 0: y1 = 0
        if x2 > width: x2 = width
        if y2 > height: y2 = height
        
        # Cortar a imagem pelos limites
        cropped_img = img[y1:y2, x1:x2, :]
        
        # Redimensionar a imagem para o tamanho esperado pela arquitetura
        img_resized = array_to_img(cropped_img).resize(target_size)
        img_resized = img_to_array(img_resized) / 255.0           # Normalizar
        
        # Guardar a imagem pré-processada
        cropped_images.append(img_resized)

        original_label = df_limited['labels'].iloc[i]
        mapped_labels.append(class_mapping[original_label])

    # Converter listas para numpy arrays
    cropped_images = np.array(cropped_images)
    mapped_labels = np.array(mapped_labels)

    # Dividir o conjunto de dados em treino e teste
    X_train, X_test, y_train, y_test = train_test_split(cropped_images, mapped_labels, test_size=0.2, random_state=42, stratify=mapped_labels)
    
    return X_train, X_test, y_train, y_test

file = r".\DataSet\train.pickle"
X_train, X_test, y_train, y_test = preprocess_data(file)

def create_pretrained_model(input_shape, num_classes, learning_rate=0.001, dense_units=128):
    # Carregar o modelo MobileNetV2 pré-treinado no ImageNet
    base_model = MobileNetV2(weights='imagenet', include_top=False, input_shape=input_shape)

    # Congelar os pesos da base
    base_model.trainable = False

    # Adicionar camadas customizadas no topo (top layers)
    x = base_model.output
    x = GlobalAveragePooling2D()(x)
    x = Dense(dense_units, activation='relu')(x)
    x = Dropout(0.5)(x)  # Regularização com dropout
    x = Dense(dense_units // 2, activation='relu')(x)
    x = Dropout(0.5)(x)  # Regularização com dropout
    predictions = Dense(num_classes, activation='softmax')(x)

    # Criar o modelo final
    model = Model(inputs=base_model.input, outputs=predictions)

    # Compilar o modelo
    model.compile(optimizer=Adam(learning_rate=learning_rate), 
                  loss='sparse_categorical_crossentropy', 
                  metrics=['accuracy'])

    return model

input_shape = (224, 224, 3)  # Imagem 32x32 com 3 canais de cor (RGB)
num_classes = len(classes)

# Definir hiperparâmetros
learning_rate = 0.001
dense_units = 256

pretrained_model = create_pretrained_model(input_shape, num_classes, learning_rate, dense_units)

# Treinar o modelo
history = pretrained_model.fit(X_train, y_train, epochs=10, validation_data=(X_test, y_test), batch_size=32)

pretrained_model.save("traffic_sign_model.h5")

# Avaliar o modelo
test_loss, test_acc = pretrained_model.evaluate(X_test, y_test)
print(f'Test accuracy: {test_acc:.4f}')

y_probs = pretrained_model.predict(X_test)
y_pred = np.argmax(y_probs, axis=1)

# Calcular as Métricas
accuracy = accuracy_score(y_test, y_pred)
recall = recall_score(y_test, y_pred, average='weighted')
precision = precision_score(y_test, y_pred, average='weighted')
fmeasure = f1_score(y_test, y_pred, average='weighted')

# Exibir as métricas finais
print(f"Accuracy: {accuracy:.4f}")
print(f"Recall: {recall:.4f}")
print(f"Precision: {precision:.4f}")
print(f"F-measure: {fmeasure:.4f}")

# Calcular o AUC por classe
auc_scores = []
for i in range(num_classes):
    auc = roc_auc_score((y_test == i).astype(int), y_probs[:, i])
    auc_scores.append(auc)
    print(f"AUC for class {classes[i]}: {auc:.4f}")

# Criar dataframes para salvar no Excel
df_results = pd.DataFrame({
    'Metric': ['Accuracy', 'Recall', 'Precision', 'F-measure'],
    'Value': [accuracy, recall, precision, fmeasure]
})

df_auc_scores = pd.DataFrame({
    'Class': classes,
    'AUC': auc_scores
})

df_confusion_mtx = pd.DataFrame(
    confusion_matrix(y_test, y_pred),
    index=[f"True_{cls}" for cls in classes],
    columns=[f"Pred_{cls}" for cls in classes]
)

# Criar um relatório de classificação detalhado
class_report = classification_report(y_test, y_pred, target_names=[f"Class_{cls}" for cls in classes], output_dict=True)
df_class_report = pd.DataFrame(class_report).transpose()

# Adicionar o tamanho das classes no conjunto de dados
df_shapes = pd.DataFrame({
    "Dataset": ["Training", "Testing"],
    "Samples": [X_train.shape[0], X_test.shape[0]],
    "Features Shape": [X_train.shape[1:], X_test.shape[1:]]
})

# Matriz Confusão
cm = confusion_matrix(y_test, y_pred)
plt.figure(figsize=(10, 7))
sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
plt.xlabel('Predicted')
plt.ylabel('True')
plt.title('Confusion Matrix')
plt.show()

# Salvar todos os dados em um único arquivo Excel
with pd.ExcelWriter("model_results.xlsx") as writer:
    df_shapes.to_excel(writer, sheet_name='Formas dos Conjuntos', index=False)
    df_results.to_excel(writer, sheet_name='Resultados Otimizacao', index=False)
    df_auc_scores.to_excel(writer, sheet_name='AUC por Classe', index=False)
    df_confusion_mtx.to_excel(writer, sheet_name='Matriz de Confusão')
    df_class_report.to_excel(writer, sheet_name='Relatório de Classificação')

# Plotar curvas de treino e validação
plt.figure(figsize=(12, 5))

# Curva de accuracy
plt.subplot(1, 2, 1)
plt.plot(history.history['accuracy'], label='Train Accuracy')
plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
plt.title('Model Accuracy')
plt.xlabel('Epochs')
plt.ylabel('Accuracy')
plt.legend()

# Curva de perda
plt.subplot(1, 2, 2)
plt.plot(history.history['loss'], label='Train Loss')
plt.plot(history.history['val_loss'], label='Validation Loss')
plt.title('Model Loss')
plt.xlabel('Epochs')
plt.ylabel('Loss')
plt.legend()

plt.tight_layout()
plt.show()

# Achatar as imagens para input no Random Forest
X_train_flat = X_train.reshape(X_train.shape[0], -1)
X_test_flat = X_test.reshape(X_test.shape[0], -1)

# Definir os hiperparâmetros para a random search
param_grid = {
    'n_estimators': [10, 50, 100, 200],
    'max_depth': [5, 10, 20, None],
    'min_samples_split': [2, 5, 10],
    'min_samples_leaf': [1, 2, 4],
}

# Criar o modelo base para Random Forest
rf_model = RandomForestClassifier(random_state=42)

# Realizar Random Search
random_search = RandomizedSearchCV(rf_model, param_distributions=param_grid, n_iter=20, cv=3, verbose=2, random_state=42, n_jobs=-1)
random_search.fit(X_train_flat, y_train)

# Melhor modelo encontrado
best_rf = random_search.best_estimator_

# Avaliar o modelo Random Forest no conjunto de teste
rf_predictions = best_rf.predict(X_test_flat)
rf_accuracy = accuracy_score(y_test, rf_predictions)
rf_recall = recall_score(y_test, rf_predictions, average='weighted')
rf_precision = precision_score(y_test, rf_predictions, average='weighted')
rf_fmeasure = f1_score(y_test, rf_predictions, average='weighted')

print(f"Random Forest Accuracy: {rf_accuracy:.4f}")
print(f"Random Forest Recall: {rf_recall:.4f}")
print(f"Random Forest Precision: {rf_precision:.4f}")
print(f"Random Forest F-measure: {rf_fmeasure:.4f}")