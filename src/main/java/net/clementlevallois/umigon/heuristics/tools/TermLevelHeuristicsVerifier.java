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
import net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.Term;
import net.clementlevallois.umigon.model.Text;
import net.clementlevallois.umigon.model.TypeOfToken;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import org.apache.commons.lang3.StringUtils;

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

    public static List<ResultOneHeuristics> checkTermHeuristicsOnNGrams(boolean useStrippedVersion, Term termParam, Text textParam, Map<String, TermWithConditionalExpressions> mapTermToRule, Set<String> alreadyExamined, LoaderOfLexiconsAndConditionalExpressions lexiconsWithTheirConditionalExpressions) {
        String term = useStrippedVersion ? termParam.getStrippedFormLowercase() : termParam.getOriginalFormLowercase();
        Set<String> nGrams = new NGramFinder(term).runIt(4, false).keySet();
        TermWithConditionalExpressions termWithConditionalExpressions;
        List<ResultOneHeuristics> resultsHeuristicsFinal = new ArrayList();

        for (String element : nGrams) {
            int indexElement = useStrippedVersion ? termParam.getIndexStrippedForm(): termParam.getIndexOriginalForm();
            if (alreadyExamined.contains(element.toLowerCase() + "_" + indexElement)) {
                continue;
            }
            Term termElement = new Term();
            termElement.setOriginalForm(element);
            termElement.setIndexOriginalForm(indexElement);
            termElement.setStrippedForm(element);
            termElement.setIndexStrippedForm(indexElement);            
            termWithConditionalExpressions = mapTermToRule.get(element);
            if (termWithConditionalExpressions != null) {
                List<ResultOneHeuristics> resultsHeuristics = check(useStrippedVersion, termElement, textParam, termWithConditionalExpressions, lexiconsWithTheirConditionalExpressions);
                resultsHeuristicsFinal.addAll(resultsHeuristics);
            }
        }
        return resultsHeuristicsFinal;
    }

    public static List<ResultOneHeuristics> check(boolean stripped, Term termParam, Text textParam, TermWithConditionalExpressions termWithConditionalExpressions, LoaderOfLexiconsAndConditionalExpressions lexiconsAndConditionalExpressions) {
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String termFromLexicon = termWithConditionalExpressions.getTerm();
        List<BooleanCondition> booleanConditions = termWithConditionalExpressions.getMapFeatures();
        String rule = termWithConditionalExpressions.getRule();
        String text = stripped ? textParam.getStrippedFormLowercase() : textParam.getCleanedFormLowercase();
        String term = stripped ? termParam.getStrippedFormLowercase() : termParam.getOriginalFormLowercase();
        int indexTerm = stripped ? termParam.getIndexStrippedForm() : termParam.getIndexOriginalForm();

        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        InterpreterOfConditionalExpressions interpreter = new InterpreterOfConditionalExpressions();
        Category cat;

        Map<String, Boolean> conditions = new HashMap();
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(term, indexTerm, TypeOfTokenEnum.NGRAM);
        text = text.toLowerCase();
        if (booleanConditions.size() == 1 && booleanConditions.get(0).getBooleanConditionEnum().equals(BooleanConditionEnum.none) & rule != null && !rule.isBlank() & StringUtils.isNumeric(rule)) {
            try {
                cat = new Category(rule);
                BooleanCondition booleanCondition = new BooleanCondition(BooleanCondition.BooleanConditionEnum.none);
                booleanCondition.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                resultOneHeuristics.setCategoryEnum(cat.getCategoryEnum());
                resultOneHeuristics.getBooleanConditions().add(booleanCondition);
                resultsHeuristics.add(resultOneHeuristics);
                return resultsHeuristics;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("rule was misspelled or just wrong:");
                System.out.println(rule);
                System.out.println("for term:");
                System.out.println(term);
                return resultsHeuristics;
            }
        }

        int count = 0;

        BooleanCondition.BooleanConditionEnum conditionEnum;
        Set<String> keywords;
        for (BooleanCondition booleanCondition : booleanConditions) {

            conditionEnum = booleanCondition.getBooleanConditionEnum();
            keywords = booleanCondition.getKeywords();

            boolean opposite = false;
            if (booleanCondition.getFlipped()) {
                opposite = true;
            }

            switch (conditionEnum) {

                case isImmediatelyPrecededByANegation:
                    booleanCondition = IsImmediatelyPrecededByANegation.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyFollowedByANegation:
                    booleanCondition = IsImmediatelyFollowedByANegation.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyPrecededBySpecificTerm:
                    booleanCondition = IsImmediatelyPrecededBySpecificTerm.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions, keywords);
                    break;

                case isImmediatelyFollowedBySpecificTerm:
                    booleanCondition = IsImmediatelyFollowedBySpecificTerm.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions, keywords);
                    break;

                case isImmediatelyFollowedByAnOpinion:
                    booleanCondition = IsImmediatelyFollowedByAnOpinion.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isPrecededBySubjectiveTerm:
                    booleanCondition = IsPrecededBySubjectiveTerm.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isFirstTermOfText:
                    booleanCondition = IsFirstTermOfText.check(text, term.toLowerCase());
                    break;

                case isFollowedByAPositiveOpinion:
                    booleanCondition = IsFollowedByAPositiveOpinion.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyFollowedByAPositiveOpinion:
                    booleanCondition = IsImmediatelyFollowedByAPositiveOpinion.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyFollowedByANegativeOpinion:
                    booleanCondition = IsImmediatelyFollowedByANegativeOpinion.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isFollowedBySpecificTerm:
                    booleanCondition = IsFollowedBySpecificTerm.check(text, term.toLowerCase(), keywords);
                    break;

                case isInATextWithOneOfTheseSpecificTerms:
                    booleanCondition = IsInATextWithOneOfTheseSpecificTerms.check(text, keywords);
                    break;

                case isHashtagStart:
                    booleanCondition = IsHashtagStart.check(term.toLowerCase(), termFromLexicon);
                    break;

                case isInHashtag:
                    booleanCondition = IsInHashtag.check(text, term.toLowerCase(), termFromLexicon, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isHashtagPositiveSentiment:
                    booleanCondition = IsHashtagPositiveSentiment.check(term.toLowerCase(), lexiconsAndConditionalExpressions);
                    break;

                case isHashtagNegativeSentiment:
                    booleanCondition = IsHashtagPositiveSentiment.check(term.toLowerCase(), lexiconsAndConditionalExpressions);
                    break;

                case isPrecededBySpecificTerm:
                    booleanCondition = IsPrecededBySpecificTerm.check(text, term.toLowerCase(), keywords);
                    break;

                case isQuestionMarkAtEndOfText:
                    booleanCondition = IsQuestionMarkAtEndOfText.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isAllCaps:
                    booleanCondition = IsAllCaps.check(term);
                    break;

                case isPrecededByStrongWord:
                    booleanCondition = IsPrecededByStrongWord.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyPrecededByPositive:
                    booleanCondition = IsImmediatelyPrecededByPositive.check(text, term.toLowerCase(), lexiconsAndConditionalExpressions);
                    break;

                case isPrecededByPositive:
                    booleanCondition = IsPrecededByPositive.check(text, term.toLowerCase(), lexiconsAndConditionalExpressions);
                    break;

                case isPrecededByOpinion:
                    booleanCondition = IsPrecededByOpinion.check(text, term.toLowerCase(), indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isFirstLetterCapitalized:
                    booleanCondition = IsFirstLetterCapitalized.check(term);
                    break;
            }
            if (opposite) {
                booleanCondition.setFlipped(Boolean.TRUE);
            }
            if (booleanCondition.getTokenInvestigatedGetsMatched() == null){
                System.out.println("stop a boolean expression didnt get a match result");
            }
            conditions.put(ALPHABET.substring(count, (count + 1)), booleanCondition.getTokenInvestigatedGetsMatched());
            count++;
            
            resultsHeuristics.add(resultOneHeuristics);
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
                ResultOneHeuristics resultAllConditions = new ResultOneHeuristics(cat.getCategoryEnum(), indexTerm, term, TypeOfTokenEnum.NGRAM);
                resultAllConditions.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                resultsHeuristics.add(resultAllConditions);

                return resultsHeuristics;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("outcome was misspelled or just wrong, after evaluating the heuristics:");
                System.out.println(result);
                return resultsHeuristics;
            }
        }
        return resultsHeuristics;
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
