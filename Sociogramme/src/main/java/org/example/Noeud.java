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


public class Noeud {

    private ArrayList<String> prenoms = new ArrayList<>();
    private String nom;
    private int age;
    private Sexe sexe;
    private String classe;
    private Type type = Type.ISOLE;
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
        return prenoms.get(0);
    }

    public void feterAnniversaire() {
        this.age++;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Noeud> getAmis() {
        return amis;
    }


    public void ajouterAmi(Noeud ami) {
        if (!amis.contains(ami)) {
            amis.add(ami);
        }
    }

    /*
    public void ajouterAmi(Noeud ami, String fichier) {
        // Ensure the friendship is reciprocal
        if (!amis.contains(ami)) {
            amis.add(ami);
        }


        //identifierIsoles(getListeNoeuds(fichier));
        //identifierMeneurs(getListeNoeuds(fichier));
        //identifierConseilles(getListeNoeuds(fichier));

        // Save the updated data
        sauvegarderDonnees(fichier, getListeNoeuds(fichier));
        System.out.println("here done");
    }
*/


    public void supprimerAmi(Noeud ami) {
        if (this.amis.contains(ami)) {
            this.amis.remove(ami);
        }
    }

    // IDENTIFICATION DES ISOLES, MENEURS, CONSEILLERS

    public static void identifierIsoles(List<Noeud> listeNoeuds) {
        for (Noeud noeud : listeNoeuds) {
            if (noeud.amis.isEmpty()) {
                noeud.type = Type.ISOLE;
                System.out.println(" - Isolé : " + noeud.getNom());
            }
        }
    }

    public static void identifierMeneurs(List<Noeud> listeNoeuds) {
        for (Noeud noeud : listeNoeuds) {
            int countEstAmis = 0;
            for (Noeud autre : listeNoeuds) {
                if (autre.amis.contains(noeud)) {
                    countEstAmis++;
                }
            }
            if (((double) countEstAmis / listeNoeuds.size()) > 0.6) {
                if (countEstAmis > noeud.amis.size() * 2) {
                    noeud.type = Type.MENEUR;
                    System.out.println(" - MENEUR : " + noeud.getNom());
                }
            }
        }
    }

    public static void identifierConseilles(List<Noeud> listeNoeuds) {
        for (Noeud noeud : listeNoeuds) {
            if (noeud.type == Type.MENEUR) {
                for (Noeud amisDuMeneur : noeud.amis) {
                    amisDuMeneur.type = Type.CONSEILLER;
                    System.out.println(" - CONSEILLER : " + amisDuMeneur.getNom());
                }
            }
        }
    }

    public void ajouterNoeud(String fichier) {
        JSONObject noeudJson = new JSONObject();
        noeudJson.put("nom", this.nom);
        noeudJson.put("prenoms", this.prenoms);
        noeudJson.put("age", this.age);
        noeudJson.put("sexe", this.sexe.toString());
        noeudJson.put("classe", this.classe);
        noeudJson.put("type", this.type.toString());
        noeudJson.put("amis", new JSONArray());

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

    public static void sauvegarderDonnees(String fichier, List<Noeud> listeNoeuds) {
        JSONArray noeudsJson = new JSONArray();
        for (Noeud noeud : listeNoeuds) {
            JSONObject obj = new JSONObject();
            obj.put("prenoms", noeud.prenoms);
            obj.put("nom", noeud.nom);
            obj.put("age", noeud.age);
            obj.put("sexe", noeud.sexe.toString());
            obj.put("classe", noeud.classe);
            obj.put("type", noeud.type.toString());

            // Ajouter les noms des amis dans une liste
            JSONArray amisJson = new JSONArray();
            for (Noeud ami : noeud.amis) {
                amisJson.add(ami.nom); // Sauvegarde des noms d'amis
            }
            obj.put("amis", amisJson);

            noeudsJson.add(obj);
        }

        try (FileWriter writer = new FileWriter(fichier)) {
            writer.write(noeudsJson.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void MAJ(String fichier, Noeud n) {
        JSONObject noeudJson = new JSONObject();
        noeudJson.put("nom", n.nom);
        noeudJson.put("prenoms", n.prenoms);
        noeudJson.put("age", n.age);
        noeudJson.put("sexe", n.sexe.toString());
        noeudJson.put("classe", n.classe);
        noeudJson.put("type", n.type.toString());
        noeudJson.put("amis", n.getAmis());

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


    public static List<Noeud> getListeNoeuds(String fichier) {
        List<Noeud> listeNoeuds = new ArrayList<>();
        try {
            JSONArray noeuds = chargerDonnees(fichier);
            for (Object obj : noeuds) {
                JSONObject noeudJson = (JSONObject) obj;
                String nom = (String) noeudJson.get("nom");
                ArrayList<String> prenoms = (ArrayList<String>) noeudJson.get("prenoms");
                int age = ((Long) noeudJson.get("age")).intValue();
                Sexe sexe = Sexe.valueOf((String) noeudJson.get("sexe"));
                String classe = (String) noeudJson.get("classe");
                Type type = Type.valueOf((String) noeudJson.get("type"));
                JSONArray amisJson = (JSONArray) noeudJson.get("amis");

                ArrayList<Noeud> amis = new ArrayList<>();
                for (Object amiObj : amisJson) {
                    String amiNom = (String) amiObj;
                    Noeud ami = recupererNoeud(fichier, amiNom); // Get the Noeud object from the file
                    if (ami != null) {
                        amis.add(ami);
                    }
                }

                Noeud noeud = new Noeud(prenoms, nom, age, sexe, classe);
                noeud.type = type;
                noeud.amis = amis;
                listeNoeuds.add(noeud);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listeNoeuds;
    }

    // Récupérer un noeud par nom
    public static Noeud recupererNoeud(String fichier, String nom) {
        try {
            JSONArray noeuds = chargerDonnees(fichier);
            for (Object obj : noeuds) {
                JSONObject noeudJson = (JSONObject) obj;
                if (noeudJson.get("nom").equals(nom)) {
                    ArrayList<String> prenoms = (ArrayList<String>) noeudJson.get("prenoms");
                    int age = ((Long) noeudJson.get("age")).intValue();
                    Sexe sexe = Sexe.valueOf((String) noeudJson.get("sexe"));
                    String classe = (String) noeudJson.get("classe");
                    Type type = Type.valueOf((String) noeudJson.get("type"));
                    JSONArray amisJson = (JSONArray) noeudJson.get("amis");

                    ArrayList<Noeud> amis = new ArrayList<>();
                    for (Object amiObj : amisJson) {
                        String amiNom = (String) amiObj;
                        Noeud ami = recupererNoeud(fichier, amiNom); // Recursively get amis
                        if (ami != null) {
                            amis.add(ami);
                        }
                    }

                    Noeud noeud = new Noeud(prenoms, nom, age, sexe, classe);
                    noeud.type = type;
                    noeud.amis = amis;
                    return noeud;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // Return null if no Noeud is found
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
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}