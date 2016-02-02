/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auto.core.alchemy;

/**
 *
 * @author ethachu19
 */
public class Keyword implements Comparable<Keyword>{
    public String text;
    public double relevance;
    
    public Keyword() {
        text = "";
        relevance = 0f;
    }
    
    public Keyword(String x, String y) {
        this.text = x;
        this.relevance = Double.parseDouble(y);
    }

    @Override
    public int compareTo(Keyword o) {
        return (int) (this.relevance - o.relevance);
    }
    
    @Override
    public String toString() {
        return "" + text + "|" + relevance;
    }
}
