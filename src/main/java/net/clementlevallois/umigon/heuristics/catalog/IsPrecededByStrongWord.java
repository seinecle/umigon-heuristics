/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByStrongWord;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededByStrongWord {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededByStrongWord);
        String left = text.substring(0, text.indexOf(termOrig)).trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (heuristics.getMapH3().containsKey(term.toLowerCase())) {
                booleanCondition.setTextFragmentMatched(term);
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return booleanCondition;
    }
}