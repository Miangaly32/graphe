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
public class Distance {

    private Noeud noeud;
    private int distance;

    public Distance() {
    }

    public Distance(Noeud noeud, int distance) {
        this.noeud = noeud;
        this.distance = distance;
    }

    public Noeud getNoeud() {
        return noeud;
    }

    public void setNoeud(Noeud noeud) {
        this.noeud = noeud;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}
