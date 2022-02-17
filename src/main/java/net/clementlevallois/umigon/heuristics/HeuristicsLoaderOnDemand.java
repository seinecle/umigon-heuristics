/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.heuristics.model.LanguageSpecificLexicons;
import net.clementlevallois.umigon.heuristics.model.ConditionalExpression;
import net.clementlevallois.umigon.heuristics.model.LexiconsAndConditionalExpressions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.clementlevallois.umigon.heuristics.resources.en.PlaceHolderEN;
import net.clementlevallois.umigon.heuristics.resources.fr.PlaceHolderFR;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author LEVALLOIS
 */
public class HeuristicsLoaderOnDemand {

    private final String lang;
    private BufferedReader br;
    private FileReader fr;
    private String string;
    private LexiconsAndConditionalExpressions heuristic;
    private Map<String, LexiconsAndConditionalExpressions> mapHeuristics;
    private Map<String, LexiconsAndConditionalExpressions> mapH1;
    private Map<String, LexiconsAndConditionalExpressions> mapH2;
    private Map<String, LexiconsAndConditionalExpressions> mapH3;
    private Map<String, LexiconsAndConditionalExpressions> mapH4;
    private Map<String, LexiconsAndConditionalExpressions> mapH5;
    private Map<String, LexiconsAndConditionalExpressions> mapH6;
    private Map<String, LexiconsAndConditionalExpressions> mapH7;
    private Map<String, LexiconsAndConditionalExpressions> mapH8;
    private Map<String, LexiconsAndConditionalExpressions> mapH9;
    private Map<String, LexiconsAndConditionalExpressions> mapH10;
    private Map<String, LexiconsAndConditionalExpressions> mapH11;
    private Map<String, LexiconsAndConditionalExpressions> mapH12;
    private Map<String, LexiconsAndConditionalExpressions> mapH13;
    private Map<String, LexiconsAndConditionalExpressions> mapH17;
    private Set<String> setNegations;
    private Set<String> setTimeTokens;
    private Set<String> setSubjective;
    private Set<String> setHashTags;
    private Set<String> setModerators;
    private Set<String> setFalsePositiveOpinions;
    private Set<String> setIronicallyPositive;

    private Map<String, LanguageSpecificLexicons> multilingualLexicons = new HashMap();

    public HeuristicsLoaderOnDemand(String lang) {
        this.lang = lang;
    }

    public void load() {
        if (lang == null || lang.isBlank()) {
            System.out.println("lang is null or empty in the heuristics loading class. Exiting");
            System.exit(1);
        }
        List<String> fileNames = new ArrayList();
        fileNames.add("10_negations.txt");
        fileNames.add("13_hashtags.txt");
        fileNames.add("16_moderators.txt");
        fileNames.add("1_positive tone.txt");
        fileNames.add("2_negative tone.txt");
        fileNames.add("17_hypersatisfaction.txt");
        fileNames.add("3_strength of opinion.txt");
        fileNames.add("15_isIronicallyPositive.txt");
        fileNames.add("9_commercial tone.txt");
        fileNames.add("7_call_to_action.txt");
        fileNames.add("5_question.txt");
        fileNames.add("6_subjective.txt");
        fileNames.add("4_time.txt");
        fileNames.add("8_humor or light.txt");
        fileNames.add("14_false positive opinions.txt");
        fileNames.add("12_time indications.txt");
        fileNames.add("11_hints difficulty.txt");
        mapHeuristics = new HashMap();
        setNegations = new HashSet();
        setTimeTokens = new HashSet();
        setFalsePositiveOpinions = new HashSet();
        setIronicallyPositive = new HashSet();
        setModerators = new HashSet();
        setSubjective = new HashSet();
        mapH1 = new HashMap();
        mapH2 = new HashMap();
        mapH4 = new HashMap();
        mapH3 = new HashMap();
        mapH5 = new HashMap();
        mapH7 = new HashMap();
        mapH8 = new HashMap();
        mapH9 = new HashMap();
        mapH10 = new HashMap();
        mapH11 = new HashMap();
        mapH12 = new HashMap();
        mapH13 = new HashMap();
        mapH17 = new HashMap();
        for (String fileName : fileNames) {
            try {
                InputStream inputStream = null;
                if (lang.equalsIgnoreCase("en")) {
                    inputStream = PlaceHolderEN.class.getResourceAsStream(fileName);
                }
                if (lang.equalsIgnoreCase("fr")) {
                    inputStream = PlaceHolderFR.class.getResourceAsStream(fileName);
                }
                if (inputStream == null) {
                    continue;
                }
                br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String numberPrefixInFilename = fileName.substring(0, fileName.indexOf("_"));
                int map = Integer.parseInt(numberPrefixInFilename);
                if (map == 0 || map == 14) {
                    continue;
                }
                String term = null;
                String featureString;
                String rule = null;
                String[] fields;
                String[] parametersArray;
                String[] featuresArray;
                List<String> featuresList;
                Iterator<String> featuresListIterator;
                String field0 = "";
                String field1;
                String field2;
                String field3;
                String hashtagRelevant;
                //mapFeatures:
                //key: a feature
                //value: a set of parameters for the given feature
                while ((string = br.readLine()) != null) {
                    fields = string.split("\t");
                    if (fields.length == 0) {
                        System.out.println("empty line or something in file " + fileName + "for lang" + lang);
                        continue;
                    }
                    if (!lang.equals("zh")) {
                        field0 = StringUtils.stripAccents(fields[0].trim());
                    }
                    if (field0.isEmpty()) {
                        continue;
                    }
                    field1 = (fields.length < 2) ? null : fields[1];
                    field2 = (fields.length < 3) ? "" : fields[2].trim();
                    field3 = (fields.length < 4) ? "" : fields[3].trim();

                    term = field0;

                    featureString = field1;
                    if (featureString == null | map == 3) {
                        //negations
                        if (map == 10) {
                            setNegations.add(term);
                            continue;
                        }
                        //strong
                        if (map == 3) {
                            heuristic = new LexiconsAndConditionalExpressions();
                            heuristic.setHashtagRelevant(false);
                            heuristic.generateNewHeuristic(term, "");
                            mapH3.put(term, heuristic);
                            continue;
                        }

                        //subjective
                        if (map == 6) {
                            setSubjective.add(term);
                            continue;
                        }
                        //hints difficulty
                        if (map == 11) {
                            heuristic = new LexiconsAndConditionalExpressions();
                            heuristic.setHashtagRelevant(false);
                            heuristic.generateNewHeuristic(term, "");
                            mapH11.put(term, heuristic);
                            continue;
                        }

                        //time indications
                        if (map == 12) {
                            setTimeTokens.add(term);
                            continue;
                        }
                        //set of terms which look like opinions but are false postives
                        if (map == 14) {
                            setFalsePositiveOpinions.add(term);
                            continue;
                        }
                        //set of terms which look like opinions but are false postives
                        if (map == 15) {
                            setIronicallyPositive.add(term);
                            continue;
                        }
                        //set of moderators
                        if (map == 16) {
                            setModerators.add(term);
                            continue;
                        }

                        System.out.println("error:");
                        System.out.println(string);
                        System.out.println(Arrays.toString(fields));
                        continue;
                    }
                    rule = field2;
                    hashtagRelevant = field3;
                    //parse the "feature" field to disentangle the feature from the parameters
                    //this parsing rule will be extended to allow for multiple features
                    featuresArray = featureString.split("\\+\\+\\+");
                    featuresList = Arrays.asList(featuresArray);
                    featuresListIterator = featuresList.iterator();
                    heuristic = new LexiconsAndConditionalExpressions();
                    while (featuresListIterator.hasNext()) {
                        ConditionalExpression feature = new ConditionalExpression();
                        String condition = "";

                        featureString = featuresListIterator.next();
                        if (featureString == null || featureString.isEmpty() || featureString.equals("null")) {
                            continue;
                        }
                        if (featureString.contains("///")) {
                            parametersArray = featureString.substring(featureString.indexOf("///") + 3, featureString.length()).split("\\|");
                            condition = featureString.substring(0, featureString.indexOf("///"));
                            if (condition != null & !condition.isEmpty()) {
                                feature.setCondition(condition);
                                feature.setKeywords(new HashSet(Arrays.asList(parametersArray)));
                            }
                        } else {
                            feature.setCondition(featureString);
                        }
                        heuristic.addFeature(feature);
                    }
                    if (hashtagRelevant.equalsIgnoreCase("x")) {
                        heuristic.setHashtagRelevant(false);
                    }
                    heuristic.generateNewHeuristic(term, rule);

                    mapHeuristics.put(term, heuristic);
                    //positive
                    if (map == 1) {
                        mapH1.put(term, heuristic);
                        continue;
                    }
                    //negative
                    if (map == 2) {
                        mapH2.put(term, heuristic);
                        continue;
                    }
                    //hypersatisfaction
                    if (map == 17) {
                        mapH17.put(term, heuristic);
                        continue;
                    }
                    //time
                    if (map == 4) {
                        mapH4.put(term, heuristic);
                        continue;
                    }
                    //question
                    if (map == 5) {
                        mapH5.put(term, heuristic);
                        continue;
                    }
                    //address
                    if (map == 7) {
                        mapH7.put(term, heuristic);
                        continue;
                    }
                    //humor
                    if (map == 8) {
                        mapH8.put(term, heuristic);
                        continue;
                    }
                    //commercial offer
                    if (map == 9) {
                        mapH9.put(term, heuristic);
                        continue;
                    }
                    //hashtag specific terms
                    if (map == 13) {
                        mapH13.put(term, heuristic);
                    }
                }
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("IO Exception in heuristics loader!");
            }
        }
        LanguageSpecificLexicons lex = new LanguageSpecificLexicons();
        lex.setLanguage(lang);
        lex.setMapHeuristics(mapHeuristics);
        lex.setMapH1(mapH1);
        lex.setMapH2(mapH2);
        lex.setMapH3(mapH3);
        lex.setMapH4(mapH4);
        lex.setMapH5(mapH5);
        lex.setMapH7(mapH7);
        lex.setMapH8(mapH8);
        lex.setMapH9(mapH9);
        lex.setMapH10(mapH10);
        lex.setMapH11(mapH11);
        lex.setMapH11(mapH11);
        lex.setMapH12(mapH12);
        lex.setMapH13(mapH13);
        lex.setMapH17(mapH17);
        lex.setSetHashTags(setHashTags);
        lex.setSetFalsePositiveOpinions(setFalsePositiveOpinions);
        lex.setSetIronicallyPositive(setIronicallyPositive);
        lex.setSetModerators(setModerators);
        lex.setSetNegations(setNegations);
        lex.setSetTimeTokens(setTimeTokens);
        lex.setSetSubjective(setSubjective);
        multilingualLexicons.put(lang, lex);
    }

    public Set<String> getSetNegations() {
        return multilingualLexicons.get(lang).getSetNegations();
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH3() {
        return multilingualLexicons.get(lang).getMapH3();
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH13() {
        return multilingualLexicons.get(lang).getMapH13();
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH6() {
        return mapH6;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH1() {
        return multilingualLexicons.get(lang).getMapH1();
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH17() {
        return multilingualLexicons.get(lang).getMapH17();
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH9() {
        return multilingualLexicons.get(lang).getMapH9();
    }

    public Set<String> getSetModerators() {
        return multilingualLexicons.get(lang).getSetModerators();
    }

    public Set<String> getSetIronicallyPositive() {
        return multilingualLexicons.get(lang).getSetIronicallyPositive();
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH2() {
        return multilingualLexicons.get(lang).getMapH2();
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapHeuristics() {
        return multilingualLexicons.get(lang).getMapHeuristics();
    }

    public Set<String> getSetSubjective() {
        return multilingualLexicons.get(lang).getSetSubjective();
    }
}
