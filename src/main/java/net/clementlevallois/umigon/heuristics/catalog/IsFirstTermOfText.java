/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFirstTermOfText;

/**
 *
 * @author LEVALLOIS
 */
public class IsFirstTermOfText {

    public static BooleanCondition check(String text, String term) {
        BooleanCondition booleanCondition = new BooleanCondition(isFirstTermOfText);
        String[] terms = text.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        boolean cleanStart = false;
        for (String currTerm : terms) {
            if (!cleanStart & (currTerm.startsWith("RT") || currTerm.startsWith("@"))) {
                continue;
            } else {
                cleanStart = true;
            }
            sb.append(currTerm).append(" ");
            if (cleanStart) {
                break;
            }
        }
        String textWithCheckOnStart = sb.toString().trim();
        boolean res = textWithCheckOnStart.toLowerCase().startsWith(term.toLowerCase());
        booleanCondition.setTokenInvestigatedGetsMatched(res);
        return booleanCondition;
    }
}
