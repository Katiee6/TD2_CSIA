package org.example;

import java.util.Set;

public class Graphe {

    private String nom; // nom du graphe
    private String classe; // classe pour laquelle le graphe est fait
    private Set<Noeud> noeuds;

    public Graphe(String nom, String classe) {
        this.nom = nom;
        this.classe = classe;
        this.noeuds = recupererNoeudsClasse(classe); // init
        sauvegarderGraphe();
    }

    // TODO recupérer les noeuds de la classe souhaitée (à partir de la liste de Noeuds globale)
    public Set<Noeud> recupererNoeudsClasse(String classe) {
        // boucle sur la liste de noeuds
        // si c'est la meme classe : ajouter à la nouvelle liste
        return null; // retourner la nouvelle liste
    }

    // TODO identifier si la relation est réciproque ou non pour le sens de la fleche
    // pour chaque noeud : boucler sur la liste d'amis
    // aller voir dans l'ami si je suis aussi dans sa liste, si oui -> réciproque
    // attention quand on boucle sur un noeud mentionné par d'autres


    // json
    public void sauvegarderGraphe() {
    }

    // Afficher un graphe avec GrapViz (DOT)
    public void afficherUnGraphe(Graphe graphe){
    }

}
