/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.termlevel;

import net.clementlevallois.umigon.heuristics.HeuristicsLoaderOnDemand;
import net.clementlevallois.umigon.model.ResultOneHeuristics;

/**
 *
 * @author LEVALLOIS
 */
public class IsImmediatelyPrecededByANegation {
    
    public static ResultOneHeuristics check(String text, String termOrig, int indexTerm, HeuristicsLoaderOnDemand heuristics){
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
    
}
