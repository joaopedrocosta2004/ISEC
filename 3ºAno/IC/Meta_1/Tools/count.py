import pickle
import pandas as pd

with open(r".\train.pickle", 'rb') as fl:
    data = pickle.load(fl)
    
# print(data)

df = pd.DataFrame({
    'coords': list(data['coords']),
    'labels': data['labels'],
    'sizes': list(data['sizes'])
})

# print(df.head())

print(df['labels'].value_counts())

df_labels = pd.read_csv(r"./label_names.csv")

# print(df_labels)

# print(df_labels[(df_labels['ClassId'] == 2) | (df_labels['ClassId'] == 1) | (df_labels['ClassId'] == 13) | (df_labels['ClassId'] == 12) | (df_labels['ClassId'] == 38) | (df_labels['ClassId'] == 10) | (df_labels['ClassId'] == 4) | (df_labels['ClassId'] == 5) | (df_labels['ClassId'] == 25) | (df_labels['ClassId'] == 9)])

array = []
