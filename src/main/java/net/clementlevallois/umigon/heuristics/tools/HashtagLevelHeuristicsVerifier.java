/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics.tools;

import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.heuristics.catalog.IsHashtagNegativeSentiment;
import net.clementlevallois.umigon.heuristics.catalog.IsHashtagPositiveSentiment;
import net.clementlevallois.umigon.heuristics.catalog.IsInHashtag;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.NGram;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TermWithConditionalExpressions;

/**
 *
 * @author C. Levallois
 */
public class HashtagLevelHeuristicsVerifier {

    public static List<ResultOneHeuristics> check(LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions, List<NGram> termsThatAreHashTag) {
        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        boolean stripped = false;
        int loopCountMax = 2;
        for (int i = 1; i <= loopCountMax; i++) {
            stripped = !stripped;
            for (NGram hashtag : termsThatAreHashTag) {
                BooleanCondition booleanCondition1 = IsHashtagPositiveSentiment.check(stripped, hashtag, lexiconsAndTheirConditionalExpressions);
                if (booleanCondition1.getTokenInvestigatedGetsMatched()) {
                    ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._11, hashtag);
                    resultOneHeuristics.getBooleanConditions().add(booleanCondition1);
                    resultsHeuristics.add(resultOneHeuristics);
                }
                BooleanCondition booleanCondition2 = IsHashtagNegativeSentiment.check(stripped, hashtag, lexiconsAndTheirConditionalExpressions);
                if (booleanCondition2.getTokenInvestigatedGetsMatched()) {
                    ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, hashtag);
                    resultOneHeuristics.getBooleanConditions().add(booleanCondition2);
                    resultsHeuristics.add(resultOneHeuristics);
                }

                BooleanCondition booleanCondition4 = IsInHashtag.check(stripped, hashtag, lexiconsAndTheirConditionalExpressions);
                if (booleanCondition4.getTokenInvestigatedGetsMatched()) {
                    /* highly fragile procedure:
                - we take the term that was matched in the boolean condition
                - to retrieve the associated conditional expression and rule
                - so that we can add this rule to the result of the heuristics
                This is FRAGILE because it assumes there is a numeric rule (such as "12"), not something like 12:11
                - a non numeric rule would not break the code though, it would fall back on setting a CategoryEnum 10 to the heuristics.
                     */

                    TermWithConditionalExpressions termWithConditionalExpressions = lexiconsAndTheirConditionalExpressions.getMapH13().get(booleanCondition4.getTextFragmentMatched().getString().toLowerCase());
                    ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, hashtag);
                    resultOneHeuristics.getBooleanConditions().add(booleanCondition4);
                    resultOneHeuristics.setCategoryEnum(new Category(termWithConditionalExpressions.getRule()).getCategoryEnum());
                    resultsHeuristics.add(resultOneHeuristics);
                }
            }
        }
        return resultsHeuristics;

    }

}
