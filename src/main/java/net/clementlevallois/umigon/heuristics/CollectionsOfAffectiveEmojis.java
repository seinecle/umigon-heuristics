/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author LEVALLOIS
 */
public class CollectionsOfAffectiveEmojis {

    private Set<String> setNegativeEmojis;
    private Set<String> setPositiveEmojis;
    private Set<String> setNeutralEmojis;
    private Set<String> setIntensityEmojis;

    public void load() {
        if (setNegativeEmojis != null && setPositiveEmojis != null && setNeutralEmojis != null && setIntensityEmojis != null) {
            return;
        }

        String[] negativeEmojis = {":hankey:", ":poop:", ":shit:", ":frowning:", ":injured:", ":tired_face:", ":dizzy_face:", ":head_bandage:", "thermometer_face", ":head_bandaged:", ":bandaged:", ":facepalm:", ":female_facepalm:", ":face_palm:", ":frowning_face:", ":no_good:", ":sleepy:", ":slightly_frowning:", ":cry:", ":injured:", ":rage:", ":crying_cat_face:", ":eye_roll:", ":rolling_eyes:", ":sick:", ":ill:", ":thermometer_face:", ":grimacing:", ":sob:", ":confused:", ":-1:", ":thumbsdown:", ":person_frowning:", ":sweat:", ":weary:", ":coffin:", ":funeral:", ":casket:", ":fearful:", ":expressionless:", ":anguished:", ":disappointed:", "unamused", ":angry:", ":worried:", ":person_with_pouting_face:", ":broken_heart:"};
        String[] positiveEmojis = {":sunglasses:", ":musical_note:", ":notes:", ":blush:", ":kissing_heart:", ":heart_decoration:", ":heart_decoration:", ":couplekiss_man_man:", ":+1:", ":thumbsup:", ":blue_heart:", ":blossom:", ":sunrise:", ":triumph:", ":clap:", ":hugging:", ":hug:", ":hugs:", ":champagne:", ":sparkling_wine:", ":grin:", ":grinning:", ":relaxed:", ":two_men_holding_hands:", ":joy_cat:", ":heart_eyes:", ":joy:", ":couple_with_heart_man_man:", ":smiley:", ":sparkling_heart:", ":wedding:", ":kissing_closed_eyes:", ":sparkles:", ":yum:", ":stuck_out_tongue_winking_eye:", ":laughing:", ":satisfied:", ":couplekiss:", ":smile:", ":heart:", ":sunrise_over_mountains:", ":revolving_hearts:", ":sports_medal:", ":sports_decoration:", ":couple_with_heart_woman_woman:", ":face_with_tears_of_joy:", ":face_with_thermometer:", ":face_with_head_bandage:", ":fox_face:", ":fox:", ":kissing:", ":exclamation_heart:", ":cherries:", ":congratulations:", ":beers:", ":hearts:", ":wink:", ":sweat_smile:", ":high_brightness:", ":kissing_smiling_eyes:", ":trophy:", ":green_heart:", ":love_letter:", ":star2:", ":heartbeat:", ":slightly_smiling:", ":military_medal:", ":military_decoration:", ":princess:", ":relieved:", ":kiss:", ":couple_with_heart:", ":couplekiss_woman_woman:", ":birthday:", ":gift_heart:", ":stuck_out_tongue_closed_eyes:", ":yellow_heart:", ":two_hearts:", ":ok_hand:", ":two_women_holding_hands:", ":bouquet:", ":smiling_face_with_smiling_eyes:", ":innocent:", ":smirk:"};

        String[] neutralEmojis = {":neutral_face:"};

        String[] intensityEmojis = {":exclamation:", ":heavy_exclamation_mark:", ":fire:", ":astonished:", ":boom:", ":collision:", ":bomb:"};

        setNegativeEmojis = new HashSet(Arrays.asList(negativeEmojis));
        setPositiveEmojis = new HashSet(Arrays.asList(positiveEmojis));
        setNeutralEmojis = new HashSet(Arrays.asList(neutralEmojis));
        setIntensityEmojis = new HashSet(Arrays.asList(intensityEmojis));

    }

    public Set<String> getSetNegativeEmojis() {
        return setNegativeEmojis;
    }

    public Set<String> getSetPositiveEmojis() {
        return setPositiveEmojis;
    }

    public Set<String> getSetNeutralEmojis() {
        return setNeutralEmojis;
    }

    public Set<String> getSetIntensityEmojis() {
        return setIntensityEmojis;
    }
}
