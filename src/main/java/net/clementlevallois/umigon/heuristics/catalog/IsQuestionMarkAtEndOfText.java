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
import net.clementlevallois.umigon.model.NGram;
import net.clementlevallois.umigon.model.TextFragment;
import net.clementlevallois.umigon.model.TypeOfTextFragment;

/**
 *
 * @author LEVALLOIS
 */
public class IsQuestionMarkAtEndOfText {

    public static BooleanCondition check(List<TextFragment> textFragments) {
        BooleanCondition booleanCondition = new BooleanCondition(isQuestionMarkAtEndOfText);

        List<TextFragment> workingList = new ArrayList();
        workingList.addAll(textFragments);

        TextFragment lastOne = workingList.get(workingList.size() - 1);
        boolean checksComplete = false;
        while (!checksComplete) {
            if (lastOne.getTypeOfTextFragment().equals(TypeOfTextFragment.TypeOfTextFragmentEnum.NGRAM)) {
                NGram curr = (NGram) lastOne;
                if (curr.getCleanedNgram().contains("/")) {
                    workingList.remove(workingList.size() - 1);
                } else {
                    checksComplete = true;
                }
            } else {
                checksComplete = true;
            }
        }
        lastOne = workingList.get(workingList.size() - 1);
        if (lastOne.getTypeOfTextFragment().equals(TypeOfTextFragment.TypeOfTextFragmentEnum.PUNCTUATION)) {
            if (lastOne.getString().equals("?")) {
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                booleanCondition.setTextFragmentMatched(lastOne);
            }
        }
        return booleanCondition;

    }
}
