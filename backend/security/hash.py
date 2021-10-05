from hashlib import sha256


def hashPassword(password : str):
    hh = sha256()
    hh.update(password.encode("utf-8"))
    return hh.hexdigest()


def compareHashPassword(hashedPassword : str, plainPassword : str) -> bool:
    return hashedPassword == hashPassword(plainPassword)
