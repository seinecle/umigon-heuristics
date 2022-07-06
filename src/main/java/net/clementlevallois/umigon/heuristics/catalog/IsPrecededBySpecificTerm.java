/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededBySpecificTerm {

    public static BooleanCondition check(String text, String term, int indexTerm, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededBySpecificTerm);
        try {
            String temp = text.substring(0, indexTerm).trim();
            boolean found = keywords.stream().anyMatch((candidate) -> {
                boolean contains = temp.toLowerCase().contains(candidate.toLowerCase());
                if (contains) {
                    booleanCondition.setTextFragmentMatched(candidate);
                    booleanCondition.setKeywordMatchedIndex(text.toLowerCase().indexOf(candidate.toLowerCase()));
                }
                return contains;
            });
            booleanCondition.setTokenInvestigatedGetsMatched(found);
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
