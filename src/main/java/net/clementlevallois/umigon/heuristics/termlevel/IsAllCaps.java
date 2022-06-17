/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import net.clementlevallois.umigon.heuristics.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isAllCaps;

/**
 *
 * @author LEVALLOIS
 */
public class IsAllCaps {

    public static ResultOneHeuristics check(String text, String termOrig, String termOrigCasePreserved, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isAllCaps, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        String temp = termOrigCasePreserved.replaceAll(" ", "").trim();
        char[] charArray = temp.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isUpperCase(charArray[i])) {
                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                return resultOneHeuristics;
            }
        }
        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        return resultOneHeuristics;
    }
}