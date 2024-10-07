from collections import Counter
import torch
import torch.nn as nn

# Créer des batchs d'entrée et de sortie (X et Y)
def create_batches(sequences, seq_length, batch_size):
    X = []
    Y = []
    for seq in sequences:
        for i in range(len(seq) - seq_length):
            X.append(seq[i:i + seq_length])
            Y.append(seq[i + seq_length])

    X = torch.tensor(X, dtype=torch.long)
    Y = torch.tensor(Y, dtype=torch.long)

    # Ajuster pour les batchs
    n_batches = len(X) // batch_size
    X = X[:n_batches * batch_size]  # Redimensionner X
    Y = Y[:n_batches * batch_size]  # Redimensionner Y

    # Réorganiser pour avoir les batchs
    Y = Y.view(batch_size, -1)  # (batch_size, seq_length * n_batches)

    return X, Y



class Tokenizer():
    def __init__(self, texts, batch_size):
        self.texts = texts

        # Construction du vocabulaire
        words = ' '.join(texts).split()
        word_counts = Counter(words)
        self.vocab = sorted(word_counts, key=word_counts.get, reverse=True)

        self.batch_size = batch_size
        self.word_to_idx = {word: idx for idx, word in enumerate(self.vocab)}
        self.idx_to_word = {idx: word for word, idx in self.word_to_idx.items()}

    def get_vocab(self):
        return self.vocab

    def get_word_to_idx(self):
        return self.word_to_idx

    def get_idx_to_word(self):
        return self.idx_to_word
    
    def text_to_ids(self, text):
        # Convertir le texte en une liste d'indices
        return [self.word_to_idx.get(word, 0) for word in text.split()]  # 0 pour les mots inconnus

    def id_to_text(self, idx_list):
        # Convertir une liste d'indices en texte
        return ' '.join(self.idx_to_word.get(idx, '<UNK>') for idx in idx_list)  # <UNK> pour indices inconnus

    def tokenize(self, seq_length=5):
        # Conversion du texte en séquences de tokens
        sequences = []
        for text in self.texts:
            tokenized = self.text_to_ids(text)
            sequences.append(tokenized)
    
        return create_batches(sequences, seq_length, self.batch_size)
