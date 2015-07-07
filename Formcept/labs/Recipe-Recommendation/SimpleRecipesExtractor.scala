package simplyRecipes
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor
import scala.util.matching.Regex
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

/* Processing Logic */

object Process extends Serializable{
  def apply(filename: String): org.apache.spark.rdd.RDD[(String)]= {

    //SparkContext
    val sc = new SparkContext(new SparkConf().setAppName("Recipe_Extraction"))   

    // Each input file converted into RDD
    val b = sc.textFile(filename)

    // RDD converted to string for boilerpipe type 
    val c = b.toLocalIterator.mkString

    // Boilerpipe function to convert HTML into readable content
    val d = KeepEverythingExtractor.INSTANCE.getText(c)

    // Recieved string converted back to RDD splitted by newline 
    val e = sc.parallelize(d.split("\n"))

    // Recipe Extraction logic by transformations and actions
    val recipeName = e.take(10).last
    val recipeRDD = sc.parallelize(List(recipeName))
    val prepTime = e.take(18).last
    val prepRDD = sc.parallelize(List(prepTime))
    val cookTime = e.take(19).last
    val cookRDD = sc.parallelize(List(cookTime))

    // Recipe Extraction logic by REGEX
    val re = """(?s).*?Ingredients(.*?)Method.*""".r
    val ingredients = re.replaceAllIn(d,"""$1""")
    val ingRDD = sc.parallelize(List(ingredients))

    val re2 = """(?s).*?Method(.*?)Print.*""".r
    val method = re2.replaceAllIn(d,"""$1""")
    val methodRDD = sc.parallelize(List(method))

    // Union of all extracted RDD's into a single RDD
    val rdds = Seq(recipeRDD,prepRDD,cookRDD,ingRDD,methodRDD)
    val bigRDD = sc.union(rdds)

    // Limiting the no. of partitions of output to 1
    val result = bigRDD.coalesce(1)

    // return back final RDD
    (result)
  }
}




    

