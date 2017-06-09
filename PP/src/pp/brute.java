/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp;

import static java.nio.file.Files.size;
import java.util.ArrayList;

/**
 *
 * @author tjurnicek
 */
public class brute {
    static ArrayList<String> results = new ArrayList<String>();
    final static String heslo = "accc"; //nase tajne heslo, ktere chceme prolomit
    static String [] pole;

    char[] canUse = {'a', 'b', 'c', 'd'};
    int maxlen = 4;

    public static void main(String[] args) {
       brute b = new brute();
        System.out.println("Nase tajne heslo je " + heslo + " s hashem " + MD5(heslo));
        
        if ((heslo) == (heslo)){
        System.out.println("Yes");
        }
        
      for (int counter = 0; counter < results.size(); counter++) { 		      
          System.out.println(counter + ". " + results.get(counter));
          String md5String = MD5(results.get(counter));
          String m5Heslo = MD5(heslo);
          if (md5String.equals(m5Heslo)){
          System.out.println("Naslo se na " +  counter + " s hashem " + md5String);
          
          }
      }   
        	
		

    }

    static public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public brute() {
        int k = 0;
        while (k < canUse.length) {
           nextString(new Character(canUse[k]).toString());
            k++;
        }
    }


    private void nextString(String s) {
        String zahesHeslo = MD5(heslo);
        
        int i = 0;
       //System.out.println(s);
        
        while (i < canUse.length) {

          // System.out.println(s + new Character(canUse[i]).toString());
            results.add(s + new Character(canUse[i]).toString());
            if (new String(s + new Character(canUse[i]).toString()).length() <= maxlen - 1) {
                nextString(s + new Character(canUse[i]).toString());
            }
            i++;
        }

    }
}
