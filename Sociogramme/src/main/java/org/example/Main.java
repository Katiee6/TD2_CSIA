package org.example;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Création des noeuds
        ArrayList<String> prenoms1 = new ArrayList<>();
        prenoms1.add("Alex");
        Noeud n1 = new Noeud(prenoms1, "Smith", 25, Sexe.F, "Classe A");

        ArrayList<String> prenoms2 = new ArrayList<>();
        prenoms2.add("Bob");
        Noeud n2 = new Noeud(prenoms2, "Brown", 30, Sexe.H, "Classe B");

        ArrayList<String> prenoms3 = new ArrayList<>();
        prenoms3.add("Alice");
        Noeud n3 = new Noeud(prenoms3, "Johnson", 28, Sexe.H, "Classe C");

        ArrayList<String> prenoms4 = new ArrayList<>();
        prenoms4.add("Ali");
        Noeud n4 = new Noeud(prenoms4, "Taylor", 27, Sexe.F, "Classe D");

        ArrayList<String> prenoms5 = new ArrayList<>();
        prenoms5.add("Aysu");
        Noeud n5 = new Noeud(prenoms5, "Williams", 26, Sexe.F, "Classe E");

        ArrayList<String> prenoms6 = new ArrayList<>();
        prenoms6.add("Gal");
        Noeud n6 = new Noeud(prenoms6, "Jones", 24, Sexe.H, "Classe F");

        ArrayList<String> prenoms7 = new ArrayList<>();
        prenoms7.add("Aysu");
        Noeud n7 = new Noeud(prenoms7, "Williams", 26, Sexe.F, "Classe E");

        ArrayList<String> prenoms8 = new ArrayList<>();
        prenoms8.add("Gal");
        Noeud n8 = new Noeud(prenoms8, "Jones", 24, Sexe.H, "Classe F");

/*
         //decleration d'amitié réciproque vrais amis
        n1.ajouterAmi(n5);
        n5.ajouterAmi(n1);


        //faux amis (non-réciprocité)
        n3.ajouterAmi(n2); //mais n2 ne l'ajoute pas

        //isolée comme n4 pas d'amis et dans aucune liste des autres

        //meneuse comme n2
        QuiMeneuse();
        n5.ajouterAmi(n2);
        n1.ajouterAmi(n2);

        //conseillére comme n6 le seul ami du meneur (réciproque)
       QuiestConseiller();
        n2.ajouterAmi(n6);
        n6.ajouterAmi(n2);
*/


        // Gestion des amitiés
        n1.ajouterAmi(n5); // Amitié réciproque
        n5.ajouterAmi(n1);

        n3.ajouterAmi(n2); // Amitié non réciproque

/*
        // Identifier les relations spéciales
        System.out.println("\n*** Tests des relations ***");
        if (n2.estMeneur()) {
            System.out.println(n2.getNom() + " est le meneur.");
        }

        if (n6.estConseiller(n2)) {
            System.out.println(n6.getNom() + " est le conseiller de " + n2.getNom());
        }
*/

        //Menu
        Scanner sc = new Scanner(System.in);
        System.out.println("\n*** Menu ***");
        while (true) {
            System.out.println("\nChoisissez une option :");
            System.out.println("1. Ajouter une personne");
            System.out.println("2. Supprimer une personne");
            System.out.println("3. Afficher les relations");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1:
                    System.out.println("Ajout d'une personne...");
                    ArrayList<String> prenomsNouveau = new ArrayList<>();
                    System.out.print("Entrez un prénom : ");
                    String prenom = sc.nextLine();
                    prenomsNouveau.add(prenom);

                    System.out.print("Entrez un nom : ");
                    String nom = sc.nextLine();

                    System.out.print("Entrez un âge : ");
                    int age = Integer.parseInt(sc.nextLine());

                    System.out.print("Entrez le sexe (M/F) : ");
                    Sexe sexe = sc.nextLine().equalsIgnoreCase("M") ? Sexe.H : Sexe.F;

                    System.out.print("Entrez une classe : ");
                    String classe = sc.nextLine();

                    Noeud nouveauNoeud = new Noeud(prenomsNouveau, nom, age, sexe, classe);
                    nouveauNoeud.ajouterNoeud("org.example/noeud.json");
                    System.out.println("Personne ajoutée avec succès !");
                    break;

                case 2:
                    System.out.println("Suppression d'une personne...");
                    System.out.print("Entrez le nom de la personne à supprimer : ");
                    String nomSupprimer = sc.nextLine();
                    Noeud.supprimerNoeud("org.example/noeud.json", nomSupprimer);
                    System.out.println("Personne supprimée avec succès !");
                    break;

                case 3:
                    System.out.println("Affichage des relations...");
                    // Afficher les relations en parcourant le fichier JSON
                    try {
                        JSONArray noeuds = Noeud.chargerDonnees("org.example/noeud.json");
                        System.out.println(noeuds.toJSONString());
                    } catch (Exception e) {
                        System.out.println("Erreur lors du chargement des relations.");
                    }
                    break;

                case 4:
                    System.out.println("Au revoir !");
                    return;

                default:
                    System.out.println("Option invalide. Réessayez.");
            }
        }
    }
}

