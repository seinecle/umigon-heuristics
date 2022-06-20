/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isImmediatelyFollowedByAPositiveOpinion;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByAPositiveOpinion {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isImmediatelyFollowedByAPositiveOpinion, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                boolean found = (heuristics.getMapH1().keySet().contains(temp));
                if (found) {
                    resultOneHeuristics.setKeywordMatched(temp);
                }
                resultOneHeuristics.setTokenInvestigatedGetsMatched(found);
                return resultOneHeuristics;

            } else if (nextTerms.length > 1) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                boolean found =  (heuristics.getMapH1().keySet().contains(temp));
                if (found) {
                    resultOneHeuristics.setKeywordMatched(temp);
                }
                resultOneHeuristics.setTokenInvestigatedGetsMatched(found);
                return resultOneHeuristics;
            } else if (nextTerms.length > 2) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                boolean found =  (heuristics.getMapH1().keySet().contains(temp));
                if (found) {
                    resultOneHeuristics.setKeywordMatched(temp);
                }
                resultOneHeuristics.setTokenInvestigatedGetsMatched(found);
                return resultOneHeuristics;
            } else {
                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                return resultOneHeuristics;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }
    }
}
