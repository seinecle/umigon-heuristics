/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByStrongWord;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededByStrongWord {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededByStrongWord);

        List<NGram> nGramsBeforeAnOrdinalIndex = TextFragmentOps.getNGramsBeforeAnOrdinalIndex(textFragmentsThatAreNGrams, ngram);

        Set<String> strongWords = new HashSet();
        strongWords.addAll(lexiconsAndTheirConditionalExpressions.getMapH3().keySet());

        List<NGram> nGramsThatMatchedAStrongWord = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, nGramsBeforeAnOrdinalIndex, strongWords);

        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedAStrongWord.isEmpty());
        if (!nGramsThatMatchedAStrongWord.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedAStrongWord);
            booleanCondition.setTextFragmentMatched(ngram);
        }

        return booleanCondition;
    }
}
