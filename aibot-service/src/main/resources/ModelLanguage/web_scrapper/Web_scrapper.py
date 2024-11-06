import base64
import gzip
import os
import brotli
import zlib
from io import BytesIO
import random
import re
import string
from urllib.parse import urlparse, urljoin
import requests
from bs4 import BeautifulSoup
import CSV_Reader
import sys

class Web_scrapper:
    def __init__(self, directory_path, limit):
        self.directory_path = directory_path
        self.visited_links = set()
        self.limit = limit
        from pathlib import Path

        current_dir = Path(__file__).parent

        url_therm_filter_file = current_dir / 'url_therm_filter.csv'
        string_therm_filter_file = current_dir / 'string_therm_filter.csv'
        self.exclude_url_therms = CSV_Reader.read_csv_column(url_therm_filter_file, "exclude")
        self.include_url_therms = CSV_Reader.read_csv_column(url_therm_filter_file, "include")
        self.exclude_string_therms = CSV_Reader.read_csv_column(string_therm_filter_file, "exclude")

    def is_compressed(self, content_encoding):
        return content_encoding in ['br', 'gzip', 'deflate']
    
    def get_decompressed_content(self, url):
        headers = {
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36',
                'Accept-Language': 'en-US,en;q=0.9',
                'Accept-Encoding': 'gzip, deflate, br',
                'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                'Connection': 'keep-alive'
            }

        response = requests.get(url, headers=headers)
        if response.status_code != 200:
            print(f"\033[91m Error {response.status_code} request {url}\033[00m")

        if not self.is_compressed(response):
            return response.content.decode(response.encoding)
        
        content_encoding = response.headers.get('Content-Encoding', '').lower()
        compressed_content = response.content

        # Détection du type de compression et décompression
        try:
            if 'br' in content_encoding:
                print("Décompression avec Brotli...")
                decompressed_content = brotli.decompress(compressed_content)
            elif 'gzip' in content_encoding:
                print("Décompression avec Gzip...")
                with gzip.GzipFile(fileobj=BytesIO(compressed_content)) as gz:
                    decompressed_content = gz.read()
            elif 'deflate' in content_encoding:
                print("Décompression avec Deflate...")
                decompressed_content = zlib.decompress(compressed_content)
            else:
                print("Pas de compression détectée...")
                decompressed_content = compressed_content
        except brotli.error as e:
            print(f"Erreur lors de la décompression Brotli: {e}")
            return None

        return decompressed_content.decode(response.encoding)

    def get_texts(self, url, tags=['p', 'div']):
        try:
            
            html = self.get_decompressed_content(url)
            
            soup = BeautifulSoup(html, "html.parser")

            extracted_text = []
            for tag in tags:
                elements = soup.find_all(tag)
                for element in elements:
                    extracted_text.append(element.get_text())

            final_text = "\n".join(extracted_text)
            return final_text
        except Exception as e:
            print(f"Error fetching texts from {url}: {e}")
            return ""

    def get_links(self, url):
        
        try:
            html = self.get_decompressed_content(url)
            #print(f"\033[93m {html}\033[00m")
            #test = base64.b64decode(response.content)
            #print(test)
            soup = BeautifulSoup(html, "html.parser")
            
            extracted_links = []
            tags = ['a']

            for tag in tags:
                #print("Search links")
                elements = soup.find_all(tag)
                
                for element in elements:
                    #print(f"\033[93m Found link in firsy page : {element}\033[00m")
                    href = element.get('href')
                    if href:
                        full_url = urljoin(url, href)
                        extracted_links.append(full_url)
            return extracted_links
        except Exception as e:
            print(f"\033[91m Error fetching links from {url}: {e}\033[00m")
            return []

    def skip_link(self, link):
        for str in self.include_url_therms:
            if str not in link:
                return False
        for str in self.exclude_url_therms:
            if str in link:
                return False
        return True

    def scan_site_map(self, url):
        links = self.get_links(url)
        for link in links:
            if self.limit > len(self.visited_links) and link not in self.visited_links and self.skip_link(link):
                print(f"\033[92m {link}\033[00m")
                self.visited_links.add(link)
                self.scan_site_map(link)
        return list(self.visited_links)

    def generate_random_word(self):
        # Utiliser uniquement des lettres minuscules
        letters = string.ascii_lowercase
        return ''.join(random.choice(letters) for _ in range(random.randint(5, 20)))

    def get_url_name(self, url):
        parsed_url = urlparse(url)
        path = parsed_url.path
        name = os.path.basename(path).replace(".html", ".txt") if path else "index.txt"
        if name is None:
            name = self.generate_random_word()

        if ".txt" not in name:
            name = name + ".txt"
        name = name.replace(".txt", self.generate_random_word() + ".txt")
        return name
    
    def clear_text(self, page_content):
        for regex in self.exclude_string_therms:
            page_content = re.sub(regex, ' ', page_content)
        page_content = page_content.replace('\r\n', '\n').replace('\r', '\n')
        page_content = re.sub(r'\n\s*\n+', '', page_content)
        return page_content

    def scrap(self, url):
        print("\033[93m START SCAN\033[00m")
        self.visited_links = set()  
        urls = self.scan_site_map(url) 
        #continue_choice = input("Write Files ? (Yes) : ").strip().lower()
        
        #if continue_choice != 'Yes':
         #   sys.exit()
        count = 0
        for page in urls:
            page_name = self.get_url_name(page)
            page_content = self.get_texts(page)
            page_content = self.clear_text(page_content)
            if len(page_content) > 200:
                count += 1
                self.write_file(page_name, page_content)
        print(f"\033[93m {count} File scrapperd\033[00m")

    def write_file(self, name, content):
        file_path = os.path.join(self.directory_path, name)
        try:
            with open(file_path, 'w', encoding='utf-8') as file:
                file.write(content)
        except Exception as e:
            print(f"\033[91m Error writing to file {file_path}: {e}\033[00m")
