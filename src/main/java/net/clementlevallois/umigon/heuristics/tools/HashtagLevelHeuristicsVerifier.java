/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.catalog.IsHashtagNegativeSentiment;
import net.clementlevallois.umigon.heuristics.catalog.IsHashtagPositiveSentiment;
import net.clementlevallois.umigon.heuristics.catalog.IsHashtag;
import net.clementlevallois.umigon.heuristics.catalog.IsInHashtag;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.Term;
import net.clementlevallois.umigon.model.TermWithConditionalExpressions;
import net.clementlevallois.umigon.model.Text;
import net.clementlevallois.umigon.model.TypeOfToken;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import net.clementlevallois.utils.StatusCleaner;

/**
 *
 * @author C. Levallois
 */
public class HashtagLevelHeuristicsVerifier {

    public static List<ResultOneHeuristics> check(LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions, Set<String> hashtags, Text text) {
        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        for (String hashtag : hashtags) {
            hashtag = StatusCleaner.flattenToAscii(hashtag);
            BooleanCondition booleanCondition1 = IsHashtagPositiveSentiment.check(hashtag, lexiconsAndTheirConditionalExpressions);
            if (booleanCondition1.getTokenInvestigatedGetsMatched()) {
                ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(hashtag, text.getOriginalForm().indexOf(hashtag), TypeOfToken.TypeOfTokenEnum.HASHTAG, Category.CategoryEnum._11);
                resultOneHeuristics.getBooleanConditions().add(booleanCondition1);
                resultsHeuristics.add(resultOneHeuristics);
            }
            BooleanCondition booleanCondition2 = IsHashtagNegativeSentiment.check(hashtag, lexiconsAndTheirConditionalExpressions);
            if (booleanCondition2.getTokenInvestigatedGetsMatched()) {
                ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(hashtag, text.getOriginalForm().indexOf(hashtag), TypeOfToken.TypeOfTokenEnum.HASHTAG, Category.CategoryEnum._12);
                resultOneHeuristics.getBooleanConditions().add(booleanCondition2);
                resultsHeuristics.add(resultOneHeuristics);
            }
            BooleanCondition booleanCondition3 = IsHashtag.check(hashtag, lexiconsAndTheirConditionalExpressions);
            if (booleanCondition3.getTokenInvestigatedGetsMatched()) {
                ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(hashtag, text.getOriginalForm().indexOf(hashtag), TypeOfToken.TypeOfTokenEnum.HASHTAG, Category.CategoryEnum._12);
                resultOneHeuristics.getBooleanConditions().add(booleanCondition3);
                resultsHeuristics.add(resultOneHeuristics);
            }

            Map<String, TermWithConditionalExpressions> hashtagLexicon = lexiconsAndTheirConditionalExpressions.getMapH13();
            Iterator<Map.Entry<String, TermWithConditionalExpressions>> iterator = hashtagLexicon.entrySet().iterator();
            boolean hashtagContainsRelevantTerm = false;
            TermWithConditionalExpressions termWithConditionalExpressions = null;
            while (iterator.hasNext()) {
                Map.Entry<String, TermWithConditionalExpressions> nextEntry = iterator.next();
                if (hashtag.toLowerCase().contains(nextEntry.getKey().toLowerCase())) {
                    hashtagContainsRelevantTerm = true;
                    termWithConditionalExpressions = nextEntry.getValue();
                    break;
                }
            }
            if (hashtagContainsRelevantTerm) {
                Term term = new Term();
                term.setOriginalForm(hashtag);
                term.setIndexOriginalForm(text.getOriginalForm().indexOf(hashtag));
                ResultOneHeuristics checkHeuristicsOnOneTerm = TermLevelHeuristicsVerifier.checkHeuristicsOnOneTerm(false, term, text, termWithConditionalExpressions, lexiconsAndTheirConditionalExpressions, TypeOfTokenEnum.HASHTAG);
                resultsHeuristics.add(checkHeuristicsOnOneTerm);
            }
        }
        return resultsHeuristics;

    }

}
