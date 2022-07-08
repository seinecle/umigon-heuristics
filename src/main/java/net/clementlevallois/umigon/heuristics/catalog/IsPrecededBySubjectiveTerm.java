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
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededBySubjectiveTerm;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsPrecededBySubjectiveTerm {

    public static BooleanCondition check(boolean stripped, List<NGram> textFragmentsThatAreNGrams, NGram ngram, LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions) {
        BooleanCondition booleanCondition = new BooleanCondition(isPrecededBySubjectiveTerm);

        List<NGram> nGramsBeforeAnOrdinalIndex = TextFragmentOps.getNGramsBeforeAnOrdinalIndex(textFragmentsThatAreNGrams, ngram);

        Set<String> subjectiveWords = new HashSet();
        subjectiveWords.addAll(lexiconsAndTheirConditionalExpressions.getSetSubjective());

        List<NGram> nGramsThatMatchedASubjectiveWord = TextFragmentOps.checkIfListOfNgramsMatchStringsFromCollection(stripped, nGramsBeforeAnOrdinalIndex, subjectiveWords);

        booleanCondition.setTokenInvestigatedGetsMatched(!nGramsThatMatchedASubjectiveWord.isEmpty());
        if (!nGramsThatMatchedASubjectiveWord.isEmpty()) {
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatchedASubjectiveWord);
            booleanCondition.setTextFragmentMatched(ngram);
        }

        return booleanCondition;
    }
}
