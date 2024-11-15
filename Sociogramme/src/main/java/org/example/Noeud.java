package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Noeud{

    private ArrayList<String> prenoms = new ArrayList<>();
    private String nom;
    private int age;
    private Sexe sexe;
    private String classe;
    private Type type;
    private ArrayList<Noeud> amis = new ArrayList<>();


    public Noeud(ArrayList<String> prenoms, String nom, int age, Sexe sexe, String classe) {
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
        this.age++;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void ajouterAmi(Noeud ami) {
        if (!amis.contains(ami)) {
            amis.add(ami);
        }
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


    public void ajouterNoeud(String fichier) {
        JSONObject noeudJson = new JSONObject();
        noeudJson.put("nom", this.nom);
        noeudJson.put("prenoms", this.prenoms);
        noeudJson.put("age", this.age);
        noeudJson.put("sexe", this.sexe.toString());
        noeudJson.put("classe", this.classe);

        try {
            JSONArray noeuds = chargerDonnees(fichier);

            // Ajouter le nouveau noeud
            noeuds.add(noeudJson);

            FileWriter file = new FileWriter(fichier);
            file.write(noeuds.toJSONString());
            file.flush();
            file.close();

            System.out.println("Noeud ajouté avec succès !");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray chargerDonnees(String fichier) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(fichier)) {
            Object obj = parser.parse(reader);
            return (JSONArray) obj;
        } catch (IOException e) {
            // Si le fichier n'existe pas encore, retourner un tableau vide
            return new JSONArray();
        }
    }

    public static void supprimerNoeud(String fichier, String nom) {
        try {
            JSONArray noeuds = chargerDonnees(fichier);
            noeuds.removeIf(obj -> {
                JSONObject noeud = (JSONObject) obj;
                return noeud.get("nom").equals(nom);
            });

            FileWriter file = new FileWriter(fichier);
            file.write(noeuds.toJSONString());
            file.flush();
            file.close();

            System.out.println("Noeud supprimé avec succès !");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    // Récupérer un noeud par nom
    public static JSONObject recupererNoeud(String fichier, String nom) {
        try {
            JSONArray noeuds = chargerDonnees(fichier);
            for (Object obj : noeuds) {
                JSONObject noeud = (JSONObject) obj;
                if (noeud.get("nom").equals(nom)) {
                    return noeud;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Récupérer tout les noeud
    public static JSONArray recupererToutNoeud(String fichier) {
        try {
            JSONArray noeuds = chargerDonnees(fichier);
            return noeuds;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }




}
