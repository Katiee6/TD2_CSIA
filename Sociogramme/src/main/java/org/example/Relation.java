package org.example;

public class Relation {

    private Noeud a;
    private Noeud b;

    public Relation(Noeud a, Noeud b) {
        this.a = a;
        this.b = b;

        this.a.evaluerRelations();
        this.b.evaluerRelations();
    }

    public void supprimerRelation() {
        // code de suppression

        this.a.evaluerRelations();
        this.b.evaluerRelations();
    }

}
