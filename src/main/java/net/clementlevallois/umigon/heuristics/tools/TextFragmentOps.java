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
        List<NGram> ngramResults = new ArrayList();

        int indexToLookUp = ngram.getIndexOrdinal() + relativeIndex;
        if (indexToLookUp < 0 || indexToLookUp > ngrams.size() - 1) {
            return ngramResults;
        }
        ListIterator<NGram> listIterator = ngrams.listIterator(ngrams.indexOf(ngram));
        int ngramSize = 0;
        if (relativeIndex < 0) {
            while (listIterator.hasPrevious()) {
                NGram previous = listIterator.previous();
                if (previous.getIndexOrdinal() == indexToLookUp & (previous.getIndexOrdinal()+ previous.getTerms().size())<= ngram.getIndexOrdinal()) {
                    ngramResults.add(previous);
                }
            }
        } else {
            while (listIterator.hasNext()) {
                NGram next = listIterator.next();
                if (next.getIndexOrdinal() == (indexToLookUp + ngramSize)) {
                    ngramResults.add(next);
                }
                if (next.getIndexOrdinal() > (indexToLookUp + ngramSize)) {
                    break;
                }
            }
        }

        return ngramResults;
    }

    public static List<NGram> getNGramsAtRelativeCardinalIndex(List<NGram> ngrams, NGram ngram, int relativeIndex) {
        List<NGram> ngramResults = new ArrayList();

        int indexToLookUp = ngram.getIndexCardinal() + relativeIndex;
        if (indexToLookUp < 0 || indexToLookUp > ngrams.size() - 1) {
            return ngramResults;
        }
        ListIterator<NGram> listIterator = ngrams.listIterator(ngrams.indexOf(ngram));
        int ngramSize = 0;
        int ngramMaxSize = 5;
        if (relativeIndex < 0) {
            while (listIterator.hasPrevious() && ngramSize++ < ngramMaxSize) {
                NGram previous = listIterator.previous();
                if (previous.getIndexCardinal() == (indexToLookUp + ngramSize)) {
                    ngramResults.add(previous);
                }
            }
        } else {
            while (listIterator.hasNext()) {
                NGram next = listIterator.next();
                if (next.getIndexCardinal() == (indexToLookUp + ngramSize)) {
                    ngramResults.add(next);
                }
                if (next.getIndexCardinal() > (indexToLookUp + ngramSize)) {
                    break;
                }
            }
        }

        return ngramResults;
    }

    public static List<NGram> getNGramsAfterAnOrdinalIndex(List<NGram> ngrams, NGram ngram) {
        return ngrams.subList(ngrams.indexOf(ngram), ngrams.size());
    }

    public static List<NGram> getNGramsBeforeAnOrdinalIndex(List<NGram> ngrams, NGram ngram) {
        List<NGram> results = new ArrayList();
        for (NGram ngramLoop : ngrams) {
            if (ngramLoop.getIndexOrdinal() < ngram.getIndexOrdinal()) {
                results.add(ngramLoop);
            }
        }
        return results;
    }

    public static List<NGram> checkIfListOfNgramsMatchStringsFromCollection(boolean stripped, List<NGram> ngrams, Collection<String> collection) {
        List<NGram> results = new ArrayList();
        for (NGram ngram : ngrams) {
            if (collection.contains(ngram.getCleanedAndStrippedNgramIfCondition(stripped).toLowerCase())) {
                results.add(ngram);
            }
        }
        return results;
    }
}
