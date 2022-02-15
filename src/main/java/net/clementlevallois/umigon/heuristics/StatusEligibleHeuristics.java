/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.model.Categories.Category;
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
        isTextBlank();
        return tweet;
    }

    private void isTextBlank() {
        if (status.isBlank()) {
            tweet.addToListCategories(Category._92, -1, "");
        }
    }

}