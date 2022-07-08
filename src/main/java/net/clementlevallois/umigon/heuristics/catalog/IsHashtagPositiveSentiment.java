/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagPositiveSentiment;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtagPositiveSentiment {

    public static BooleanCondition check(boolean stripped, NGram hashtag, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtagPositiveSentiment);
        boolean startsWithNegativeTerm = false;
        String ngramAsString = hashtag.getCleanedAndStrippedNgramIfCondition(stripped);
        for (String term : lexiconsAndTheirConditionalExpressions.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (ngramAsString.startsWith(term)) {
                ngramAsString = ngramAsString.replace(term, "");
            }
        }
        for (String term : lexiconsAndTheirConditionalExpressions.getSetNegations()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (ngramAsString.startsWith(term)) {
                startsWithNegativeTerm = true;
                ngramAsString = ngramAsString.replace(term, "");
            }
        }
        for (String term : lexiconsAndTheirConditionalExpressions.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (ngramAsString.startsWith(term)) {
                ngramAsString = ngramAsString.replace(term, "");
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH1().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (ngramAsString.startsWith(term) && lexiconsAndTheirConditionalExpressions.getMapH1().get(term) != null) {
                if (lexiconsAndTheirConditionalExpressions.getMapH1().get(term).isHashtagRelevant() && !startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setTextFragmentMatched(hashtag);
                }
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH2().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (ngramAsString.startsWith(term) && lexiconsAndTheirConditionalExpressions.getMapH2().get(term) != null) {
                if (lexiconsAndTheirConditionalExpressions.getMapH2().get(term).isHashtagRelevant() && startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setTextFragmentMatched(hashtag);
                }
            }
        }

        return booleanCondition;
    }
}
