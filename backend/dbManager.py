import sqlite3
from typing import List

#TODO kell egy uj db manager a phpmyadmin mysql serverhez


#


def listTokens():
    pass
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
def getUserByUsername(username):
    cur,db = createCursor()
    currentUser = cur.execute("SELECT * FROM felhasznalok WHERE username = ?",(username,))
    return currentUser if currentUser else None

def addToken(token,username):
    #check if user has exists with token
    currentUser = getUserByUsername(user)
    if currentUser:
        if currentUser[4] == "":
            pass
        if currentUser  


    else:
        return
    cur,db =createCursor()
    cur.execute("UPDATE felhasznalok SET token=? WHERE username=?", (token,username))

if __name__ == "__main__":
    print(listUsers())
    pass