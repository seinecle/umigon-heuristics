/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByANegation;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByANegation {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedByANegation);

        List<NGram> ngramsFoundAtIndexPlusOne = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, 1);
        List<NGram> ngramsFoundAtIndexPlusTwo = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, 2);

        List<NGram> allNgramsFound = new ArrayList();
        allNgramsFound.addAll(ngramsFoundAtIndexPlusOne);
        allNgramsFound.addAll(ngramsFoundAtIndexPlusTwo);

        List<NGram> nGramsThatMatchedANegation = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, allNgramsFound, lexiconsAndTheirConditionalExpressions.getSetNegations());
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedANegation.isEmpty());
        if (!nGramsThatMatchedANegation.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedANegation);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }
}
