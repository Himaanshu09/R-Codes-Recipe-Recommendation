import requests
import time
import re

data = raw_input("please enter what to search")
#data=data.replace(" ","+")
#print data
try:
	url ="http://api.wolframalpha.com/v2/query"
	for i in range(10):
	    content = requests.get(url, params = {"appid":"JWGRV2-5VLQ7JR2PV","input":str(data),"format":"plaintext"})
	

except Exception as e:
	print e
