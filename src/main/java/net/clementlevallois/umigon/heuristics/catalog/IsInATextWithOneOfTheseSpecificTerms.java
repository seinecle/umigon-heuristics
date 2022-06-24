/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInATextWithOneOfTheseSpecificTerms;

/**
 *
 * @author LEVALLOIS
 */
public class IsInATextWithOneOfTheseSpecificTerms {

    public static BooleanCondition check(String text, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isInATextWithOneOfTheseSpecificTerms);
        NGramFinder nGramFinder = new NGramFinder(text);

        Map<String, Integer> ngramsInMap = nGramFinder.runIt(2, true);
        if (ngramsInMap.isEmpty()) {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }

        Set<String> terms = ngramsInMap.keySet();
        Iterator<String> it = terms.iterator();

        while (it.hasNext()) {
            String next = it.next().trim();
            if (keywords.contains(next.toLowerCase())) {
                booleanCondition.setKeywordMatched(next);
                booleanCondition.setKeywordMatchedIndex(text.indexOf(next));
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        return booleanCondition;
    }
}
