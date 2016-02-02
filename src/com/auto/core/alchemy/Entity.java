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
public class Entity implements Comparable<Entity> {
    public String type;
    public double relevance;
    public int count;
    public String text;
    
    public Entity() {}
    
    public Entity (String w, String x, String y, String z) {
        type = w;
        relevance = Double.parseDouble(x);
        count = Integer.parseInt(y);
        text = z;
    }

    @Override
    public int compareTo(Entity o) {
        return (int) (this.relevance - o.relevance);
    }
    
    @Override
    public String toString() {
        return "" + type + "|" + relevance + "|" + count + "|" + text;
    }
}
