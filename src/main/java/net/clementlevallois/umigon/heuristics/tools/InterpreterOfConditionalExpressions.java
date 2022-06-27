/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics.tools;

import java.util.HashMap;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author C. Levallois
 *
 * This class interprets conditions written in human-readable format, and
 * returns the outcome
 *
 * example below: human-written rule:
 *
 *
 */
public class InterpreterOfConditionalExpressions {

    public static void main(String args[]) {
        String rule = "if(A||B){12} else{11}";

        Map<String, Boolean> c = new HashMap();
        c.put("A", false);
        c.put("B", false);
        String output = "";
        try {
            output = ((Integer) MVEL.eval(rule, c)).toString();
        } catch (Exception e) {
            System.out.println("error with rule: " + rule);
        }
        System.out.println("rule: " + rule);
        System.out.println("expected result: " + "");
        System.out.println("produced result: " + output);

    }

    public InterpreterOfConditionalExpressions() {
    }

    public String interprete(String rule, Map<String, Boolean> heuristics) {

        //99% of the cases: the rule is of the form 012:011
        if (rule.length() < 7) {
            return simpleInterpretation(rule, heuristics);
        }
        String result;
        try {
            Object eval = MVEL.eval(rule, heuristics);
            if (eval != null) {
                result = ((Integer) eval).toString();
            } else {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("error with rule: " + rule);
            System.out.println("map of booleans was: " + heuristics.toString());
            return "-1";
        }
        if (result.isBlank()){
            System.out.println("error with rule: " + rule);
            System.out.println("map of booleans was: " + heuristics.toString());
            return "-1";            
        }
        return result;
    }

    private String simpleInterpretation(String rule, Map<String, Boolean> heuristics) {
        String[] outcomes = rule.split(":");
        if (!heuristics.values().isEmpty()) {
            if (heuristics.values().iterator().next()) {
                return outcomes[0];
            } else if (outcomes.length > 1) {
                return outcomes[1];
            } else {
                return "10"; // 10 is or neutral
            }
        } else {
            return rule;
        }
    }
}
