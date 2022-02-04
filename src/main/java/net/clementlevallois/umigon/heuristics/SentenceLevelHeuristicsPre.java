/*
 * To change this template, choose Toolc | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import com.vdurmont.emoji.EmojiParser;
import net.clementlevallois.umigon.model.CategoryAndIndex;

/*
 *
 * @author C. Levallois
 */
public class SentenceLevelHeuristicsPre {


    public String containsPercentage(String text) {
        //do we find a percentage?
        boolean res = text.matches(".*\\d%.*");
        if (res) {
            //if so, is it followed by "off"?
            res = (text.toLowerCase().matches(".*\\d% off.*") | text.toLowerCase().matches(".*\\d% cash back.*"));
            if (res) {
                return "611";
            } else {
                return "621";
            }
        }
        return null;
    }

    public CategoryAndIndex containsOnomatopaes(String text) {

        //awwww
        int index = 0;
        boolean res = text.matches(".*aww+\\s*.*");
        if (res) {
            index = text.indexOf("aww");
            return new CategoryAndIndex("11", index);
        }
        //yesssss
        res = text.toLowerCase().matches(".*yess+\\s*.*");
        if (res) {
            index = text.indexOf("yess");
            return new CategoryAndIndex("11", index);
        }

        //ewwww
        res = text.matches(".*[^n]eww+\\s*.*");
        if (res) {
            index = text.indexOf("eww");
            return new CategoryAndIndex("12", index);
        }

        //arrrgh
        res = text.matches(".*arr+g\\s*.*");
        if (res) {
            index = text.indexOf("arr");
            return new CategoryAndIndex("12", index);
        }

        //ouchhh
        res = text.matches(".*ouu+ch+\\s*.*");
        if (res) {
            index = text.indexOf("ouch");
            return new CategoryAndIndex("12", index);
        }

        //yaaaay
        res = text.matches(".*ya+y\\s*.*");
        if (res) {
            index = text.indexOf("ya");
            return new CategoryAndIndex("11", index);
        }
        //yeeeey
        res = text.matches(".*ye+y\\s*.*");
        if (res) {
            index = text.indexOf("ye");
            return new CategoryAndIndex("11", index);
        }

        //ahahaha
        res = text.matches(".*haha\\s*.*");
        if (res) {
            index = text.indexOf("haha");
            return new CategoryAndIndex("11", index);

        }

        //LMFAO
        res = text.matches(".*lmfao+\\s*.*");
        if (res) {
            index = text.indexOf("lmfao");
            return new CategoryAndIndex("11", index);
        }

        //LMAO
        res = text.matches(".*lmao+\\s*.*");
        if (res) {
            index = text.indexOf("lmao");
            return new CategoryAndIndex("11", index);
        }

        //yeaaaa
        res = text.matches(".*yeaa+\\s*.*");
        if (res) {
            index = text.indexOf("yeaa");
            return new CategoryAndIndex("11", index);
        }

        //yuumm
        res = text.matches(".*yu+mm+\\s*.*");
        if (res) {
            index = text.indexOf("yu");
            return new CategoryAndIndex("11", index);
        }

        //yeeeee
        res = text.matches(".*yeee+\\s*.*");
        if (res) {
            index = text.indexOf("yeee");
            return new CategoryAndIndex("11", index);
        }

        //whyyyy
        res = text.matches(".*whyy+\\s*.*");
        if (res) {
            index = text.indexOf("why");
            return new CategoryAndIndex("12", index);
        }

        //helppp
        res = text.matches(".*helpp+\\s*.*");
        if (res) {
            index = text.indexOf("help");
            return new CategoryAndIndex("12", index);
        }

        //noooo
        res = text.matches(".* nooo+\\s*.*");
        if (res) {
            index = text.indexOf("nooo");
            return new CategoryAndIndex("12", index);
        }

        //wuhuu
        res = text.matches(".*wu+huu+\\s*.*");
        if (res) {
            index = text.indexOf("wu");
            return new CategoryAndIndex("11", index);
        }

        //buhuu
        res = text.matches(".*bu+hu+\\s*.*");
        if (res) {
            index = text.indexOf("bu");
            return new CategoryAndIndex("12", index);
        }

        //boooo
        res = text.matches(".* booo+\\s*.*");
        if (res) {
            index = text.indexOf("booo");
            return new CategoryAndIndex("12", index);
        }

        //uuuugh
        res = text.matches(".*? [u]{3,}+gh+\\s*.*");
        if (res) {
            index = text.indexOf("uu");
            return new CategoryAndIndex("12", index);
        }

        //woohoo
        res = text.matches(".*wo+hoo+\\s*.*");
        if (res) {
            index = text.indexOf("wo");
            return new CategoryAndIndex("11", index);
        }

        //yaaaaahooooo
        res = text.matches(".*?y[a]{3,}+ho+\\s*.*");
        if (res) {
            index = text.indexOf("ya");
            return new CategoryAndIndex("11", index);
        }

        return null;
    }

    public CategoryAndIndex containsEmojisinAscii(String text) {
        int index = 0;

        //multiple exclamation marks
        boolean res = text.matches(".*!!+\\s*.*");
        if (res) {
            index = text.indexOf("!!");
            return new CategoryAndIndex("022", index);
        }

        //question mark
        res = text.matches(".*\\?+\\s*.*");
        if (res) {
            index = text.indexOf("?");
            return new CategoryAndIndex("040", index);
        }

        //smiley ☺
        res = text.matches(".*☺+\\s*.*");
        if (res) {
            index = text.indexOf("☺");
            return new CategoryAndIndex("011", index);
        }

        //heart &lt;3
        res = text.matches(".*&lt;3\\s*.*");
        if (res) {
            index = text.indexOf("&lt;3");
            return new CategoryAndIndex("011", index);
        }

        //heart ♥
        res = text.matches(".*♥+\\s*.*");
        if (res) {
            index = text.indexOf("♥");
            return new CategoryAndIndex("011", index);
        }

        //heart and smileys ending in 3: <3 :3 =3
        res = text.matches(".*[<:=]3+\\s*.*");
        if (res) {
            index = text.indexOf("3");
            return new CategoryAndIndex("011", index);
        }

        //smiley :)
        res = text.matches(".*:\\)+\\s*.*");
        if (res) {
            index = text.indexOf(":)");
            return new CategoryAndIndex("011", index);
        }

        //smiley :-)
        res = text.matches(".*:-\\)+\\s*.*");
        if (res) {
            index = text.indexOf(":-)");
            return new CategoryAndIndex("011", index);
        }

        //smiley : )
        res = text.matches(".*: \\)+\\s*.*");
        if (res) {
            index = text.indexOf(": )");
            return new CategoryAndIndex("011", index);
        }

        //smiley :]
        res = text.matches(".*:\\]+\\s*.*");
        if (res) {
            index = text.indexOf(":]");
            return new CategoryAndIndex("011", index);
        }

        //smiley ^_^
        res = text.matches(".*\\^_*\\^\\s*.*");
        if (res) {
            index = text.indexOf("^");
            return new CategoryAndIndex("011", index);
        }

        //smiley :O or :D or :0 or ;p or :-p or :p
        res = text.toLowerCase().matches(".*(?<!\\S)(:d|:o|:0|;p|:-p|:p)(?!\\S).*");
        if (res) {
            index = text.indexOf(":");
            return new CategoryAndIndex("011", index);
        }

        //smiley (:
        res = text.matches(".*(?<!\\S)\\(:(?!\\S).*");
        if (res) {
            index = text.indexOf("(:");
            return new CategoryAndIndex("011", index);
        }

        //smiley ;)
        res = text.matches(".*;\\)+\\s*.*");
        if (res) {
            index = text.indexOf(";)");
            return new CategoryAndIndex("011", index);
        }

        //smiley :|
        res = text.matches(".*:\\|+\\s*.*");
        if (res) {
            index = text.indexOf(":|");
            return new CategoryAndIndex("012", index);
        }

        //smiley :S
        res = text.matches(".*:S\\s*.*");
        if (res) {
            index = text.indexOf(":S");
            return new CategoryAndIndex("012", index);
        }

        //smiley =(
        res = text.matches(".*=\\(+\\s*.*");
        if (res) {
            index = text.indexOf("=(");
            return new CategoryAndIndex("012", index);
        }

        //smiley T_T
        res = text.matches("t_t");
        if (res) {
            index = text.indexOf("t_t");
            return new CategoryAndIndex("012", index);
        }

        //smiley :-(
        res = text.matches(".*:-\\(+\\s*.*");
        if (res) {
            index = text.indexOf(":-(");
            return new CategoryAndIndex("012", index);
        }

        //smiley :-/
        res = text.matches(".*:-/+\\s*.*");
        if (res) {
            index = text.indexOf(":-/");
            return new CategoryAndIndex("012", index);
        }

        //smiley :'(
        res = text.matches(".*:'\\(+\\s*.*");
        if (res) {
            index = text.indexOf(":'(");
            return new CategoryAndIndex("012", index);
        }

        //smiley :(
        res = text.matches(".*:\\(+\\s*.*");
        if (res) {
            index = text.indexOf(":(");
            return new CategoryAndIndex("012", index);
        }

        //smiley :/
        res = text.matches(".*:/+\\s*.*");
        if (res) {
            index = text.indexOf(":/");
            return new CategoryAndIndex("012", index);
        }

        //kisses xxx
        res = text.matches(".*xx+\\s*.*");
        if (res) {
            index = text.indexOf("xx");
            return new CategoryAndIndex("011", index);
        }

        //kisses xoxoxo
        res = text.matches(".*(xo)\\1{1,}x*o*\\s*.*");
        if (res) {
            return new CategoryAndIndex("011", index);
        }

        return null;
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
    public CategoryAndIndex containsSentimentEmojis(CollectionsOfAffectiveEmojis emojis, String text) {
        int index;

        String textWithEmojisAsAliases = EmojiParser.parseToAliases(text);
        String[] terms = textWithEmojisAsAliases.split(":");
        for (String term : terms) {
            index = text.indexOf(term);
            if (emojis.getSetNegativeEmojis().contains(":" + term + ":")) {
                return new CategoryAndIndex("012", index);
            } else if (emojis.getSetPositiveEmojis().contains(":" + term + ":")) {
                return new CategoryAndIndex("011", index);
            }
        }

        boolean res = text.matches(".*<3+\\s*.*");
        if (res) {
            index = text.indexOf("<3");
            return new CategoryAndIndex("011", index);
        }

        return null;

    }
}
