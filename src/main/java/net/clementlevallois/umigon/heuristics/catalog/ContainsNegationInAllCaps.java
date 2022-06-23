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
        for (String negation : negations) {
            if (text.contains(negation.toUpperCase())) {
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                booleanCondition.setKeywordMatched(negation);
                booleanCondition.setKeywordMatchedIndex(text.indexOf(negation.toUpperCase()));
            }
        }
        return booleanCondition;
    }
}
