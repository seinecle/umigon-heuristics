/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByPositive;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededByPositive {

    public static BooleanCondition check(String text, String term, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededByPositive);
        String left = text.substring(0, text.indexOf(term)).trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String element : ngrams) {
            if (heuristics.getMapH1().containsKey(element.toLowerCase())) {
                booleanCondition.setKeywordMatched(element);
                booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(element.toLowerCase()));
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return booleanCondition;
    }
}
