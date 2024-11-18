
package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Noeud {
    private static int compteurId = 0;  // Compteur pour les ID uniques
  //  private int id;  // ID unique pour chaque noeud
    private int id;  // ID unique pour chaque noeud
    private ArrayList<String> prenoms;
    private String nom;
    private int age;
    private Sexe sexe;
    private String classe;
    Type type = Type.ISOLE;
    private ArrayList<Integer> amis;  // Liste des IDs des amis

    // Constructeur
    public Noeud(int id, ArrayList<String> prenoms, String nom, int age, Sexe sexe, String classe, ArrayList<Integer> amis) {
        this.id = id;  // Attribution d'un ID unique
        this.prenoms = prenoms;
        this.nom = nom;
        this.age = age;
        this.sexe = sexe;
        this.classe = classe;
        this.type = type;
        this.amis = amis;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenomUsage() {
        return prenoms.get(0);
    }

    public void feterAnniversaire() {
        this.age++;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Integer> getAmis() {
        return amis;
    }

    public void ajouterAmi(Noeud ami) {
        if (!amis.contains(ami.getId())) {
            amis.add(ami.getId());
        }
    }

    public void ajouterNoeud(String fichier) throws IOException {
        JSONArray noeuds = chargerDonnees(fichier);

        //créer un objet JSON représentant ce noeud
        JSONObject noeudJson = new JSONObject();
        noeudJson.put("id", id);
        noeudJson.put("prenoms", prenoms);
        noeudJson.put("nom", nom);
        noeudJson.put("age", age);
        noeudJson.put("sexe", sexe.toString());
        noeudJson.put("classe", classe);
        noeudJson.put("type", type.toString());
        noeudJson.put("amis", amis);

        noeuds.add(noeudJson);
        try (FileWriter file = new FileWriter(fichier)) {
            file.write(noeuds.toJSONString());
        }
    }

    public static void supprimerNoeud(String fichier, String nom) {
        try {
            // Charger les données existantes
            JSONArray noeuds = chargerDonnees(fichier);
            JSONArray noeudsRestants = new JSONArray();

            // Parcourir les nœuds et filtrer ceux à conserver
            for (Object obj : noeuds) {
                JSONObject noeud = (JSONObject) obj;
                if (!noeud.get("nom").equals(nom)) {
                    noeudsRestants.add(noeud);
                }
            }

            // Sauvegarder les données mises à jour
            try (FileWriter file = new FileWriter(fichier)) {
                file.write(noeudsRestants.toJSONString());
            }

            System.out.println("Personne supprimée avec succès !");
        } catch (IOException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }



    public static JSONArray chargerDonnees(String fichier) throws IOException {
        //lire le fichier JSON et le convertir en tableau
        try (FileReader reader = new FileReader(fichier)) {
            return (JSONArray) new org.json.simple.parser.JSONParser().parse(reader);
        } catch (org.json.simple.parser.ParseException e) {
            return new JSONArray();
        }
    }

    public static Noeud getNoeudById(int id, List<Noeud> noeuds) {
        for (Noeud noeud : noeuds) {
            if (noeud.getId() == id) {
                return noeud;
            }
        }
        return null; // Return null if not found
    }


    // IDENTIFICATION DES ISOLES, MENEURS, CONSEILLERS
/*
    public static String identifierIsoles(List<Noeud> listeNoeuds) {
        for (Noeud noeud : listeNoeuds) {
            if (noeud.amis.isEmpty()) {
                noeud.type = Type.ISOLE;
                return noeud.getNom();
            }
        }
    }

    public static String identifierMeneurs(List<Noeud> listeNoeuds) {
        for (Noeud noeud : listeNoeuds) {
            int countEstAmis = 0;
            for (Noeud autre : listeNoeuds) {
                if (autre.amis.contains(noeud.getId())) {
                    countEstAmis++;
                }
            }
            if (((double) countEstAmis / listeNoeuds.size()) > 0.6 && countEstAmis > noeud.amis.size() * 2) {
                noeud.type = Type.MENEUR;
                return noeud.getNom();
            }
        }
    }

    public static String identifierConseilles(List<Noeud> listeNoeuds) {
        for (Noeud noeud : listeNoeuds) {
            if (noeud.type == Type.MENEUR) {
                for (int amiId : noeud.amis) {
                    Noeud ami = listeNoeuds.stream()
                            .filter(n -> n.getId() == amiId)
                            .findFirst()
                            .orElse(null);

                    if (ami != null && ami.type == Type.ISOLE) {
                        ami.type = Type.CONSEILLER;
                        return ami.getNom();
                    }
                }
            }
        }
    }*/


    public static List<String> identifierIsoles(List<Integer> ids, List<Noeud> noeuds) {
        List<String> isoles = new ArrayList<>();
        for (Integer id : ids) {
            Noeud noeud = getNoeudById(id, noeuds);
            if (noeud != null && noeud.amis.isEmpty()) {
                noeud.type = Type.ISOLE;
                isoles.add(noeud.getNom());
            }
        }
        return isoles;
    }

    public static List<String> identifierMeneurs(List<Integer> ids, List<Noeud> noeuds) {
        List<String> meneurs = new ArrayList<>();
        for (Integer id : ids) {
            Noeud noeud = getNoeudById(id, noeuds);
            if (noeud != null) {
                int countEstAmis = 0;
                for (int i=0; i<noeud.amis.size(); i++) {
                    if (ids.contains(noeud.amis.get(i))) {
                        countEstAmis++;
                    }
                }
                if (((double) countEstAmis / ids.size()) > 0.6 && countEstAmis > noeud.amis.size() * 2) {
                    noeud.type = Type.MENEUR;
                    meneurs.add(noeud.getNom());
                }
            }
        }
        return meneurs;
    }

    public static List<String> identifierConseilles(List<Integer> ids, List<Noeud> noeuds) {
        List<String> conseillers = new ArrayList<>();
        for (Integer id : ids) {
            Noeud noeud = getNoeudById(id, noeuds);
            if (noeud != null && noeud.type == Type.MENEUR) {
                for (Integer amiId : noeud.amis) {
                    Noeud ami = getNoeudById(amiId, noeuds);
                    if (ami != null && ami.type == Type.ISOLE) {
                        ami.type = Type.CONSEILLER;
                        conseillers.add(ami.getNom());
                    }
                }
            }
        }
        return conseillers;
    }

    public static List<Noeud> loadNoeuds(String fichier) throws IOException {
        JSONArray noeudsJson = chargerDonnees(fichier);
        List<Noeud> noeuds = new ArrayList<>();

        for (Object obj : noeudsJson) {
            JSONObject noeudJson = (JSONObject) obj;
            int id = ((Long) noeudJson.get("id")).intValue();
            ArrayList<String> prenoms = (ArrayList<String>) noeudJson.get("prenoms");
            String nom = (String) noeudJson.get("nom");
            int age = ((Long) noeudJson.get("age")).intValue();
            Sexe sexe = Sexe.valueOf((String) noeudJson.get("sexe"));
            String classe = (String) noeudJson.get("classe");
            Type type = Type.valueOf((String) noeudJson.get("type"));
            ArrayList<Integer> amis = (ArrayList<Integer>) noeudJson.get("amis");

            Noeud noeud = new Noeud(id, prenoms, nom, age, sexe, classe, amis);
            noeuds.add(noeud);
        }

        return noeuds;
    }



    public static void genererDot(String fichierJSON, String fichierDOT) {
        try {
            JSONArray noeuds = chargerDonnees(fichierJSON);
            StringBuilder dotContent = new StringBuilder("Graphe {\n");

            for (Object obj : noeuds) {
                JSONObject noeud = (JSONObject) obj;
                String nom = (String) noeud.get("nom");
                JSONArray amis = (JSONArray) noeud.get("amis");

                for (Object amiObj : amis) {
                    String amiNom = (String) amiObj;
                    dotContent.append("  ").append(nom).append(" -> ").append(amiNom).append(";\n");
                }
            }

            dotContent.append("}\n");
            FileWriter file = new FileWriter(fichierDOT);
            file.write(dotContent.toString());
            file.flush();
            file.close();
            System.out.println("Graph DOT généré avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}