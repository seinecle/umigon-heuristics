/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.heuristics.model.ConditionalExpression;
import net.clementlevallois.umigon.heuristics.model.LexiconsAndConditionalExpressions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import net.clementlevallois.ngramops.NGramFinder;
import net.clementlevallois.umigon.model.Categories.Category;
import net.clementlevallois.umigon.model.CategoryAndIndex;

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
    private List<ConditionalExpression> features;
    private String rule;
    private String text;
    private String termOrigCasePreserved;
    private String termOrig;
    private int indexTerm;
    private String lang;
    private HeuristicsLoaderOnDemand heuristics;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    InterpreterOfConditionalExpressions interpreter;

    public TermLevelHeuristics(HeuristicsLoaderOnDemand heuristics) {
        this.heuristics = heuristics;
    }

    public List<CategoryAndIndex> checkFeatures(LexiconsAndConditionalExpressions heuristic, String textParam, String textStripped, String termOrig, int indexTerm, boolean stripped) {
        this.termHeuristic = heuristic.getTerm();
        this.features = heuristic.getMapFeatures();
        this.rule = heuristic.getRule();
        this.text = stripped ? textStripped.toLowerCase() : textParam.toLowerCase();
        this.termOrig = termOrig.toLowerCase();
        this.termOrigCasePreserved = termOrig;
        this.indexTerm = indexTerm;

        List<CategoryAndIndex> cats = new ArrayList();

        interpreter = new InterpreterOfConditionalExpressions();
        Category cat;

        Map<String, Boolean> conditions = new HashMap();
        boolean outcome = false;
        text = text.toLowerCase();
        if ((features == null || features.isEmpty()) & rule != null && !rule.isBlank()) {
            try {
                cat = Category.valueOf("_" + rule);
                cats.add(new CategoryAndIndex(cat, indexTerm));
                return cats;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("rule was misspelled or just wrong:");
                System.out.println(rule);
                return cats;
            }
        }

        int count = 0;

        String condition;
        Set<String> keywords;
        for (ConditionalExpression feature : features) {

            condition = feature.getCondition();
            keywords = feature.getKeywords();

            boolean opposite = false;
            if (condition.startsWith("!")) {
                opposite = true;
                condition = condition.substring(1);
            }

            switch (condition) {

                case "isImmediatelyPrecededByANegation":
                    outcome = isImmediatelyPrecededByANegation();
                    break;

                case "isImmediatelyFollowedByANegation":
                    outcome = isImmediatelyFollowedByANegation();
                    break;

                case "isImmediatelyPrecededBySpecificTerm":
                    outcome = isImmediatelyPrecededBySpecificTerm(keywords);
                    break;

                case "isImmediatelyFollowedBySpecificTerm":
                    outcome = isImmediatelyFollowedBySpecificTerm(keywords);
                    break;

                case "isImmediatelyFollowedByAnOpinion":
                    outcome = isImmediatelyFollowedByAnOpinion();
                    break;

                case "isPrecededBySubjectiveTerm":
                    outcome = isPrecededBySubjectiveTerm();
                    break;

                case "isFirstTermOfStatus":
                    outcome = isFirstTermOfStatus();
                    break;

                case "isFollowedByAPositiveOpinion":
                    outcome = isFollowedByAPositiveOpinion();
                    break;

                case "isImmediatelyFollowedByAPositiveOpinion":
                    outcome = isImmediatelyFollowedByAPositiveOpinion();
                    break;

                case "isImmediatelyFollowedByANegativeOpinion":
                    outcome = isImmediatelyFollowedByANegativeOpinion();
                    break;

                case "isFollowedBySpecificTerm":
                    outcome = isFollowedBySpecificTerm(keywords);
                    break;

                case "isInAStatusWithOneOfTheseSpecificTerms":
                    outcome = isInAStatusWithOneOfTheseSpecificTerms(keywords);
                    break;

                case "isHashtagStart":
                    outcome = isHashtagStart();
                    break;

                case "isInHashtag":
                    outcome = isInHashtag();
                    break;

                case "isHashtag":
                    outcome = isHashtag();
                    break;

                case "isPrecededBySpecificTerm":
                    outcome = isPrecededBySpecificTerm(keywords);
                    break;

                case "isQuestionMarkAtEndOfStatus":
                    outcome = isQuestionMarkAtEndOfStatus();
                    break;

                case "isAllCaps":
                    outcome = isAllCaps();
                    break;

                case "isPrecededByStrongWord":
                    outcome = isPrecededByStrongWord();
                    break;

                case "isImmediatelyPrecededByPositive":
                    outcome = isImmediatelyPrecededByPositive();
                    break;

                case "isPrecededByPositive":
                    outcome = isPrecededByPositive();
                    break;

                case "isPrecededByOpinion":
                    outcome = isPrecededByOpinion();
                    break;

                case "isFirstLetterCapitalized":
                    outcome = isFirstLetterCapitalized();
                    break;

            }
            outcome = opposite ? !outcome : outcome;
            conditions.put(ALPHABET.substring(count, (count + 1)), outcome);
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
                cat = Category.valueOf("_" + result);
                cats.add(new CategoryAndIndex(cat, indexTerm));
                return cats;
            } catch (IllegalArgumentException wrongCode) {
                System.out.println("outcome was misspelled or just wrong, after evaluating the heuristics:");
                System.out.println(result);
                return cats;
            }
        }
        return cats;
    }

    public boolean isImmediatelyFollowedByAnOpinion() {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                return (heuristics.getMapH1().keySet().contains(temp) || heuristics.getMapH2().keySet().contains(temp));
            } else if (nextTerms.length > 1) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                return (heuristics.getMapH1().keySet().contains(temp) || heuristics.getMapH2().keySet().contains(temp));
            } else if (nextTerms.length > 2) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                return (heuristics.getMapH1().keySet().contains(temp) || heuristics.getMapH2().keySet().contains(temp));
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

    public boolean isFollowedByAPositiveOpinion() {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            return heuristics.getMapH1().keySet().stream().anyMatch((positiveTerm) -> (temp.contains(positiveTerm)));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            return false;
        }
    }

    public boolean isImmediatelyFollowedByAPositiveOpinion() {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                return (heuristics.getMapH1().keySet().contains(temp));
            } else if (nextTerms.length > 1) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                return (heuristics.getMapH1().keySet().contains(temp));
            } else if (nextTerms.length > 2) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                return (heuristics.getMapH1().keySet().contains(temp));
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

    public boolean isImmediatelyFollowedByANegativeOpinion() {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                return (heuristics.getMapH2().keySet().contains(temp));
            } else if (nextTerms.length > 1) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                return (heuristics.getMapH2().keySet().contains(temp));
            } else if (nextTerms.length > 2) {
                temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                return (heuristics.getMapH2().keySet().contains(temp));
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

    public boolean isImmediatelyFollowedBySpecificTerm(Set<String> parameters) {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).trim();
            String[] nextTerms = temp.split(" ");
            if (nextTerms.length > 0) {
                temp = nextTerms[0].trim();
                boolean isNextTermRelevant = parameters.contains(temp);
                if (isNextTermRelevant) {
                    return true;
                } else if (nextTerms.length > 1) {
                    temp = nextTerms[0].trim() + " " + nextTerms[1].trim();
                    return parameters.contains(temp);
                } else if (nextTerms.length > 2) {
                    temp = nextTerms[0].trim() + " " + nextTerms[1].trim() + " " + nextTerms[2].trim();
                    return parameters.contains(temp);
                } else {
                    return false;
                }
            }
            return false;
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            return false;
        }

    }

    public boolean isInAStatusWithOneOfTheseSpecificTerms(Set<String> parameters) {
        NGramFinder nGramFinder = new NGramFinder(text);

        Map<String, Integer> ngramsInMap = nGramFinder.runIt(2, true);
        if (ngramsInMap.isEmpty()) {
            return false;
        }

        Set<String> terms = ngramsInMap.keySet();
        Iterator<String> it = terms.iterator();

        while (it.hasNext()) {
            String next = it.next().trim();
            if (parameters.contains(next)) {
                return true;
            }
        }
        return false;
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

    public boolean isHashtagStart() {
        return (termOrig.toLowerCase().startsWith(termHeuristic.toLowerCase()));
    }

    public boolean isInHashtag() {
        return (termOrig.toLowerCase().contains(termHeuristic));
    }

    public boolean isHashtag() {
        return (termOrig.toLowerCase().contains(termHeuristic));
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
        } else {
            return false;
        }
    }

    public boolean isPrecededBySpecificTerm(Set<String> parameters) {
        try {
            String temp = text.substring(0, text.indexOf(termOrig)).trim().toLowerCase();
            return parameters.stream().anyMatch((candidate) -> (temp.contains(candidate)));

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            return false;
        }

    }

    public boolean isPrecededByStrongWord() {
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (heuristics.getMapH3().containsKey(term)) {
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

        if (temp.length > 0 && heuristics.getMapH1().containsKey(temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else if (temp.length > 1 && heuristics.getMapH1().containsKey(temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else if (temp.length > 2 && heuristics.getMapH1().containsKey(temp[temp.length - 3].trim().toLowerCase() + " " + temp[temp.length - 2].trim().toLowerCase() + " " + temp[temp.length - 1].trim().toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPrecededByPositive() {
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (heuristics.getMapH1().containsKey(term)) {
                return true;
            }
        }
        return false;
    }

    public boolean isQuestionMarkAtEndOfStatus() {
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
        text = sb.toString().trim();
        if (text.isEmpty()) {
            return false;
        } else {
            return ("?".equals(String.valueOf(text.charAt(text.length() - 1))));
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

    public boolean isImmediatelyPrecededByANegation() {
        termHeuristic = termHeuristic.toLowerCase();
        try {
            String leftPart = text.substring(0, indexTerm).toLowerCase().trim();
            String[] temp = leftPart.split(" ");

            //if the array is empty it means that the term is the first of the status;
            switch (temp.length) {
                case 0: {
                    return false;
                }
                case 1: {
                    return heuristics.getSetNegations().contains(temp[0]);
                }
                default: {
                    if (heuristics.getSetNegations().contains(temp[temp.length - 1])) {
                        return true;
                    } else if (temp.length > 1 && heuristics.getMapH3().containsKey(temp[temp.length - 1]) & heuristics.getSetNegations().contains(temp[temp.length - 2])) {
                        return true;
                    }
                    //in the case of "not really hot", return true
                    String negativeTerm = temp[temp.length - 2];
                    String booster = temp[temp.length - 1];
                    if (heuristics.getSetNegations().contains(negativeTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                        return true;
                    }
                    //in the case of "not the hottest", return true
                    negativeTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                    if (heuristics.getSetNegations().contains(negativeTerm.trim())) {
                        return true;
                    }
                }
                if (temp.length > 2) {
                    //in the case of "don't really like", return true [don't counts as two words: don t]
                    String negativeTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                    String booster = temp[temp.length - 1];
                    if (heuristics.getSetNegations().contains(negativeTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            System.out.println("index term was: " + indexTerm);
            return false;
        }
    }

    public boolean isImmediatelyPrecededBySpecificTerm(Set<String> parameters) {
        termHeuristic = termHeuristic.toLowerCase();
        try {
            String leftPart = text.substring(0, indexTerm).toLowerCase().trim();
            String[] temp = leftPart.split(" ");

            //if the array is empty it means that the term is the first of the status;
            switch (temp.length) {
                case 0: {
                    return false;
                }
                case 1: {
                    return parameters.contains(temp[0]);
                }
                default: {
                    if (parameters.contains(temp[temp.length - 1])) {
                        return true;
                    } else if (temp.length > 1 && heuristics.getMapH3().containsKey(temp[temp.length - 1]) & parameters.contains(temp[temp.length - 2])) {
                        return true;
                    }
                    //in the case of "not really hot", return true
                    String specificTerm = temp[temp.length - 2];
                    String booster = temp[temp.length - 1];
                    if (parameters.contains(specificTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                        return true;
                    }
                    //in the case of "not the hottest", return true
                    specificTerm = temp[temp.length - 2] + " " + temp[temp.length - 1];
                    if (parameters.contains(specificTerm.trim())) {
                        return true;
                    }
                }
                if (temp.length > 2) {
                    //in the case of "don't really like", return true [don't counts as two words: don t]
                    String specificTerm = temp[temp.length - 3] + " " + temp[temp.length - 2];
                    String booster = temp[temp.length - 1];
                    if (parameters.contains(specificTerm.trim()) && heuristics.getMapH3().containsKey(booster)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            System.out.println("index term was: " + indexTerm);
            return false;
        }
    }

    public boolean isImmediatelyFollowedByANegation() {
        try {
            String temp = text.substring(text.indexOf(termOrig) + termOrig.length()).toLowerCase().trim();
            String[] firstTermAfterTermOfInterest = temp.split(" ");

            //if the array is empty it means that the term is the last of the status;
            switch (firstTermAfterTermOfInterest.length) {
                case 0: {
                    return false;
                }
                case 1: {
                    return false;
                }
                default: {
                    String nextTerm = firstTermAfterTermOfInterest[1].trim();
                    Set<String> setNegations = heuristics.getSetNegations();
                    boolean containsTerm = setNegations.contains(nextTerm);
                    return containsTerm;
                }

            }
            //in this case the term is followed by at least one term. If the first term is negative, then we return "true"
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("status was: " + text);
            System.out.println("term was: " + termOrig);
            return false;
        }
    }

    public boolean isFirstTermOfStatus() {
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
        text = sb.toString().trim();
        boolean res = text.startsWith(termOrig);
        return res;
    }

    public boolean isPrecededBySubjectiveTerm() {
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();

        for (String term : ngrams) {
            if (heuristics.getMapH1().containsKey(term)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPrecededByOpinion() {
        String left = text.substring(0, text.indexOf(termOrig)).toLowerCase().trim();
        Set<String> ngrams = new NGramFinder(left).runIt(4, true).keySet();
        for (String term : ngrams) {
            if (heuristics.getMapH1().containsKey(term)) {
                return true;
            }
        }
        for (String term : ngrams) {
            if (heuristics.getMapH2().containsKey(term)) {
                return true;
            }
        }
        return false;
    }

}
