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
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByPositive;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededByPositive {


    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededByPositive);

        List<NGram> nGramsBeforeAnOrdinalIndex = TextFragmentOps.getNGramsBeforeAnOrdinalIndex(textFragmentsThatAreNGrams, ngram);

        Set<String> positiveOpinions = new HashSet();
        positiveOpinions.addAll(lexiconsAndTheirConditionalExpressions.getMapH1().keySet());

        List<NGram> nGramsThatMatchedAPositiveOpinion = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, nGramsBeforeAnOrdinalIndex, positiveOpinions);

        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedAPositiveOpinion.isEmpty());
        if (!nGramsThatMatchedAPositiveOpinion.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedAPositiveOpinion);
            booleanCondition.setTextFragmentMatched(ngram);
        }

        return booleanCondition;
    }
}
