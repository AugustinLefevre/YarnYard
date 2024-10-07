import torch
import torch.nn as nn

#vocab_size : taille du vocabulaire (le nombre total de mots uniques dans votre jeu de données).
#embed_size : taille de la représentation vectorielle pour chaque mot (embedding).
#hidden_size : nombre de neurones dans la couche LSTM.
#num_layers : nombre de couches LSTM.

class TextGenerationModel(nn.Module):
    def __init__(self, vocab_size, embed_size, hidden_size, num_layers):
        super(TextGenerationModel, self).__init__()
        self.embedding = nn.Embedding(vocab_size, embed_size)
        self.lstm = nn.LSTM(embed_size, hidden_size, num_layers, batch_first=True)
        self.fc = nn.Linear(hidden_size, vocab_size)
        self.hidden_size = hidden_size  # Stocke hidden_size
        self.num_layers = num_layers    # Stocke num_layers

    def forward(self, x, hidden):
        # Embedding des entrées
        x = self.embedding(x)
        # LSTM
        out, hidden = self.lstm(x, hidden)
        # Prédiction des prochains mots
        out = self.fc(out)
        return out, hidden

    def init_hidden(self, batch_size):
        # Initialisation des états cachés
        return (torch.zeros(self.num_layers, batch_size, self.hidden_size),
                torch.zeros(self.num_layers, batch_size, self.hidden_size))
