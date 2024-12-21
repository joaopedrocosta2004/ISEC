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
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense


classes = [2, 8, 9, 10, 11, 13, 18, 25, 35, 38]

# Converter as classes para índices numéricos consecutivos
class_mapping = {label: idx for idx, label in enumerate(classes)}

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
        
        # Redimensionar a imagem para 32x32
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

file = r".\train.pickle"
X_train, X_test, y_train, y_test = preprocess_data(file)

def create_cnn_model(input_shape, num_classes):
    model = Sequential()

    model.add(Conv2D(32, (3, 3), activation='relu', input_shape=input_shape))
    model.add(MaxPooling2D((2, 2)))

    model.add(Conv2D(64, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2, 2)))

    model.add(Conv2D(128, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2, 2)))

    model.add(Flatten())

    model.add(Dense(128, activation='relu'))
    
    model.add(Dense(64, activation='relu'))
    
    model.add(Dense(num_classes, activation='softmax'))

    model.compile(optimizer='adam', 
                  loss='sparse_categorical_crossentropy', 
                  metrics=['accuracy'])

    return model

input_shape = (32, 32, 3)  # Imagem 32x32 com 3 cores (RGB)
num_classes = 10

cnn_model = create_cnn_model(input_shape, num_classes)

cnn_model.summary()

# Treinar o modelo
history = cnn_model.fit(X_train, y_train, epochs=10, validation_data=(X_test, y_test), batch_size=32)

# Avaliar o modelo
test_loss, test_acc = cnn_model.evaluate(X_test, y_test)
print(f'Test accuracy: {test_acc:.4f}')

y_probs = cnn_model.predict(X_test)

y_pred = np.argmax(y_probs, axis=1)

# Calcular as Métricas
accuracy = accuracy_score(y_test, y_pred)
recall = recall_score(y_test, y_pred, average='weighted')
precision = precision_score(y_test, y_pred, average='weighted')
fmeasure = f1_score(y_test, y_pred, average='weighted')


print(f"Accuracy: {accuracy}")
print(f"Recall: {recall}")
print(f"Precision: {precision}")
print(f"F-measure: {fmeasure}")

print("\nAUC")
for i in range (len(classes)):
    auc = roc_auc_score(y_test == i, y_probs[:, i])
    print(f"AUC for class {classes[i]}: {auc:.4f}")


# Matriz Confusão
cm = confusion_matrix(y_test, y_pred)
plt.figure(figsize=(10, 7))
sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
plt.xlabel('Predicted')
plt.ylabel('True')
plt.title('Confusion Matrix')
plt.show()


