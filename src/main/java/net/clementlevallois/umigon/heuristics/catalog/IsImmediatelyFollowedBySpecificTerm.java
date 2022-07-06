/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyFollowedBySpecificTerm {

    public static BooleanCondition check(String text, String term, int indexTerm, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isImmediatelyFollowedBySpecificTerm);
        try {
            String temp = text.substring(text.indexOf(term) + term.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                boolean isNextTermRelevant = keywords.contains(temp.toLowerCase());
                if (isNextTermRelevant) {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setTextFragmentMatched(temp);
                    booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp.toLowerCase()));
                    return booleanCondition;
                } else if (nextTerms.length > 1) {
                    temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                    boolean found = keywords.contains(temp.toLowerCase());
                    if (found) {
                        booleanCondition.setTextFragmentMatched(temp);
                        booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp.toLowerCase()));
                    } else {
                        booleanCondition.setTextFragmentsAssociatedTotheBooleanCondition(keywords);
                    }
                    booleanCondition.setTokenInvestigatedGetsMatched(found);
                    return booleanCondition;
                } else if (nextTerms.length > 2) {
                    temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                    boolean found = keywords.contains(temp.toLowerCase());
                    if (found) {
                        booleanCondition.setTextFragmentMatched(temp);
                        booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(temp.toLowerCase()));
                    } else {
                        booleanCondition.setTextFragmentsAssociatedTotheBooleanCondition(keywords);
                    }
                    booleanCondition.setTokenInvestigatedGetsMatched(found);
                    return booleanCondition;
                } else {
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
                    booleanCondition.setTextFragmentsAssociatedTotheBooleanCondition(keywords);
                    return booleanCondition;
                }
            }
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            booleanCondition.setTextFragmentsAssociatedTotheBooleanCondition(keywords);
            return booleanCondition;
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + term);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
