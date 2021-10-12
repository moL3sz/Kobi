from logging import debug
from os import abort
from flask import Flask, request, url_for
from security.hash import compareHashPassword, hashPassword
from security.tokenAuth import loginUser
from security.tokenAuth import hasToke
from dbManager import addUser
import json



app = Flask(__name__)



#endpoints
@app.route("/get_drinks/<category>",methods=["GET"])
def getDrinks(category):
    try:

        #token  = request.form["token"]
        #if not hasToke(token):
        #   abort(403)


        print(category)
        drinkJSON = json.load(open("test_drinks.json","r"))
        return str(drinkJSON)
        #return "202"
        pass
    except Exception as e:
        raise e
    return "200"





@app.route("/")
def index():
    return "<h1>Szia lajos</h1>"
@app.route("/login",methods=["POST"])
def login():
    try:
        pass
        print(request.form)
        username = request.form["username"]
        password = request.form["password"]
        print(username,password)
        return "200" if loginUser("asf",username,password) else "403"
    except Exception as e:
        abort(500)
    return "401"
    pass

@app.route("/registrate",methods=["POST"])
def registrate():
    try:

        username = request.form["username"]
        #email = request.form["emailAddress"]
        password = request.form["password"]
        hashedPassword = hashPassword(password)
        #TODO save the credentials into the database
        print(username,password)
        #
        
        addUser(username,hashedPassword)
        return "200"

        pass
    except Exception as e:
        raise e
        return "500"
    pass
    return "200"
