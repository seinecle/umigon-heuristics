/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByANegativeOpinion;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByANegativeOpinion {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedByANegativeOpinion);

        List<NGram> ngramsFoundAtIndexPlusOne = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, 1);
        List<NGram> ngramsFoundAtIndexPlusTwo = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, 2);

        List<NGram> allNgramsFound = new ArrayList();
        allNgramsFound.addAll(ngramsFoundAtIndexPlusOne);
        allNgramsFound.addAll(ngramsFoundAtIndexPlusTwo);

        List<NGram> nGramsThatMatchedANegativeOpinion = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, allNgramsFound, lexiconsAndTheirConditionalExpressions.getMapH2().keySet());
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedANegativeOpinion.isEmpty());
        if (!nGramsThatMatchedANegativeOpinion.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedANegativeOpinion);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }
}
