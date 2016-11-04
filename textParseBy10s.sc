import scala.xml._
import scala.io.Source
import java.io._

@main
def script(s: String) = {

  val scholiaEx = scala.io.Source.fromFile(s).getLines.toVector


  val filteredArray = scholiaEx.map(s => s.split("\t")).filter(_.size.toString.contains("2"))

  val justSchol = filteredArray.map( a => a(1))

  val wordVector = justSchol.map(_.split("[\\\\>   ,\\[\\]\\.·⁑:\"·]+").filterNot(_.isEmpty))

  val allWords = wordVector.flatten

  val filteredWords = allWords.filterNot(_.matches("[A-Za-z0-9]+")).filterNot(_.contains("urn"))

  val uniqueWords = filteredWords.groupBy( w => w).map(_._1)
  val sortedWords = uniqueWords.toVector.sorted

  // integer number of tens to process:
  val total10s = sortedWords.size / 10



  println("For total words " + sortedWords.size)
  for ( i <-  0 to total10s - 1) {

    println("Look at decade " + i)
    val tenWords = sortedWords.drop(i * 10).take(10)
    val fName = "decade" + i + ".tsv"

    val parsedResults = tenWords.map( w => {
      val analysis = parse(w)
      w + "\t" + analysis.mkString(" ")
    })


    val pw = new PrintWriter(new File(fName))
    pw.write(parsedResults.mkString("\n"))
    pw.close
  }

  val lastWords = sortedWords.drop(total10s * 10)
  val parsedResults = lastWords.map( w => {
    val analysis = parse(w)
    w + "\t" + analysis.mkString(" ")
  })


  val pw = new PrintWriter(new File("lastWords.tsv"))
  pw.write(parsedResults.mkString("\n"))
  pw.close
}

//This is a series of functons taken from Prof. Smtih's spark notebook about how to parse
def lemmaForEntry (nseq: NodeSeq): String = {

  if (nseq.size > 0) {
    nseq(0).text
  } else ""
}

def idForEntry (nseq: NodeSeq): String = {

  if (nseq.size > 0) {
      nseq(0).text.replaceFirst("http://data.perseus.org/collections/urn:cite:perseus:grclexent.","")
  } else ""
}

//def formatEntry(e: Elem) : String = {
def formatEntry(e: Elem): String = {
  val uriGroup = e \ "@uri"
  val uri = idForEntry(uriGroup)
  val headWordList = e \\ "hdwd"
  val headWord = lemmaForEntry(headWordList)
  uri + "_" + headWord

}


def parse (s: String) = {
  val baseUrl = "https://services.perseids.org/bsp/morphologyservice/analysis/word?lang=grc&engine=morpheusgrc&word="
  val request = baseUrl + s


  val morphReply = scala.io.Source.fromURL(request).mkString

  val root = XML.loadString(morphReply)
  val entries = root \\ "entry"

  val lexent = entries.map( e => e match {
    case el: Elem => formatEntry(el)
    case _ => ""

  })


lexent

}
