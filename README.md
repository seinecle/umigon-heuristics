# Umigon-heuristics

Includes lists of lexicons with conditional expressions, heuristics and heuristics interpreters. These are the fundamental blocks underpinning applications in sentiment analysis, emotion identification and more.

# Key features

* Multilingual. French and English are covered and new languages will be added.

* Enables state of the art sentiment analysis in French and English. See the umigon-core repository for the sentiment analysis classification engine that makes use of these heuristics.

* Lexicons and heuristics for emotion analysis to be added to this repo in 2022.

# To do
The repo includes lexicons, heuristics, and classes making use of these heuristics to identify semantic features in a text.

The denominations (what is a heuristic? a conditional expression?) are not super clear and consistent. It should get straightened up.

Also, there is a small entanglement between lexicons, conditional expressions, heuristics and the applications they can serve. For instance, you will find classes or methods that relate to sentiment analysis.

Example:

The method isHashTagStartingWithAnOpinion should do what it says. It does, but the body of this method also works on categorizating the document where the hashtag appears as either positive, negative or neutral in sentiment. These parts should be removed and placed in the repo dedicated to sentiment analysis (umigon-core, that you will find on github as well).

 __The end goal is to decouple neatly the lexicons + conditional expressions, heuristics and their interpreters on one side, and the classification engines (for sentiment analysis, emotion detection...) on the other side__. This is work on progress.

# License
Apache v2 license. These data and code can be used for non commercial *and commercial* purposes. Just make sure to include the Apache v2 license and the copyright notice.


# Credit
For academic use, cite this reference:

Levallois, Clement. “Umigon: Sentiment analysis on Tweets based on terms lists and heuristics”. Proceedings of the 7th International Workshop on Semantic Evaluation (SemEval), 2013, Atlanta, Georgia


# Contact
Clement Levallois, @seinecle on Twitter or https://clementlevallois.net
