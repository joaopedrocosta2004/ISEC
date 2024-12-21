import pickle
import os
from PIL import Image

# Carregar o dataset
with open('./train.pickle', 'rb') as file:
    data = pickle.load(file)

# Configurações de saída
output_dir = 'traffic_sign_images_by_class'
os.makedirs(output_dir, exist_ok=True)

# Classes desejadas e limite de imagens por classe
target_classes = {2, 8, 9, 10, 11, 13, 18, 25, 35, 38}
max_images_per_class = 1000

# Arrays de dados
features = data['features']
labels = data['labels']

# Dicionário para contar imagens salvas por classe
images_saved = {label: 0 for label in target_classes}

# Filtrando e salvando as imagens
for idx, (image_array, label) in enumerate(zip(features, labels)):
    if label in target_classes and images_saved[label] < max_images_per_class:
        # Criar pasta da classe se não existir
        class_dir = os.path.join(output_dir, f'class_{label}')
        os.makedirs(class_dir, exist_ok=True)
        
        # Converte a imagem para o formato PIL e salva
        img = Image.fromarray(image_array)
        img_filename = os.path.join(class_dir, f'class_{label}_img_{idx}.jpg')
        img.save(img_filename)
        
        # Atualiza o contador de imagens salvas para a classe
        images_saved[label] += 1
        
    # Para se todas as classes atingiram o limite de imagens
    if all(count >= max_images_per_class for count in images_saved.values()):
        break

print(f"Imagens foram salvas em '{output_dir}' em pastas organizadas por classe, com até {max_images_per_class} imagens por classe.")
