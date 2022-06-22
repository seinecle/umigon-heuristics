/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededBySpecificTerm {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededBySpecificTerm);
        try {
            String leftPart = text.substring(0, indexTerm).toLowerCase().trim();
            String[] temp = leftPart.split(" ");

            //if the array is empty it means that the term is the first of the status;
            switch (temp.length) {
                case 0: {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return booleanCondition;
                }
                case 1: {
                    booleanCondition.setTokenInvestigatedGetsMatched(keywords.contains(temp[0]));
                    booleanCondition.setKeywordMatched(temp[0]);
                    booleanCondition.setKeywordMatchedIndex(text.indexOf(temp[0]));
                    return booleanCondition;
                }
                default: {
                    if (keywords.contains(temp[temp.length - 1])) {
                        booleanCondition.setKeywordMatched(temp[temp.length - 1]);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(temp[temp.length - 1]));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                    if (temp.length > 1 && heuristics.getMapH3().containsKey(temp[temp.length - 1]) & keywords.contains(temp[temp.length - 2])) {
                        booleanCondition.setKeywordMatched(temp[temp.length - 2]);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(temp[temp.length - 2]));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                    //in the case of "not the hottest", return true
                    String specificTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                    if (keywords.contains(specificTerm.trim())) {
                        booleanCondition.setKeywordMatched(specificTerm);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(specificTerm));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                }
                if (temp.length > 2) {
                    //in the case of "don't really like", return true [don't counts as two words: don t]
                    String specificTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                    String booster = temp[temp.length - 1];
                    if (keywords.contains(specificTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                        booleanCondition.setKeywordMatched(specificTerm.trim());
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(specificTerm.trim()));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                    for (String parameter : keywords) {
                        if (leftPart.contains(parameter)) {
                            String fromParamExcludedToTermExcluded = leftPart.substring(leftPart.indexOf(parameter) + parameter.length()).toLowerCase().trim();
                            if (!fromParamExcludedToTermExcluded.isBlank() && heuristics.getMapH3().containsKey(fromParamExcludedToTermExcluded)) {
                                booleanCondition.setKeywordMatched(fromParamExcludedToTermExcluded);
                                booleanCondition.setKeywordMatchedIndex(text.indexOf(fromParamExcludedToTermExcluded));
                                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                                return booleanCondition;
                            }
                        }
                    }
                }

                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                return booleanCondition;
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            System.out.println("index term was: " + indexTerm);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
