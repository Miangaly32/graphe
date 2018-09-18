/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonction;

import graphe.Arc;
import graphe.Graphe;
import graphe.Noeud;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mrandria
 */
public class Dijkstra {

    public static void main(String[] args) {
        Noeud a = new Noeud("A");
        Noeud b = new Noeud("B");
        Noeud c = new Noeud("C");
        Noeud d = new Noeud("D");
        Noeud e = new Noeud("E");
        Noeud f = new Noeud("F");
        Noeud g = new Noeud("G");
        Noeud h = new Noeud("H");

        List<Arc> arcA = new ArrayList(2);
        arcA.add(new Arc(a, b, 7));
        arcA.add(new Arc(a, e, 14));
        a.setArcs(arcA);

        List<Arc> arcB = new ArrayList(1);
        arcB.add(new Arc(b, c, 8));
        b.setArcs(arcB);

        List<Arc> arcC = new ArrayList(1);
        arcC.add(new Arc(c, d, 6));
        c.setArcs(arcC);

        List<Arc> arcD = new ArrayList(2);
        arcD.add(new Arc(d, a, 18));
        arcD.add(new Arc(d, f, 11));
        d.setArcs(arcD);

        List<Arc> arcE = new ArrayList(1);
        arcE.add(new Arc(e, f, 19));
        e.setArcs(arcE);

        List<Arc> arcF = new ArrayList(2);
        arcF.add(new Arc(f, g, 4));
        arcF.add(new Arc(f, h, 13));
        f.setArcs(arcF);

        List<Arc> arcG = new ArrayList(2);
        arcG.add(new Arc(g, c, 5));
        arcG.add(new Arc(g, h, 8));
        g.setArcs(arcG);

        List<Arc> arcH = new ArrayList(1);
        arcH.add(new Arc(h, c, 9));
        h.setArcs(arcH);

        List<Noeud> sommets = new ArrayList(8);
        sommets.add(a);
        sommets.add(b);
        sommets.add(c);
        sommets.add(d);
        sommets.add(e);
        sommets.add(f);
        sommets.add(g);
        sommets.add(h);

        Graphe graphe = new Graphe();
        graphe.setSommets(sommets);

        Dijkstra dijkstra = new Dijkstra();
        Graphe pluscourtchemin = dijkstra.dijkstra(graphe, a, h);
        List<Noeud> sommetsR = pluscourtchemin.getSommets();
        for (Noeud s : sommetsR) {
            System.out.print(s.getNom() + " - ");
        }
    }

    public Graphe dijkstra(Graphe graphe, Noeud depart, Noeud arrivee) {
        Graphe g = new Graphe();
        List<Noeud> sommetReturn = new ArrayList();
        // intialisation
        initialisation(graphe, depart);

        List<Noeud> sommets = graphe.getSommets();

        for (Noeud s : sommets) {
            // get sommet avec minimum de poids
            Noeud min = trouveMin(sommets);
            min.setDejaVu(true);
            traitement(min);
        }
        Noeud s = arrivee;
        while (!s.equals(depart)) {
            sommetReturn.add(s);
            s = s.getPredecesseur();
        }
        sommetReturn.add(depart);
        g.setSommets(sommetReturn);
        return g;
    }

    public void initialisation(Graphe graphe, Noeud depart) {
        depart.setPoids(0);
        List<Noeud> sommets = graphe.getSommets();
        for (Noeud s : sommets) {
            if (!depart.equals(s)) {
                s.setPoids((int) Double.POSITIVE_INFINITY);
            }
        }
    }

    public Noeud trouveMin(List<Noeud> noeuds) {
        Noeud min = new Noeud();
        min.setPoids((int) Double.POSITIVE_INFINITY);

        for (Noeud n : noeuds) {
            if (!n.isDejaVu() && n.getPoids() < min.getPoids()) {
                min = n;
            }
        }
        return min;
    }

    public void traitement(Noeud noeud) {
        List<Arc> arcs = noeud.getArcs();
        for (Arc arc : arcs) {
            Noeud fils = arc.getB();
            if (fils.getPoids() > noeud.getPoids() + arc.getDistance()) {
                fils.setPoids(noeud.getPoids() + arc.getDistance());
                fils.setPredecesseur(noeud);
            }
        }
    }
}
