/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isQuestionMarkAtEndOfText;

/**
 *
 * @author LEVALLOIS
 */
public class IsQuestionMarkAtEndOfText {

    public static BooleanCondition check(String text) {
        BooleanCondition booleanCondition = new BooleanCondition(isQuestionMarkAtEndOfText);
        List<String> terms = new ArrayList();
        Collections.addAll(terms, text.trim().split(" "));
        StringBuilder sb = new StringBuilder();
        boolean cleanEnd = false;
        ListIterator<String> termsIterator = terms.listIterator(terms.size());
        while (termsIterator.hasPrevious() & !cleanEnd) {
            String string = termsIterator.previous();
            if (!cleanEnd && (string.contains("/") || string.startsWith("#") || string.startsWith("@") || string.equals("\\|") || string.equals("") || string.contains("via") || string.equals("..."))) {
                continue;
            } else {
                cleanEnd = true;
            }
            sb.insert(0, string);
        }
        String temp = sb.toString().trim();
        if (temp.isEmpty()) {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.FALSE);
            return booleanCondition;
        } else {
            boolean found = ("?".equals(String.valueOf(temp.charAt(temp.length() - 1))));
            booleanCondition.setTokenInvestigatedGetsMatched(found);
            return booleanCondition;
        }
    }
}
