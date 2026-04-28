import requests
import csv
import time

from selenium import webdriver
from selenium.webdriver.common.by import By
from bs4 import BeautifulSoup
from selenium.webdriver.chrome.options import Options

options = Options()
# options.add_argument("--headless")
# options.add_argument("--disable-gpu")
options.add_experimental_option(
    "prefs",
    {
        "profile.managed_default_content_settings.images": 2,
        "profile.managed_default_content_settings.stylesheets": 2,
        "profile.managed_default_content_settings.fonts": 2,
    }
)

LISTINDEX = 50
driver = webdriver.Chrome(options=options)


def get_nutrition_info(url):
    driver.get(url)
    time.sleep(1)
    label_items = driver.find_elements(By.CLASS_NAME, "label-item")
    for item in label_items:
        metricobj = item.find_element(By.CLASS_NAME, "metric")
        metric = metricobj.text
        # valueobj = item.find_element(By.CLASS_NAME, "value")
        sronly = item.find_element(By.CLASS_NAME, "sr-only")
        value = sronly.text
        only_num_value = ""
        for ch in value:
            if ch.isdigit() or ch == ".":
                only_num_value += ch
            else:
                break
        if metric == "Calories:":
            calories.append(only_num_value)
        elif metric == "Saturated Fat:":
            saturated_fat.append(only_num_value)
        elif metric == "Trans Fat:":
            trans_fat.append(only_num_value)
        elif metric == "Sugar:":
            sugar.append(only_num_value)
        elif metric == "Protein:":
            protein.append(only_num_value)
        elif metric == "Sodium:":
            sodium.append(only_num_value)


url = requests.get("https://www.mcdonalds.com/ca/en-ca/full-menu.html")
soup = BeautifulSoup(url.text, "html.parser")
items = soup.find_all(
    "div", attrs={"class": "cmp-category__item-name"})
itemlinks = soup.find_all("a", class_="cmp-category__item-link")

href = ["https://www.mcdonalds.com" + link.get("href") for link in itemlinks]
itemname = [item.text for item in items]
href_slice = href[8:len(href)]
itemname_slice = itemname[8:len(itemname)]

items_test = itemname_slice[0:LISTINDEX]
links_test = href_slice[0:LISTINDEX]

calories = []
saturated_fat = []
trans_fat = []
sugar = []
protein = []
sodium = []

for link in href_slice:
    get_nutrition_info(link)
driver.quit()

file = open("MCDscrape.csv", "w")
writer = csv.writer(file)
writer.writerow(["item", "calories", "saturated fat (g)",
                "trans fat (g)", "sugar (g)", "protein (g)", "sodium (mg)"])
for item, cal, sat, tra, sug, pro, sod in zip(itemname_slice, calories, saturated_fat, trans_fat, sugar, protein, sodium):
    # print(item + "-" + link)
    writer.writerow([item, cal, sat, tra, sug, pro, sod])
