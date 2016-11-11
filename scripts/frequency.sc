import io.Source

@main
def frequency(f: String) {
// count number of characters
val characters = Source.fromFile(f).size
println("Number of characters in file: " + characters)

val lines = Source.fromFile(f).getLines.toVector
// split between words and filter out empties
val words = lines.mkString.split("\\W").filterNot(_.isEmpty)
println("Number of words in file: " + words.size)

// group words
val distinctWords = words.groupBy(w => w)
println("Number of distinct words in file: " + distinctWords.size)

// divide into sentences
val sentences = lines.mkString.split("\\.")
println("Number of sentences in file: " + sentences.size )

}
