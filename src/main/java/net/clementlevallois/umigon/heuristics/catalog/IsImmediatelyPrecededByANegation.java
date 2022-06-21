/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByANegation;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByANegation {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededByANegation);
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
                    boolean found = heuristics.getSetNegations().contains(temp[0]);
                    if (found) {
                        booleanCondition.setKeywordMatched(temp[0]);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(temp[0]));
                    }
                    booleanCondition.setTokenInvestigatedGetsMatched(found);
                    return booleanCondition;
                }
                default: {
                    if (heuristics.getSetNegations().contains(temp[temp.length - 1])) {
                        booleanCondition.setKeywordMatched(temp[temp.length - 1]);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(temp[temp.length - 1]));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    } else if (temp.length > 1) {
                        if (heuristics.getMapH3().containsKey(temp[temp.length - 1]) & heuristics.getSetNegations().contains(temp[temp.length - 2])) {
                            booleanCondition.setKeywordMatched(temp[temp.length - 2]);
                            booleanCondition.setKeywordMatchedIndex(text.indexOf(temp[temp.length - 2]));
                            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return booleanCondition;
                        }

                        //in the case of "not the hottest", return true
                        String negativeTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                        if (heuristics.getSetNegations().contains(negativeTerm.trim())) {
                            booleanCondition.setKeywordMatched(negativeTerm.trim());
                            booleanCondition.setKeywordMatchedIndex(text.indexOf(negativeTerm.trim()));
                            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return booleanCondition;
                        }
                    }
                    if (temp.length > 2) {
                        //in the case of "don't really like", return true [don't counts as two words: don t]
                        String negativeTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                        String booster = temp[temp.length - 1];
                        if (heuristics.getSetNegations().contains(negativeTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                            booleanCondition.setKeywordMatched(negativeTerm.trim());
                            booleanCondition.setKeywordMatchedIndex(text.indexOf(negativeTerm.trim()));
                            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return booleanCondition;
                        }
                    }
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return booleanCondition;
                }
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
