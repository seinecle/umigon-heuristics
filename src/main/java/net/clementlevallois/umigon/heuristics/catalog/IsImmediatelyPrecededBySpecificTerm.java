/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededBySpecificTerm {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededBySpecificTerm, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
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
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(keywords.contains(temp[0]));
                    resultOneHeuristics.setKeywordMatched(temp[0]);
                    return resultOneHeuristics;
                }
                default: {
                    if (keywords.contains(temp[temp.length - 1])) {
                        resultOneHeuristics.setKeywordMatched(temp[temp.length - 1]);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return resultOneHeuristics;
                    }
                    if (temp.length > 1 && heuristics.getMapH3().containsKey(temp[temp.length - 1]) & keywords.contains(temp[temp.length - 2])) {
                        resultOneHeuristics.setKeywordMatched(temp[temp.length - 2]);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return resultOneHeuristics;
                    }
                    //in the case of "not the hottest", return true
                    String specificTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                    if (keywords.contains(specificTerm.trim())) {
                        resultOneHeuristics.setKeywordMatched(specificTerm);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return resultOneHeuristics;
                    }
                }
                if (temp.length > 2) {
                    //in the case of "don't really like", return true [don't counts as two words: don t]
                    String specificTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                    String booster = temp[temp.length - 1];
                    if (keywords.contains(specificTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                        resultOneHeuristics.setKeywordMatched(specificTerm.trim());
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return resultOneHeuristics;
                    }
                    for (String parameter : keywords) {
                        if (leftPart.contains(parameter)) {
                            String fromParamExcludedToTermExcluded = leftPart.substring(leftPart.indexOf(parameter) + parameter.length()).toLowerCase().trim();
                            if (!fromParamExcludedToTermExcluded.isBlank() && heuristics.getMapH3().containsKey(fromParamExcludedToTermExcluded)) {
                                resultOneHeuristics.setKeywordMatched(fromParamExcludedToTermExcluded);
                                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                                return resultOneHeuristics;
                            }
                        }
                    }
                }

                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                return resultOneHeuristics;
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
