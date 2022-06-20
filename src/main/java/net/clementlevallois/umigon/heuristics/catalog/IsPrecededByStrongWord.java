/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isPrecededByStrongWord;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededByStrongWord {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isPrecededByStrongWord, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (heuristics.getMapH3().containsKey(term)) {
                resultOneHeuristics.setKeywordMatched(term);
                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return resultOneHeuristics;
            }
        }
        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return resultOneHeuristics;
    }
}