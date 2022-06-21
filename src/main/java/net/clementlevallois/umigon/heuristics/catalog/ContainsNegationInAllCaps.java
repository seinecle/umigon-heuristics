/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.model.BooleanCondition;

/**
 *
 * @author LEVALLOIS
 */
public class ContainsNegationInAllCaps {

    public static BooleanCondition check(String text, Set<String> negations) {
        BooleanCondition booleanCondition = new BooleanCondition(BooleanCondition.BooleanConditionEnum.isNegationInCaps);
        for (String term : negations) {
            if (text.contains(term.toUpperCase())) {
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                booleanCondition.setKeywordMatched(term);
                booleanCondition.setKeywordMatchedIndex(text.indexOf(term.toUpperCase()));
            }
        }
        return booleanCondition;
    }
}
