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

    public static BooleanCondition check(String text, String termOrig, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isFollowedBySpecificTerm);
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            keywords.stream().anyMatch((candidate) -> {
                boolean contains = temp.contains(candidate);
                if (contains) {
                    booleanCondition.setKeywordMatched(candidate);
                    booleanCondition.setKeywordMatchedIndex(temp.indexOf(candidate));
                    booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                }
                return contains;
            });
            
            return booleanCondition;

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        }
    }
}
