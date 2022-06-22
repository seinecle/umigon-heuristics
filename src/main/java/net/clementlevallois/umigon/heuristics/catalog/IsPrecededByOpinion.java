/*
 * author: Cl�ment Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByOpinion;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededByOpinion {

    public static BooleanCondition check(String text, String term, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededByOpinion);
        String left = text.substring(0, text.indexOf(term)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();
        for (String element : ngrams) {
            if (heuristics.getMapH1().containsKey(element) || heuristics.getMapH2().containsKey(element)) {
                booleanCondition.setKeywordMatched(element);
                booleanCondition.setKeywordMatchedIndex(text.indexOf(element));
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return booleanCondition;
    }
}
