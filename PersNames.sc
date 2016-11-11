// this script takes a two column tsv file, with the first column containing
// identifiers and the second containing XML text of HMT-edited scholia.
// It isolates the XML, extracts every personal name referred to in the
// comments, and then narrows down the list to the unique names. It then
// this list out.

// import the io and xml libraries in order to import a filr
// and then read the xml
import scala.io.Source
import scala.xml.XML

@main
def getNames(f: String) {

// import the tsv file
val lines = Source.fromFile(f).getLines.toVector
// isolate the XML
val xmlString = lines.map( v => v.split("\t")).map(x => x(1))
// make each xml string into parseable XML using the loadString function
val parseableXML= xmlString.map( string => XML.loadString(string))
// isolate the comment from the lemma (second child of the <div> element)
val comments = parseableXML.map(node => node.child(1))
// extract the identifiers associated with each personal Name
// which is referenced in the scholia comments
val names = comments \\ "persName" \\ "@n"
// narrow down to unique names and make it a vector
val uniqueNames = names.groupBy(n => n).keysIterator.toVector
// print out all the different persName identifiers
for (n <- uniqueNames) {
  println(n)
}
}
