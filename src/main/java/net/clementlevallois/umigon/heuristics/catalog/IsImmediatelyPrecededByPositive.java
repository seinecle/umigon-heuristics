/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByPositive;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByPositive {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededByPositive);

        List<NGram> ngramsFoundAtIndexMinusOne = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, -1);
        List<NGram> ngramsFoundAtIndexMinusTwo = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, -2);

        List<NGram> allNgramsFound = new ArrayList();
        allNgramsFound.addAll(ngramsFoundAtIndexMinusOne);
        allNgramsFound.addAll(ngramsFoundAtIndexMinusTwo);

        Set<String> positiveOpinions = new HashSet();
        positiveOpinions.addAll(lexiconsAndTheirConditionalExpressions.getMapH1().keySet());
        
        
        List<NGram> nGramsThatMatchedANegation = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, allNgramsFound, positiveOpinions);
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedANegation.isEmpty());
        if (!nGramsThatMatchedANegation.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedANegation);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }
}