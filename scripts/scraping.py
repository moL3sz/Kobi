from requests import get
from bs4 import BeautifulSoup
import csv



def writeDrinks(piak):
    with open("piak.csv","w",newline="") as f:
        csvfile = csv.writer(f,delimiter=";")
        for pia in piak:
            csvfile.writerow([pia.nev,pia.alkohol,pia.mennyiseg,pia.ar])




class Pia:
    def __init__(self,nev,alkohol,mennyiseg,ar) -> None:
        self.nev = nev
        self.alkohol = alkohol
        self.mennyiseg = mennyiseg
        self.ar = ar
    def __repr__(self) -> str:
        return ";".join([self.nev,self.alkohol,self.mennyiseg,self.ar])
        


def scrapePage(pagenumber):
    piaclasses = []
    url = "https://idrinks.hu/szeszesital?sort=pd.name&order=ASC&page={}#content".format(pagenumber)

    res = get(url)

    page = BeautifulSoup(res.text, "html.parser")
    pia_container = page.find("div", {"class":"product-snapshot-vertical snapshot_vertical list grid-style snapshot-list-secondary-image list_with_divs"})

    piak = pia_container.find_all("div",{"class":"product-snapshot list_div_item"})


    for pia in piak:
        t = pia.find("div",{"class":"snapshot_vertical_product"}).find("div",{"class":"snapshot-list-item list_prouctname"}).text
        bontott = t.split("[")
        piaNev = bontott[0].strip()
        print(bontott[1])
        try: 
            mennyiseg, alkohol = bontott[1].split("|")
            alkohol = alkohol.split("]")[0]
            ar = pia.find("div",{"class":"snapshot_vertical_product"}).find("div",{"class":"list_prouctprice"}).text.strip()
            pia_ = Pia(piaNev,alkohol,mennyiseg,ar)
            piaclasses.append(pia_)
        except ValueError:
            continue
    writeDrinks(piaclasses)


       
        
    pass


def main():
    n = 74



    for page in range(1,n+1):
        scrapePage(page)
        pass
    pass

main()
