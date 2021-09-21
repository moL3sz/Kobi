from dbManager import *
from .hash import *


def loginUser(token : str, username, password : str) -> bool:
    users = listUsers()
    """
    for user in users:
        ##token, username, password
        if user[4] == token and user[1] == username and compareHashPassword(user[2],password):
            return True
    return False"""
    for user in users:
        if user[1] == username and compareHashPassword(user[2],password):
            return True
    return False

def hasToke(token_):
    tokens = listTokens()
    for token in tokens:
        if token == token_:
            return True
    return False



 






