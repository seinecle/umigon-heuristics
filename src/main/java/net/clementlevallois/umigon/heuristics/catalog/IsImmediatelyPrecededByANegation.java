/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByANegation;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByANegation {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededByANegation);

        List<NGram> ngramsFoundAtIndexMinusOne = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, -1);
        List<NGram> ngramsFoundAtIndexMinusTwo = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, -2);

        List<NGram> allNgramsFound = new ArrayList();
        allNgramsFound.addAll(ngramsFoundAtIndexMinusOne);
        allNgramsFound.addAll(ngramsFoundAtIndexMinusTwo);

        List<NGram> nGramsThatMatchedANegation = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, allNgramsFound, lexiconsAndTheirConditionalExpressions.getSetNegations());
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedANegation.isEmpty());
        if (!nGramsThatMatchedANegation.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedANegation);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }

}
