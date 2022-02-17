/*
 * To change this template, choose Toolc | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import com.vdurmont.emoji.EmojiParser;
import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.model.Categories.Category;
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

    public List<CategoryAndIndex> containsOnomatopaes(String text) {
        List<CategoryAndIndex> cats = new ArrayList();

        //awwww
        int index = 0;
        boolean res = text.matches(".*aww+\\s*.*");
        if (res) {
            index = text.indexOf("aww");
            cats.add(new CategoryAndIndex(Category._11, index, "awwww"));
            cats.add(new CategoryAndIndex(Category._17, index, "awww"));
        }
        //yesssss
        res = text.toLowerCase().matches(".*yess+\\s*.*");
        if (res) {
            index = text.indexOf("yess");
            cats.add(new CategoryAndIndex(Category._11, index, "yesssss"));
            cats.add(new CategoryAndIndex(Category._17, index, "yesssss"));
        }

        //ewwww
        res = text.matches(".*[^n]eww+\\s*.*");
        if (res) {
            index = text.indexOf("eww");
            cats.add(new CategoryAndIndex(Category._12, index, "ewww"));
        }

        //arrrgh
        res = text.matches(".*arr+g\\s*.*");
        if (res) {
            index = text.indexOf("arr");
            cats.add(new CategoryAndIndex(Category._12, index, "arrgh"));
        }

        //ouchhh
        res = text.matches(".*ouu+ch+\\s*.*");
        if (res) {
            index = text.indexOf("ouch");
            cats.add(new CategoryAndIndex(Category._12, index, "ouchh"));
        }

        //yaaaay
        res = text.matches(".*ya+y\\s*.*");
        if (res) {
            index = text.indexOf("ya");
            cats.add(new CategoryAndIndex(Category._11, index, "yaaay"));
            cats.add(new CategoryAndIndex(Category._17, index, "yaay"));
        }
        //yeeeey
        res = text.matches(".*ye+y\\s*.*");
        if (res) {
            index = text.indexOf("ye");
            cats.add(new CategoryAndIndex(Category._11, index, "yeeey"));
            cats.add(new CategoryAndIndex(Category._17, index, "yeeey"));
        }

        //ahahaha
        res = text.matches(".*haha\\s*.*");
        if (res) {
            index = text.indexOf("haha");
            cats.add(new CategoryAndIndex(Category._11, index, "ahahaha"));
        }

        //LMFAO
        res = text.matches(".*lmfao+\\s*.*");
        if (res) {
            index = text.indexOf("lmfao");
            cats.add(new CategoryAndIndex(Category._11, index, "LMFAO"));
        }

        //LMAO
        res = text.matches(".*lmao+\\s*.*");
        if (res) {
            index = text.indexOf("lmao");
            cats.add(new CategoryAndIndex(Category._11, index, "LMAO"));
        }

        //yeaaaa
        res = text.matches(".*yeaa+\\s*.*");
        if (res) {
            index = text.indexOf("yeaa");
            cats.add(new CategoryAndIndex(Category._11, index, "yeaaa"));
            cats.add(new CategoryAndIndex(Category._17, index, "yeeea"));
        }

        //yuumm
        res = text.matches(".*yu+mm+\\s*.*");
        if (res) {
            index = text.indexOf("yu");
            cats.add(new CategoryAndIndex(Category._11, index, "yuumm"));
            cats.add(new CategoryAndIndex(Category._17, index, "yuumm"));
        }

        //yeeeee
        res = text.matches(".*yeee+\\s*.*");
        if (res) {
            index = text.indexOf("yeee");
            cats.add(new CategoryAndIndex(Category._11, index, "yeee"));
            cats.add(new CategoryAndIndex(Category._17, index, "yeee"));
        }

        //whyyyy
        res = text.matches(".*whyy+\\s*.*");
        if (res) {
            index = text.indexOf("why");
            cats.add(new CategoryAndIndex(Category._12, index, "whyyy"));
        }

        //helppp
        res = text.matches(".*helpp+\\s*.*");
        if (res) {
            index = text.indexOf("help");
            cats.add(new CategoryAndIndex(Category._12, index, "helppp"));
        }

        //noooo
        res = text.matches(".* nooo+\\s*.*");
        if (res) {
            index = text.indexOf("nooo");
            cats.add(new CategoryAndIndex(Category._12, index, "nooo"));
        }

        //wuhuu
        res = text.matches(".*wu+huu+\\s*.*");
        if (res) {
            index = text.indexOf("wu");
            cats.add(new CategoryAndIndex(Category._11, index, "wuhuu"));
            cats.add(new CategoryAndIndex(Category._17, index, "wuhuu"));
        }

        //buhuu
        res = text.matches(".*bu+hu+\\s*.*");
        if (res) {
            index = text.indexOf("bu");
            cats.add(new CategoryAndIndex(Category._12, index, "buhuu"));
        }

        //boooo
        res = text.matches(".* booo+\\s*.*");
        if (res) {
            index = text.indexOf("booo");
            cats.add(new CategoryAndIndex(Category._12, index, "booo"));
        }

        //uuuugh
        res = text.matches(".*? [u]{3,}+gh+\\s*.*");
        if (res) {
            index = text.indexOf("uu");
            cats.add(new CategoryAndIndex(Category._12, index, "uuugh"));
        }

        //woohoo
        res = text.matches(".*wo+hoo+\\s*.*");
        if (res) {
            index = text.indexOf("wo");
            cats.add(new CategoryAndIndex(Category._11, index, "woohoo"));
            cats.add(new CategoryAndIndex(Category._17, index, "woohoo"));
        }

        //yaaaaahooooo
        res = text.matches(".*?y[a]{3,}+ho+\\s*.*");
        if (res) {
            index = text.indexOf("ya");
            cats.add(new CategoryAndIndex(Category._11, index, "yahooo"));
            cats.add(new CategoryAndIndex(Category._17, index, "yahooo"));
        }
        return cats;
    }

    public List<CategoryAndIndex> containsEmojisinAscii(String text) {
        int index = 0;

        List<CategoryAndIndex> cats = new ArrayList();

        //multiple exclamation marks
        boolean res = text.matches(".*!!+\\s*.*");
        if (res) {
            index = text.indexOf("!!");
            cats.add(new CategoryAndIndex(Category._22, index, "!!!"));
        }

        //question mark
        res = text.matches(".*\\?+\\s*.*");
        if (res) {
            index = text.indexOf("?");
            cats.add(new CategoryAndIndex(Category._40, index, "?"));
        }

        //smiley ☺
        res = text.matches(".*☺+\\s*.*");
        if (res) {
            index = text.indexOf("☺");
            cats.add(new CategoryAndIndex(Category._11, index, "☺"));
        }

        //heart &lt;3
        res = text.matches(".*&lt;3\\s*.*");
        if (res) {
            index = text.indexOf("&lt;3");
            cats.add(new CategoryAndIndex(Category._11, index, "&lt;3"));
        }

        //heart ♥
        res = text.matches(".*♥+\\s*.*");
        if (res) {
            index = text.indexOf("♥");
            cats.add(new CategoryAndIndex(Category._11, index, "♥"));
        }

        //heart and smileys ending in 3: <3 :3 =3
        res = text.matches(".*[<:=]3+\\s*.*");
        if (res) {
            index = text.indexOf("3");
            cats.add(new CategoryAndIndex(Category._11, index, "<3"));
        }

        //smiley :)
        res = text.matches(".*:\\)+\\s*.*");
        if (res) {
            index = text.indexOf(":)");
            cats.add(new CategoryAndIndex(Category._11, index, ":)"));
        }

        //smiley :-)
        res = text.matches(".*:-\\)+\\s*.*");
        if (res) {
            index = text.indexOf(":-)");
            cats.add(new CategoryAndIndex(Category._11, index, ":-)"));
        }

        //smiley : )
        res = text.matches(".*: \\)+\\s*.*");
        if (res) {
            index = text.indexOf(": )");
            cats.add(new CategoryAndIndex(Category._11, index, ": )"));
        }

        //smiley :]
        res = text.matches(".*:\\]+\\s*.*");
        if (res) {
            index = text.indexOf(":]");
            cats.add(new CategoryAndIndex(Category._11, index, ":]"));
        }

        //smiley ^_^
        res = text.matches(".*\\^_*\\^\\s*.*");
        if (res) {
            index = text.indexOf("^");
            cats.add(new CategoryAndIndex(Category._11, index, ":)"));
        }

        //smiley :O or :D or :0 or ;p or :-p or :p
        res = text.toLowerCase().matches(".*(?<!\\S)(:d|:o|:0|;p|:-p|:p)(?!\\S).*");
        if (res) {
            index = text.indexOf(":");
            cats.add(new CategoryAndIndex(Category._11, index, ":o"));
        }

        //smiley (:
        res = text.matches(".*(?<!\\S)\\(:(?!\\S).*");
        if (res) {
            index = text.indexOf("(:");
            cats.add(new CategoryAndIndex(Category._11, index, "(:"));
        }

        //smiley ;)
        res = text.matches(".*;\\)+\\s*.*");
        if (res) {
            index = text.indexOf(";)");
            cats.add(new CategoryAndIndex(Category._11, index, ";)"));
        }

        //smiley :|
        res = text.matches(".*:\\|+\\s*.*");
        if (res) {
            index = text.indexOf(":|");
            cats.add(new CategoryAndIndex(Category._12, index, ":|"));
        }

        //smiley :S
        res = text.matches(".*:S\\s*.*");
        if (res) {
            index = text.indexOf(":S");
            cats.add(new CategoryAndIndex(Category._12, index, ":S"));
        }

        //smiley =(
        res = text.matches(".*=\\(+\\s*.*");
        if (res) {
            index = text.indexOf("=(");
            cats.add(new CategoryAndIndex(Category._12, index, "=("));
        }

        //smiley T_T
        res = text.matches("t_t");
        if (res) {
            index = text.indexOf("t_t");
            cats.add(new CategoryAndIndex(Category._12, index, "T_T"));
        }

        //smiley :-(
        res = text.matches(".*:-\\(+\\s*.*");
        if (res) {
            index = text.indexOf(":-(");
            cats.add(new CategoryAndIndex(Category._12, index, ":-("));
        }

        //smiley :-/
        res = text.matches(".*:-/+\\s*.*");
        if (res) {
            index = text.indexOf(":-/");
            cats.add(new CategoryAndIndex(Category._12, index, ":-/"));
        }

        //smiley :'(
        res = text.matches(".*:'\\(+\\s*.*");
        if (res) {
            index = text.indexOf(":'(");
            cats.add(new CategoryAndIndex(Category._12, index, ":'("));
        }

        //smiley :(
        res = text.matches(".*:\\(+\\s*.*");
        if (res) {
            index = text.indexOf(":(");
            cats.add(new CategoryAndIndex(Category._12, index, ":("));
        }

        //smiley :/
        
        // this matches :/ preceded by a space but specifically not :// because :// could be something in http://www....
        res = text.matches(".* :/[^/]\\s*.*");
        if (res) {
            index = text.indexOf(":/");
            cats.add(new CategoryAndIndex(Category._12, index, ":/"));
        }

        //kisses xxx
        res = text.matches(".*xx+\\s*.*");
        if (res) {
            index = text.indexOf("xx");
            cats.add(new CategoryAndIndex(Category._11, index, "xxx"));
        }

        //kisses xoxoxo
        res = text.matches(".*(xo)\\1{1,}x*o*\\s*.*");
        if (res) {
            cats.add(new CategoryAndIndex(Category._11, index, "xoxoxo"));
        }

        return cats;
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
    public List<CategoryAndIndex> containsAffectiveEmojis(CollectionsOfAffectiveEmojis emojis, String text) {
        int index;
        List<CategoryAndIndex> cats = new ArrayList();

        String textWithEmojisAsAliases = EmojiParser.parseToAliases(text);
        String[] terms = textWithEmojisAsAliases.split(":");
        for (String term : terms) {
            index = text.indexOf(term);
            term = ":" + term + ":";
            if (emojis.getSetNegativeEmojis().contains(term)) {
                cats.add(new CategoryAndIndex(Category._12, index, term));
            } else if (emojis.getSetPositiveEmojis().contains(term)) {
                cats.add(new CategoryAndIndex(Category._11, index, term));
            } else if (emojis.getSetHyperSatisfactionEmojis().contains(term)) {
                cats.add(new CategoryAndIndex(Category._17, index, term));
            }
        }

        return cats;

    }
}
