
library(XML)
## here input is the string you want to enter like 12 cloves of garlic
getURL1<-function(input,format="plaintext"){
  root<- "http://api.wolframalpha.com/v2/query?appid=JWGRV2-5VLQ7JR2PV"
  u<-paste(root,"&input=",input,"&format=html")
  appid<-toString("JWGRV2-5VLQ7JR2PV")
  input<-toString("12 cloves of garlic")
  return(URLencode(u))
}
ex<-xmlParse(u)