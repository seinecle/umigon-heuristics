/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFollowedBySpecificTerm;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsFollowedBySpecificTerm {

        public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, Set<String> associatedKeywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isFollowedBySpecificTerm);
        booleanCondition.setAssociatedKeywords(associatedKeywords);
        List<NGram> nGramsAfterAnOrdinalIndex = TextFragmentOps.getNGramsAfterAnOrdinalIndex(textFragmentsThatAreNGrams, ngram);
        List<NGram> nGramsThatMatchedASpecificTerm = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, nGramsAfterAnOrdinalIndex, associatedKeywords);
        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedASpecificTerm.isEmpty());
        if (!nGramsThatMatchedASpecificTerm.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedASpecificTerm);
            booleanCondition.setTextFragmentMatched(ngram);
        }
        return booleanCondition;
    }
    
}
