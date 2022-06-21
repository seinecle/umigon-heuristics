/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFollowedByAPositiveOpinion;

/**
 *
 * @author LEVALLOIS
 */
public class IsFollowedByAPositiveOpinion {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isFollowedByAPositiveOpinion);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            boolean found = heuristics.getMapH1().keySet().stream().anyMatch((positiveTerm) -> {
                boolean contains = temp.contains(positiveTerm);
                if (contains) {
                    booleanCondition.setKeywordMatched(positiveTerm);
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setKeywordMatchedIndex(temp.indexOf(positiveTerm));
                }
                return contains;
            });

            return booleanCondition;

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
