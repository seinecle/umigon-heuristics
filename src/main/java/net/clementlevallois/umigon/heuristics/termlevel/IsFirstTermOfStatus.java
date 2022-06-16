/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.HeuristicsLoaderOnDemand;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.heuristics.ConditionalExpression.ConditionEnum.isFirstTermOfStatus;

/**
 *
 * @author LEVALLOIS
 */
public class IsFirstTermOfStatus {

    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, HeuristicsLoaderOnDemand heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isFirstTermOfStatus, termOrig, indexTerm, TypeOfTokenEnum.NGRAM);
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
