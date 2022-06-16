/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import net.clementlevallois.umigon.heuristics.HeuristicsLoaderOnDemand;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.heuristics.ConditionalExpression.ConditionEnum.isFollowedByAPositiveOpinion;

/**
 *
 * @author LEVALLOIS
 */
public class IsFollowedByAPositiveOpinion {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, HeuristicsLoaderOnDemand heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isFollowedByAPositiveOpinion, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            boolean found = heuristics.getMapH1().keySet().stream().anyMatch((positiveTerm) -> {
                boolean contains = temp.contains(positiveTerm);
                if (contains) {
                    resultOneHeuristics.setKeywordMatched(positiveTerm);
                }
                return contains;
            });

            resultOneHeuristics.setTokenInvestigatedGetsMatched(found);
            return resultOneHeuristics;

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }
    }
}
