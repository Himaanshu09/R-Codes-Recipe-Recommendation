library(boilerpipeR)
library(RCurl)
url <- "http://www.taste.com.au/recipes/28864/five+spiced+eggplant+with+tofu+and+chinese+greens?ref=collections,tofu-recipes"
content1s <- getURL(url)
extract<-KeepEverythingExtractor(content1s)
writeLines(extract,"1.txt")
#For Ingredients Extraction 
j=1000
i=1 
a<-paste(i,".txt",sep="")  
filename<-readChar(a, file.info(a)$size)
b<-paste(j,".txt",sep="")
{if(grepl("Energy",filename))
{writeLines(gsub(".*Ingredients\nNutrition\\s*|Energy.*", "", filename),b)
}
else  {writeLines(gsub(".*Ingredients\n\\s*|Select all.*", "", filename),b)
}}
#For Method 
{if(grepl("Notes\n",filename))
{writeLines(gsub(".*Notes\n\\s*|Find recipes*", "", filename),b)
}
else  {writeLines(gsub(".*Method\n\\s*|Find recipes.*", "", filename),b)}}
#for Servings
b<-paste("Servings",i,".txt",sep="")
{if(grepl("SERVINGS\n",filename))
{writeLines(toString(regmatches(filename, gregexpr("\\bDIFFICULTY\\s*\\K.*(?=\\n+.*SERVINGS\\b)", filename, perl=TRUE))),b)}
else  {writeLines(toString(regmatches(filename, gregexpr("\\bDIFFICULTY\\s*\\K.*(?=\\n+.*MAKES\\b)", filename, perl=TRUE))),b)}}
#For To Cook
b<-paste("To Cook",i,".txt",sep="")
writeLines(toString(regmatches(filename, gregexpr("\\bTo Prep\\s*\\K.*(?=\\n+.*To Cook\\b)", filename, perl=TRUE))),b)
#For to Prep
b<-paste("To Prep",i,".txt",sep="")
writeLines(toString(regmatches(filename, gregexpr("\\b\\b.*\\n+\\s*\\K.*(?=\\n+.*To Prep\\b)", filename, perl=TRUE))),b)
#For title
b<-paste("Title",i,".txt",sep="")
writeLines(toString(regmatches(filename, gregexpr("\\bTrifle\\b.*\\n+\\K.*(?=\\n+.* Comment|Comments \\b)", filename, perl=TRUE))),b)