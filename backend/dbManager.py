import sqlite3
from typing import List

#TODO kell egy uj db manager a phpmyadmin mysql serverhez
def createCursor():
    db = sqlite3.connect("test.db")
    return db.cursor(),db


def listUsers() -> List[tuple]:
    cur = createCursor()[0]
    data = cur.execute("SELECT * FROM test").fetchall()
    return data
    pass
def addUser(username, password):
    cur,db = createCursor()
    cur.execute("INSERT INTO test (name,password) VALUES(?, ?)", (username,password))
    db.commit()
if __name__ == "__main__":
    print(listUsers())
    pass