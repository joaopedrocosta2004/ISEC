import os
import pickle
import numpy as np
from PIL import Image

# Diretório contendo as pastas organizadas por classe
input_dir = 'traffic_sign_images_by_class'
output_pickle_file = 'traffic_sign_images_by_class.pickle'

# Dicionário para armazenar as imagens por classe
data_by_class = {}

# Carregar imagens de cada pasta (classe)
for class_folder in os.listdir(input_dir):
    class_path = os.path.join(input_dir, class_folder)
    if os.path.isdir(class_path):
        class_label = int(class_folder.split('_')[1])  # Extraindo a classe do nome da pasta
        images = []
        
        # Iterando sobre as imagens da pasta
        for img_file in os.listdir(class_path)[:1000]:  # Limitando a 1000 imagens por classe
            img_path = os.path.join(class_path, img_file)
            with Image.open(img_path) as img:
                images.append(np.array(img))
        
        # Armazenar a lista de imagens no dicionário
        data_by_class[class_label] = images

# Salvar o dicionário como arquivo pickle
with open(output_pickle_file, 'wb') as file:
    pickle.dump(data_by_class, file)

print(f"As imagens foram salvas no arquivo '{output_pickle_file}' em formato pickle.")
