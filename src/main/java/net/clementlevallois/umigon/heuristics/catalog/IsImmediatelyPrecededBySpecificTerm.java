/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededBySpecificTerm;
import net.clementlevallois.umigon.model.NGram;
import net.clementlevallois.umigon.model.TextFragment;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededBySpecificTerm {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram,  LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions, Set<TextFragment> textFragmentsAssociatedWithTheBooleanCondition) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyPrecededBySpecificTerm);
        try {
            List<NGram> leftPart = textFragmentsThatAreNGrams.subList(0, textFragmentsThatAreNGrams.indexOf(ngram));
            
            //if the array is empty it means that the term is the first of the status;
            switch (leftPart.size()) {
                case 0: {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    booleanCondition.setTextFragmentsAssociatedTotheBooleanCondition(textFragmentsAssociatedWithTheBooleanCondition);
                    return booleanCondition;
                }
                case 1: {
                    booleanCondition.setTokenInvestigatedGetsMatched(textFragmentsAssociatedWithTheBooleanCondition.contains(leftPart.get(0).getCleanedAndStrippedNgramIfCondition(stripped).toLowerCase()));
                    booleanCondition.setTextFragmentMatched(leftPart.get(0));
                    return booleanCondition;
                }
                default: {
                    if (textFragmentsAssociatedWithTheBooleanCondition.contains(leftPart.get(leftPart.size() - 1))) {
                        booleanCondition.setTextFragmentMatched(temp[temp.length - 1]);
                        booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp[temp.length - 1].toLowerCase()));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                    if (temp.length > 1 && lexiconsAndTheirConditionalExpressions.getMapH3().containsKey(temp[temp.length - 1].toLowerCase()) & keywords.contains(temp[temp.length - 2].toLowerCase())) {
                        booleanCondition.setTextFragmentMatched(temp[temp.length - 2]);
                        booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp[temp.length - 2].toLowerCase()));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                    //in the case of "not the hottest", return true
                    String specificTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                    if (keywords.contains(specificTerm.toLowerCase().trim())) {
                        booleanCondition.setTextFragmentMatched(specificTerm);
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(specificTerm));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                }
                if (temp.length > 2) {
                    //in the case of "don't really like", return true [don't counts as two words: don t]
                    String specificTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                    String booster = temp[temp.length - 1];
                    if (keywords.contains(specificTerm.toLowerCase().trim()) && lexiconsAndTheirConditionalExpressions.getMapH3().containsKey(booster.toLowerCase())) {
                        booleanCondition.setTextFragmentMatched(specificTerm.trim());
                        booleanCondition.setKeywordMatchedIndex(text.indexOf(specificTerm.trim()));
                        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        return booleanCondition;
                    }
                    for (String parameter : keywords) {
                        if (leftPart.toLowerCase().contains(parameter.toLowerCase())) {
                            String fromParamExcludedToTermExcluded = leftPart.substring(leftPart.indexOf(parameter.toLowerCase()) + parameter.length()).toLowerCase().trim();
                            if (!fromParamExcludedToTermExcluded.isBlank() && lexiconsAndTheirConditionalExpressions.getMapH3().containsKey(fromParamExcludedToTermExcluded.toLowerCase())) {
                                booleanCondition.setTextFragmentMatched(fromParamExcludedToTermExcluded);
                                booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(fromParamExcludedToTermExcluded.toLowerCase()));
                                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                                return booleanCondition;
                            }
                        }
                    }
                }

                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                booleanCondition.setTextFragmentsAssociatedTotheBooleanCondition(keywords);
                return booleanCondition;
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
