/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByPositive;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByPositive {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededByPositive, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        String[] temp = text.substring(0, text.indexOf(termOrig)).trim().split(" ");
        if (temp.length == 0) {
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }

        if (temp.length > 0 && heuristics.getMapH1().containsKey(temp[temp.length - 1].trim().toLowerCase())) {
            resultOneHeuristics.setKeywordMatched(temp[temp.length - 1]);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            return resultOneHeuristics;
        } else if (temp.length > 1 && heuristics.getMapH1().containsKey(temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            resultOneHeuristics.setKeywordMatched(temp[temp.length - 2] + " " + temp[temp.length - 1]);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            return resultOneHeuristics;
        } else if (temp.length > 2 && heuristics.getMapH1().containsKey(temp[temp.length - 3].trim().toLowerCase() + " " + temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            resultOneHeuristics.setKeywordMatched(temp[temp.length - 3] + " " + temp[temp.length - 2] + " " + temp[temp.length - 1]);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            return resultOneHeuristics;
        } else {
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }
    }
}
