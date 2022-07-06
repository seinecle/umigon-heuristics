/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Map;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByPositive;
import net.clementlevallois.umigon.model.TermWithConditionalExpressions;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByPositive {

    public static BooleanCondition check(String text, String term, int termIndex, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        Map<String, TermWithConditionalExpressions> positiveTermsAndTheirConditionalExpressions = lexiconsAndTheirConditionalExpressions.getMapH1();
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededByPositive);
        String[] temp = text.substring(0, termIndex).trim().split(" ");
        if (temp.length == 0) {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }

        if (temp.length > 0 && positiveTermsAndTheirConditionalExpressions.containsKey(temp[temp.length - 1].trim().toLowerCase())) {
            booleanCondition.setTextFragmentMatched(temp[temp.length - 1]);
            booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp[temp.length - 1].toLowerCase()));
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            return booleanCondition;
        } else if (temp.length > 1 && positiveTermsAndTheirConditionalExpressions.containsKey(temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            booleanCondition.setTextFragmentMatched(temp[temp.length - 2] + " " + temp[temp.length - 1]);
            booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp[temp.length - 2].toLowerCase() + " " + temp[temp.length - 1].toLowerCase()));
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            return booleanCondition;
        } else if (temp.length > 2 && positiveTermsAndTheirConditionalExpressions.containsKey(temp[temp.length - 3].trim().toLowerCase() + " " + temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            booleanCondition.setTextFragmentMatched(temp[temp.length - 3] + " " + temp[temp.length - 2] + " " + temp[temp.length - 1]);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp[temp.length - 3].toLowerCase() + " " + temp[temp.length - 2].toLowerCase() + " " + temp[temp.length - 1].toLowerCase()));
            return booleanCondition;
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
