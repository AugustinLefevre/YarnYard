import subprocess
import sys

def install_package(package):
    subprocess.check_call([sys.executable, "-m", "pip", "install", package])

install_package("numpy")
install_package("pandas")
install_package("beautifulsoup4")
install_package("wordcloud")
install_package("spacy")
install_package("matplotlib")
install_package("sentence-transformers")
install_package("pysbd")

def install_spacy_model():
    subprocess.check_call([sys.executable, "-m", "spacy", "download", "fr_core_news_lg"])

install_spacy_model()