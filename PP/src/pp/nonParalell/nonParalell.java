/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp.nonParalell;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author tjurnicek
 */
public class nonParalell {

    static ArrayList<String> results = new ArrayList<String>(); //Arraylist urceny pro ulozeni vsech kombinaci hesla
    static String heslo = ""; //nase tajne heslo, ktere chceme prolomit
    char[] znaky = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    int maxDelka = 0;

    public static void main(String[] args) {

        nonParalell b = new nonParalell(5, "zebra"); //konfigurace - zadavame max. delku retezce a heslo
        
        System.out.println("V tomto nastaveni existuje " + results.size() + " kombinaci");

        System.out.println("Nase tajne heslo je " + heslo + " s hashem " + MD5(heslo));
       
        long start = System.currentTimeMillis();

        b.zpracuj(results);

        long end = System.currentTimeMillis();
        System.out.println("Nalezení hesla trvalo: " + (end - start) / 1000 + " s");
    }

    public void zpracuj(ArrayList results) {

        for (int i = 0; i < results.size(); i++) {
            String md5String = MD5((String) results.get(i));
            String md5Heslo = MD5(heslo);
            if (md5String.equals(md5Heslo)) {
                System.out.println("Naslo se na " + i + ". pozici s hashem " + md5String);
                break;
            }

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

    public nonParalell(int maxDelka, String heslo) {
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
