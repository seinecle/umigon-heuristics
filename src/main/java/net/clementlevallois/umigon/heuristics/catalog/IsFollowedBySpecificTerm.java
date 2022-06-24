/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFollowedBySpecificTerm;

/**
 *
 * @author LEVALLOIS
 */
public class IsFollowedBySpecificTerm {

    public static BooleanCondition check(String text, String term, int termIndex, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isFollowedBySpecificTerm);
        try {
            String temp = text.substring(termIndex + term.length()).trim();
            keywords.stream().anyMatch((candidate) -> {
                int index = temp.toLowerCase().indexOf(candidate.toLowerCase());
                if (index != -1) {
                    booleanCondition.setKeywordMatched(candidate);
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    booleanCondition.setKeywordMatchedIndex(index);
                }
                return (index != -1);
            });
            
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
