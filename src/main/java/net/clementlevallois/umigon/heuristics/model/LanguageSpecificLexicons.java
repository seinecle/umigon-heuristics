/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics.model;

import net.clementlevallois.umigon.heuristics.model.LexiconsAndConditionalExpressions;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author LEVALLOIS
 */
public class LanguageSpecificLexicons {

    String language;
    
    Map<String, LexiconsAndConditionalExpressions> mapHeuristics;
    Map<String, LexiconsAndConditionalExpressions> mapH1;
    Map<String, LexiconsAndConditionalExpressions> mapH2;
    Map<String, LexiconsAndConditionalExpressions> mapH3;
    Map<String, LexiconsAndConditionalExpressions> mapH4;
    Map<String, LexiconsAndConditionalExpressions> mapH5;
    Map<String, LexiconsAndConditionalExpressions> mapH7;
    Map<String, LexiconsAndConditionalExpressions> mapH8;
    Map<String, LexiconsAndConditionalExpressions> mapH9;
    Map<String, LexiconsAndConditionalExpressions> mapH10;
    Map<String, LexiconsAndConditionalExpressions> mapH11;
    Map<String, LexiconsAndConditionalExpressions> mapH12;
    Map<String, LexiconsAndConditionalExpressions> mapH13;
    Set<String> setNegations;
    Set<String> setTimeTokens;
    Set<String> setHashTags;
    Set<String> setModerators;
    Set<String> setSubjective;
    Set<String> setFalsePositiveOpinions;
    Set<String> setIronicallyPositive;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapHeuristics() {
        return mapHeuristics;
    }

    public void setMapHeuristics(Map<String, LexiconsAndConditionalExpressions> mapHeuristics) {
        this.mapHeuristics = mapHeuristics;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH1() {
        return mapH1;
    }

    public void setMapH1(Map<String, LexiconsAndConditionalExpressions> mapH1) {
        this.mapH1 = mapH1;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH2() {
        return mapH2;
    }

    public void setMapH2(Map<String, LexiconsAndConditionalExpressions> mapH2) {
        this.mapH2 = mapH2;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH3() {
        return mapH3;
    }

    public void setMapH3(Map<String, LexiconsAndConditionalExpressions> mapH3) {
        this.mapH3 = mapH3;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH4() {
        return mapH4;
    }

    public void setMapH4(Map<String, LexiconsAndConditionalExpressions> mapH4) {
        this.mapH4 = mapH4;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH5() {
        return mapH5;
    }

    public void setMapH5(Map<String, LexiconsAndConditionalExpressions> mapH5) {
        this.mapH5 = mapH5;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH7() {
        return mapH7;
    }

    public void setMapH7(Map<String, LexiconsAndConditionalExpressions> mapH7) {
        this.mapH7 = mapH7;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH8() {
        return mapH8;
    }

    public void setMapH8(Map<String, LexiconsAndConditionalExpressions> mapH8) {
        this.mapH8 = mapH8;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH9() {
        return mapH9;
    }

    public void setMapH9(Map<String, LexiconsAndConditionalExpressions> mapH9) {
        this.mapH9 = mapH9;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH10() {
        return mapH10;
    }

    public void setMapH10(Map<String, LexiconsAndConditionalExpressions> mapH10) {
        this.mapH10 = mapH10;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH11() {
        return mapH11;
    }

    public void setMapH11(Map<String, LexiconsAndConditionalExpressions> mapH11) {
        this.mapH11 = mapH11;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH12() {
        return mapH12;
    }

    public void setMapH12(Map<String, LexiconsAndConditionalExpressions> mapH12) {
        this.mapH12 = mapH12;
    }

    public Map<String, LexiconsAndConditionalExpressions> getMapH13() {
        return mapH13;
    }

    public void setMapH13(Map<String, LexiconsAndConditionalExpressions> mapH13) {
        this.mapH13 = mapH13;
    }

    public Set<String> getSetNegations() {
        return setNegations;
    }

    public void setSetNegations(Set<String> setNegations) {
        this.setNegations = setNegations;
    }

    public Set<String> getSetTimeTokens() {
        return setTimeTokens;
    }

    public void setSetTimeTokens(Set<String> setTimeTokens) {
        this.setTimeTokens = setTimeTokens;
    }

    public Set<String> getSetHashTags() {
        return setHashTags;
    }

    public void setSetHashTags(Set<String> setHashTags) {
        this.setHashTags = setHashTags;
    }

    public Set<String> getSetModerators() {
        return setModerators;
    }

    public void setSetModerators(Set<String> setModerators) {
        this.setModerators = setModerators;
    }

    public Set<String> getSetFalsePositiveOpinions() {
        return setFalsePositiveOpinions;
    }

    public void setSetFalsePositiveOpinions(Set<String> setFalsePositiveOpinions) {
        this.setFalsePositiveOpinions = setFalsePositiveOpinions;
    }

    public Set<String> getSetIronicallyPositive() {
        return setIronicallyPositive;
    }

    public void setSetIronicallyPositive(Set<String> setIronicallyPositive) {
        this.setIronicallyPositive = setIronicallyPositive;
    }

    public Set<String> getSetSubjective() {
        return setSubjective;
    }

    public void setSetSubjective(Set<String> setSubjective) {
        this.setSubjective = setSubjective;
    }
    
    



}

