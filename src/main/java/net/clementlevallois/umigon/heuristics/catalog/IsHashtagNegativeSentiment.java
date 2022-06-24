/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagNegativeSentiment;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtagNegativeSentiment {

    public static BooleanCondition check(String hashtag, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtagNegativeSentiment);
        boolean startsWithNegativeTerm = false;
        for (String term : lexiconsAndTheirConditionalExpressions.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : lexiconsAndTheirConditionalExpressions.getSetNegations()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                startsWithNegativeTerm = true;
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : lexiconsAndTheirConditionalExpressions.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH2().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && lexiconsAndTheirConditionalExpressions.getMapH2().get(term) != null) {
                if (lexiconsAndTheirConditionalExpressions.getMapH2().get(term).isHashtagRelevant() && !startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setKeywordMatched(hashtag);
                }
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH1().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && lexiconsAndTheirConditionalExpressions.getMapH1().get(term) != null) {
                if (lexiconsAndTheirConditionalExpressions.getMapH1().get(term).isHashtagRelevant() && startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setKeywordMatched(hashtag);
                }
            }
        }

        return booleanCondition;
    }
}
