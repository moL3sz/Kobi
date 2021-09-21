from logging import debug
from flask import Flask, request, url_for
from security.hash import compareHashPassword, hashPassword
from security.tokenAuth import loginUser
from dbManager import addUser




app = Flask(__name__)

#endpoints


@app.route("/login",methods=["POST"])
def login():
    try:
        pass
        username = request.form["username"]
        password = request.form["password"]
        return "200" if loginUser("asf",username,password) else "403"
    except Exception as e:
        raise e
    return "401"
    pass


@app.route("/dominik",methods=["GET"])
def jaja():
    return "Hello szia szevasz"


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
if __name__ == "__main__":
    app.run(debug=True,host="172.22.8.68", port=4000)
