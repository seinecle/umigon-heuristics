/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByANegativeOpinion;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByANegativeOpinion {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedByANegativeOpinion);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                boolean found = (heuristics.getMapH2().keySet().contains(temp));
                if (found) {
                    booleanCondition.setKeywordMatched(temp);
                    booleanCondition.setKeywordMatchedIndex(text.indexOf(temp));
                }
                booleanCondition.setTokenInvestigatedGetsMatched(found);
                return booleanCondition;
            } else if (nextTerms.length > 1) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                boolean found = (heuristics.getMapH2().keySet().contains(temp));
                booleanCondition.setTokenInvestigatedGetsMatched(found);
                if (found) {
                    booleanCondition.setKeywordMatched(temp);
                    booleanCondition.setKeywordMatchedIndex(text.indexOf(temp));
                }
                return booleanCondition;
            } else if (nextTerms.length > 2) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                boolean found = (heuristics.getMapH2().keySet().contains(temp));
                if (found) {
                    booleanCondition.setKeywordMatched(temp);
                    booleanCondition.setKeywordMatchedIndex(text.indexOf(temp));
                }
                booleanCondition.setTokenInvestigatedGetsMatched(found);
                return booleanCondition;
            } else {
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                return booleanCondition;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
