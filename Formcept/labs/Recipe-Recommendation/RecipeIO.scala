package recipeIO
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object RecipeIO {

val sc = new SparkContext(new SparkConf().setAppName("Recipe_Extraction"))   

def read(INPUT_PATH: String): org.apache.spark.rdd.RDD[(String)]= {
 

 val data = sc.wholeTextFiles(INPUT_PATH)
 val files = data.map { case (filename, content) => filename}
 (files)

 }



def write(OUTPUT_PATH: String, output: org.apache.spark.rdd.RDD[(String)]) = {

        
 output.saveAsTextFile(OUTPUT_PATH);

 }
}



            
