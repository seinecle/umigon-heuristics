/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.List;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByANegation;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByANegation {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalEpxressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededByANegation);
        try {
            List<NGram> leftPart = textFragmentsThatAreNGrams.subList(0, textFragmentsThatAreNGrams.indexOf(ngram));

            //if the array is empty it means that the term is the first of the status;
            switch (leftPart.size()) {
                case 0: {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    return booleanCondition;
                }
                case 1: {
                    boolean found = lexiconsAndTheirConditionalEpxressions.getSetNegations().contains(leftPart.get(0).getCleanedAndStrippedNgramIfCondition(stripped));
                    if (found) {
                        booleanCondition.setTextFragmentMatched(temp[0]);
                        booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp[0].toLowerCase()));
                    }
                    booleanCondition.setTokenInvestigatedGetsMatched(found);
                    return booleanCondition;
                }
                default: {
                    if (lexiconsAndTheirConditionalEpxressions.getSetNegations().contains(temp[temp.length - 1].toLowerCase())) {
                        booleanCondition.setTextFragmentMatched(temp[temp.length - 1]);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(temp[temp.length - 1]));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    } else if (temp.length > 1) {
                        if (lexiconsAndTheirConditionalEpxressions.getMapH3().containsKey(temp[temp.length - 1].toLowerCase()) & lexiconsAndTheirConditionalEpxressions.getSetNegations().contains(temp[temp.length - 2].toLowerCase())) {
                            booleanCondition.setTextFragmentMatched(temp[temp.length - 2]);
                            booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp[temp.length - 2].toLowerCase()));
                            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return booleanCondition;
                        }

                        //in the case of "not the hottest", return true
                        String negativeTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                        if (lexiconsAndTheirConditionalEpxressions.getSetNegations().contains(negativeTerm.toLowerCase().trim())) {
                            booleanCondition.setTextFragmentMatched(negativeTerm.trim());
                            booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(negativeTerm.trim().toLowerCase()));
                            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            return booleanCondition;
                        }
                    }
                    if (temp.length > 2) {
                        //in the case of "don't really like", return true [don't counts as two words: don t]
                        String negativeTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                        String booster = temp[temp.length - 1];
                        if (lexiconsAndTheirConditionalEpxressions.getSetNegations().contains(negativeTerm.trim().toLowerCase()) && lexiconsAndTheirConditionalEpxressions.getMapH3().containsKey(booster.toLowerCase())) {
                            booleanCondition.setTextFragmentMatched(negativeTerm.trim());
                            booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(negativeTerm.trim().toLowerCase()));
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
            System.out.println("term was: " + term);
            System.out.println("index term was: " + indexTerm);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }

}
