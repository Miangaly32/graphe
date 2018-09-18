/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mrandria
 */
public class Noeud {

    private int poids;
    private String nom;
//    private List<Noeud> fils;
//    private List<Distance> distances;
    private List<Arc> arcs = new ArrayList<Arc>();
    private Noeud predecesseur;
    private boolean dejaVu = false;

    public Noeud(String nom) {
        this.nom = nom;
    }

    public Noeud() {
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Noeud getPredecesseur() {
        return predecesseur;
    }

    public void setPredecesseur(Noeud predecesseur) {
        this.predecesseur = predecesseur;
    }

    /*  public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }

    public int getDistance(Noeud noeud) {
        for (Distance d : distances) {
            if (d.getNoeud().equals(noeud)) {
                return d.getDistance();
            }
        }
        if (!noeud.getDistances().isEmpty()) {
            return noeud.getDistance(this);
        }
        return -1;
    }

    public void addDistance(Distance d) {
        distances.add(d);
    }*/
    public List<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }

    public boolean isDejaVu() {
        return dejaVu;
    }

    public void setDejaVu(boolean dejaVu) {
        this.dejaVu = dejaVu;
    }

    public boolean equals(Noeud noeud) {
        return noeud.getNom().equals(this.getNom());
    }

}
