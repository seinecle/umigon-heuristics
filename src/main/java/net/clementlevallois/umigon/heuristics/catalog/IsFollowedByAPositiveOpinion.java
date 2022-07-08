/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.List;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFollowedByAPositiveOpinion;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsFollowedByAPositiveOpinion {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isFollowedByAPositiveOpinion);

        List<NGram> nGramsAfterAnOrdinalIndex = TextFragmentOps.getNGramsAfterAnOrdinalIndex(textFragmentsThatAreNGrams, ngram);
        List<NGram> nGramsThatMatchedAPositiveOpinion = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, nGramsAfterAnOrdinalIndex, lexiconsAndTheirConditionalExpressions.getMapH1().keySet());
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedAPositiveOpinion.isEmpty());
        if (!nGramsThatMatchedAPositiveOpinion.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedAPositiveOpinion);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }
}
