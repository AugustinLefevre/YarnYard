#@title Utility functions
import matplotlib.pyplot as plt
from html_text_extractor import get_expression_body
import unicodedata
import sys

from wordcloud import WordCloud
import spacy

nlp = spacy.load("fr_core_news_lg")


#remove pontuation and stop word and return a dictionary with word and count
def clean_text(text):
  doc = nlp(text)

  bag = {}

  for token in doc:
    #count if the token is a stop word or a punctuatuon 
    if not token.is_stop and not token.is_punct and not token.is_space:
      #lower_token = token.text.lower()

      #LEMMATIZATION
      lower_token = token.lemma_.lower()
      count = bag.get(lower_token, 0)
      bag[lower_token] = count + 1

  return bag
  

# Utility function to plot the wordcloud using matplotlib
def plot_cloud(wordcloud):
  fig, ax = plt.subplots(1,1, figsize = (9,6))
  plt.imshow(wordcloud, interpolation='bilinear')
  plt.axis("off")
  plt.show()

# Default options for wordcloud generation
WORDCLOUD_OPTIONS = {
    "background_color": "white",
    "color_func": lambda *args, **kwargs: "black",
    "random_state": 8,
    "normalize_plurals": False,
    "width": 1024,
    "height": 768,
    "max_words": 300,
    "stopwords": []
}

def get_top_word(bag, int):
  return sorted(bag, key=bag.get, reverse=True)[:int]


#url = "https://www.radiofrance.fr/franceinter/podcasts/l-edito-culture/l-edito-culture-du-mardi-31-octobre-2023-3337100"
#url= "https://www.larousse.fr/dictionnaires/francais/test/77497"
url = sys.argv[1]
text = get_expression_body(url)
#print(text)

bag = clean_text(text)

wordcloud_v2 = WordCloud(**WORDCLOUD_OPTIONS)

wordcloud_v2.generate_from_frequencies(bag)

plot_cloud(wordcloud_v2)
print(get_top_word(bag, 8))
