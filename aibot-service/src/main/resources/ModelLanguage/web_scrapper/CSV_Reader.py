import csv

def read_csv_column(file_path, column_name):
    list = []
    
    with open(file_path, mode='r', encoding='utf-8') as file:
        csv_reader = csv.DictReader(file) 
        for row in csv_reader:
            if column_name in row:
                list.append(row[column_name])

    return list
