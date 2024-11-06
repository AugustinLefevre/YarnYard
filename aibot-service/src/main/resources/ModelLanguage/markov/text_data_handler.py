import os

def get_texts(directory):
    texts = []
    for file in os.listdir(directory):
        path = os.path.join(directory, file)
    
        if os.path.isfile(path):
            with open(path, "r", encoding="utf-8") as fichier:
                content = fichier.read()
                texts.append(content)
        else:
            texts = texts + get_texts(path)
    return texts
