/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.heuristics.tools.TextFragmentOps;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByOpinion;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededByOpinion {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededByOpinion);

        List<NGram> nGramsBeforeAnOrdinalIndex = TextFragmentOps.getNGramsBeforeAnOrdinalIndex(textFragmentsThatAreNGrams, ngram);

        Set<String> opinions = new HashSet();
        opinions.addAll(lexiconsAndTheirConditionalExpressions.getMapH1().keySet());
        opinions.addAll(lexiconsAndTheirConditionalExpressions.getMapH2().keySet());

        List<NGram> nGramsThatMatchedAnOpinion = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, nGramsBeforeAnOrdinalIndex, opinions);

        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedAnOpinion.isEmpty());
        if (!nGramsThatMatchedAnOpinion.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedAnOpinion);
            booleanCondition.setTextFragmentMatched(ngram);
        }

        return booleanCondition;
    }

}
