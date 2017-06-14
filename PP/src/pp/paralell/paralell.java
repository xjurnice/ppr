/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp.paralell;

import pp.paralell.Vlakno;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author tjurnicek
 */
public class paralell {

    static ArrayList<String> results = new ArrayList<String>();
    final static String heslo = "xss"; //nase tajne heslo, ktere chceme prolomit
    static ArrayList<String> results1 = new ArrayList<>();

    static ArrayList<String> results2 = new ArrayList<>();
    char[] znaky = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    int maxlen = 4;

    public static void main(String[] args) {
        System.out.println("Nase tajne heslo je " + heslo + " s hashem " + MD5(heslo));
        paralell b = new paralell();
        System.out.println(results.size());
        int cores = 8;

        int velikostMnoz = results.size() / cores;

 

        ArrayList<String>[] outer = new ArrayList[cores];
        Vlakno[] vlakna = new Vlakno[cores];

        int zacMnoz = 0; //zac nastaven na 0 pro prvni mnozinu
        int konMnoz = velikostMnoz;
        for (int i = 0; i < cores; i++) { // cyklus vytvari pole poli podle poctu jader
            outer[i] = new ArrayList<String>();

            for (int j = zacMnoz; j < konMnoz; j++) { //zanoreny cyklus prirazuje prvky rovnomerne do mnozin
                outer[i].add(results.get(j));
            }
            zacMnoz = zacMnoz + velikostMnoz;
            // System.out.println(zacMnoz);
            konMnoz = konMnoz + velikostMnoz;

        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < cores; i++) {
            vlakna[i] = new Vlakno(i + ". vlakno", outer[i]);
            vlakna[i].start();
        }

        System.out.println("-------------------------");

        long end = System.currentTimeMillis();
        System.out.println("Prolomeni trvalo:" + (end - start) + " ms");
/*
        System.out.println(zacMnoz);
        System.out.println(konMnoz);

        System.out.println(outer[0].size());
        System.out.println(outer[1].size());
        System.out.println(outer[2].size());
        System.out.println(outer[3].size());
        System.out.println(outer[4].size());
        System.out.println(outer[5].size());
        System.out.println(outer[6].size());
        System.out.println(outer[7].size());
*/
        //   System.out.println(velikostMnoz * 8);
    }

    public static void zpracuj(ArrayList<String> results, String jmenoVlakna) {

        for (int i = 0; i < results.size(); i++) {
            //System.out.println(counter + ". " + results.get(counter));
            String md5String = MD5((String) results.get(i));
            String md5Heslo = MD5(heslo);
            if (md5String.equals(md5Heslo)) {
                System.out.println("Naslo vlakno:" + jmenoVlakna);
                System.out.println("Naslo se na " + i + ". pozici s hashem " + md5String);
                Vlakno.currentThread().interrupt();
              
               
            }

        }

    }

    static public String MD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public paralell() {
        int k = 0;
        while (k < znaky.length) {
            nextString(new Character(znaky[k]).toString());
            k++;
        }
    }

    private void nextString(String s) {
        String zahesHeslo = MD5(heslo);

        int i = 0;
        //System.out.println(s);

        while (i < znaky.length) {

            results.add(s + new Character(znaky[i]).toString()); //vkladame do Arraylistu
            if (new String(s + new Character(znaky[i]).toString()).length() <= maxlen - 1) {
                nextString(s + new Character(znaky[i]).toString());
            }
            i++;
        }

    }
}
