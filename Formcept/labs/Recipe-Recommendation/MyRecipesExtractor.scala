package myRecipes
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor
import scala.util.matching.Regex
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf


object Process extends Serializable{
  def apply(filename: String): org.apache.spark.rdd.RDD[(String)]= {
    
    val sc = new SparkContext(new SparkConf().setAppName("Recipe_Extraction"))   
    val b = sc.textFile(filename)
    val c = b.toLocalIterator.mkString
    val d = KeepEverythingExtractor.INSTANCE.getText(c)

    val e = sc.parallelize(file2.split("\n"))
    val recipeName = e.take(48).last
    val recipeRDD = sc.parallelize(List(recipeName))
    
    val re = """(?s).*?Add to Shopping List(.*?)Preparation.*""".r
    val ingredients = re.replaceAllIn(d,"""$1""")
    val ingRDD = sc.parallelize(List(ingredients))

    val re2 = """(?s).*?Preparation(.*?)Note:.*""".r
    val method = re2.replaceAllIn(d,"""$1""")
    val methodRDD = sc.parallelize(List(method))

    val rdds = Seq(recipeRDD,ingRDD,methodRDD)
    val bigRDD = sc.union(rdds)

    val result = bigRDD.coalesce(1)
    (result)
