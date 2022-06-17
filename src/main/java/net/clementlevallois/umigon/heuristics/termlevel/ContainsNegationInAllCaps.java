/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.ConditionalExpression;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken;
import net.clementlevallois.utils.StatusCleaner;

/**
 *
 * @author LEVALLOIS
 */
public class ContainsNegationInAllCaps {

    public static ResultOneHeuristics containsNegationInCaps(String text, Set<String> negations) {
        StatusCleaner cleaner = new StatusCleaner();
        text = cleaner.removeStartAndFinalApostrophs(text);
        text = cleaner.removePunctuationSigns(text).trim();

        for (String term : negations) {
            if (text.contains(term.toUpperCase())) {
                int indexStrongNegation = text.indexOf(term.toUpperCase());
                ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, indexStrongNegation, term, TypeOfToken.TypeOfTokenEnum.NGRAM);
                resultOneHeuristics.setConditionEnum(ConditionalExpression.ConditionEnum.isNegationInCaps);
                return resultOneHeuristics;
            }
        }
        return null;
    }

}
