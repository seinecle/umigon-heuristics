/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.clementlevallois.umigon.heuristics.resources.multilingual.PlaceHolderMULTI;

/**
 *
 * @author LEVALLOIS
 */
public class EmojisHeuristicsandResourcesLoader {

    private static Set<String> setNegativeEmojis;
    private static Set<String> setPositiveEmojis;
    private static Set<String> setHyperSatisfactionEmojis;
    private static Set<String> setNeutralEmojis;
    private static Set<String> setIntensityEmojis;

    public static void load() {
        if (setNegativeEmojis != null && setPositiveEmojis != null && setNeutralEmojis != null && setIntensityEmojis != null) {
            return;
        }
        setNegativeEmojis = new HashSet();
        setPositiveEmojis = new HashSet();
        setNeutralEmojis = new HashSet();
        setIntensityEmojis = new HashSet();
        setHyperSatisfactionEmojis = new HashSet();

        try ( // we load the patterns of interest
                 InputStream inputStream = PlaceHolderMULTI.class.getResourceAsStream("emojis.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            List<String> patternsOfInterestAsTSV = br.lines().collect(Collectors.toList());
            for (String patternOfInterestAsTSV : patternsOfInterestAsTSV) {
                String[] elements = patternOfInterestAsTSV.split("\t");
                switch (elements[1]) {

                    case "positive":
                        setPositiveEmojis.add(elements[0]);
                        break;
                    case "negative":
                        setNegativeEmojis.add(elements[0]);
                        break;
                    case "neutral":
                        setNeutralEmojis.add(elements[0]);
                        break;
                    case "hyper-satisfaction":
                        setHyperSatisfactionEmojis.add(elements[0]);
                        break;
                    case "intensity":
                        setIntensityEmojis.add(elements[0]);
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Set<String> getSetNegativeEmojis() {
        return setNegativeEmojis;
    }

    public static Set<String> getSetPositiveEmojis() {
        return setPositiveEmojis;
    }

    public static Set<String> getSetNeutralEmojis() {
        return setNeutralEmojis;
    }

    public static Set<String> getSetIntensityEmojis() {
        return setIntensityEmojis;
    }

    public static Set<String> getSetHyperSatisfactionEmojis() {
        return setHyperSatisfactionEmojis;
    }
}
