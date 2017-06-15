/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp.paralell;

import java.util.ArrayList;
import static pp.paralell.paralell.MD5;
import static pp.paralell.paralell.heslo;

/**
 *
 * @author tjurnicek
 */
class Vlakno extends Thread {

    private static volatile boolean isDone = false;

    ArrayList<String> mnozina;

    public Vlakno(String jmeno, ArrayList<String> items) {
        super(jmeno);
        this.mnozina = items;

    }

    public static void zpracuj(ArrayList<String> results, String jmenoVlakna) {

    }

    @Override
    public void run() {

        final long start = System.currentTimeMillis() / 1000;

        synchronized (this.mnozina) {

            System.out.println("Thread (" + this.getName() + ") has started!");

            for (int i = 0; i < this.mnozina.size(); i++) {
                //System.out.println(counter + ". " + results.get(counter));
                if (!isDone) {
                    //System.out.println("furt hledam");
                    String md5String = MD5((String) this.mnozina.get(i));
                    String md5Heslo = MD5(heslo);
                    if (md5String.equals(md5Heslo)) {
                        System.out.println("Naslo vlakno:" + this.getName());
                        System.out.println("Naslo se na " + i + ". pozici s hashem " + md5String);

                        Vlakno.isDone = true;
                        Vlakno.currentThread().interrupt();

                    }
                } else {
                    Vlakno.currentThread().interrupt();
                    break;

                }
            }

        }
        final long end = System.currentTimeMillis() / 1000;
        System.out.println("Thread (" + this.getName() + ") ended in " + (end - start) + " seconds! ");
    }


}
