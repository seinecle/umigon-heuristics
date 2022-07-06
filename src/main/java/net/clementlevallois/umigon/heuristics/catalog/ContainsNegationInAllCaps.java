/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.Set;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.Term;
import net.clementlevallois.umigon.model.TextFragment;
import net.clementlevallois.umigon.model.TypeOfTextFragment;

/**
 *
 * @author LEVALLOIS
 */
public class ContainsNegationInAllCaps {

    public static BooleanCondition check(Term term, Set<String> negations) {
        BooleanCondition booleanCondition = new BooleanCondition(BooleanCondition.BooleanConditionEnum.isNegationInCaps);
        for (String negation : negations) {
            int indexOf = term.getCleanedAndStrippedForm().indexOf(negation.toUpperCase());
            if (indexOf!= -1) {
                TextFragment textFragmentMatched = new Term();
                textFragmentMatched.setString(negation.toUpperCase());
                int indexCardinalTextFragmentMatched = term.getIndexCardinal() + indexOf;
                textFragmentMatched.setIndexCardinal(indexCardinalTextFragmentMatched);
                textFragmentMatched.setIndexOrdinal(term.getIndexOrdinal());
                textFragmentMatched.setTypeOfTextFragment(TypeOfTextFragment.TypeOfTextFragmentEnum.TERM);
                booleanCondition.setTextFragmentMatched(textFragmentMatched);
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                return booleanCondition;
            }
        }
        return booleanCondition;
    }
}
