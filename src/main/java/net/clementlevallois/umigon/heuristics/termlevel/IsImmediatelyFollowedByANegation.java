/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isImmediatelyFollowedByANegation;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByANegation {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isImmediatelyFollowedByANegation, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).toLowerCase().trim();
            String[] firstTermAfterTermOfInterest = temp.split(" ");

            //if the array is empty it means that the term is the last of the status;
            switch (firstTermAfterTermOfInterest.length) {
                case 0: {
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return resultOneHeuristics;
                }
                case 1: {
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return resultOneHeuristics;
                }
                default: {
                    String nextTerm = firstTermAfterTermOfInterest[1].trim();
                    Set<String> setNegations = heuristics.getSetNegations();
                    boolean containsTerm = setNegations.contains(nextTerm);
                    if (containsTerm) {
                        resultOneHeuristics.setKeywordMatched(nextTerm);
                    }
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(containsTerm);
                    return resultOneHeuristics;
                }

            }
            //in this case the term is followed by at least one term. If the first term is negative, then we return "true"
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }
    }
}
