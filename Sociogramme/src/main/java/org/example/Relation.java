package org.example;

public class Relation {

    private Personne a;
    private Personne b;

    public Relation(Personne a, Personne b) {
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
