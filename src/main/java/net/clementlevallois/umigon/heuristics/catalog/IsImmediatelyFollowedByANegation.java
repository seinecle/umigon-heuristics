/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByANegation;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedByANegation {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedByANegation);
        List<NGram> rightPart = textFragmentsThatAreNGrams.subList(textFragmentsThatAreNGrams.indexOf(ngram), textFragmentsThatAreNGrams.size() - 1);

        //if the list is empty it means that the term is the last of the status;
        switch (rightPart.size()) {
            case 0: {
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                return booleanCondition;
            }
            case 1: {
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                return booleanCondition;
            }
            default: {
                String nextTerm = rightPart.get(1).getCleanedAndStrippedNgramIfCondition(stripped);
                Set<String> setNegations = heuristics.getSetNegations();
                boolean containsTerm = setNegations.contains(nextTerm.toLowerCase());
                if (containsTerm) {
                    booleanCondition.setTextFragmentMatched(rightPart.get(1));
                }
                booleanCondition.setTokenInvestigatedGetsMatched(containsTerm);
                return booleanCondition;
            }

        }
    }
}
