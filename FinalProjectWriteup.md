The text that we were working with was the scholia to the Venetus A manuscript of the *Iliad*. These scholia are scholarly footnotes which comment on various aspects of the Iliadic text. Because the Venetus A is the oldest complete manuscript of the *Iliad*, these scholia are especially significant to the history of Homeric scholarship. This manuscript is not just old, but also replete with scholia, many of which have never been fully published. These scholia can occur in five distinct zones: main, intermarginal, interior, interlinear, and exterior. For a better understanding of the layout of this manuscript, follow this [link](http://www.homermultitext.org/hmt-digital/indices?urn=urn%3Acite%3Ahmt%3Avaimg.VA012RN-0013) to the Homer Multitext website.

Because we have both been working with this manuscript for the past three years as part of the Homer Multitext project, this particular corpus is of extreme interest to us. In our research we frequently question what the reasoning was behind this distinct five-zoned layout of the Venetus A. Past scholars have attempted to answer this question, but because of the amount of scholia, (around 8,000 in eighteen books alone) their previous method of close-reading is insufficient for such an analysis. One would be hard pressed to make any intelligible comments about minute linguistic patterns in the scholia over so large and varied a corpus. While we both have our own separate interests in discovering patterns in the scholia, we first wanted to confirm that the differences among the types of scholia could be proven with basic digital technologies. 

The motivation for using topic modeling on this corpus lay in topic modeling's ability to find latent patterns of language usage that are potentially isolated in certain areas of a text. So, hypothetically, should the main scholia and the intermarginal scholia use different vocabulary, a topic model would reveal one topic containing words from the intermarginal scholia and a separate topic with words from the main scholia. 

Because of the size and messiness of our data, we were unable to run a topic model within the spark-notebook environment. Therefore we used Thomas Koentges' ToPan topic modeling software. Before we could run any topic models, we first had to prepare our text for analysis. The text that we used came from the digital edition of the Venetus A which previous members of the Homer Multitext project had created. These digital editions of the scholia are in an XML format, so our first step was to extract the text from the XML. Our first topic model was run on eighteen books of scholia taken directly from the HMT archive, which we cloned for our own purposes. You can find the original, unedited edition of the scholia [here](https://github.com/cjschu17/drdwPortfolio/blob/master/editions/Scholia/rawScholiaWithXML.tsv). The script for extracting the scholia from the XML is [here](????????).

Once we had extracted the text, we then had to parse every individual word. Because the morphology of Greek is much more complex than that of English, stemming was not a viable option for our purposes. Specifically, a successful stemmer requires a language whose words do not alter their root, or stem, very often. Instead, a perfect language for stemming would have words that only change in their endings. English largely fits this mold, but this is far from the truth for ancient Greek, whose words change their root very often. Thus we used the Morpheus parser in order to obtain the dictionary form, or lemma, of each word in the scholia. COME BACK TO WHERE YOU CAN FIND THE VERSIONS/SCRIPTS Were the data to be perfectly clean, this would be the last step needed before topic modeling. However, our data was exceptionally messy.

The Homer Multitext project seeks to create digital *diplomatic* editions. Their editions reflect as closely as possible every intentional mark on each page. As such, not every word in their editions is completely valid according to the Morpheus parser. If a word is blatantly misspelled in the manuscript, for example, the editing team records it as it is. Because of this editing policy, many words in their editions fail to parse in Morpheus. More common than misspellings, however, are what the project refers to as "Byzantine orthographic variants." These refer to the exclusion of any diacritical marks from a word. Without these marks, Morpheus treats these otherwise correctly spelled words as invalid Greek. Thus, our corpus of scholia was cluttered with such orthographic variants which ultimately could not be parsed by Morpheus. We are currently in the process of developing a script that will try to resolve these orthographic variants with a normalized form, but this was not ready at the time of analysis. Moving forward, we recognize the importance of clean data for obtaining conclusions of greater significance. 

Despite the messiness of our data, we had to continue on to analysis. In the folder `scripts/Portfolio3`????? are the scala scripts which were written to prepare the text for analysis using topic modeling in a spark-notebook. Ultimately, as stated above, topic modeling in the spark-notebook was not possible, but the scripts for preparing a stop-word list and chunking the text into individual files containing one scholion each are functional.

So, as mentioned, our analysis was run on the ToPan software. Other than a clean text, ToPan requires a stop-word list. ToPan lets users dictate how many of the most frequently occurring words should be removed from the corpus prior to analysis. In addition, users also have the ability to comb through that list of most frequent words and select which words from that list they wish to keep in the analysis. We had to run many topic models in order to determine what were the optimal number of stop words and topics. Our clearest topic model was run using 15 topics, 5000 iterations (the maximum number), and a stop-word list excluding many of the 255 most common words. For the words which we kept from our stop-word list, go [here](https://github.com/cjschu17/drdwPortfolio/blob/master/editions/Scholia/non-stopwords.txt). To read an in-depth discussion of exactly how we ran these topic models, go [here](https://github.com/mwauke/seniorThesis/blob/master/ToPanWriteup.md).

After running the 15-topic topic model, we then attempted to give human-identifiable labels to each topic. Of the fifteen topics, about ten had clear themes. The others often were a mix of different themes that were unclear to us. There was only one topic which we classified as complete junk. For the list and our analysis of all of these topics, go [here](https://github.com/cjschu17/drdwPortfolio/wiki/Looking-at-the-Data-from-ToPan-Topic-Model---12-6-16). For pictures of each topic from the ToPan visualization, go [here](https://github.com/cjschu17/drdwPortfolio/tree/master/images/ToPanViz12-6-16).

After analyzing the topics, we looked at the theta-table which ToPan also provides. The raw data can be found [here](https://github.com/cjschu17/drdwPortfolio/blob/master/editions/Scholia/thetaTable.tsv). Each row of the theta table corresponds to an individual scholion, while each column corresponds to an individual topic. The cells of the theta-table contain some decimal number which represents the strength of the correlation between a particular scholion and a particular topic. A value closer to 1 indicates a strong correlation, while those closer to 0 indicate a poor correlation. So the numerical weights in each cell across a single row relate how well a particular scholion corresponds to each of the various topics.

As stated above, we were interested in seeing if there was any difference in content among the types of scholia. So to address this question, we extracted all the scholia from the theta-table which had weights of at least 0.9 in any topic. We  grouped these scholia into fifteen distinct tables, one table per topic, such that a single table contained all of the scholia with a score of at least 0.9 for that particular topic. These tables can be found [here](https://github.com/cjschu17/drdwPortfolio/tree/master/editions/Scholia/TableFolder). We added one more layer of analysis by grouping the scholia in each of these tables by their zone, i.e. intermarginal, interlinear, main, etc. In the end, we were able to create a frequency table for each of the topics which breaks down the distribution of scholia zone among those scholia having a weight of 0.9 or greater in that topic. For the results, we direct you back to our analysis wiki [here](https://github.com/cjschu17/drdwPortfolio/wiki/Looking-at-the-Data-from-ToPan-Topic-Model---12-6-16).

With all of the data collection finished, we were finally able to analyze our initial results. When we were first looking through the ToPan visualization for this topic model we noticed that there was a topic which seemed to focus on the Alexandrian editor, Aristarchus. This was [topic 8](https://github.com/cjschu17/drdwPortfolio/blob/master/images/ToPanViz12-6-16/Topic8.png). This is significant for the following reason:

The Alexandrian editors are largely credited with fixing the Iliadic text, and their scholarship and comments concerning what passages would stay in their final editions were transmitted alongside the text. As a result, even a millennium after these Alexandrian editors were working, the Venetus A manuscript contains many scholia which make reference to their disagreements and commentaries. Thus, while it is not surprising that the work of the Alexandrian editors warrants its own topic, the fact that Aristarchus should have his own is noteworthy. At the very least, this shows that the sort of comments and language which coincide with the name "Aristarchus" seems to be definitively different from the sort used with the other Alexandrian editors, Zenodotus and Aristophanes. 

So when we moved into the analysis of the theta-table data, we were particularly interested in seeing how the Aristarchus topic, topic 8, would distribute itself among the zones of scholia. This topic 8 was noticeable for two reasons. First, it had more scholia with a weight greater than 0.9 than any of the other topics. Clearly, this indicates that this is a very strong topic which seems to have a clearly defined vocabulary set associated with it.

Secondly, in terms of this topic's distribution among the scholia zones, we found it to be disproportionately associated with the intermarginal scholia. 59.1% of the scholia with a 0.9 or greater weight in topic 8 were from the intermarginal scholia. By comparison, only 15.4% of the total scholia in the Venetus A are intermarginal. If the allocation of scholia content were unrelated to the zone within which a scholion lies, i.e., intermarginal, interior, etc., we would expect the distribution of every topic to reflect the overall distribution of scholia in the manuscript as a whole. For example, the intermarginal scholia, comprising only 15.4% of the manuscript's total scholia, should only ever comprise 15.4% of a particular topic's most relevant scholia. Clearly, this is not the case for topic 8. What this shows firstly is that the allocation of scholia content *is* related to the zone within which a scholion lies.

Beyond this, it is difficult without further analyses to draw firm conclusions. However, there is more to say about topic eight and Aristarchus. First, it must be made clear that we are not claiming that Aristarchus is only, or even mostly, referenced in the intermarginal scholia. Rather, these results show that there is a pattern of language which involves Aristarchus that is most frequently associated with the intermarginal scholia. What this ultimately points to is a look into how the Venetus A was designed. For instance, a pattern of language that exists almost entirely in the intermarginal scholia points to their being a source of information that was used exclusively when creating this zone of scholia. Additionally, the data indicate that the intermarginal scholia may have been used differently than the other zones of scholia. Specifically, the data suggest that certain arguments of Aristarchus are only presented in the intermarginal scholia and that such arguments are not made in the other zones of scholia. 

As we said, however, more and better analyses are required before any more conclusions can be drawn. What that means is not only getting cleaner data as mentioned previously, but also looking beyond just a cursory glance at the numbers from the theta-table. In particular, we looked only at how the zone of scholia related to the particular topics, and not the actual content of the scholia. We are confident that reading the highest-scoring scholia for each topic will more clearly show what patterns of language each of the topics contains. For now, though, we are content with our result demonstrating that topic modeling the scholia of the Venetus A not only works, but can also produce meaningful results that can impact the field of Homeric studies.

