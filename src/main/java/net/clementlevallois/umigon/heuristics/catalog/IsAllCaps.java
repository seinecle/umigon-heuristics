/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isAllCaps;

/**
 *
 * @author LEVALLOIS
 */
public class IsAllCaps {

    public static BooleanCondition check(String termOrigCasePreserved) {
        BooleanCondition booleanCondition = new BooleanCondition(isAllCaps);
        String temp = termOrigCasePreserved.replaceAll(" ", "").trim();
        char[] charArray = temp.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isUpperCase(charArray[i])) {
                return booleanCondition;
            }
        }
        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        return booleanCondition;
    }
}