/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInATextWithOneOfTheseSpecificTerms;

/**
 *
 * @author LEVALLOIS
 */
public class IsInATextWithOneOfTheseSpecificTerms {

    public static BooleanCondition check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isInATextWithOneOfTheseSpecificTerms, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        NGramFinder nGramFinder = new NGramFinder(text);

        Map<String, Integer> ngramsInMap = nGramFinder.runIt(2, true);
        if (ngramsInMap.isEmpty()) {
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return resultOneHeuristics;
        }

        Set<String> terms = ngramsInMap.keySet();
        Iterator<String> it = terms.iterator();

        while (it.hasNext()) {
            String next = it.next().trim();
            if (keywords.contains(next)) {
                resultOneHeuristics.setKeywordMatched(next);
                resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return resultOneHeuristics;
            }
        }
        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return resultOneHeuristics;
    }
}
