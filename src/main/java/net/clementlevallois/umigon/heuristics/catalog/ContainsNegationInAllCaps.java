/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.ConditionalExpression;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken;

/**
 *
 * @author LEVALLOIS
 */
public class ContainsNegationInAllCaps {

    public static List<ResultOneHeuristics> check(String text, Set<String> negations) {
        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();
        for (String term : negations) {
            if (text.contains(term.toUpperCase())) {
                int indexStrongNegation = text.indexOf(term.toUpperCase());
                ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, indexStrongNegation, term, TypeOfToken.TypeOfTokenEnum.NGRAM);
                resultOneHeuristics.setConditionEnum(ConditionalExpression.ConditionEnum.isNegationInCaps);
                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                resultsHeuristics.add(resultOneHeuristics);
            }
        }
        return resultsHeuristics;
    }

}
