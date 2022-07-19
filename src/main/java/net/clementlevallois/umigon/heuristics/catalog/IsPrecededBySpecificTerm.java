/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededBySpecificTerm;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededBySpecificTerm {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededBySpecificTerm);
        booleanCondition.setAssociatedKeywords(keywords);
        List<NGram> nGramsBeforeAnOrdinalIndex = TextFragmentOps.getNGramsBeforeAnOrdinalIndex(textFragmentsThatAreNGrams, ngram);

        List<NGram> nGramsThatMatchedSpecificTerms = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, nGramsBeforeAnOrdinalIndex, keywords);

        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedSpecificTerms.isEmpty());
        if (!nGramsThatMatchedSpecificTerms.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedSpecificTerms);
            booleanCondition.setTextFragmentMatched(ngram);
        }

        return booleanCondition;
    }
}
