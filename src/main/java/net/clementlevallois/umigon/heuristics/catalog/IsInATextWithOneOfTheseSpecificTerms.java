/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInATextWithOneOfTheseSpecificTerms;
import net.clementlevallois.umigon.model.NGram;

/**
 *
 * @author LEVALLOIS
 */
public class IsInATextWithOneOfTheseSpecificTerms {

    public static BooleanCondition check(boolean stripped, NGram ngram, List<NGram> ngrams, Set<String> keywords) {
        BooleanCondition booleanCondition = new BooleanCondition(isInATextWithOneOfTheseSpecificTerms);
        List<NGram> nGramsThatMatched = new ArrayList();
        for (NGram ngramLoop : ngrams) {
            if (keywords.contains(ngramLoop.getCleanedAndStrippedNgramIfCondition(stripped))) {
                nGramsThatMatched.add(ngramLoop);
            }
        }
        if (nGramsThatMatched.isEmpty()) {
            return booleanCondition;
        } else {
            booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
            booleanCondition.setTextFragmentMatched(ngram);
            booleanCondition.setAssociatedKeywordMatchedAsTextFragment(nGramsThatMatched);
            return booleanCondition;
        }
    }
}
