/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInHashtag;

/**
 *
 * @author LEVALLOIS
 */
public class IsInHashtag {

    public static BooleanCondition check(String text, String termOrig, String termHeuristic, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isInHashtag, termOrig, indexTerm, TypeOfTokenEnum.HASHTAG);
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
