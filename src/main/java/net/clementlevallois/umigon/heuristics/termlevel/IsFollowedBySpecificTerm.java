/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.HeuristicsLoaderOnDemand;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.heuristics.ConditionalExpression.ConditionEnum.isFollowedBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsFollowedBySpecificTerm {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, HeuristicsLoaderOnDemand heuristics, Set<String> keywords) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isFollowedBySpecificTerm, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            boolean found = keywords.stream().anyMatch((candidate) -> {
                boolean contains = temp.contains(candidate);
                if (contains) {
                    resultOneHeuristics.setKeywordMatched(candidate);
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