/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.HeuristicsLoaderOnDemand;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.heuristics.ConditionalExpression.ConditionEnum.isImmediatelyFollowedBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedBySpecificTerm {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, HeuristicsLoaderOnDemand heuristics, Set<String> keywords) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isImmediatelyFollowedBySpecificTerm, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                boolean isNextTermRelevant = keywords.contains(temp);
                if (isNextTermRelevant) {
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    if (isNextTermRelevant) {
                        resultOneHeuristics.setKeywordMatched(temp);
                    }
                    return resultOneHeuristics;
                } else if (nextTerms.length > 1) {
                    temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                    boolean found = keywords.contains(temp);
                    if (found) {
                        resultOneHeuristics.setKeywordMatched(temp);
                    }
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(found);
                    return resultOneHeuristics;
                } else if (nextTerms.length > 2) {
                    temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                    boolean found = keywords.contains(temp);
                    if (found) {
                        resultOneHeuristics.setKeywordMatched(temp);
                    }
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(found);
                    return resultOneHeuristics;
                } else {
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return resultOneHeuristics;
                }
            }
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
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
