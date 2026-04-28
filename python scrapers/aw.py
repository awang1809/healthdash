import csv
import time
import requests

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

LISTINDEX = 5
driver = webdriver.Chrome(options=options)


def get_nutrition_info(url):
    driver.get(url)
    time.sleep(1)
    nowrap = driver.find_elements(By.CLASS_NAME, "nutrition")
    for item in nowrap:
        itemprop = item.get_attribute("itemprop")
        text = item.text
        only_num_value = ""
        for ch in text:
            if ch.isdigit() or ch == ".":
                only_num_value += ch
            else:
                break
        if itemprop == "calories":
            calories.append(only_num_value)
        elif itemprop == "proteinContent":
            protein.append(only_num_value)
    button = driver.find_element(By.CLASS_NAME, "icon")
    driver.execute_script("arguments[0].scrollIntoView(true);", button)
    driver.execute_script("arguments[0].click();", button)
    time.sleep(1)
    content = driver.find_element(By.CLASS_NAME, "nutrients")
    lists = content.find_elements(By.TAG_NAME, "li")
    for lst in lists:
        text = lst.text
        print(text)
        only_num_value = ""
        for ch in text:
            if ch.isdigit() or ch == ".":
                only_num_value += ch
        if "Sugar" in text:
            sugar.append(only_num_value)
        elif "Trans Fat" in text:
            trans_fat.append(only_num_value)
        elif "Saturated Fat" in text:
            saturated_fat.append(only_num_value)
        elif "Sodium" in text:
            sodium.append(only_num_value)


def get_all_items(url):
    driver.get(url)
    items = driver.find_elements(By.CLASS_NAME, "item")
    itemlinks = [item.get_attribute("href") for item in items]
    itemnames = [item.get_attribute("title") for item in items]
    linksfilter = filter(lambda x: "our-values" not in x, itemlinks)
    linksfilter = filter(lambda x: "locations#brewbar" not in x, linksfilter)
    itemsfilter = filter(lambda x: "Learn More" not in x, itemnames)
    [allitemnames.append(name) for name in itemsfilter]
    [allitemlinks.append(link) for link in linksfilter]


driver.get("https://web.aw.ca/en/our-menu")
categorymenu = driver.find_element(By.CLASS_NAME, "category-menu")
menus = categorymenu.find_elements(By.TAG_NAME, "a")
links = [menu.get_attribute("href") for menu in menus]

allitemnames = []
allitemlinks = []

for link in links:
    get_all_items(link)

calories = []
saturated_fat = []
trans_fat = []
sugar = []
protein = []
sodium = []

itemstest = allitemnames[0:LISTINDEX]
linkstest = allitemlinks[0:LISTINDEX]

for link in allitemlinks:
    get_nutrition_info(link)
driver.quit()

file = open("AWscrape.csv", "w")
writer = csv.writer(file)
writer.writerow(["item", "calories", "saturated fat (g)",
                "trans fat (g)", "sugar (g)", "protein (g)", "sodium (mg)"])
for item, cal, sat, tra, sug, pro, sod in zip(allitemnames, calories, saturated_fat, trans_fat, sugar, protein, sodium):
    # print(item + "-" + link)
    writer.writerow([item, cal, sat, tra, sug, pro, sod])

print(len(allitemnames))
print(len(calories))
print(len(protein))
print(len(saturated_fat))
print(len(trans_fat))
print(len(sodium))
print(len(sugar))