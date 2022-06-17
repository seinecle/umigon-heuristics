/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import net.clementlevallois.umigon.heuristics.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isFirstLetterCapitalized;

/**
 *
 * @author LEVALLOIS
 */
public class IsFirstLetterCapitalized {

    public static ResultOneHeuristics check(String text, String termOrig, String termOrigCasePreserved, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isFirstLetterCapitalized, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        if (!termOrigCasePreserved.isEmpty()) {
            boolean res = (Character.isUpperCase(termOrigCasePreserved.codePointAt(0)));
            resultOneHeuristics.setTokenInvestigatedGetsMatched(res);
            return resultOneHeuristics;
        } else {
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }
    }
}
