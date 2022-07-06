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

    public static BooleanCondition check(String text, String term, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isFollowedByAPositiveOpinion);
        try {
            String temp = text.substring(indexTerm + term.length()).trim().toLowerCase();
            heuristics.getMapH1().keySet().stream().anyMatch((positiveTerm) -> {
                int index = temp.toLowerCase().indexOf(positiveTerm.toLowerCase());
                if (index != -1) {
                    booleanCondition.setTextFragmentMatched(positiveTerm);
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setKeywordMatchedIndex(index);
                }
                return (index != -1);
            });

            return booleanCondition;

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + term);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
