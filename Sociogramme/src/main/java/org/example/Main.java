/**
 * Authors : Katia Bouarab and Mèlanie Pignon.**/

package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.Noeud.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //Dans ce qui suit un jeu de données (On a repris celui du TD)
        // Création des noeuds
        List<Noeud> listNoeud = new ArrayList<>();

        ArrayList<String> prenoms1 = new ArrayList<>();
        ArrayList<Integer> amis1 = new ArrayList<>();
        prenoms1.add("Alex");
        Noeud n1 = new Noeud(1,prenoms1, "Smith", 25, Sexe.F, "Classe A", amis1);

       listNoeud.add(n1);

        ArrayList<String> prenoms2 = new ArrayList<>();
        ArrayList<Integer> amis2 = new ArrayList<>();
        prenoms2.add("Bob");
        Noeud n2 = new Noeud(2, prenoms2, "Brown", 30, Sexe.H, "Classe A", amis2);
        listNoeud.add(n2);


        ArrayList<String> prenoms3 = new ArrayList<>();
        ArrayList<Integer> amis3 = new ArrayList<>();
        prenoms3.add("Alice");
        Noeud n3 = new Noeud(3, prenoms3, "Johnson", 28, Sexe.H, "Classe A", amis3);
        listNoeud.add(n3);


        ArrayList<String> prenoms4 = new ArrayList<>();
        ArrayList<Integer> amis4 = new ArrayList<>();
        prenoms4.add("Ali");
        Noeud n4 = new Noeud(4, prenoms4, "Taylor", 27, Sexe.F, "Classe A", amis4);
        listNoeud.add(n4);


        ArrayList<String> prenoms5 = new ArrayList<>();
        ArrayList<Integer> amis5 = new ArrayList<>();
        prenoms5.add("Aysu");
        Noeud n5 = new Noeud(5, prenoms5, "Williams", 26, Sexe.F, "Classe A", amis5);

        ArrayList<String> prenoms6 = new ArrayList<>();
        ArrayList<Integer> amis6 = new ArrayList<>();
        prenoms6.add("Gal");
        Noeud n6 = new Noeud(6, prenoms6, "Jones", 24, Sexe.H, "Classe A", amis6);

        ArrayList<String> prenoms7 = new ArrayList<>();
        ArrayList<Integer> amis7 = new ArrayList<>();
        prenoms7.add("Solal");
        Noeud n7 = new Noeud(7, prenoms7, "Faty", 26, Sexe.F, "Classe A", amis7);

        ArrayList<String> prenoms8 = new ArrayList<>();
        ArrayList<Integer> amis8 = new ArrayList<>();
        prenoms8.add("Ines");
        Noeud n8 = new Noeud(8, prenoms8, "Grant", 24, Sexe.H, "Classe A", amis8);


      // Déclaration des amitiés réciproques
        amis5.add(n1.getId());  // Amitié réciproque entre n1 et n5
        amis1.add(n5.getId());
        amis2.add(n3.getId());  // Amitié non-réciproque entre n3 et n2

// n4 est isolé, donc pas d'amitié à ajouter

// n2 est meneur, ajout des amis
        amis2.add(n5.getId());  // Amitié avec n5 (réelle)
        amis1.add(n2.getId());   // Amitié avec n2

// n6 est conseiller, le seul ami du meneur n2
        amis6.add(n2.getId());  // Amitié réciproque avec n2
        amis2.add(n6.getId());   // Amitié réciproque avec n6

// Ajouter les amitiés au fichier JSON après modifications
        n1.ajouterNoeud("noeud.json");
        n2.ajouterNoeud("noeud.json");
        n3.ajouterNoeud("noeud.json");
        n4.ajouterNoeud("noeud.json");
        n5.ajouterNoeud("noeud.json");
        n6.ajouterNoeud("noeud.json");
        n7.ajouterNoeud("noeud.json");
        n8.ajouterNoeud("noeud.json");


        // Menu principal
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
                    afficherRoles(listNoeud);
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

    private static void ajouterPersonne(Scanner sc) {
        System.out.println("Ajout d'une personne...");
        ArrayList<String> prenoms = new ArrayList<>();

        System.out.print("Entrez une id (number) : ");
        int id = Integer.parseInt(sc.nextLine());

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

        ArrayList<Integer> amis = new ArrayList<>();
        Noeud nouveauNoeud = new Noeud(id, prenoms, nom, age, sexe, classe, amis);
        try {
            nouveauNoeud.ajouterNoeud("noeud.json");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
        System.out.println("Personne ajoutée avec succès!");
    }


    private static void supprimerPersonne(Scanner sc) {
        System.out.println("Suppression d'une personne...");
        System.out.print("Entrez le nom de la personne à supprimer : ");
        String nom = sc.nextLine();
        supprimerNoeud("noeud.json", nom);
    }

    private static void listerPersonnes() {
        System.out.println("Liste des personnes enregistrées :");
        try {
            JSONArray noeuds = chargerDonnees("noeud.json");
            for (Object obj : noeuds) {
                JSONObject noeud = (JSONObject) obj;
                System.out.println("Nom : " + noeud.get("nom"));
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des données : " + e.getMessage());
        }
    }

    private static void afficherRoles(List<Noeud> listNoeud) throws IOException {
        List<Noeud> noeuds = loadNoeuds("noeud.json"); // Load the list of Noeuds from your data source (JSON file, etc.)

        // Let's assume you already have a list of IDs (ids)
        List<Integer> ids = new ArrayList<>(); // Example: populate this list with actual IDs
        for(Noeud n : noeuds){
            ids.add(n.getId());
        }

        // Identify roles
        List<String> isoles = identifierIsoles(ids, noeuds);
       List<String> meneurs = identifierMeneurs(ids, noeuds);
        List<String> conseillers = identifierConseilles(ids, noeuds);

        // Display roles
        System.out.println("\nIsolés : ");
        for (String nom : isoles) {
            System.out.println(" - " + nom);
        }

        System.out.println("\nMeneurs : ");
        for (String nom : meneurs) {
            System.out.println(" - " + nom);
        }

        System.out.println("\nConseillers : ");
        for (String nom : conseillers) {
            System.out.println(" - " + nom);
        }
    }


    private static void genererGrapheDot() {
        try {
            genererDot("noeud.json", "graphe.dot");
        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du graphe : " + e.getMessage());
        }
    }
}