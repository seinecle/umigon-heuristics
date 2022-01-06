/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.model.Document;

/**
 *
 * @author C. Levallois
 */

public class StatusEligibleHeuristics {

    private String status;
    private Document tweet;

    public StatusEligibleHeuristics() {
    }

    public Document applyRules(Document tweet, String status) {
        this.status = status;
        this.tweet = tweet;
        isStatusEmpty();
        return tweet;
    }

    private void isStatusEmpty() {
        if (status.isEmpty()) {
            tweet.addToListCategories("92", -1);
        }
    }

}