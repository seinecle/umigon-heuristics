/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.HeuristicsLoaderOnDemand;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.heuristics.ConditionalExpression.ConditionEnum.isPrecededBySubjectiveTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededBySubjectiveTerm {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, HeuristicsLoaderOnDemand heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isPrecededBySubjectiveTerm, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (heuristics.getMapH1().containsKey(term)) {
                resultOneHeuristics.setKeywordMatched(term);
                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return resultOneHeuristics;
            }
        }
        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return resultOneHeuristics;
    }
}
