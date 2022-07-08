/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtag;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtag {

    public static BooleanCondition check(boolean stripped, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtag);
        Set<String> hashtagsInList = lexiconsAndTheirConditionalExpressions.getMapH13().keySet();
        boolean found = hashtagsInList.contains(ngram.getCleanedAndStrippedNgramIfCondition(stripped));
        if (found) {
            booleanCondition.setTextFragmentMatched(ngram);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return booleanCondition;

    }
}
