import sqlite3

db = sqlite3.connect("test.db")
def ital_feltoltes(ital_name, category, ar):
    sqlcode = "INSERT INTO italok(name, ar, cat_id) VALUES(?,?,?)"
    cursor  = db.cursor()
    cursor.execute(sqlcode,(ital_name,ar,category))
    db.commit()



def main():

    print("Italfeltöltés\n")
    while True:
        ital_name = input("Add meg az ital nevet: ")
        ar = int(input("Add meg az arat: "))
        cat_id  = int(input("Add meg a kategoriat: "))

        ital_feltoltes(ital_name,cat_id,ar)

        end = input("Szeretned folytatni? (y/n): ")
        if end == "n":
            break
main()

