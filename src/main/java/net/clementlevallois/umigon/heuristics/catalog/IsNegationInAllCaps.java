/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsNegationInAllCaps {

    public static BooleanCondition check(NGram ngram, Set<String> negations) {
        BooleanCondition booleanCondition = new BooleanCondition(BooleanCondition.BooleanConditionEnum.isNegationInCaps);
        for (String negation : negations) {
            if (ngram.getCleanedAndStrippedNgram().equals(negation.toUpperCase())) {
                booleanCondition.setTextFragmentMatched(ngram);
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        return booleanCondition;
    }
}
