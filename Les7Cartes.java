import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedList;

public class Les7Cartes {
    ArrayList<Carte> tLes7Cartes = new ArrayList<>();
    long compteur;
    int compteurBrelan = 0;
    int compteurPaire = 0;
    int compteurCouleur = 0;
    boolean suiteAS5 = false;
    char[] compteurValChar;
    boolean meilleureMain = false;

    public Les7Cartes(LinkedList<Carte> les5Cartes, ArrayList<Carte> tLes2Cartes) {
        //regroupe les 2Cartes et les 5Cartes dans tLes7Cartes
        for (int i = 0; i < les5Cartes.size() + tLes2Cartes.size(); i++) {
            if (i < tLes2Cartes.size()) {
                tLes7Cartes.add(tLes2Cartes.get(i));
            } else {
                tLes7Cartes.add(les5Cartes.get(i - tLes2Cartes.size()));
            }
        }
        //récupère le compteur propre à chaque ensemble de 7 Cartes (par rapport aux valeurs) pour l'utiliser dans les tests de combinaison
        this.getCompteur();
    }

    public void chaineTest(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        //tests pour la Quinte Flush
        this.quinteFlush(tCompteurs, tJoueurs, p);

        //tests pour le Carre
        if (!meilleureMain) {
            this.carre(tCompteurs,  tJoueurs, p);
        }

        //tests pour le Full
        if (!meilleureMain) {
            this.full(tCompteurs,  tJoueurs, p);
        }

        //tests pour la Couleur
        if (!meilleureMain) {
            this.couleur(tCompteurs, tJoueurs, p);
        }

        //tests pour la Suite
        if (!meilleureMain) {
            this.suite(tCompteurs, tJoueurs, p);
        }

        //tests pour le Brelan
        if (!meilleureMain) {
            this.brelan(tCompteurs, tJoueurs, p);
        }

        //tests pour la Double Paire
        if (!meilleureMain) {
            this.doublePaire(tCompteurs,  tJoueurs, p);
        }


        //tests pour la Paire
        if (!meilleureMain) {
            this.paire(tCompteurs,  tJoueurs, p);
        }

        //tests pour la Carte Haute
        if (!meilleureMain) {
            this.carteHaute(tCompteurs, tJoueurs, p);
        }

    }

    public void testEgalite(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {

        //Egalite carte haute
        if (tJoueurs.get(i).getScore() == 1) {
            this.egaliteCarteHaute(tJoueurs, joueursMaxScore, i);
        }

        //egalite paire
        if (tJoueurs.get(i).getScore() == 2) {
            this.egalitePaire(tJoueurs, joueursMaxScore, i);
        }

        //egalite double Paire
        if (tJoueurs.get(i).getScore() == 3) {
            this.egaliteDoublePaire(tJoueurs, joueursMaxScore, i);
        }

        //egalite Brelan
        if (tJoueurs.get(i).getScore() == 4) {
            this.egaliteBrelan(tJoueurs, joueursMaxScore, i);
        }

        //egalite Suite
        if (tJoueurs.get(i).getScore() == 5) {
            this.egaliteSuite(tJoueurs, joueursMaxScore, i);
        }

        //egalite Couleur
        if (tJoueurs.get(i).getScore() == 6) {
            this.egaliteCouleur(tJoueurs, joueursMaxScore, i);
        }

        //egalite Full
        if (tJoueurs.get(i).getScore() == 7) {
            this.egaliteFull(tJoueurs, joueursMaxScore, i);
        }

        //egalite Carre
        if (tJoueurs.get(i).getScore() == 8) {
            this.egaliteCarre(tJoueurs, joueursMaxScore, i);

        }

        //egalite Quinte Flush
        if (tJoueurs.get(i).getScore() == 9) {
            this.egaliteQuinteFlush(tJoueurs, joueursMaxScore, i);
        }
    }

    public void getCompteur() {
        for (Carte c : tLes7Cartes) {
            int val = c.getValeur();
            this.compteur += Math.pow(10, val);
        }
    }

    //digits du compteur stockées dans un tableau
    public void getCompteurValChar() {
        String t;
        t = this.compteur + "";
        this.compteurValChar = t.toCharArray();
    }

    //crée un tableau permettant de récupérer les couleurs des 7 Cartes
    public char[] getStockageCouleurChar() {
        int stockageCouleur = 0;
        char[] stockageCouleurChar;
        int val = 0;

        for (Carte c : tLes7Cartes) {
            String couleur = c.getCouleur();
            switch (couleur) {
                case "spades":
                    val = 0;
                    break;
                case "diamonds":
                    val = 1;
                    break;
                case "hearts":
                    val = 2;
                    break;
                case "clubs":
                    val = 3;
                    break;
            }
            stockageCouleur += Math.pow(10, val);
        }
        String t = stockageCouleur + "";
        stockageCouleurChar = t.toCharArray();
        return stockageCouleurChar;
    }

    public int valeurBrelan() {
        int valeurBrelan = 0;
        for (int k = 0; k < this.compteurValChar.length; k++) {
            if (this.compteurValChar[k] == '3') {
                valeurBrelan = this.compteurValChar.length - k - 1;
                break;
            }
        }
        return valeurBrelan;
    }

    public int[] valeurPaire(int nbPaires) {
        int [] valeurPaire = new int [nbPaires];
        int i=0;
        for (int k = 0; k < this.compteurValChar.length; k++) {
            if (this.compteurValChar[k] == '2' || this.compteurValChar[k] == '3') {
                valeurPaire[i] = this.compteurValChar.length - k - 1;
                i++;
            }
            if (i==nbPaires){
                break;
            }
        }
        return valeurPaire;
    }

    //renvoie les valeurs des cartes ayant les plus grandes valeurs en tenant compte de certaines valeurs à ne pas prendre
    public int[] getMaxVal(int nbCartes, int combinaisonValeur1, int combinaisonValeur2){
        Collections.sort(this.tLes7Cartes, Collections.reverseOrder());
        int [] maxVal=new int[nbCartes];
        int i=0;
        for (Carte c : this.tLes7Cartes) {
            if (c.getValeur() != combinaisonValeur1 && c.getValeur() != combinaisonValeur2) {
                maxVal[i]=c.getValeur();
                i++;
            }
            if (i==nbCartes){
                break;
            }
        }
        return maxVal;
    }

    public LinkedList<Carte> getCartesMemeCouleur() {
        LinkedList<Carte> cartesMemeCouleur = new LinkedList<>();
        char[] stockageCouleurChar;
        String couleur = null;
        stockageCouleurChar = getStockageCouleurChar();

        //Vérifie s'il y a une couleur et récupère la couleur correspondante le cas échéant
        for (int j = 0; j < stockageCouleurChar.length; j++) {
            if (stockageCouleurChar[j] == '5' || stockageCouleurChar[j] == '6' || stockageCouleurChar[j] == '7') {
                compteurCouleur++;
                if (((stockageCouleurChar.length) - j) == 1) {
                    couleur = "spades";
                }
                if (((stockageCouleurChar.length) - j) == 2) {
                    couleur = "diamonds";
                }
                if (((stockageCouleurChar.length) - j) == 3) {
                    couleur = "hearts";
                }
                if (((stockageCouleurChar.length) - j) == 4) {
                    couleur = "clubs";
                }
                break;
            }
        }

        //stocke les cartes ayant la même couleur
        for (Carte c : tLes7Cartes) {
            if (c.getCouleur() == couleur) {
                cartesMemeCouleur.add(c);
            }
        }
        return cartesMemeCouleur;
    }


    public void quinteFlush(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        LinkedList<Carte> cartesMemeCouleur = getCartesMemeCouleur();
        char[] compteurValCharQF;
        long compteurQF = 0;

        //crée un compteur de valeurs des cartes de même couleur
        for (Carte c : cartesMemeCouleur) {
            int val = c.getValeur();
            compteurQF += Math.pow(10, val);
        }

        String t = compteurQF + "";
        compteurValCharQF = t.toCharArray();

        //Vérifie si les cartes de meme couleur ont des valeurs qui se suivent
        for (int k = 0; k < compteurValCharQF.length - 5; k++) {
            if (compteurValCharQF[k] != '0' && compteurValCharQF[k + 1] != '0' && compteurValCharQF[k + 2] != '0' && compteurValCharQF[k + 3] != '0' && compteurValCharQF[k + 4] != '0') {
                if (p == 0) {
                    tCompteurs[0]++;
                }
                tJoueurs.get(p).setScore(9);
                meilleureMain = true;
                break;
            }
        }

        //cas particulier de la QF de as à 5
        if (!meilleureMain) {
            if (compteurValCharQF.length == 15) {
                if (compteurValCharQF[0] != '0' && compteurValCharQF[9] != '0' && compteurValCharQF[10] != '0' && compteurValCharQF[11] != '0' && compteurValCharQF[12] != '0') {
                    if (p == 0) {
                        tCompteurs[0]++;
                    }
                    tJoueurs.get(p).setScore(9);
                    meilleureMain = true;
                }
            }
        }
    }


    public void carre(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        this.getCompteurValChar();

        //Vérifie si 4 cartes de même valeur sont dans les7Cartes
        for (int k = 0; k < compteurValChar.length; k++) {
            if (compteurValChar[k] == '4') {
                if (p == 0) {
                    tCompteurs[1]++;
                }
                tJoueurs.get(p).setScore(8);
                meilleureMain = true;
            }
        }
    }

    public void full(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        //Vérifie s'il y a 3 cartes ayant une même valeur et au moins 2 autres cartes ayant une même valeur (différente de celle des 3 cartes)
        for (int k = 0; k < compteurValChar.length; k++) {
            if (compteurValChar[k] == '3') {
                compteurBrelan++;
            }
            if (compteurValChar[k] == '2') {
                compteurPaire++;
            }
        }
        if ((compteurBrelan > 0 && compteurPaire > 0) || compteurBrelan == 2) {
            if (p == 0) {
                tCompteurs[2]++;
            }
            tJoueurs.get(p).setScore(7);
            meilleureMain = true;
        }
    }

    //Vérifie si au moins 5 cartes ont la meme couleur
    public void couleur(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        if (compteurCouleur > 0) {
            if (p == 0) {
                tCompteurs[3]++;
            }
            tJoueurs.get(p).setScore(6);
            meilleureMain = true;
        }
    }

    //Verifie si au moins 5 cartes se suivent (en valeur)
    public void suite(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        for (int k = 0; k < compteurValChar.length - 5; k++) {
            if (compteurValChar[k] != '0' && compteurValChar[k + 1] != '0' && compteurValChar[k + 2] != '0' && compteurValChar[k + 3] != '0' && compteurValChar[k + 4] != '0') {
                if (p == 0) {
                    tCompteurs[4]++;
                }
                tJoueurs.get(p).setScore(5);
                meilleureMain = true;
                break;
            }
        }

        //cas particulier de la suite de As à 5
        if (!meilleureMain) {
            if (compteurValChar.length == 15) {
                if (compteurValChar[0] != '0' && compteurValChar[9] != '0' && compteurValChar[10] != '0' && compteurValChar[11] != '0' && compteurValChar[12] != '0') {
                    if (p == 0) {
                        tCompteurs[4]++;
                    }
                    suiteAS5 = true;
                    tJoueurs.get(p).setScore(5);
                    meilleureMain = true;
                }
            }
        }
    }

    public void brelan(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        if (compteurBrelan > 0) {
            if (p == 0) {
                tCompteurs[5]++;
            }
            tJoueurs.get(p).setScore(4);
            meilleureMain = true;
        }
    }

    public void doublePaire(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        if (compteurPaire > 1) {
            if (p == 0) {
                tCompteurs[6]++;
            }
            tJoueurs.get(p).setScore(3);
            meilleureMain = true;
        }
    }

    public void paire(int[] tCompteurs, ArrayList<Joueur> tJoueurs, int p) {
        if (compteurPaire > 0) {
            if (p == 0) {
                tCompteurs[7]++;
            }
            tJoueurs.get(p).setScore(2);
            meilleureMain = true;
        }
    }

    public void carteHaute(int[] tCompteurs,  ArrayList<Joueur> tJoueurs, int p) {
        if (p == 0) {
            tCompteurs[8]++;
        }
        tJoueurs.get(p).setScore(1);
    }

    public void egaliteQuinteFlush(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        int QFHauteur1;
        int QFHauteur2;
        LinkedList<Carte> cartesMemeCouleur1 = this.getCartesMemeCouleur();
        LinkedList<Carte> cartesMemeCouleur2 = joueursMaxScore.get(0).les7Cartes.getCartesMemeCouleur();

        Collections.sort(cartesMemeCouleur1, Collections.reverseOrder());
        Collections.sort(cartesMemeCouleur2, Collections.reverseOrder());

        //Etablit la carte de la plus haute valeur parmi l'ensemble des cartes de la combinaison
        if (cartesMemeCouleur1.get(0).getValeur() != 14) {
            QFHauteur1 = cartesMemeCouleur1.get(0).getValeur();
        } else QFHauteur1 = cartesMemeCouleur1.get(1).getValeur(); //cas particulier de la QF As a 5

        if (cartesMemeCouleur2.get(0).getValeur() != 14) {
            QFHauteur2 = cartesMemeCouleur2.get(0).getValeur();
        } else QFHauteur2 = cartesMemeCouleur2.get(1).getValeur();

        if (QFHauteur1 > QFHauteur2) {
            tJoueurs.get(i).setScore(9.5);
            for (Joueur j :joueursMaxScore){
                j.setScore(9);
            }
            joueursMaxScore.clear();
            joueursMaxScore.add(tJoueurs.get(i));
        }
        if (QFHauteur1 < QFHauteur2) {
            for (Joueur j :joueursMaxScore){
                j.setScore(9.5);
            }
        }
        if (QFHauteur1==QFHauteur2){
            joueursMaxScore.add(tJoueurs.get(i));
            for (Joueur j :joueursMaxScore){
                j.setScore(9.5);
            }
        }
    }

    public void egaliteCarre(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        int carreVal1 = 0;
        int carreVal2 = 0;

        for (int k = 0; k < this.compteurValChar.length; k++) {
            if (this.compteurValChar[k] == '4') {
                carreVal1 = this.compteurValChar.length - k - 1;
                break;
            }
        }
        for (int k = 0; k < joueursMaxScore.get(0).les7Cartes.compteurValChar.length; k++) {
            if (joueursMaxScore.get(0).les7Cartes.compteurValChar[k] == '4') {
                carreVal2 = joueursMaxScore.get(0).les7Cartes.compteurValChar.length - k - 1;
                break;
            }
        }

        if (carreVal1 > carreVal2) {
            tJoueurs.get(i).setScore(8.5);
            for (Joueur j :joueursMaxScore){
                j.setScore(8);
            }
            joueursMaxScore.clear();
            joueursMaxScore.add(tJoueurs.get(i));
        }
        if (carreVal1 < carreVal2) {
            for (Joueur j :joueursMaxScore){
                j.setScore(8.5);
            }
        }
        if (carreVal1==carreVal2){
            //etablit une 5eme carte, ayant la plus haute valeur
            int maxVal1 = this.getMaxVal(1, carreVal1, 0)[0];
            int maxVal2 = joueursMaxScore.get(0).les7Cartes.getMaxVal(1, carreVal1, 0)[0];

            if (maxVal1 > maxVal2) {
                tJoueurs.get(i).setScore(8.5);
                for (Joueur j :joueursMaxScore){
                    j.setScore(8);
                }
                joueursMaxScore.clear();
                joueursMaxScore.add(tJoueurs.get(i));
            }
            if (maxVal1 < maxVal2) {
                for (Joueur j :joueursMaxScore){
                    j.setScore(8.5);
                }
            }
            if (maxVal1==maxVal2){
                joueursMaxScore.add(tJoueurs.get(i));
                for (Joueur j :joueursMaxScore){
                    j.setScore(8.5);
                }
            }
        }
    }

    public void egaliteFull(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        int brelanVal1 = this.valeurBrelan();
        int brelanVal2 = joueursMaxScore.get(0).les7Cartes.valeurBrelan();

        if (brelanVal1 == brelanVal2) {
            int paireVal1 = this.valeurPaire(1)[0];
            int paireVal2 = joueursMaxScore.get(0).les7Cartes.valeurPaire(1)[0];

            if (paireVal1 > paireVal2) {
                tJoueurs.get(i).setScore(7.5);
                for (Joueur j :joueursMaxScore){
                    j.setScore(7);
                }
                joueursMaxScore.clear();
                joueursMaxScore.add(tJoueurs.get(i));
            }
            if (paireVal1 < paireVal2) {
                for (Joueur j :joueursMaxScore){
                    j.setScore(7.5);
                }
            }
            if (paireVal1==paireVal2){
                joueursMaxScore.add(tJoueurs.get(i));
                for (Joueur j :joueursMaxScore){
                    j.setScore(7.5);
                }
            }
        } else {
            if (brelanVal1 > brelanVal2) {
                tJoueurs.get(i).setScore(7.5);
                for (Joueur j :joueursMaxScore){
                    j.setScore(7);
                }
                joueursMaxScore.clear();
                joueursMaxScore.add(tJoueurs.get(i));
            }
            if (brelanVal1 < brelanVal2) {
                for (Joueur j :joueursMaxScore){
                    j.setScore(7.5);
                }
            }
        }
    }

    public void egaliteCouleur(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        boolean egalite=false;
        LinkedList<Carte> cartesMemeCouleur1 = this.getCartesMemeCouleur();
        LinkedList<Carte> cartesMemeCouleur2 = joueursMaxScore.get(0).les7Cartes.getCartesMemeCouleur();
        Collections.sort(cartesMemeCouleur1, Collections.reverseOrder());
        Collections.sort(cartesMemeCouleur2, Collections.reverseOrder());

        for (int j = 0; j < 5; j++) {
            if (cartesMemeCouleur1.get(j).getValeur() > cartesMemeCouleur2.get(j).getValeur()) {
                tJoueurs.get(i).setScore(6.5);
                for (Joueur k :joueursMaxScore){
                    k.setScore(6);
                }
                joueursMaxScore.clear();
                joueursMaxScore.add(tJoueurs.get(i));
                egalite=false;
                break;
            }
            if (cartesMemeCouleur1.get(j).getValeur() < cartesMemeCouleur2.get(j).getValeur()) {
                for (Joueur k :joueursMaxScore){
                    k.setScore(6.5);
                }
                egalite=false;
                break;
            }
            egalite=true;
        }
        if (egalite){
            joueursMaxScore.add(tJoueurs.get(i));
            for (Joueur j :joueursMaxScore){
                j.setScore(6.5);
            }
        }
    }

    public void egaliteSuite(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        int suiteHauteur1 = 0;
        int suiteHauteur2 = 0;
        Collections.sort(this.tLes7Cartes, Collections.reverseOrder());
        Collections.sort(joueursMaxScore.get(0).les7Cartes.tLes7Cartes, Collections.reverseOrder());

        //Etablit la carte de la plus haute valeur parmi l'ensemble des cartes de la combinaison
        if (this.suiteAS5) {
            suiteHauteur1 = 5;
        }
        if (!this.suiteAS5) {
            for (int k = 0; k < compteurValChar.length - 5; k++) {
                if (compteurValChar[k] != '0' && compteurValChar[k + 1] != '0' && compteurValChar[k + 2] != '0' && compteurValChar[k + 3] != '0' && compteurValChar[k + 4] != '0') {
                    suiteHauteur1 = this.compteurValChar.length - k - 1;
                    break;
                }
            }
        }

        if (joueursMaxScore.get(0).les7Cartes.suiteAS5) {
            suiteHauteur2 = 5;
        }
        if (!joueursMaxScore.get(0).les7Cartes.suiteAS5) {
            for (int k = 0; k < joueursMaxScore.get(0).les7Cartes.compteurValChar.length - 5; k++) {
                if (joueursMaxScore.get(0).les7Cartes.compteurValChar[k] != '0' && joueursMaxScore.get(0).les7Cartes.compteurValChar[k + 1] != '0' && joueursMaxScore.get(0).les7Cartes.compteurValChar[k + 2] != '0' && joueursMaxScore.get(0).les7Cartes.compteurValChar[k + 3] != '0' && joueursMaxScore.get(0).les7Cartes.compteurValChar[k + 4] != '0') {
                    suiteHauteur2 = joueursMaxScore.get(0).les7Cartes.compteurValChar.length - k - 1;
                    break;
                }
            }
        }
        if (suiteHauteur1 > suiteHauteur2) {
            tJoueurs.get(i).setScore(5.5);
            for (Joueur k :joueursMaxScore){
                k.setScore(5);
            }
            joueursMaxScore.clear();
            joueursMaxScore.add(tJoueurs.get(i));
        }
        if (suiteHauteur1 < suiteHauteur2) {
            for (Joueur j :joueursMaxScore){
                j.setScore(5.5);
            }
        }
        if (suiteHauteur1==suiteHauteur2){
            joueursMaxScore.add(tJoueurs.get(i));
            for (Joueur j :joueursMaxScore){
                j.setScore(5.5);
            }
        }
    }

    public void egaliteBrelan(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        boolean egalite=false;
        int brelanVal1 = this.valeurBrelan();
        int brelanVal2 = joueursMaxScore.get(0).les7Cartes.valeurBrelan();

        if (brelanVal1 > brelanVal2) {
            tJoueurs.get(i).setScore(4.5);
            for (Joueur k :joueursMaxScore){
                k.setScore(4);
            }
            joueursMaxScore.clear();
            joueursMaxScore.add(tJoueurs.get(i));
        }
        if (brelanVal1 < brelanVal2) {
            for (Joueur j :joueursMaxScore){
                j.setScore(4.5);
            }
        }
        if (brelanVal1==brelanVal2){
            //etablit une 4eme et une 5eme carte, ayant les plus hautes valeurs
            int [] maxVal1=this.getMaxVal(2,brelanVal1, 0);
            int [] maxVal2=joueursMaxScore.get(0).les7Cartes.getMaxVal(2,brelanVal1, 0);
            for(int j=0; j< maxVal1.length;j++) {
                if (maxVal1[j] > maxVal2[j]) {
                    tJoueurs.get(i).setScore(4.5);
                    for (Joueur k :joueursMaxScore){
                        k.setScore(4);
                    }
                    joueursMaxScore.clear();
                    joueursMaxScore.add(tJoueurs.get(i));
                    egalite=false;
                    break;
                }
                if (maxVal1[j] < maxVal2[j]) {
                    for (Joueur k :joueursMaxScore){
                        k.setScore(4.5);
                    }
                    egalite=false;
                    break;
                }
                egalite=true;
            }
            if (egalite){
                joueursMaxScore.add(tJoueurs.get(i));
                for (Joueur j :joueursMaxScore){
                    j.setScore(4.5);
                }
            }
        }
    }

    public void egaliteDoublePaire(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        boolean egalite = false;
        int [] paireVal1=this.valeurPaire(2);
        int [] paireVal2=joueursMaxScore.get(0).les7Cartes.valeurPaire(2);

        for (int k=0;k<paireVal1.length;k++){
            if(paireVal1[k]>paireVal2[k]){
                tJoueurs.get(i).setScore(3.5);
                for (Joueur j :joueursMaxScore){
                    j.setScore(3);
                }
                joueursMaxScore.clear();
                joueursMaxScore.add(tJoueurs.get(i));
                egalite=false;
                break;
            }
            if (paireVal1[k] < paireVal2[k]) {
                for (Joueur j :joueursMaxScore){
                    j.setScore(3.5);
                }
                egalite=false;
                break;
            }
            egalite=true;
        }

        if (egalite) {
            //etablit une 5eme carte, ayant la plus haute valeur
            int maxVal1 = this.getMaxVal(1, paireVal1[0],paireVal1[1])[0];
            int maxVal2 = joueursMaxScore.get(0).les7Cartes.getMaxVal(1, paireVal1[0],paireVal1[1])[0];

            if (maxVal1 > maxVal2) {
                tJoueurs.get(i).setScore(3.5);
                for (Joueur k :joueursMaxScore){
                    k.setScore(3);
                }
                joueursMaxScore.clear();
                joueursMaxScore.add(tJoueurs.get(i));
            }
            if (maxVal1 < maxVal2) {
                for (Joueur j :joueursMaxScore){
                    j.setScore(3.5);
                }            }
            if (maxVal1==maxVal2){
                joueursMaxScore.add(tJoueurs.get(i));
                for (Joueur j :joueursMaxScore){
                    j.setScore(3.5);
                }
            }
        }
    }

    public void egalitePaire(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        boolean egalite=false;
        int paireVal1=this.valeurPaire(1)[0];
        int paireVal2=joueursMaxScore.get(0).les7Cartes.valeurPaire(1)[0];

        if(paireVal1>paireVal2){
            tJoueurs.get(i).setScore(2.5);
            for (Joueur k :joueursMaxScore){
                k.setScore(2);
            }
            joueursMaxScore.clear();
            joueursMaxScore.add(tJoueurs.get(i));
        }
        if (paireVal1<paireVal2){
            for (Joueur j :joueursMaxScore){
                j.setScore(2.5);
            }
        }
        if(paireVal1==paireVal2) {
            //etablit une 3eme, 4eme et 5eme carte, ayant les plus hautes valeurs
            int [] maxVal1=this.getMaxVal(3, paireVal1, 0);
            int [] maxVal2=joueursMaxScore.get(0).les7Cartes.getMaxVal(3, paireVal2, 0);
            for(int j=0; j< maxVal1.length;j++) {
                if (maxVal1[j] > maxVal2[j]) {
                    tJoueurs.get(i).setScore(2.5);
                    for (Joueur k :joueursMaxScore){
                        k.setScore(2);
                    }
                    joueursMaxScore.clear();
                    joueursMaxScore.add(tJoueurs.get(i));
                    egalite=false;
                    break;
                }
                if (maxVal1[j] < maxVal2[j]) {
                    for (Joueur k :joueursMaxScore){
                        k.setScore(2.5);
                    }
                    egalite=false;
                    break;
                }
                egalite =true;
            }
            if (egalite){
                joueursMaxScore.add(tJoueurs.get(i));
                for (Joueur j :joueursMaxScore){
                    j.setScore(2.5);
                }
            }
        }
    }

    public void egaliteCarteHaute(ArrayList<Joueur> tJoueurs, LinkedList<Joueur> joueursMaxScore, int i) {
        boolean egalite=false;
        //etablit les 5 cartes, ayant les plus hautes valeurs
        int [] maxVal1=this.getMaxVal(5, 0, 0);
        int [] maxVal2=joueursMaxScore.get(0).les7Cartes.getMaxVal(5, 0, 0);

        for(int j=0; j< maxVal1.length;j++) {
            if (maxVal1[j] > maxVal2[j]) {
                tJoueurs.get(i).setScore(1.5);
                for (Joueur k :joueursMaxScore){
                    k.setScore(1);
                }
                joueursMaxScore.clear();
                joueursMaxScore.add(tJoueurs.get(i));
                egalite=false;
                break;
            }
            if (maxVal1[j] < maxVal2[j]) {
                for (Joueur k :joueursMaxScore){
                    k.setScore(1.5);
                }
                egalite=false;
                break;
            }
            egalite=true;
        }
        if (egalite){
            joueursMaxScore.add(tJoueurs.get(i));
            for (Joueur j :joueursMaxScore){
                j.setScore(1.5);
            }
        }
    }
}
