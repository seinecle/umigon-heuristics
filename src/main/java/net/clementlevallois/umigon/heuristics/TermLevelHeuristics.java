/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.model.ConditionalExpression;
import net.clementlevallois.umigon.model.LexiconsAndConditionalExpressions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.heuristics.termlevel.IsAllCaps;
import net.clementlevallois.umigon.heuristics.termlevel.IsFirstLetterCapitalized;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyFollowedByANegation;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyFollowedByAnOpinion;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyFollowedBySpecificTerm;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyPrecededByANegation;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyPrecededBySpecificTerm;
import net.clementlevallois.umigon.heuristics.termlevel.IsFirstTermOfText;
import net.clementlevallois.umigon.heuristics.termlevel.IsFollowedByAPositiveOpinion;
import net.clementlevallois.umigon.heuristics.termlevel.IsFollowedBySpecificTerm;
import net.clementlevallois.umigon.heuristics.termlevel.IsHashtag;
import net.clementlevallois.umigon.heuristics.termlevel.IsHashtagStart;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyFollowedByANegativeOpinion;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyFollowedByAPositiveOpinion;
import net.clementlevallois.umigon.heuristics.termlevel.IsImmediatelyPrecededByPositive;
import net.clementlevallois.umigon.heuristics.termlevel.IsInATextWithOneOfTheseSpecificTerms;
import net.clementlevallois.umigon.heuristics.termlevel.IsInHashtag;
import net.clementlevallois.umigon.heuristics.termlevel.IsPrecededByPositive;
import net.clementlevallois.umigon.heuristics.termlevel.IsPrecededBySpecificTerm;
import net.clementlevallois.umigon.heuristics.termlevel.IsPrecededByStrongWord;
import net.clementlevallois.umigon.heuristics.termlevel.IsPrecededBySubjectiveTerm;
import net.clementlevallois.umigon.heuristics.termlevel.IsQuestionMarkAtEndOfText;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
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
public class TermLevelHeuristics {

    private String termHeuristic;
    private List<ConditionalExpression> conditionalExpressions;
    private String rule;
    private String text;
    private String termOrigCasePreserved;
    private String termOrig;
    private final LoaderOfLexiconsAndConditionalExpressions lexiconsAndConditionalExpressions;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    InterpreterOfConditionalExpressions interpreter;

    public TermLevelHeuristics(LoaderOfLexiconsAndConditionalExpressions lexiconsAndConditionalExpressions) {
        this.lexiconsAndConditionalExpressions = lexiconsAndConditionalExpressions;
    }

    public List<ResultOneHeuristics> checkFeatures(LexiconsAndConditionalExpressions heuristic, String textParam, String textStripped, String termOrig, int indexTerm, boolean stripped) {
        this.termHeuristic = heuristic.getTerm();
        this.conditionalExpressions = heuristic.getMapFeatures();
        this.rule = heuristic.getRule();
        this.text = stripped ? textStripped.toLowerCase() : textParam.toLowerCase();
        this.termOrig = termOrig.toLowerCase();
        this.termOrigCasePreserved = termOrig;

        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        interpreter = new InterpreterOfConditionalExpressions();
        Category cat;

        Map<String, Boolean> conditions = new HashMap();
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(null, indexTerm, termOrigCasePreserved, TypeOfTokenEnum.NGRAM);
        text = text.toLowerCase();
        if ((conditionalExpressions == null || conditionalExpressions.isEmpty()) & rule != null && !rule.isBlank() & StringUtils.isNumeric(rule)) {
            try {
                cat = new Category(rule);
                resultOneHeuristics = new ResultOneHeuristics(cat.getCategoryEnum(), indexTerm, termOrigCasePreserved, TypeOfTokenEnum.NGRAM);
                resultOneHeuristics.setTypeOfToken(TypeOfToken.TypeOfTokenEnum.NGRAM);
                resultsHeuristics.add(resultOneHeuristics);
                return resultsHeuristics;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("rule was misspelled or just wrong:");
                System.out.println(rule);
                System.out.println("for term:");
                System.out.println(termOrig);
                return resultsHeuristics;
            }
        }

        int count = 0;

        ConditionalExpression.ConditionEnum conditionEnum;
        Set<String> keywords;
        for (ConditionalExpression conditionalExpression : conditionalExpressions) {

            conditionEnum = conditionalExpression.getConditionEnum();
            keywords = conditionalExpression.getKeywords();

            boolean opposite = false;
            if (conditionalExpression.getFlipped()) {
                opposite = true;
            }

            switch (conditionEnum) {

                case isImmediatelyPrecededByANegation:
                    resultOneHeuristics = IsImmediatelyPrecededByANegation.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyFollowedByANegation:
                    resultOneHeuristics = IsImmediatelyFollowedByANegation.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyPrecededBySpecificTerm:
                    resultOneHeuristics = IsImmediatelyPrecededBySpecificTerm.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions, keywords);
                    break;

                case isImmediatelyFollowedBySpecificTerm:
                    resultOneHeuristics = IsImmediatelyFollowedBySpecificTerm.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions, keywords);
                    break;

                case isImmediatelyFollowedByAnOpinion:
                    resultOneHeuristics = IsImmediatelyFollowedByAnOpinion.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isPrecededBySubjectiveTerm:
                    resultOneHeuristics = IsPrecededBySubjectiveTerm.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isFirstTermOfText:
                    resultOneHeuristics = IsFirstTermOfText.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isFollowedByAPositiveOpinion:
                    resultOneHeuristics = IsFollowedByAPositiveOpinion.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyFollowedByAPositiveOpinion:
                    resultOneHeuristics = IsImmediatelyFollowedByAPositiveOpinion.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyFollowedByANegativeOpinion:
                    resultOneHeuristics = IsImmediatelyFollowedByANegativeOpinion.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isFollowedBySpecificTerm:
                    resultOneHeuristics = IsFollowedBySpecificTerm.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions, keywords);
                    break;

                case isInATextWithOneOfTheseSpecificTerms:
                    resultOneHeuristics = IsInATextWithOneOfTheseSpecificTerms.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions, keywords);
                    break;

                case isHashtagStart:
                    resultOneHeuristics = IsHashtagStart.check(text, termOrig, termHeuristic, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isInHashtag:
                    resultOneHeuristics = IsInHashtag.check(text, termOrig, termHeuristic, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isHashtag:
                    resultOneHeuristics = IsHashtag.check(text, termOrig, termHeuristic, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isPrecededBySpecificTerm:
                    resultOneHeuristics = IsPrecededBySpecificTerm.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions, keywords);
                    break;

                case isQuestionMarkAtEndOfText:
                    resultOneHeuristics = IsQuestionMarkAtEndOfText.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isAllCaps:
                    resultOneHeuristics = IsAllCaps.check(text, termOrig, termOrigCasePreserved, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isPrecededByStrongWord:
                    resultOneHeuristics = IsPrecededByStrongWord.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isImmediatelyPrecededByPositive:
                    resultOneHeuristics = IsImmediatelyPrecededByPositive.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isPrecededByPositive:
                    resultOneHeuristics = IsPrecededByPositive.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isPrecededByOpinion:
                    resultOneHeuristics = IsPrecededByPositive.check(text, termOrig, indexTerm, lexiconsAndConditionalExpressions);
                    break;

                case isFirstLetterCapitalized:
                    resultOneHeuristics = IsFirstLetterCapitalized.check(text, termOrig, termOrigCasePreserved, indexTerm, lexiconsAndConditionalExpressions);
                    break;
            }
            if (opposite) {
                resultOneHeuristics.setTokenInvestigatedGetsMatched(!resultOneHeuristics.getTokenInvestigatedGetsMatched());
            }
            conditions.put(ALPHABET.substring(count, (count + 1)), resultOneHeuristics.getTokenInvestigatedGetsMatched());
            count++;
        }

        String result = interpreter.interprete(rule, conditions);
        if (result != null && !result.isBlank()) {
            if (result.equals("-1")) {
                System.out.println("problem with this rule:");
                System.out.println("rule: " + rule);
                System.out.println("term: " + termOrigCasePreserved);
                System.out.println("text: " + text);
            }
            try {
                cat = new Category(result);
                resultsHeuristics.add(new ResultOneHeuristics(cat.getCategoryEnum(), indexTerm, termOrigCasePreserved, TypeOfTokenEnum.NGRAM));
                return resultsHeuristics;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("outcome was misspelled or just wrong, after evaluating the heuristics:");
                System.out.println(result);
                return resultsHeuristics;
            }
        }
        return resultsHeuristics;
    }

    public boolean isImmediatelyFollowedByVerbPastTense() {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            boolean pastTense;
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                pastTense = temp.endsWith("ed");
                if (pastTense) {
                    return true;
                }
                pastTense = temp.endsWith("ought") & !temp.startsWith("ought");
                return pastTense;
            } else {
                return false;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            return false;
        }

    }

    public boolean isFollowedBySpecificTerm(Set<String> parameters) {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            return parameters.stream().anyMatch((candidate) -> (temp.contains(candidate)));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            return false;
        }
    }

    public boolean isFirstLetterCapitalized() {
        if (!termOrigCasePreserved.isEmpty()) {
            return (Character.isUpperCase(termOrigCasePreserved.codePointAt(0)));
        } else {
            return false;
        }
    }

    public boolean isImmmediatelyPrecededBySpecificTerm(Set<String> parameters) {
        String[] temp = text.substring(0, text.indexOf(termOrig)).trim().split(" ");
        if (temp.length == 0) {
            return false;
        }
        if (temp.length > 0 && parameters.contains(temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else if (temp.length > 1 && parameters.contains(temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else if (temp.length > 2 && parameters.contains(temp[temp.length - 3].trim().toLowerCase() + " " + temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } // fait un peu chier
        // fait vraiment trop chier
        else if (temp.length > 2 && parameters.contains(temp[temp.length - 3].trim().toLowerCase())) {
            String intervalWord = temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase();
            if (lexiconsAndConditionalExpressions.getMapH3().containsKey(intervalWord)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isPrecededByStrongWord() {
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (lexiconsAndConditionalExpressions.getMapH3().containsKey(term)) {
                return true;
            }
        }
        return false;
    }

    public boolean isImmediatelyPrecededByPositive() {
        String[] temp = text.substring(0, text.indexOf(termOrig)).trim().split(" ");
        if (temp.length == 0) {
            return false;
        }

        if (temp.length > 0 && lexiconsAndConditionalExpressions.getMapH1().containsKey(temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else if (temp.length > 1 && lexiconsAndConditionalExpressions.getMapH1().containsKey(temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else if (temp.length > 2 && lexiconsAndConditionalExpressions.getMapH1().containsKey(temp[temp.length - 3].trim().toLowerCase() + " " + temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPrecededByPositive() {
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (lexiconsAndConditionalExpressions.getMapH1().containsKey(term)) {
                return true;
            }
        }
        return false;
    }

    public boolean isQuestionMarkAtEndOfText() {
        List<String> terms = new ArrayList();
        Collections.addAll(terms, text.trim().split(" "));
        StringBuilder sb = new StringBuilder();
        boolean cleanEnd = false;
        ListIterator<String> termsIterator = terms.listIterator(terms.size());
        while (termsIterator.hasPrevious() & !cleanEnd) {
            String string = termsIterator.previous();
            if (!cleanEnd && (string.contains("/") || string.startsWith("#") || string.startsWith("@") || string.equals("\\|") || string.equals("") || string.contains("via") || string.equals("..."))) {
                continue;
            } else {
                cleanEnd = true;
            }
            sb.insert(0, string);
        }
        String temp = sb.toString().trim();
        if (temp.isEmpty()) {
            return false;
        } else {
            return ("?".equals(String.valueOf(temp.charAt(temp.length() - 1))));
        }
    }

    public boolean isAllCaps() {
        String temp = termOrigCasePreserved.replaceAll(" ", "").trim();
        char[] charArray = temp.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isUpperCase(charArray[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isPrecededByOpinion() {
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();
        for (String term : ngrams) {
            if (lexiconsAndConditionalExpressions.getMapH1().containsKey(term)) {
                return true;
            }
        }
        for (String term : ngrams) {
            if (lexiconsAndConditionalExpressions.getMapH2().containsKey(term)) {
                return true;
            }
        }
        return false;
    }

}
