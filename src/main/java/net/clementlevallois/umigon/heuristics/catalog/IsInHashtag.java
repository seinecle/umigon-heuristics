/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInHashtag;

/**
 *
 * @author LEVALLOIS
 */
public class IsInHashtag {

    public static BooleanCondition check(String hashtag, int indexHashtag, String termFromLexicon) {
        BooleanCondition booleanCondition = new BooleanCondition(isInHashtag);
        if (hashtag.contains(termFromLexicon.toLowerCase())) {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            booleanCondition.setKeywordMatched(hashtag);
            booleanCondition.setKeywordMatchedIndex(indexHashtag);
            return booleanCondition;
        }
        return booleanCondition;
    }
}
