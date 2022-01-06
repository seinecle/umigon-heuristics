/*
 * To change this template, choose Toolc | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import com.vdurmont.emoji.EmojiParser;
import net.clementlevallois.umigon.model.Document;
import net.clementlevallois.utils.StatusCleaner;

/*
 *
 * @author C. Levallois
 */
public class SentenceLevelHeuristicsPre {

    private String cleanedText;
    private Document text;
    private final EmojisLoader emojis;

    public SentenceLevelHeuristicsPre(EmojisLoader emojis) {
        this.emojis = emojis;
        emojis.load();
    }

    public Document applyRules(Document tweet, String status) {
        StatusCleaner cleaner = new StatusCleaner();
        this.cleanedText = cleaner.removeHashtags(status.toLowerCase()).trim();
        this.text = tweet;

        containsPercentage();
        containsPunctuation();
        containsOnomatopaes();
        containsEmojis();
//        isAClientTweet();
//        isARetweetOfAClientTweet();
//        containsTimeIndication();
        return tweet;
    }

    public void containsPercentage() {
        //do we find a percentage?
        boolean res = cleanedText.matches(".*\\d%.*");
        if (res) {
            //if so, is it followed by "off"?
            res = (cleanedText.toLowerCase().matches(".*\\d% off.*") | cleanedText.toLowerCase().matches(".*\\d% cash back.*"));
            if (res) {
                text.addToListCategories("611", -1);
            } else {
                text.addToListCategories("621", -1);

            }
        }
    }

    public void containsOnomatopaes() {

        //awwww
        int index = 0;
        boolean res = cleanedText.matches(".*aww+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("aww");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("ww")) {
                text.setFinalNote(1);
            }
        }
        //yesssss
        res = cleanedText.toLowerCase().matches(".*yess+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("yess");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("sss")) {
                text.setFinalNote(1);
            }
        }

        //ewwww
        res = cleanedText.matches(".*[^n]eww+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("eww");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("ww")) {
                text.setFinalNote(-1);
            }
        }

        //arrrgh
        res = cleanedText.matches(".*arr+g\\s*.*");
        if (res) {
            index = cleanedText.indexOf("arr");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("gh")) {
                text.setFinalNote(-1);
            }
        }

        //ouchhh
        res = cleanedText.matches(".*ouu+ch+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("ouch");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(" ouch") || cleanedText.endsWith("uchhh")) {
                text.setFinalNote(-1);
            }
        }

        //yaaaay
        res = cleanedText.matches(".*ya+y\\s*.*");
        if (res) {
            index = cleanedText.indexOf("ya");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("ay")) {
                text.setFinalNote(1);
            }
        }
        //yeeeey
        res = cleanedText.matches(".*ye+y\\s*.*");
        if (res) {
            index = cleanedText.indexOf("ye");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("ey")) {
                text.setFinalNote(1);
            }
        }

        //ahahaha
        res = cleanedText.matches(".*haha\\s*.*");
        if (res) {
            index = cleanedText.indexOf("haha");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("aha")) {
                text.setFinalNote(1);
            }

        }

        //LMFAO
        res = cleanedText.matches(".*lmfao+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("lmfao");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("lmfao")) {
                text.setFinalNote(1);
            }
        }

        //LMAO
        res = cleanedText.matches(".*lmao+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("lmao");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("lmao")) {
                text.setFinalNote(1);
            }
        }

        //yeaaaa
        res = cleanedText.matches(".*yeaa+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("yeaa");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("aa")) {
                text.setFinalNote(1);
            }
        }

        //yuumm
        res = cleanedText.matches(".*yu+mm+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("yu");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("m")) {
                text.setFinalNote(1);
            }
        }

        //yeeeee
        res = cleanedText.matches(".*yeee+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("yeee");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("eee")) {
                text.setFinalNote(1);
            }
        }

        //whyyyy
        res = cleanedText.matches(".*whyy+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("why");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("yy")) {
                text.setFinalNote(-1);
            }
        }

        //helppp
        res = cleanedText.matches(".*helpp+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("help");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("pp")) {
                text.setFinalNote(-1);
            }
        }

        //noooo
        res = cleanedText.matches(".* nooo+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("nooo");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("ooo")) {
                text.setFinalNote(-1);
            }
        }

        //wuhuu
        res = cleanedText.matches(".*wu+huu+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("wu");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("uu")) {
                text.setFinalNote(1);
            }
        }

        //buhuu
        res = cleanedText.matches(".*bu+hu+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("bu");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("uu")) {
                text.setFinalNote(-1);
            }
        }

        //boooo
        res = cleanedText.matches(".* booo+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("booo");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("ooo")) {
                text.setFinalNote(-1);
            }
        }

        //uuuugh
        res = cleanedText.matches(".*? [u]{3,}+gh+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("uu");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("h")) {
                text.setFinalNote(-1);
            }
        }

        //woohoo
        res = cleanedText.matches(".*wo+hoo+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("wo");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("oo")) {
                text.setFinalNote(1);
            }
        }

        //yaaaaahooooo
        res = cleanedText.matches(".*?y[a]{3,}+ho+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("ya");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("oo")) {
                text.setFinalNote(1);
            }
        }
    }

    public void containsPunctuation() {
        int index = 0;

        //multiple exclamation marks
        boolean res = cleanedText.matches(".*!!+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("!!");
            text.addToListCategories("022", index);
        }

        //smiley ☺
        res = cleanedText.matches(".*☺+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("☺");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("☺")) {
                text.setFinalNote(1);
            }
        }

        //heart &lt;3
        res = cleanedText.matches(".*&lt;3\\s*.*");
        if (res) {
            index = cleanedText.indexOf("&lt;3");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("&lt;3")) {
                text.setFinalNote(1);
            }
        }

        //heart ♥
        res = cleanedText.matches(".*♥+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("♥");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("♥")) {
                text.setFinalNote(1);
            }
        }

        //heart and smileys ending in 3: <3 :3 =3
        res = cleanedText.matches(".*[<:=]3+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("3");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("3")) {
                text.setFinalNote(1);
            }
        }

        //smiley :)
        res = cleanedText.matches(".*:\\)+\\s*.*");
        if (res) {
            text.addTermToPositive(":)");
            index = cleanedText.indexOf(":)");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith(":)") || cleanedText.endsWith(":))") || cleanedText.endsWith(":)))")) {
                text.setFinalNote(1);
            }
        }

        //smiley :-)
        res = cleanedText.matches(".*:-\\)+\\s*.*");
        if (res) {
            text.addTermToPositive(":-)");
            index = cleanedText.indexOf(":-)");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith(":-)") || cleanedText.endsWith(":-))") || cleanedText.endsWith(":-)))")) {
                text.setFinalNote(1);
            }
        }

        //smiley : )
        res = cleanedText.matches(".*: \\)+\\s*.*");
        if (res) {
            index = cleanedText.indexOf(": )");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith(": )") || cleanedText.endsWith(": ))") || cleanedText.endsWith(": )))")) {
                text.setFinalNote(1);
            }

        }

        //smiley :]
        res = cleanedText.matches(".*:\\]+\\s*.*");
        if (res) {
            index = cleanedText.indexOf(":]");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith(":]") || cleanedText.endsWith(":]]")) {
                text.setFinalNote(1);
            }

        }

        //smiley ^_^
        res = cleanedText.matches(".*\\^_*\\^\\s*.*");
        if (res) {
            text.addTermToPositive("^_^");
            index = cleanedText.indexOf("^");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("^")) {
                text.setFinalNote(1);
            }
        }

        //smiley :O or :D or :0 or ;p or :-p or :p
        res = cleanedText.toLowerCase().matches(".*(?<!\\S)(:d|:o|:0|;p|:-p|:p)(?!\\S).*");
        if (res) {
            text.addTermToPositive(":0");
            index = cleanedText.indexOf(":");            
            text.addToListCategories("11", index);
            if (cleanedText.toLowerCase().endsWith(":d") || cleanedText.endsWith(":o") || cleanedText.endsWith(":0") || cleanedText.endsWith(";p") || cleanedText.endsWith(":-p") || cleanedText.endsWith(":p")) {
                text.setFinalNote(1);
            }
        }

        //smiley (:
        res = cleanedText.matches(".*(?<!\\S)\\(:(?!\\S).*");
        if (res) {
            text.addTermToPositive("(:");
            index = cleanedText.indexOf("(:");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("(:")) {
                text.setFinalNote(1);
            }
        }

        //smiley ;)
        res = cleanedText.matches(".*;\\)+\\s*.*");
        if (res) {
            text.addTermToPositive(";)");
            index = cleanedText.indexOf(";)");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith(";)") || cleanedText.endsWith(";))") || cleanedText.endsWith(";)))")) {
                text.setFinalNote(1);
            }
        }

        //smiley :|
        res = cleanedText.matches(".*:\\|+\\s*.*");
        if (res) {
            index = cleanedText.indexOf(":|");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(":|")) {
                text.setFinalNote(-1);
            }
        }

        //smiley :S
        res = cleanedText.matches(".*:S\\s*.*");
        if (res) {
            text.addTermToNegative(":S");
            index = cleanedText.indexOf(":S");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(":S")) {
                text.setFinalNote(-1);
            }
        }

        //smiley =(
        res = cleanedText.matches(".*=\\(+\\s*.*");
        if (res) {
            text.addTermToNegative("=(");
            index = cleanedText.indexOf("=(");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("=(") || cleanedText.endsWith("=((") || cleanedText.endsWith("=(((")) {
                text.setFinalNote(-1);
            }

        }

        //smiley T_T
        res = cleanedText.matches("t_t");
        if (res) {
            index = cleanedText.indexOf("t_t");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith("t_t") || cleanedText.endsWith("tt")) {
                text.setFinalNote(-1);
            }

        }

        //smiley :-(
        res = cleanedText.matches(".*:-\\(+\\s*.*");
        if (res) {
            text.addTermToNegative(":(");
            index = cleanedText.indexOf(":-(");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(":-(") || cleanedText.endsWith(":-((") || cleanedText.endsWith(":-(((")) {
                text.setFinalNote(-1);
            }
        }

        //smiley :-/
        res = cleanedText.matches(".*:-/+\\s*.*");
        if (res) {
            index = cleanedText.indexOf(":-/");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(":-/") || cleanedText.endsWith(":-//") || cleanedText.endsWith(":-///")) {
                text.setFinalNote(-1);
            }
        }

        //smiley :'(
        res = cleanedText.matches(".*:'\\(+\\s*.*");
        if (res) {
            index = cleanedText.indexOf(":'(");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(":'(") || cleanedText.endsWith(":'((") || cleanedText.endsWith(":'(((")) {
                text.setFinalNote(-1);
            }

        }

        //smiley :(
        res = cleanedText.matches(".*:\\(+\\s*.*");
        if (res) {
            index = cleanedText.indexOf(":(");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(":(") || cleanedText.endsWith(":((") || cleanedText.endsWith(":(((")) {
                text.setFinalNote(-1);
            }
        }

        //smiley :/
        res = cleanedText.matches(".*:/+\\s*.*");
        if (res) {
            text.addTermToNegative(":/");
            index = cleanedText.indexOf(":/");
            text.addToListCategories("12", index);
            if (cleanedText.endsWith(":/") || cleanedText.endsWith("://") || cleanedText.endsWith(":///")) {
                text.setFinalNote(-1);
            }
        }

        //question mark
        res = cleanedText.matches(".*\\?+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("?");
            text.addToListCategories("40", index);
        }

        //kisses xxx
        res = cleanedText.matches(".*xx+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("xx");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("xx")) {
                text.setFinalNote(1);
            }
        }

        //kisses xoxoxo
        res = cleanedText.matches(".*(xo)\\1{1,}x*o*\\s*.*");
        if (res) {
            text.addTermToPositive(":xoxo");
            index = cleanedText.indexOf("xo");
            text.addToListCategories("11", index);
        }
        if (cleanedText.endsWith("xo") || cleanedText.endsWith("ox")) {
            text.setFinalNote(1);
        }

    }

//    public void containsTimeIndication() {
//        Heuristic heuristic;
//        TermLevelHeuristics termLevelHeuristics = new TermLevelHeuristics();
//        int index = 0;
//        for (String term : HeuristicsLoaderAtStartup.getMapH4().keySet()) {
//            if (lc.contains(term)) {
//                heuristic = HeuristicsLoaderAtStartup.getMapH12().get(term);
//                String result = termLevelHeuristics.checkFeatures(heuristic, lc, lc, term);
//                if (result != null) {
//                    index = lc.indexOf(term);
//                    tweet.addToListCategories(result, index);
//                }
//            }
//        }
//    }
    public void isAClientTweet() {
//        if (tweet.getUser().toLowerCase().equalc(ControllerBean.getClient())) {
//            tweet.addToListCategories("0612", -1);
//        }
    }

    public void isARetweetOfAClientTweet() {
//        if (lc.toLowerCase().contains("rt @" + ControllerBean.getClient())) {
//            tweet.addToListCategories("06121", -1);
//        }
    }

    private void containsEmojis() {
        int index;

        String statusEmojis = EmojiParser.parseToAliases(cleanedText);
        String[] terms = statusEmojis.split(":");
        for (String term : terms) {
            index = cleanedText.indexOf(term);

            if (emojis.getSetNegativeEmojis().contains(":"+term+":")) {
                text.addToListCategories("12", index);
                text.addTermToNegative(term);
                if (cleanedText.endsWith(term)) {
                    text.setFinalNote(-1);
                }
            } else if (emojis.getSetPositiveEmojis().contains(":"+term+":")) {
                text.addToListCategories("11", index);
                text.addTermToPositive(term);
                if (cleanedText.endsWith(term)) {
                    text.setFinalNote(1);
                }
            }
        }

        boolean res = cleanedText.matches(".*<3+\\s*.*");
        if (res) {
            index = cleanedText.indexOf("<3");
            text.addToListCategories("11", index);
            if (cleanedText.endsWith("3")) {
                text.setFinalNote(1);
            }
        }

    }
}
