/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class TextFragmentOps {

    public static List<NGram> getNGramsAtRelativeOrdinalIndex(List<NGram> ngrams, NGram ngram, int relativeIndex) {
        int indexToLookUp = ngram.getIndexOrdinalInSentence() + relativeIndex;
        ListIterator<NGram> listIterator = ngrams.listIterator(indexToLookUp);
        List<NGram> ngramResults = new ArrayList();
        int ngramSize = 0;
        int ngramMaxSize = 5;
        if (relativeIndex < 0) {
            while (listIterator.hasPrevious() && ngramSize++ < ngramMaxSize) {
                NGram previous = listIterator.previous();
                if (previous.getIndexOrdinalInSentence() == (indexToLookUp + ngramSize)) {
                    ngramResults.add(ngram);
                }
            }
        } else {
            while (listIterator.hasNext()) {
                NGram next = listIterator.next();
                if (next.getIndexOrdinalInSentence() == (indexToLookUp + ngramSize)) {
                    ngramResults.add(ngram);
                }
                if (next.getIndexOrdinalInSentence() > (indexToLookUp + ngramSize)) {
                    break;
                }
            }
        }

        return ngramResults;
    }

    public static List<NGram> getNGramsAfterAnOrdinalIndex(List<NGram> ngrams, NGram ngram) {
        int indexToLookUp = ngram.getIndexOrdinalInSentence();
        return ngrams.subList(indexToLookUp + 1, ngrams.size());
    }

    public static List<NGram> getNGramsBeforeAnOrdinalIndex(List<NGram> ngrams, NGram ngram) {
        int indexToLookUp = ngram.getIndexOrdinalInSentence();
        return ngrams.subList(0, indexToLookUp);
    }

    public static List<NGram> checkIfListOfNgramsMatchStringsFromCollection(boolean stripped, List<NGram> ngrams, Collection<String> collection) {
        List<NGram> results = new ArrayList();
        for (NGram ngram : ngrams) {
            if (collection.contains(ngram.getCleanedAndStrippedNgramIfCondition(stripped))) {
                results.add(ngram);
            }
        }
        return results;
    }
}
