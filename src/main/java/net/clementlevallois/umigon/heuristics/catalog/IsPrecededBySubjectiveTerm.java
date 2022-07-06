/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededBySubjectiveTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededBySubjectiveTerm {

    public static BooleanCondition check(String text, String term, int indexTerm, LoaderOfLexiconsAndConditionalExpressions lexiconsandTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededBySubjectiveTerm);
        String left = text.substring(0, text.indexOf(term)).trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String element : ngrams) {
            if (lexiconsandTheirConditionalExpressions.getSetSubjective().contains(element.toLowerCase())) {
                booleanCondition.setTextFragmentMatched(element);
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return booleanCondition;
    }
}
