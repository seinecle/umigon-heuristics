/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFirstLetterCapitalized;

/**
 *
 * @author LEVALLOIS
 */
public class IsFirstLetterCapitalized {

    public static BooleanCondition check(String termOrigCasePreserved) {
        BooleanCondition booleanCondition = new BooleanCondition(isFirstLetterCapitalized);
        if (!termOrigCasePreserved.isEmpty()) {
            boolean res = (Character.isUpperCase(termOrigCasePreserved.codePointAt(0)));
            booleanCondition.setTokenInvestigatedGetsMatched(res);
            return booleanCondition;
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
