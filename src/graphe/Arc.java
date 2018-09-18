/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphe;

/**
 *
 * @author mrandria
 */
public class Arc {

    Noeud a, b;
    // arc oriente a->b
    int distance;

    public Arc(Noeud a, Noeud b, int distance) {
        this.a = a;
        this.b = b;
        this.distance = distance;
    }

    public Arc() {
    }

    public Noeud getA() {
        return a;
    }

    public void setA(Noeud a) {
        this.a = a;
    }

    public Noeud getB() {
        return b;
    }

    public void setB(Noeud b) {
        this.b = b;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}
