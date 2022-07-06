/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtag;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtag {

    public static BooleanCondition check(String hashtag, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtag);
        Set<String> hashtagsInList = lexiconsAndTheirConditionalExpressions.getMapH13().keySet();
        boolean found = hashtagsInList.contains(hashtag.toLowerCase());
        if (found) {
            booleanCondition.setTextFragmentMatched(hashtag);
            booleanCondition.setKeywordMatchedIndex(-1);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return booleanCondition;

    }
}
