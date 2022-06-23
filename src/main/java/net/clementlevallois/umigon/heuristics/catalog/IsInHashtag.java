/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInHashtag;

/**
 *
 * @author LEVALLOIS
 */
public class IsInHashtag {

    public static BooleanCondition check(String text, String term, String termHeuristic, int indexTerm) {
        BooleanCondition booleanCondition = new BooleanCondition(isInHashtag);
        boolean found = term.toLowerCase().contains(termHeuristic.toLowerCase());
        if (found) {
            booleanCondition.setKeywordMatched(termHeuristic);
            booleanCondition.setKeywordMatchedIndex(indexTerm);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return booleanCondition;

    }
}
