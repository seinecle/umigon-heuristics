/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Iterator;
import java.util.Map;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInHashtag;
import net.clementlevallois.umigon.model.TermWithConditionalExpressions;

/**
 *
 * @author LEVALLOIS
 */
public class IsInHashtag {

    public static BooleanCondition check(String hashtag, String text, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalEpxressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isInHashtag);
        Map<String, TermWithConditionalExpressions> hashtagLexicon = lexiconsAndTheirConditionalEpxressions.getMapH13();
        Iterator<Map.Entry<String, TermWithConditionalExpressions>> iterator = hashtagLexicon.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, TermWithConditionalExpressions> nextEntry = iterator.next();
            if (hashtag.toLowerCase().contains(nextEntry.getKey().toLowerCase())) {
                booleanCondition.setKeywordMatched(nextEntry.getKey().toLowerCase());
                booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(nextEntry.getKey().toLowerCase()));
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        return booleanCondition;
    }
}
