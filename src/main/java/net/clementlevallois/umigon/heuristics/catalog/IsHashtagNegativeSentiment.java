/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagNegativeSentiment;
import net.clementlevallois.umigon.model.Term;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtagNegativeSentiment {

    public static BooleanCondition check(Term hashtag, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isHashtagNegativeSentiment);
        String cleanedAndStrippedForm = hashtag.getCleanedAndStrippedForm();
        boolean startsWithNegativeTerm = false;
        for (String term : lexiconsAndTheirConditionalExpressions.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (cleanedAndStrippedForm.startsWith(term)) {
                cleanedAndStrippedForm = cleanedAndStrippedForm.replace(term, "");
            }
        }
        for (String term : lexiconsAndTheirConditionalExpressions.getSetNegations()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (cleanedAndStrippedForm.startsWith(term)) {
                startsWithNegativeTerm = true;
                cleanedAndStrippedForm = cleanedAndStrippedForm.replace(term, "");
            }
        }
        for (String term : lexiconsAndTheirConditionalExpressions.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (cleanedAndStrippedForm.startsWith(term)) {
                cleanedAndStrippedForm = cleanedAndStrippedForm.replace(term, "");
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH2().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (cleanedAndStrippedForm.startsWith(term) && lexiconsAndTheirConditionalExpressions.getMapH2().get(term) != null) {
                if (lexiconsAndTheirConditionalExpressions.getMapH2().get(term).isHashtagRelevant() && !startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setTextFragmentMatched(hashtag);
                }
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH1().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (cleanedAndStrippedForm.startsWith(term) && lexiconsAndTheirConditionalExpressions.getMapH1().get(term) != null) {
                if (lexiconsAndTheirConditionalExpressions.getMapH1().get(term).isHashtagRelevant() && startsWithNegativeTerm) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setTextFragmentMatched(hashtag);
                }
            }
        }
        return booleanCondition;
    }
}
