import json;
import markovify;
from text_data_handler import get_texts

training_text = get_texts('d:/spring-aungular/TalesTrade/aibot-service/src/main/resources/ModelLanguage/training_text/science_fiction_gpt')

texts = " ".join(training_text)

model = markovify.Text(texts, 4)

cnt = 4
print("process ...")

while cnt > 0:
    sent = model.make_short_sentence(2000)
    if(sent):
        print(sent)
        cnt -= 1