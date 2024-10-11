package org.example;

import java.util.ArrayList;

public class Personne {

    private ArrayList<String> prenoms = new ArrayList<>();
    private String nom;
    private int age;
    private Sexe sexe;
    private Type type;
    private String classe;

    public Personne(ArrayList<String> prenoms, String nom, int age, Sexe sexe, String classe) {
        this.prenoms = prenoms;
        this.nom = nom;
        this.age = age;
        this.sexe = sexe;
        this.classe = classe;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenomUsage() {
        return prenoms.getFirst();
    }

    public void feterAnniversaire() {
        this.age += 1;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void evaluerRelations() {
        this.deciderType();
        this.identifierGroupes();
    }

    public void deciderType() {
        // calculs
        //this.type = ;
    }

    public void identifierGroupes() {
        // calculs pour récupérer les personnes du ou des groupes d'amis
    }

}
