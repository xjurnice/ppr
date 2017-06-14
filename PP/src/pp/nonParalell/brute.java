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
public class brute {

    static ArrayList<String> results = new ArrayList<String>();
    final static String heslo = "abcxx"; //nase tajne heslo, ktere chceme prolomit

    char[] znaky = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    int maxlen = 5;

    public static void main(String[] args) {
        brute b = new brute();
        System.out.println("Nase tajne heslo je " + heslo + " s hashem " + MD5(heslo));
            long start = System.currentTimeMillis();

        brute.zpracuj(results);
        
         long end = System.currentTimeMillis();
        System.out.println("Prolomeni trvalo:" + (end - start) + " ms");
    }

    static public void zpracuj(ArrayList results){

        for (int i = 0; i < results.size(); i++) {
            //System.out.println(counter + ". " + results.get(counter));
            String md5String = MD5((String) results.get(i));
            String md5Heslo = MD5(heslo);
            if (md5String.equals(md5Heslo)) {
                System.out.println("Naslo se na " + i + ". pozici s hashem " + md5String);
                break;
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

    public brute() {
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
