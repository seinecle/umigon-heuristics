package net.clementlevallois.umigon.heuristics.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author C. Levallois
 */
public class LexiconsAndConditionalExpressions {

    private String term;
    private List<ConditionalExpression> features;
    private String rule;
    private boolean hashtagRelevant;

    public LexiconsAndConditionalExpressions() {
        hashtagRelevant = true;
        features = new ArrayList();
    }

    public void generateNewHeuristic(String term, String rule) {
        this.term = term;
        this.rule = rule;
    }

    public String getTerm() {
        return term;
    }

    public void addFeature(ConditionalExpression feature) {
        features.add(feature);
    }

    public List<ConditionalExpression> getMapFeatures() {
        return features;
    }

    public String getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return "Heuristic{" + "term=" + term + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.term != null ? this.term.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LexiconsAndConditionalExpressions other = (LexiconsAndConditionalExpressions) obj;
        if ((this.term == null) ? (other.term != null) : !this.term.equals(other.term)) {
            return false;
        }
        return true;
    }

    public boolean isHashtagRelevant() {
        return hashtagRelevant;
    }

    public void setHashtagRelevant(boolean hashtagRelevant) {
        this.hashtagRelevant = hashtagRelevant;
    }

}
