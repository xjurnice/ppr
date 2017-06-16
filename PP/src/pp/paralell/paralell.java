/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp.paralell;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author tjurnicek
 */
public class paralell {

    static ArrayList<String> results = new ArrayList<String>();
    static String heslo = ""; //nase tajne heslo, ktere chceme prolomit
    char[] znaky = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    int maxDelka = 0;

    public static void main(String[] args) {
        paralell b = new paralell(5, "zebra");
        System.out.println("Nase tajne heslo je " + heslo + " s hashem " + MD5(heslo));

        System.out.println("V tomto nastaveni existuje " + results.size() + " kombinaci");
        int cores = 8; //pocet vlaken

        int velikostMnoz = results.size() / cores;

        ArrayList<String>[] outer = new ArrayList[cores];
        Vlakno[] vlakna = new Vlakno[cores];

        int zacMnoz = 0; //zac nastaven na 0 pro prvni mnozinu
        int konMnoz = velikostMnoz; //konec nastaven na velikost mnoziny
        for (int i = 0; i < cores; i++) { // cyklus vytvari pole poli podle poctu jader
            outer[i] = new ArrayList<String>();

            for (int j = zacMnoz; j < konMnoz; j++) { //zanoreny cyklus prirazuje prvky rovnomerne do mnozin
                outer[i].add(results.get(j));
            }
            zacMnoz = zacMnoz + velikostMnoz; //posun o velikostMnoz
            konMnoz = konMnoz + velikostMnoz; //posun o velikostMnoz

        }
        for (int i = 0; i < cores; i++) {
            vlakna[i] = new Vlakno(i + ". vlakno", outer[i]);
            vlakna[i].start();
        }

    }

    static public String MD5(String ret) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(ret.getBytes());

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public paralell(int maxDelka, String heslo) {
        this.maxDelka = maxDelka;
        this.heslo = heslo;
        int i = 0;
        while (i < znaky.length) {
            nextString(new Character(znaky[i]).toString());
            i++;
        }
    }

    private void nextString(String ret) {

        int i = 0;

        while (i < znaky.length) {

            results.add(ret + new Character(znaky[i]).toString()); //vkladame do Arraylistu
            if ((ret + new Character(znaky[i]).toString()).length() <= maxDelka - 1) {
                nextString(ret + new Character(znaky[i]).toString());
            }
            i++;
        }

    }
}
