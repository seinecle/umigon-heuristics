module net.clementlevallois.umigon.heuristics {
    requires net.clementlevallois.umigon.model;
    requires net.clementlevallois.utils;
    requires net.clementlevallois.stopwords;
    requires net.clementlevallois.ngramops;
//    requires org.apache.commons.lang3;
    requires emoji.java;
    requires mvel2;
    
    exports net.clementlevallois.umigon.heuristics.tools;
    exports net.clementlevallois.umigon.heuristics.catalog;
}
