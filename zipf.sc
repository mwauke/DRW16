import io.Source

@main
def zipf(f: String) {
//get lines from a file
val lines = Source.fromFile(f).getLines.toVector

println("Read " + lines.size + " lines.")

val words = lines.mkString.split("\\W").filterNot(_.isEmpty)

println("Read " + words.size + " words")

val groupedWords = words.groupBy(w => w)

val wordfreqs = groupedWords.map { case (k,v) => (k,v.size) }
//make a sequence out of the map and sort by the second part
// ie, the value, not the key
val sorted = wordfreqs.toSeq.sortBy(_._2)

for (wordfreqs <- sorted) {
  println(wordfreqs)
  }
}
