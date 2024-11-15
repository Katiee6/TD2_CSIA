package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.lang.model.type.NullType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.Noeud.*;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        //création des noeuds (exemple du TD)
        // qui vont nous servire pou un jeu de données
        ArrayList<String> prenoms1 = new ArrayList<>();
        prenoms1.add("Alex");
        Noeud n1 = new Noeud(prenoms1, "Smith", 25, Sexe.F, "Classe A");

        ArrayList<String> prenoms2 = new ArrayList<>();
        prenoms2.add("Bob");
        Noeud n2 = new Noeud(prenoms2, "Brown", 30, Sexe.H, "Classe A");

        ArrayList<String> prenoms3 = new ArrayList<>();
        prenoms3.add("Alice");
        Noeud n3 = new Noeud(prenoms3, "Johnson", 28, Sexe.H, "Classe A");

        ArrayList<String> prenoms4 = new ArrayList<>();
        prenoms4.add("Ali");
        Noeud n4 = new Noeud(prenoms4, "Taylor", 27, Sexe.F, "Classe A");

        ArrayList<String> prenoms5 = new ArrayList<>();
        prenoms5.add("Aysu");
        Noeud n5 = new Noeud(prenoms5, "Williams", 26, Sexe.F, "Classe A");

        ArrayList<String> prenoms6 = new ArrayList<>();
        prenoms6.add("Gal");
        Noeud n6 = new Noeud(prenoms6, "Jones", 24, Sexe.H, "Classe A");

        ArrayList<String> prenoms7 = new ArrayList<>();
        prenoms7.add("Solal");
        Noeud n7 = new Noeud(prenoms7, "Faty", 26, Sexe.F, "Classe A");

        ArrayList<String> prenoms8 = new ArrayList<>();
        prenoms8.add("Ines");
        Noeud n8 = new Noeud(prenoms8, "Grant", 24, Sexe.H, "Classe A");

        //ajout au fichier JSON
        n1.ajouterNoeud("noeud.json");
        n2.ajouterNoeud("noeud.json");
        n3.ajouterNoeud("noeud.json");
        n4.ajouterNoeud("noeud.json");
        n5.ajouterNoeud("noeud.json");
        n6.ajouterNoeud("noeud.json");
        n7.ajouterNoeud("noeud.json");
        n8.ajouterNoeud("noeud.json");

        /*
        //decleration d'amitié réciproque vrais amis
        n1.ajouterAmi(n5, "noeud.json");
        n5.ajouterAmi(n1,"noeud.json");

        //faux amis (non-réciprocité)
        n3.ajouterAmi(n2,"noeud.json"); //mais n2 ne l'ajoute pas

        //isolée comme n4 pas d'amis et dans aucune liste des autres

        //meneuse comme n2
        n5.ajouterAmi(n2,"noeud.json");
        n1.ajouterAmi(n2,"noeud.json");

        //conseillére comme n6 le seul ami du meneur (réciproque)
        n2.ajouterAmi(n6,"noeud.json");
        n6.ajouterAmi(n2,"noeud.json");

        // Gestion des amitiés
        n1.ajouterAmi(n5,"noeud.json"); // Amitié réciproque
        n5.ajouterAmi(n1,"noeud.json");
        n3.ajouterAmi(n2,"noeud.json"); // Amitié non réciproque

        */
        n1.ajouterAmi(n3);
        n1.MAJ("noeud.json", n1);

        //Menu principal
        Scanner sc = new Scanner(System.in);
        System.out.println("\n*** Menu ***");
        while (true) {
            System.out.println("\nChoisissez une option :");
            System.out.println("1. Ajouter une personne");
            System.out.println("2. Supprimer une personne");
            System.out.println("3. Lister toutes les personnes");
            System.out.println("4. Afficher les rôles (isolés, meneurs, conseillers)");
            System.out.println("5. Générer un graphe (Dot)");
            System.out.println("6. Quitter");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1:
                    ajouterPersonne(sc);
                    break;

                case 2:
                    supprimerPersonne(sc);
                    break;

                case 3:
                    listerPersonnes();
                    break;
                case 4:
                    afficherRoles();
                    break;

                case 5:
                    genererGrapheDot();
                    break;

                case 6:
                    System.out.println("Au revoir !");
                    return;

                default:
                    System.out.println("Option invalide. Réessayez.");
            }
        }
    }

    //les options du menu :

    private static void ajouterPersonne(Scanner sc) {
        System.out.println("Ajout d'une personne...");
        ArrayList<String> prenoms= new ArrayList<>();
        System.out.print("Entrez un prénom : ");
        String prenom = sc.nextLine();
        prenoms.add(prenom);

        System.out.print("Entrez un nom : ");
        String nom = sc.nextLine();

        System.out.print("Entrez un âge : ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.print("Entrez le sexe (M/F) : ");
        Sexe sexe = sc.nextLine().equalsIgnoreCase("M") ? Sexe.H : Sexe.F;

        System.out.print("Entrez une classe : ");
        String classe = sc.nextLine();

        Noeud nouveauNoeud = new Noeud(prenoms, nom, age, sexe, classe);
        nouveauNoeud.ajouterNoeud("noeud.json");
        System.out.println("Personne ajoutée avec succès !");
    }

    private static void supprimerPersonne(Scanner sc) {
        System.out.println("Suppression d'une personne...");
        System.out.print("Entrez le nom de la personne à supprimer : ");
        String nom = sc.nextLine();
        Noeud.supprimerNoeud("noeud.json", nom);
        System.out.println("Personne supprimée avec succès !");
    }

    private static void listerPersonnes() {
        System.out.println("Liste des personnes enregistrées :");
        try {
            JSONArray noeuds = Noeud.chargerDonnees("noeud.json");
            for (Object obj : noeuds) {
                JSONObject noeud = (JSONObject) obj;
                System.out.println("Nom : " + noeud.get("nom") + ", Classe : " + noeud.get("classe"));
            }

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des personnes.");
        }
    }

    private static void afficherRoles() {
        try {
            // Charger les données JSON
            JSONArray noeudsJson = Noeud.chargerDonnees("noeud.json");

            // Convertir le JSON en objets Noeud
            List<Noeud> listeNoeuds = new ArrayList<>();
            for (Object obj : noeudsJson) {
                JSONObject noeudJson = (JSONObject) obj;
                Noeud noeud = new Noeud(
                        (ArrayList<String>) noeudJson.get("prenoms"),
                        (String) noeudJson.get("nom"),
                        ((Long) noeudJson.get("age")).intValue(),
                        Sexe.valueOf((String) noeudJson.get("sexe")),
                        (String) noeudJson.get("classe")
                );

                JSONArray amisJson = (JSONArray) noeudJson.get("amis");
                for (Object amiNom : amisJson) {
                    for (Noeud possibleAmi : listeNoeuds) {
                        if (possibleAmi.getNom().equals(amiNom)) {
                            noeud.ajouterAmi(possibleAmi);
                            break;
                        }
                    }
                }

                listeNoeuds.add(noeud);
            }

            //identification des rôles
            System.out.println("\n*** Identification des rôles ***");
            System.out.println("\n1. Isolés :");
            Noeud.identifierIsoles(listeNoeuds);
            System.out.println("\n2. Meneurs :");
            Noeud.identifierMeneurs(listeNoeuds);
            System.out.println("\n3. Conseillers :");
            Noeud.identifierConseilles(listeNoeuds);

        } catch (Exception e) {
            System.out.println("Erreur lors de l'affichage des rôles : " + e.getMessage());
            e.printStackTrace();
        }
    }

    //générer un graphe DOT
    private static void genererGrapheDot() {
        System.out.println("Génération du graphe au format DOT...");
        Noeud.genererDot("noeud.json", "graphe.dot");
        System.out.println("fichier DOT généré avec succès");
    }
}

