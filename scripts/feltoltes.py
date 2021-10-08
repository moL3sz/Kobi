from os import name, terminal_size
import sqlite3

db = sqlite3.connect("test.db")
def ital_feltoltes(ital_name,category, ar):
    sqlcode = "insert into italok(name,ar,cat_id) values (?,?,?)"
    cursor = db.cursor()
    cursor.execute(sqlcode,(ital_name,ar,category))
    db.commit()

def adatmodositas(oszlop,mire,id):
    sqlcode = "update italok set {} = ? where id = ?".format(oszlop)
    cursor = db.cursor()
    cursor.execute(sqlcode,(mire,id))
    db.commit()  

def sortorles(id):
    sqlcode = "delete from italok where id = ?"
    cursor = db.cursor()
    cursor.execute(sqlcode,(id))
    db.commit()  

def kieg_feltoltes(kieg_name):
    sqlcode = "insert into kiegeszitok(kieg_name) values (?)"
    cursor = db.cursor()
    cursor.execute(sqlcode,(kieg_name))
    db.commit()

def main():
        answer = input("Válassz opciót! (1 Italfeltöltés, 2 Adatmódosítás, 3 Törlés)\n")       
        if answer == "1":
            print("Italfeltöltés\n")
            while True:
                ital_name = input("Add meg az ital nevét: ")
                ar = int(input("Add meg az árát: "))
                cat_id = int(input("Add meg a kategóriát: "))
                    
                ital_feltoltes(ital_name,cat_id,ar)

                end = input("Szeretnéd folytatni? (y/n): ")
                if end == "n":
                    break 
        elif answer == "2":
            print("Adatmódosítás\n")
            while True:
                oszlop = input("Melyik oszlop adatát szeretnéd módosítani?")
                mire = input("Mire?")
                id = int(input("Hanyas id?"))

                adatmodositas(oszlop,mire,id)

                end = input("Szeretnéd folytatni? (y/n): ")
                if end == "n":
                    break 

            
        elif answer == "3":
            print("Törlés")
            while True:
                id = (input("Melyik sort szeretnéd törölni?"))

                sortorles(id)

                end = input("Szeretnéd folytatni? (y/n): ")
                if end == "n":
                    break 
                
        elif answer == "4":
            print("Új kiegészítő")
            while True:
                kieg_name = (input("Adja meg a kiegészítő nevét"))

                kieg_feltoltes(kieg_name)

                end = input("Szeretnéd folytatni? (y/n): ")
                if end == "n":
                    break
        
        else:
            exit

main()