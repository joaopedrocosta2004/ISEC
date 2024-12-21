import pickle
import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix, accuracy_score, recall_score, precision_score, f1_score, roc_auc_score
import seaborn as sns
import matplotlib.pyplot as plt
from tensorflow.keras.preprocessing.image import img_to_array, array_to_img
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout
from tensorflow.keras.optimizers import Adam
from SwarmPackagePy import gwo

# Classes
classes = [2, 8, 9, 10, 11, 13, 18, 25, 35, 38]
class_mapping = {label: idx for idx, label in enumerate(classes)}

# Função de pré-processamento
def preprocess_data(file, target_size=(32, 32)):
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
    df_limited = df_filtered.groupby('labels').apply(lambda x: x.sample(n=1000, random_state=42)).reset_index(drop=True)

    cropped_images = []
    mapped_labels = []

    for i in range(len(df_limited)):
        img = df_limited['features'].iloc[i]
        x1, y1, x2, y2 = df_limited['coords'].iloc[i]
        width, height = df_limited['sizes'].iloc[i]

        x1 = max(0, x1)
        y1 = max(0, y1)
        x2 = min(width, x2)
        y2 = min(height, y2)

        cropped_img = img[y1:y2, x1:x2, :]
        img_resized = array_to_img(cropped_img).resize(target_size)
        img_resized = img_to_array(img_resized) / 255.0

        cropped_images.append(img_resized)
        mapped_labels.append(class_mapping[df_limited['labels'].iloc[i]])

    cropped_images = np.array(cropped_images)
    mapped_labels = np.array(mapped_labels)

    X_train, X_test, y_train, y_test = train_test_split(cropped_images, mapped_labels, test_size=0.2, random_state=42, stratify=mapped_labels)
    
    return X_train, X_test, y_train, y_test

# Carregar e preprocessar os dados
file = r".\DataSet\train.pickle"
X_train, X_test, y_train, y_test = preprocess_data(file)

# Função para criar o modelo CNN
def create_cnn_model(input_shape, num_classes, learning_rate, dropout_rate):
    model = Sequential()

    model.add(Conv2D(32, (3, 3), activation='relu', input_shape=input_shape))
    model.add(MaxPooling2D((2, 2)))

    model.add(Conv2D(64, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2, 2)))

    model.add(Conv2D(128, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2, 2)))

    model.add(Flatten())
    model.add(Dropout(dropout_rate))
    model.add(Dense(num_classes, activation='softmax'))
    
    model.compile(optimizer=Adam(learning_rate),
                  loss='sparse_categorical_crossentropy',
                  metrics=['accuracy'])
    
    return model

# Função de treinamento para o GWO
def fitness_function(hyperparameters):
    learning_rate, dropout_rate = hyperparameters
    input_shape = (32, 32, 3)
    num_classes = 10

    model = create_cnn_model(input_shape, num_classes, learning_rate, dropout_rate)

    history = model.fit(X_train, y_train, epochs=5, validation_data=(X_test, y_test), verbose=0)
    val_loss = model.evaluate(X_test, y_test, verbose=0)[0]
    
    print(f"Hyperparameters: {hyperparameters}, Validation Loss: {val_loss}")
    return val_loss

# Parâmetros do GWO
n_agents =10
n_iterations = 20
dimensions = 2
lower_bound = [0.0001, 0.0]  # Limites inferiores: learning rate e dropout
upper_bound = [0.2, 0.7]     # Limites superiores: learning rate e dropout

# Inicializar e executar o GWO
optimizer = gwo(n_agents, fitness_function, lower_bound, upper_bound, dimensions, n_iterations)
best_learning_rate, best_dropout_rate = optimizer.get_Gbest()

print(f"\nMelhores hiperparâmetros encontrados:")
print(f"Learning Rate: {best_learning_rate}")
print(f"Dropout Rate: {best_dropout_rate}\n")

# Treinar o modelo final com os melhores hiperparâmetros
final_model = create_cnn_model((32, 32, 3), 10, best_learning_rate, best_dropout_rate)
history = final_model.fit(X_train, y_train, epochs=10, validation_data=(X_test, y_test))

# Avaliar o modelo final
test_loss, test_acc = final_model.evaluate(X_test, y_test)
print(f"\nTest Loss: {test_loss:.4f}, Test Accuracy: {test_acc:.4f}")

# Previsões e métricas
y_probs = final_model.predict(X_test)
y_pred = np.argmax(y_probs, axis=1)

accuracy = accuracy_score(y_test, y_pred)
recall = recall_score(y_test, y_pred, average='weighted')
precision = precision_score(y_test, y_pred, average='weighted')
fmeasure = f1_score(y_test, y_pred, average='weighted')

print(f"\nAccuracy: {accuracy:.4f}")
print(f"Recall: {recall:.4f}")
print(f"Precision: {precision:.4f}")
print(f"F-measure: {fmeasure:.4f}")

print("\nAUC")
for i in range (len(classes)):
    auc = roc_auc_score(y_test == i, y_probs[:, i])
    print(f"AUC for class {classes[i]}: {auc:.4f}")

# Matriz de Confusão
cm = confusion_matrix(y_test, y_pred)
plt.figure(figsize=(10, 7))
sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
plt.xlabel('Predicted')
plt.ylabel('True')
plt.title('Confusion Matrix')
plt.show()