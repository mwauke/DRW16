import io.Source
import $ivy.`com.github.rholder:snowball-stemmer:1.3.0.581.1`
import org.tartarus.snowball.SnowballStemmer
import org.tartarus.snowball.ext.englishStemmer

@main
def stemming(f: String) {

val lines = Source.fromFile(f).getLines.toVector
val words = lines.mkString.split("\\W").filterNot(_.isEmpty)
val stemmer = new englishStemmer()
for (w <- words) {
  stemmer.setCurrent(w)
  stemmer.stem()
  val stemmed = stemmer.getCurrent().toLowerCase
  println(w + "->" + stemmed)
  }
}
