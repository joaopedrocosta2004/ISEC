import streamlit as st
from PIL import Image
import numpy as np
import tensorflow as tf

# Carregar o modelo treinado (garanta que o modelo está no mesmo diretório do script)
@st.cache_resource
def load_model():
    model = tf.keras.models.load_model("traffic_sign_model.h5")
    return model

# Função para processar a imagem e fazer a previsão
def predict(image, model):
    try:
        # Pré-processar a imagem
        img = image.convert("RGB")  # Converter para RGB
        img = img.resize((224, 224))  # Redimensionar para o tamanho esperado pelo modelo
        img_array = np.array(img) / 255.0  # Normalizar os valores de pixel
        img_array = np.expand_dims(img_array, axis=0)  # Adicionar dimensão batch

        # Fazer a previsão
        predictions = model.predict(img_array)
        predicted_class_idx = np.argmax(predictions[0])
        confidence = predictions[0][predicted_class_idx]

        return predicted_class_idx, confidence
    except Exception as e:
        st.error(f"Erro ao processar a imagem: {e}")
        return None, None

# Dicionário de classes do modelo
classes = [2, 8, 9, 10, 11]
class_names = {
    0: "Sinal 2 - Speed limit (50km/h)",
    1: "Sinal 8 - Speed limit (120km/h)",
    2: "Sinal 9 - No passing",
    3: "Sinal 10 - No passing for vehicles over 3.5 metric tons",
    4: "Sinal 11 - Right-of-way at the next intersection",
}

# Configurar título e descrição da página
st.title("Reconhecimento de Sinais de Trânsito")
st.write("Carregue uma imagem para classificar o sinal de trânsito utilizando o modelo treinado.")

# Carregar o modelo
model = load_model()

# Carregar a imagem
uploaded_image = st.file_uploader("Envie uma imagem no formato JPG ou PNG", type=["jpg", "jpeg", "png"])

if uploaded_image is not None:
    # Exibir a imagem carregada
    image = Image.open(uploaded_image)
    st.image(image, caption="Imagem carregada", use_container_width=True)

    # Botão para classificar a imagem
    if st.button("Classificar"):
        st.write("Classificando a imagem...")

        # Fazer a previsão
        with st.spinner("Processando..."):
            predicted_class_idx, confidence = predict(image, model)

        # Exibir os resultados
        if predicted_class_idx is not None:
            predicted_class = class_names.get(predicted_class_idx, "Classe desconhecida")
            st.success(f"Classe prevista: {predicted_class}")
            st.write(f"Confiança: {confidence:.4f}")
        else:
            st.error("Erro ao classificar a imagem. Verifique o formato ou tente novamente.")
