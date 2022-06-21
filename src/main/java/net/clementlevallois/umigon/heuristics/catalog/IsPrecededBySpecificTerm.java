/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededBySpecificTerm {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededBySpecificTerm, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        try {
            String temp = text.substring(0, text.indexOf(termOrig)).trim().toLowerCase();
            boolean found = keywords.stream().anyMatch((candidate) -> {
                boolean contains = temp.contains(candidate.toLowerCase());
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
