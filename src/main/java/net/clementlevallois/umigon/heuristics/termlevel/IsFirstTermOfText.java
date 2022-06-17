/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isFirstTermOfText;

/**
 *
 * @author LEVALLOIS
 */
public class IsFirstTermOfText {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isFirstTermOfText, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
        String[] terms = text.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        boolean cleanStart = false;
        for (String currTerm : terms) {
            if (!cleanStart & (currTerm.startsWith("RT") || currTerm.startsWith("@"))) {
                continue;
            } else {
                cleanStart = true;
            }
            sb.append(currTerm).append(" ");
            if (cleanStart) {
                break;
            }
        }
        String textWithCheckOnStart = sb.toString().trim();
        boolean res = textWithCheckOnStart.startsWith(termOrig);
        resultOneHeuristics.setTokenInvestigatedGetsMatched(res);
        return resultOneHeuristics;
    }
}
