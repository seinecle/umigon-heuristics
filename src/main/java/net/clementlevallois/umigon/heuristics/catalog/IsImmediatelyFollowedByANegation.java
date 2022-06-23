/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByANegation;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByANegation {

    public static BooleanCondition check(String text, String term, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedByANegation);
        try {
            String temp = text.substring(indexTerm + term.length()).toLowerCase().trim();
            String[] firstTermAfterTermOfInterest = temp.split(" ");

            //if the array is empty it means that the term is the last of the status;
            switch (firstTermAfterTermOfInterest.length) {
                case 0: {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return booleanCondition;
                }
                case 1: {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return booleanCondition;
                }
                default: {
                    String nextTerm = firstTermAfterTermOfInterest[1].trim();
                    Set<String> setNegations = heuristics.getSetNegations();
                    boolean containsTerm = setNegations.contains(nextTerm);
                    if (containsTerm) {
                        booleanCondition.setKeywordMatched(nextTerm);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(nextTerm));
                    }
                    booleanCondition.setTokenInvestigatedGetsMatched(containsTerm);
                    return booleanCondition;
                }

            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + term);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
