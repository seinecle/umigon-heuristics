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

    public static BooleanCondition check(String term, String termHeuristic) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtagStart);
        boolean found = term.toLowerCase().startsWith(termHeuristic.toLowerCase());
        if (found) {
            booleanCondition.setTextFragmentMatched(termHeuristic);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return booleanCondition;
    }
}
