/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Iterator;
import java.util.Map;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInHashtag;
import net.clementlevallois.umigon.model.Hashtag;
import net.clementlevallois.umigon.model.NGram;
import net.clementlevallois.umigon.model.TermWithConditionalExpressions;
import net.clementlevallois.umigon.model.TextFragment;

/**
 *
 * @author LEVALLOIS
 */
public class IsInHashtag {

    public static BooleanCondition check(boolean stripped, NGram hashtag, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalEpxressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isInHashtag);
        Map<String, TermWithConditionalExpressions> hashtagLexicon = lexiconsAndTheirConditionalEpxressions.getMapH13();
        Iterator<Map.Entry<String, TermWithConditionalExpressions>> iterator = hashtagLexicon.entrySet().iterator();
        String hashTagStringToLowerCase = hashtag.getCleanedAndStrippedNgramIfCondition(stripped).toLowerCase();
        while (iterator.hasNext()) {
            Map.Entry<String, TermWithConditionalExpressions> nextEntry = iterator.next();
            int indexCardinalOfWordInHashtag = hashTagStringToLowerCase.indexOf(nextEntry.getKey().toLowerCase());
            if (indexCardinalOfWordInHashtag != -1) {
                TextFragment textFragmentMatched = new Hashtag();
                textFragmentMatched.setOriginalForm(nextEntry.getKey());
                int indexCardinalTextFragmentMatched = hashtag.getIndexCardinal() + indexCardinalOfWordInHashtag;
                textFragmentMatched.setIndexCardinal(indexCardinalTextFragmentMatched);
                textFragmentMatched.setIndexOrdinal(hashtag.getIndexOrdinal());
                booleanCondition.setTextFragmentMatched(textFragmentMatched);
                booleanCondition.getAssociatedKeywordMatchedAsNGrams().add(hashtag);
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        return booleanCondition;
    }
}
