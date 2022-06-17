/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import net.clementlevallois.umigon.heuristics.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isInHashtag;

/**
 *
 * @author LEVALLOIS
 */
public class IsInHashtag {

    public static ResultOneHeuristics check(String text, String termOrig, String termHeuristic, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isInHashtag, termOrig, indexTerm, TypeOfTokenEnum.HASHTAG);
        boolean found = termOrig.toLowerCase().contains(termHeuristic.toLowerCase());
        if (found) {
            resultOneHeuristics.setKeywordMatched(termHeuristic);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return resultOneHeuristics;

    }
}
