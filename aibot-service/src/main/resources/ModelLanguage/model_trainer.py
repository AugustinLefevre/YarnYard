import pandas as pd
from text_data_handler import get_texts
import tokenizer
from Transformer_model import TextGenerationModel 
import torch
import torch.nn as nn
import matplotlib.pyplot as plt

# Charger les textes à partir du chemin spécifié
texts = get_texts('d:/spring-aungular/TalesTrade/aibot-service/src/main/resources/ModelLanguage/training_text')

# Initialiser le tokenizer avec les textes chargés et une taille de vocabulaire de 32
tokenizer = tokenizer.Tokenizer(texts, 32)

# Tokenisation des données
X, Y = tokenizer.tokenize()

print(f"Max index in X: {X.max()}, Min index in X: {X.min()}")
print(f"Max index in Y: {Y.max()}, Min index in Y: {Y.min()}")
vocab_size = 10000
X = torch.clamp(X, min=0, max=vocab_size-1)
Y = torch.clamp(Y, min=0, max=vocab_size-1)

# Paramètres du modèle
vocab_size = 10000  # Taille du vocabulaire (nombre maximum de mots que le modèle peut traiter)
embed_size = 1024    # Taille des vecteurs d'embedding qui représentent chaque mot, permettant une meilleure compréhension des relations entre eux
hidden_size = 1024   # Taille des états cachés dans le modèle, déterminant la capacité d'apprentissage du modèle
num_layers = 30     # Nombre de couches dans le modèle Transformer; plus il y a de couches, plus le modèle peut apprendre des relations complexes
nhead = 8           # Nombre de têtes d'attention dans chaque couche, permettant au modèle de se concentrer sur différentes parties de l'entrée simultanément
dim_feedforward = 512  # Taille des couches feedforward dans le modèle, qui sont utilisées après les couches d'attention
num_epochs = 200 # Nombre total d'époques pour l'entraînement, représentant le nombre de fois où le modèle va passer sur l'ensemble de données

epochs = list(range(1, num_epochs+1))
losses = [] # Liste pour stocker les valeurs de perte à chaque étape d'entraînement pour l'analyse ultérieure

model = TextGenerationModel(vocab_size, embed_size, num_layers, nhead, dim_feedforward)
optimizer = torch.optim.Adam(model.parameters(), lr=0.00001)
criterion = nn.CrossEntropyLoss()

batch_size = min(32, len(X))
sequence_length = 200 # Longueur de séquence maximale pour les entrées; toutes les séquences seront tronquées à cette longueur

Y = Y[:, :sequence_length]

# Entraînement du modèle
for epoch in range(num_epochs):
    for i in range(0, len(X) - batch_size + 1, batch_size):
        inputs = X[i:i + batch_size]
        targets = Y[i:i + batch_size]
        targets = targets[:, :5]
        targets = targets.contiguous().view(-1)

        if targets.shape[0] == 0:
           # print(f"Skipping empty target batch at iteration {i}")
            continue

        optimizer.zero_grad()

        outputs = model(inputs)

        outputs = outputs.view(-1, vocab_size)

        targets = targets.view(-1)
        if outputs.shape[0] != targets.shape[0]:
            raise ValueError(f"Mismatch between output size {outputs.shape[0]} and target size {targets.shape[0]}")

        loss = criterion(outputs, targets)

        loss.backward()

        optimizer.step()

        if i % (batch_size * 10) == 0:
            print(f'Epoch {epoch + 1}, Batch {i // batch_size + 1}, Loss: {loss.item()}')
            epochs = list(range(1, 201))
            losses.append(loss.item())


def nucleus_sampling(logits, p=0.9):
    probabilities = torch.softmax(logits, dim=-1)
    sorted_logits, sorted_indices = torch.sort(probabilities, descending=True)
    cumulative_probs = torch.cumsum(sorted_logits, dim=-1)

    sorted_indices_to_remove = cumulative_probs > p
    sorted_indices_to_remove[:, 1:] = sorted_indices_to_remove[:, :-1].clone()
    sorted_indices_to_remove[:, 0] = 0

    indices_to_remove = sorted_indices[sorted_indices_to_remove]

    for idx in indices_to_remove:
        logits[0, idx.item()] = float('-inf')

    return torch.multinomial(torch.softmax(logits, dim=-1), 1).item()


def generate_text(model, start_text, length, temperature=1.0):
    input_ids = tokenizer.text_to_ids(start_text)
    input_tensor = torch.tensor(input_ids).unsqueeze(0)

    generated_text = start_text

    for _ in range(length):
        output = model(input_tensor)
        
        logits = output[:, -1, :]

        logits = logits / temperature

        next_token = nucleus_sampling(logits, p=0.9)

        generated_text += " " + tokenizer.id_to_text([next_token])

        input_tensor = torch.cat((input_tensor, torch.tensor([[next_token]])), dim=1)

    return generated_text

generated_text = generate_text(model, "il était une fois,", 50, temperature=0.9)
print(generated_text)

plt.plot(epochs, losses)
plt.title('Perte au cours de l\'entraînement')
plt.xlabel('Époques')
plt.ylabel('Perte')
plt.show()
