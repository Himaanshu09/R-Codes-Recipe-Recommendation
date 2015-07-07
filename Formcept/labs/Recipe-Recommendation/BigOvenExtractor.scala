package bigOven
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf    
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor
import scala.util.matching.Regex

object Process extends Serializable{
  def apply(filename: String): org.apache.spark.rdd.RDD[(String)]= {
    val sc = new SparkContext(new SparkConf().setAppName("Recipe_Extraction"))   

    val b = sc.textFile(filename)
    val c = b.toLocalIterator.mkString
    val d = KeepEverythingExtractor.INSTANCE.getText(c)

    val e = sc.parallelize(d.split("\n"))

    val recipeName = e.take(15).last
    val recipeRDD = sc.parallelize(List(recipeName))

    val timeRegex = """Ready in [0-9] hour""".r
    val timeString = timeRegex.findFirstIn(d).mkString
    val time = timeString.replaceAll("[^-?0-9]+", " ")
    val timeRDD = sc.parallelize(List(time))
   
    val re = """^(?s).*?Are you making this\?(.*?)Original recipe makes [0-9].*$""".r
    val ingredients = re.findFirstMatchIn(d) match {

    case Some(m)  => m.group(1).mkString
    case None => print("")}
    val ingredients1 = ingredients.toString
    val ingRDD = sc.parallelize(List(ingredients1))

    val re2 = """(?s).*?Preparation(.*?)(?:Notes|Credits).*""".r
    val method = re2.replaceAllIn(d,"""$1""")
    val methodRDD = sc.parallelize(List(method))

    val rdds = Seq(recipeRDD,timeRDD,ingRDD,methodRDD)
    val bigRDD = sc.union(rdds)

    val result = bigRDD.coalesce(1)
    (result)
}
}
