library(boilerpipeR)
library(RCurl)
url <- "http://www.taste.com.au/recipes/28864/five+spiced+eggplant+with+tofu+and+chinese+greens?ref=collections,tofu-recipes"
content1s <- getURL(url)
extract<-KeepEverythingExtractor(content1s)
writeLines(extract,"1.txt")
extract