import torch
import torch.nn as nn

# Classe pour l'encodage positionnel
class PositionalEncoding(nn.Module):
    def __init__(self, embed_size, max_len=5000):
        super(PositionalEncoding, self).__init__()
        pe = torch.zeros(max_len, embed_size)
        position = torch.arange(0, max_len, dtype=torch.float).unsqueeze(1)
        div_term = torch.exp(torch.arange(0, embed_size, 2).float() * (-torch.log(torch.tensor(10000.0)) / embed_size))
        pe[:, 0::2] = torch.sin(position * div_term)
        pe[:, 1::2] = torch.cos(position * div_term)
        pe = pe.unsqueeze(0).transpose(0, 1)  # forme (max_len, 1, embed_size)
        self.register_buffer('pe', pe)

    def forward(self, x):
        # x : (batch_size, seq_length, embed_size)
        x = x + self.pe[:x.size(0), :]  # On ajoute l'encodage positionnel
        return x

class TextGenerationModel(nn.Module):
    def __init__(self, vocab_size, embed_size, num_layers, nhead, dim_feedforward):
        super(TextGenerationModel, self).__init__()
        self.embedding = nn.Embedding(vocab_size, embed_size)
        self.positional_encoding = PositionalEncoding(embed_size)
        encoder_layer = nn.TransformerEncoderLayer(
            d_model=embed_size, 
            nhead=nhead, 
            dim_feedforward=dim_feedforward,
            batch_first=True  # IMPORTANT: Assurez-vous que batch_first est à True
        )
        self.transformer = nn.TransformerEncoder(encoder_layer, num_layers)
        self.fc = nn.Linear(embed_size, vocab_size)

    def forward(self, x, src_mask=None):
        x = self.embedding(x)  # Vérifiez que cette ligne ne produit pas une forme incorrecte
        x = self.positional_encoding(x)  # Cela doit être (batch_size, seq_length, embed_size)

        out = self.transformer(x, src_mask)  # out : (batch_size, seq_length, embed_size)

        # Prédiction des prochains mots
        out = self.fc(out)  # out : (batch_size, seq_length, vocab_size)
        return out



    def generate_mask(self, sz):
        mask = torch.triu(torch.ones(sz, sz) * float('-inf'), diagonal=1)
        return mask
