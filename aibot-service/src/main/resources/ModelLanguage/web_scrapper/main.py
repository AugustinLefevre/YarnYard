import sys
from Web_scrapper import Web_scrapper

if len(sys.argv) < 2:
        print("Usage: python main.py 'url' 'directory path")
        sys.exit(1)
url = sys.argv[1]
directory_path = sys.argv[2]
limit = sys.argv[3]
print(f"url : {url}")
print(f"directory_path : {directory_path}")

ws = Web_scrapper(directory_path, int(limit))
ws.scrap(url)
