module net.clementlevallois.umigon.heuristics {
    requires net.clementlevallois.umigon.model;
    requires net.clementlevallois.utils;
    requires net.clementlevallois.stopwords;
    requires emoji.java;
    requires mvel2;
    
    exports net.clementlevallois.umigon.heuristics.tools;
    exports net.clementlevallois.umigon.heuristics.catalog;
}
