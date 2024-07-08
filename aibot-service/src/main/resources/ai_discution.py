import argparse
import random
import torch
from transformers import AutoTokenizer, AutoModelForCausalLM

class ThreadedConversation:
    def __init__(self, model_name, persona="friendly"):
        self.tokenizer, self.model, self.eos_token_id, self.pad_token_id = self.load_model_and_tokenizer(model_name)
        self.history = []
        self.last_generated_response = ""
        self.persona = persona
    
    def load_model_and_tokenizer(self, model_name):
        tokenizer = AutoTokenizer.from_pretrained(model_name)
        model = AutoModelForCausalLM.from_pretrained(model_name)
        eos_token_id = tokenizer.eos_token_id
        pad_token_id = tokenizer.pad_token_id if tokenizer.pad_token_id is not None else eos_token_id
        return tokenizer, model, eos_token_id, pad_token_id
    
    def add_message_to_history(self, message):
        self.history.append(message)
    
    def is_complete_sentence(self, text):
        endings = [".", "!", "?"]
        return any(text.strip().endswith(e) for e in endings)
    
    def generate_response(self, max_new_tokens=50, temperature=0.7, top_p=0.9, top_k=50):
        context = " ".join(self.history[-3:])  # Consider the last 3 exchanges for context
        
        # Add persona templates if applicable
        if self.persona == "friendly":
            templates = ["Salut ! Ça va bien ?", "Hello ! Comment ça se passe ?", "Hey ! Quoi de neuf ?", "Coucou ! Tout va bien ?"]
            if not self.history:
                context = random.choice(templates)
        
        input_ids = self.tokenizer.encode(context, return_tensors="pt", add_special_tokens=True)
        attention_mask = torch.ones(input_ids.shape, dtype=torch.long)

        max_length = len(input_ids[0]) + max_new_tokens

        output_ids = self.model.generate(
            input_ids,
            attention_mask=attention_mask,
            max_length=max_length,
            temperature=temperature,
            top_p=top_p,
            top_k=top_k,
            pad_token_id=self.pad_token_id,
            eos_token_id=self.eos_token_id,
            no_repeat_ngram_size=2,
            do_sample=True
        )

        generated_text = self.tokenizer.decode(output_ids[0], skip_special_tokens=True)

        if not self.is_complete_sentence(generated_text):
            generated_text += "..."

        self.last_generated_response = generated_text.strip()
        self.history.append(self.last_generated_response)
        return self.last_generated_response

def main():
    parser = argparse.ArgumentParser(description="Generate text using a pre-trained language model with threaded conversation.")
    parser.add_argument("input_text", type=str, help="The input text to start the conversation.")
    parser.add_argument("--model_name", type=str, default="microsoft/DialoGPT-medium", help="The name of the model to use.")
    parser.add_argument("--persona", type=str, default="friendly", choices=["friendly", "default"], help="The persona for generating responses.")
    parser.add_argument("--max_new_tokens", type=int, default=50, help="The maximum number of new tokens to generate.")
    parser.add_argument("--temperature", type=float, default=0.7, help="The temperature parameter for sampling.")
    parser.add_argument("--top_p", type=float, default=0.9, help="The top-p parameter for nucleus sampling.")
    parser.add_argument("--top_k", type=int, default=50, help="The top-k parameter for sampling.")

    args = parser.parse_args()

    conversation = ThreadedConversation(args.model_name, args.persona)
    conversation.add_message_to_history(args.input_text)

    while True:
        generated_response = conversation.generate_response(
            max_new_tokens=args.max_new_tokens,
            temperature=args.temperature,
            top_p=args.top_p,
            top_k=args.top_k
        )
        print(f"AI: {generated_response}")

        user_input = input("You: ").strip()
        if user_input.lower() in ["exit", "quit"]:
            break
        conversation.add_message_to_history(user_input)

if __name__ == "__main__":
    main()
