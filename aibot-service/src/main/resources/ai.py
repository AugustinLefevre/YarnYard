import sys
import torch
from transformers import AutoTokenizer, AutoModelForCausalLM

# Utiliser un modèle GPT-2 fine-tuné pour le français
#model_name = "dbddv01/gpt2-french-small"
#model_name = "EleutherAI/gpt-neo-1.3B"
#model_name = "gpt2"
#model_name = "distilgpt2"
model_name= "asi/gpt-fr-cased-small"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForCausalLM.from_pretrained(model_name)

# Assigner les IDs corrects pour eos_token_id et pad_token_id
eos_token_id = tokenizer.eos_token_id
pad_token_id = tokenizer.pad_token_id if tokenizer.pad_token_id is not None else eos_token_id

def generate_text(input_text, max_length=150, num_return_sequences=1, temperature=0.7, top_p=0.9, top_k=50):
    input_ids = tokenizer.encode(input_text, return_tensors="pt", add_special_tokens=True)

    # Création de l'attention mask
    attention_mask = torch.ones(input_ids.shape, dtype=torch.long)

    # Génération du texte avec les paramètres ajustés
    output_ids = model.generate(
        input_ids,
        attention_mask=attention_mask,
        max_length=max_length,
        num_return_sequences=num_return_sequences,
        temperature=temperature,
        top_p=top_p,
        top_k=top_k,
        pad_token_id=pad_token_id,
        eos_token_id=eos_token_id,
        no_repeat_ngram_size=2,  # Pour éviter la répétition des n-grams
        do_sample=True  # Activer le mode d'échantillonnage
    )

    # Décoder la sortie
    generated_text = tokenizer.decode(output_ids[0], skip_special_tokens=True)
    return generated_text

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python ai.py 'your input text'")
        sys.exit(1)

    input_text = sys.argv[1]
    generated_text = generate_text(input_text)
    print(generated_text)