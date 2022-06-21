/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagStart;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtagStart {

    public static BooleanCondition check(String termOrig, String termHeuristic) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtagStart);
        boolean found = termOrig.toLowerCase().startsWith(termHeuristic.toLowerCase());
        if (found) {
            booleanCondition.setKeywordMatched(termHeuristic);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return booleanCondition;
    }
}
