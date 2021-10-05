import sqlite3

db = sqlite3.connect("test.db")
def ital_feltoltes(ital_name,category, ar):
    sqlcode = "insert into italok(name,ar,cat_id) values (?,?,?)"
    cursor = db.cursor()
    cursor.execute(sqlcode,(ital_name,ar,category))
    db.commit()

def main():
#  input("Hozzáadás vagy törlés (1/2)? ")
#if input == "1":    
    print("Italfeltöltés\n")
    while True:
        ital_name = input("Add meg az ital nevét: ")
        ar = int(input("Add meg az árát: "))
        cat_id = int(input("Add meg a kategóriát: "))
        
        ital_feltoltes(ital_name,cat_id,ar)

        end = input("Szeretnéd folytatni? (y/n): ")
        if end == "n":
            break        
main()