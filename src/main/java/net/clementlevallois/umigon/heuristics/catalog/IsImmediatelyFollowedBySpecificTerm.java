/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedBySpecificTerm;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedBySpecificTerm {

    public static BooleanCondition check(boolean stripped, List<NGram> ngrams, NGram ngram, Set<String> specificTerms) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedBySpecificTerm);
        booleanCondition.setAssociatedKeywords(specificTerms);
        List<NGram> ngramsFoundAtIndexPlusOne = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(ngrams, ngram, 1);
        List<NGram> ngramsFoundAtIndexPlusTwo = TextFragmentOps.getNGramsAtRelativeOrdinalIndex(ngrams, ngram, 2);

        List<NGram> allNgramsFound = new ArrayList();
        allNgramsFound.addAll(ngramsFoundAtIndexPlusOne);
        allNgramsFound.addAll(ngramsFoundAtIndexPlusTwo);

        List<NGram> nGramsThatMatchedASpecificTerm = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, allNgramsFound, specificTerms);
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedASpecificTerm.isEmpty());
        if (!nGramsThatMatchedASpecificTerm.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedASpecificTerm);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }
}
