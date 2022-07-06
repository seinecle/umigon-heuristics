/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics.tools;

import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.TermWithConditionalExpressions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.catalog.IsAllCaps;
import net.clementlevallois.umigon.heuristics.catalog.IsFirstLetterCapitalized;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyFollowedByANegation;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyFollowedByAnOpinion;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyFollowedBySpecificTerm;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyPrecededByANegation;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyPrecededBySpecificTerm;
import net.clementlevallois.umigon.heuristics.catalog.IsFirstTermOfText;
import net.clementlevallois.umigon.heuristics.catalog.IsFollowedByAPositiveOpinion;
import net.clementlevallois.umigon.heuristics.catalog.IsFollowedBySpecificTerm;
import net.clementlevallois.umigon.heuristics.catalog.IsHashtagPositiveSentiment;
import net.clementlevallois.umigon.heuristics.catalog.IsHashtagStart;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyFollowedByANegativeOpinion;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyFollowedByAPositiveOpinion;
import net.clementlevallois.umigon.heuristics.catalog.IsImmediatelyPrecededByPositive;
import net.clementlevallois.umigon.heuristics.catalog.IsInATextWithOneOfTheseSpecificTerms;
import net.clementlevallois.umigon.heuristics.catalog.IsInHashtag;
import net.clementlevallois.umigon.heuristics.catalog.IsPrecededByOpinion;
import net.clementlevallois.umigon.heuristics.catalog.IsPrecededByPositive;
import net.clementlevallois.umigon.heuristics.catalog.IsPrecededBySpecificTerm;
import net.clementlevallois.umigon.heuristics.catalog.IsPrecededByStrongWord;
import net.clementlevallois.umigon.heuristics.catalog.IsPrecededBySubjectiveTerm;
import net.clementlevallois.umigon.heuristics.catalog.IsQuestionMarkAtEndOfText;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.Category.CategoryEnum;
import net.clementlevallois.umigon.model.NGram;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.Term;
import net.clementlevallois.umigon.model.Text;
import net.clementlevallois.umigon.model.TextFragment;
import net.clementlevallois.umigon.model.TypeOfTextFragment.TypeOfTextFragmentEnum;

/*
 Copyright 2008-2013 Clement Levallois
 Authors : Clement Levallois <clementlevallois@gmail.com>
 Website : http://www.clementlevallois.net


 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2013 Clement Levallois. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s): Clement Levallois

 */
public class TermLevelHeuristicsVerifier {

    public static List<ResultOneHeuristics> checkHeuristicsOnNGrams(boolean useStrippedVersion, NGram ngramParam, List<NGram> textFragmentsThatAreNGrams, Map<String, TermWithConditionalExpressions> mapTermToRule, Set<String> alreadyExamined, LoaderOfLexiconsAndConditionalExpressions lexiconsWithTheirConditionalExpressions) {
        String term = useStrippedVersion ? ngramParam.getCleanedAndStrippedForm() : ngramParam.getString();
        Set<String> nGrams = new NGramFinder(term).runIt(4, false).keySet();
        TermWithConditionalExpressions termWithConditionalExpressions;
        List<ResultOneHeuristics> resultsHeuristicsFinal = new ArrayList();

        for (String element : nGrams) {
            int indexElement = useStrippedVersion ? textParam.getStrippedForm().indexOf(element) : textParam.getCleanedForm().indexOf(element);
            if (alreadyExamined.contains(element.toLowerCase() + "_" + indexElement)) {
                continue;
            }
            Term termElement = new Term();
            termElement.setString(element);
            termElement.setIndexOriginalForm(indexElement);
            termElement.setCleanedAndStrippedForm(element);
            termElement.setIndexStrippedForm(indexElement);
            termWithConditionalExpressions = mapTermToRule.get(element);
            if (termWithConditionalExpressions != null) {
                ResultOneHeuristics resultsOneHeuristics = checkHeuristicsOnOneTerm(useStrippedVersion, termElement, textParam, termWithConditionalExpressions, lexiconsWithTheirConditionalExpressions, TypeOfTextFragmentEnum.NGRAM);
                resultsHeuristicsFinal.add(resultsOneHeuristics);
            }
        }
        return resultsHeuristicsFinal;
    }

    public static ResultOneHeuristics checkHeuristicsOnOneTerm(NGram ngramParam, List<NGram> textFragmentsThatAreNGrams, TermWithConditionalExpressions termWithConditionalExpressions, LoaderOfLexiconsAndConditionalExpressions lexiconsAndConditionalExpressions) {
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String termFromLexicon = termWithConditionalExpressions.getTerm();
        List<BooleanCondition> booleanConditions = termWithConditionalExpressions.getMapFeatures();
        String rule = termWithConditionalExpressions.getRule();

        InterpreterOfConditionalExpressions interpreter = new InterpreterOfConditionalExpressions();
        Category cat;

        Map<String, Boolean> conditions = new HashMap();
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(ngramParam);
        if (booleanConditions.isEmpty()) {
            try {
                cat = new Category(rule);
                BooleanCondition booleanCondition = new BooleanCondition(BooleanCondition.BooleanConditionEnum.none);
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                resultOneHeuristics.setCategoryEnum(cat.getCategoryEnum());
                resultOneHeuristics.getBooleanConditions().add(booleanCondition);
                return resultOneHeuristics;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("rule was misspelled or just wrong:");
                System.out.println(rule);
                System.out.println("for term:");
                System.out.println(ngramParam.getCleanedNgram());
                return resultOneHeuristics;
            }
        }

        BooleanCondition.BooleanConditionEnum conditionEnum;
        Set<TextFragment> textFragmentsAssociatedToTheBooleanCondition;
        

        int numberOfExaminationsOfBooleanConditions = 1;
        String nGramStripped = ngramParam.getCleanedAndStrippedNgramIfCondition(true);
        String nGramNonStripped = ngramParam.getCleanedAndStrippedNgramIfCondition(false);
        if (!nGramStripped.equals(nGramNonStripped)) {
            numberOfExaminationsOfBooleanConditions = 2;
        }
        
        boolean stripped = true;

        for (int i = 1; i <= numberOfExaminationsOfBooleanConditions; i++) {
            /*
            a little trick to achieve the following effect:
            - the first loop will examine the NON stripped version of the ngram
            - the second loop will examine the stripped version of the ngram
            - when there is just one loop, only the NON stripped version of the ngram is examined            
            */

            stripped = !stripped;
            for (BooleanCondition booleanCondition : booleanConditions) {

                conditionEnum = booleanCondition.getBooleanConditionEnum();
                textFragmentsAssociatedToTheBooleanCondition = booleanCondition.getTextFragmentsAssociatedTotheBooleanCondition();

                boolean opposite = false;
                if (booleanCondition.getFlipped()) {
                    opposite = true;
                }

                switch (conditionEnum) {

                    case isImmediatelyPrecededByANegation:
                        booleanCondition = IsImmediatelyPrecededByANegation.check(stripped, textFragmentsThatAreNGrams, ngramParam, lexiconsAndConditionalExpressions);
                        break;

                    case isImmediatelyFollowedByANegation:
                        booleanCondition = IsImmediatelyFollowedByANegation.check(stripped, textFragmentsThatAreNGrams, ngramParam, lexiconsAndConditionalExpressions);
                        break;

                    case isImmediatelyPrecededBySpecificTerm:
                        booleanCondition = IsImmediatelyPrecededBySpecificTerm.check(stripped, textFragmentsThatAreNGrams, ngramParam, lexiconsAndConditionalExpressions, textFragmentsAssociatedToTheBooleanCondition);
                        break;

                    case isImmediatelyFollowedBySpecificTerm:
                        booleanCondition = IsImmediatelyFollowedBySpecificTerm.check(text, term, indexTerm, lexiconsAndConditionalExpressions, textFragmentsAssociatedToTheBooleanCondition);
                        break;

                    case isImmediatelyFollowedByAnOpinion:
                        booleanCondition = IsImmediatelyFollowedByAnOpinion.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isPrecededBySubjectiveTerm:
                        booleanCondition = IsPrecededBySubjectiveTerm.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isFirstTermOfText:
                        booleanCondition = IsFirstTermOfText.check(text, term);
                        break;

                    case isFollowedByAPositiveOpinion:
                        booleanCondition = IsFollowedByAPositiveOpinion.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isImmediatelyFollowedByAPositiveOpinion:
                        booleanCondition = IsImmediatelyFollowedByAPositiveOpinion.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isImmediatelyFollowedByANegativeOpinion:
                        booleanCondition = IsImmediatelyFollowedByANegativeOpinion.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isFollowedBySpecificTerm:
                        booleanCondition = IsFollowedBySpecificTerm.check(text, term, indexTerm, textFragmentsAssociatedToTheBooleanCondition);
                        break;

                    case isInATextWithOneOfTheseSpecificTerms:
                        booleanCondition = IsInATextWithOneOfTheseSpecificTerms.check(text, textFragmentsAssociatedToTheBooleanCondition);
                        break;

                    case isHashtagStart:
                        booleanCondition = IsHashtagStart.check(term, termFromLexicon);
                        break;

                    case isInHashtag:
                        booleanCondition = IsInHashtag.check(term, text, lexiconsAndConditionalExpressions);
                        break;

                    case isHashtagPositiveSentiment:
                        booleanCondition = IsHashtagPositiveSentiment.check(term, lexiconsAndConditionalExpressions);
                        break;

                    case isHashtagNegativeSentiment:
                        booleanCondition = IsHashtagPositiveSentiment.check(term, lexiconsAndConditionalExpressions);
                        break;

                    case isPrecededBySpecificTerm:
                        booleanCondition = IsPrecededBySpecificTerm.check(text, term, indexTerm, textFragmentsAssociatedToTheBooleanCondition);
                        break;

                    case isQuestionMarkAtEndOfText:
                        booleanCondition = IsQuestionMarkAtEndOfText.check(text);
                        break;

                    case isAllCaps:
                        booleanCondition = IsAllCaps.check(term);
                        break;

                    case isPrecededByStrongWord:
                        booleanCondition = IsPrecededByStrongWord.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isImmediatelyPrecededByPositive:
                        booleanCondition = IsImmediatelyPrecededByPositive.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isPrecededByPositive:
                        booleanCondition = IsPrecededByPositive.check(text, term, lexiconsAndConditionalExpressions);
                        break;

                    case isPrecededByOpinion:
                        booleanCondition = IsPrecededByOpinion.check(text, term, indexTerm, lexiconsAndConditionalExpressions);
                        break;

                    case isFirstLetterCapitalized:
                        booleanCondition = IsFirstLetterCapitalized.check(term);
                        break;
                }

                boolean booleanOutcomeAfterPossibleInversion;
                if (opposite) {
                    booleanCondition.setFlipped(Boolean.TRUE);
                    booleanOutcomeAfterPossibleInversion = !booleanCondition.getTokenInvestigatedGetsMatched();
                } else {
                    booleanOutcomeAfterPossibleInversion = booleanCondition.getTokenInvestigatedGetsMatched();
                }
                if (booleanCondition.getTokenInvestigatedGetsMatched() == null) {
                    System.out.println("stop: a boolean expression didnt get a match result in Term Heuristics Verifier");
                }
                conditions.put(ALPHABET.substring(numberOfExaminationsOfBooleanConditions, (numberOfExaminationsOfBooleanConditions + 1)), booleanOutcomeAfterPossibleInversion);
                numberOfExaminationsOfBooleanConditions++;
                resultOneHeuristics.getBooleanConditions().add(booleanCondition);
            }
        }

        String result = interpreter.interprete(rule, conditions);
        if (result != null && !result.isBlank()) {
            if (result.equals("-1")) {
                System.out.println("problem with this rule:");
                System.out.println("rule: " + rule);
                System.out.println("term: " + term);
                System.out.println("text: " + text);
            }
            try {
                cat = new Category(result);
                resultOneHeuristics.setCategoryEnum(cat.getCategoryEnum());
                return resultOneHeuristics;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("outcome was misspelled or just wrong, after evaluating the heuristics:");
                System.out.println(result);
                return new ResultOneHeuristics(CategoryEnum._10, indexTerm, term, TypeOfTextFragmentEnum.NGRAM);
            }
        }
        return new ResultOneHeuristics(term, indexTerm, TypeOfTextFragmentEnum.NGRAM);
    }

//    public boolean isImmediatelyFollowedByVerbPastTense() {
//        try {
//            String temp = text.substring(text.indexOf(term) + term.length()).trim();
//            boolean pastTense;
//            String[] nextTerms = temp.split(" ");
//            if (nextTerms.length > 0) {
//                temp = nextTerms[0].trim();
//                pastTense = temp.endsWith("ed");
//                if (pastTense) {
//                    return true;
//                }
//                pastTense = temp.endsWith("ought") & !temp.startsWith("ought");
//                return pastTense;
//            } else {
//                return false;
//            }
//        } catch (StringIndexOutOfBoundsException e) {
//            System.out.println(e.getMessage());
//            System.out.println("status was: " + text);
//            System.out.println("term was: " + term);
//            return false;
//        }
//    }
}
