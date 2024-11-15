package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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




    // IDENTIFICATION DES ISOLES, MENEURS, CONSEILLERS

    public static void identifierIsoles(List<Noeud> listeNoeuds) {
        for (Noeud noeud: listeNoeuds) {
            boolean isole = false;
            if (noeud.amis.isEmpty()) {
                for (Noeud autre : listeNoeuds) {
                    if (autre.amis.contains(noeud)) {
                        isole = false;
                        break; // il n'est pas isolé puisqu'il est dans une liste d'amis
                    }
                    isole = true;
                }
                if(isole) {
                    noeud.type = Type.ISOLE;
                }
            }
        }
    }

    public static void identifierMeneurs(List<Noeud> listeNoeuds) {
        for (Noeud noeud : listeNoeuds) {
            int countEstAmis = 0;
            for (Noeud autre : listeNoeuds) {
                if (autre.amis.contains(noeud)) {
                    countEstAmis++; // on compte le nombre de personne qui le considèrent comme amis
                }
            }
            // si 60% de la population est ami avec lui
            if (((double) countEstAmis / listeNoeuds.size()) > 0.6) {
                // s'il y a plus de personne qui le considèrent comme son ami que l'inverse (au moins 2 fois plus)
                if (countEstAmis > noeud.amis.size() * 2) {
                    noeud.type = Type.MENEUR;
                }
            }
        }
    }

    public static void identifierConseilles(List<Noeud> listeNoeuds) {
        for (Noeud noeud: listeNoeuds) {
            if (noeud.type == Type.MENEUR) {
                // Les amis d'un meneur sont des conseillers
                for (Noeud amisDuMeneur : noeud.amis) {
                        amisDuMeneur.type = Type.CONSEILLER;
                }
            }
        }
    }



}
