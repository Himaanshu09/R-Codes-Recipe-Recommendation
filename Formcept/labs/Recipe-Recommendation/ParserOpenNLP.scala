import java.io.FileInputStream
import java.io.InputStream
import java.util.HashSet
import java.util.Set
import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.Parse
import opennlp.tools.parser.Parser
import opennlp.tools.parser.ParserFactory
import opennlp.tools.parser.ParserModel
import ParserTest._


object ParserTest1 extends Serializable {
  
  def apply(line: String): (String) = {

    val re = """^([^,]*)""".r
    val line2 = re.findAllIn(line).mkString
    val re2 = """\((.*?)\)""".r 
    val line3 = re2.replaceAllIn(line2,"""""")

    val is = new FileInputStream("/home/intern/en-parser-chunking.bin")
    val model = new ParserModel(is)
    val parser = ParserFactory.create(model)
    var nounPhrases = ""
    var integer = ""
    val topParses = ParserTool.parseLine(line3, parser, 1)
    for (p <- topParses) {
    getNounPhrases(p)

    }
  def getNounPhrases(p: Parse) {
    if (p.getType == "NN" || p.getType == "NNS" || p.getType == "NNP" || 
      p.getType == "NNPS") {
      nounPhrases += p.getCoveredText + " "
    }    
    
    if (p.getType == "CD") {
      integer += p.getCoveredText + " "
    }    

    for (child <- p.getChildren) {
      getNounPhrases(child)
    }
}
val tokens = nounPhrases.split(' ')
val integ = integer.split(' ')
val cut = tokens.take(3).mkString(" ")
val int = integ.take(1).mkString(" ") 
(int + " " + cut)

}
}
