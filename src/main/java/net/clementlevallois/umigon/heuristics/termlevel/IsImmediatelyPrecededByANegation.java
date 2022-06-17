/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import net.clementlevallois.umigon.heuristics.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isImmediatelyPrecededByANegation;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByANegation {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isImmediatelyPrecededByANegation, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        try {
            String leftPart = text.substring(0, indexTerm).toLowerCase().trim();
            String[] temp = leftPart.split(" ");

            //if the array is empty it means that the term is the first of the status;
            switch (temp.length) {
                case 0: {
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return resultOneHeuristics;
                }
                case 1: {
                    boolean found = heuristics.getSetNegations().contains(temp[0]);
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(found);
                    if (found) {
                        resultOneHeuristics.setKeywordMatched(temp[0]);
                    }
                    return resultOneHeuristics;
                }
                default: {
                    if (heuristics.getSetNegations().contains(temp[temp.length - 1])) {
                        resultOneHeuristics.setKeywordMatched(temp[temp.length - 1]);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return resultOneHeuristics;
                    } else if (temp.length > 1) {
                        if (heuristics.getMapH3().containsKey(temp[temp.length - 1]) & heuristics.getSetNegations().contains(temp[temp.length - 2])) {
                            resultOneHeuristics.setKeywordMatched(temp[temp.length - 2]);
                            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return resultOneHeuristics;
                        }

                        //in the case of "not the hottest", return true
                        String negativeTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                        if (heuristics.getSetNegations().contains(negativeTerm.trim())) {
                            resultOneHeuristics.setKeywordMatched(negativeTerm.trim());
                            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return resultOneHeuristics;
                        }
                    }
                    if (temp.length > 2) {
                        //in the case of "don't really like", return true [don't counts as two words: don t]
                        String negativeTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                        String booster = temp[temp.length - 1];
                        if (heuristics.getSetNegations().contains(negativeTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                            resultOneHeuristics.setKeywordMatched(negativeTerm.trim());
                            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return resultOneHeuristics;
                        }
                    }
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return resultOneHeuristics;
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            System.out.println("index term was: " + indexTerm);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }
    }

}
