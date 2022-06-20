/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.catalog.IsHashTagContainingAffectiveTerm;
import net.clementlevallois.umigon.heuristics.catalog.IsHashTagStartingWithAffectiveTerm;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.Text;

/**
 *
 * @author C. Levallois
 */
public class HashtagLevelHeuristicsVerifier {

    public static List<ResultOneHeuristics> check(LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions, Text text) {
        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        Set<String> hashtags = new HashSet();

        if (text.getOriginalForm().contains("#")) {
            String[] terms = text.getOriginalForm().split(" ");
            for (String string : terms) {
                if (string.startsWith("#")) {
                    hashtags.add(string.substring(1));
                }
            }
        }

        for (String hashtag : hashtags) {
            List<ResultOneHeuristics> checkAgainstListOfHashtags = IsHashTagContainingAffectiveTerm.check(lexiconsAndTheirConditionalExpressions, hashtag);
            resultsHeuristics.addAll(checkAgainstListOfHashtags);

            checkAgainstListOfHashtags = IsHashTagStartingWithAffectiveTerm.check(lexiconsAndTheirConditionalExpressions, hashtag);
            resultsHeuristics.addAll(checkAgainstListOfHashtags);

        }

        return resultsHeuristics;

    }

}
