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
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByAnOpinion;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByAnOpinion {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedByAnOpinion);
        List<NGram> ngramsFoundAtIndexPlusOne = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, 1);
        List<NGram> ngramsFoundAtIndexPlusTwo = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(textFragmentsThatAreNGrams, ngram, 2);

        List<NGram> allNgramsFound = new ArrayList();
        allNgramsFound.addAll(ngramsFoundAtIndexPlusOne);
        allNgramsFound.addAll(ngramsFoundAtIndexPlusTwo);
        
        Set<String> opinions = new HashSet();
        opinions.addAll(lexiconsAndTheirConditionalExpressions.getMapH1().keySet());
        opinions.addAll(lexiconsAndTheirConditionalExpressions.getMapH2().keySet());

        List<NGram> nGramsThatMatchedAnOpinion = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, allNgramsFound, opinions);
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedAnOpinion.isEmpty());
        if (!nGramsThatMatchedAnOpinion.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedAnOpinion);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }
}