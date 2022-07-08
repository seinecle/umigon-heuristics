/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagStart;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtagStart {

    public static BooleanCondition check(boolean stripped, NGram ngram, String termHeuristic) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtagStart);
        boolean found = ngram.getCleanedAndStrippedNgramIfCondition(stripped).toLowerCase().startsWith(termHeuristic.toLowerCase());
        if (found) {
            booleanCondition.setTextFragmentMatched(ngram);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return booleanCondition;
    }
}
