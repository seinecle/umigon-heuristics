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

    public static BooleanCondition check(String hashtag, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtagNegativeSentiment);
        boolean startsWithNegativeTerm = false;
        for (String term : heuristics.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : heuristics.getSetNegations()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                startsWithNegativeTerm = true;
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : heuristics.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }

        for (String term : heuristics.getMapH2().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && heuristics.getMapH2().get(term) != null) {
                if (heuristics.getMapH2().get(term).isHashtagRelevant() && !startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                }
            }
        }

        for (String term : heuristics.getMapH1().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && heuristics.getMapH1().get(term) != null) {
                if (heuristics.getMapH1().get(term).isHashtagRelevant() && startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                }
            }
        }

        return booleanCondition;
    }
}
