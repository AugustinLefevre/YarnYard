import requests
from bs4 import BeautifulSoup

def get_expression_body(url):

  html = requests.get(url).content.decode('utf-8')
  soup = BeautifulSoup(html, "html.parser")
  #print(soup.contents)
  body = soup.select_one(".Expression-container")

  # Liste des balises à récupérer
  tags_to_extract = ['p']
    
  # Extraire le texte des balises spécifiées
  extracted_text = []
    
  for tag in tags_to_extract:
      elements = soup.find_all(tag)
      for element in elements:
          extracted_text.append(element.get_text())

  # Joindre le texte extrait en une seule chaîne
  final_text = "\n".join(extracted_text)
  print(final_text)
  return final_text