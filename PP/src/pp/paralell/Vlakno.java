/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp.paralell;

import java.util.ArrayList;

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
    
   public void setDone(){
   isDone=true;
   }
   
   

    @Override
    public void run() {
        //System.out.println("Vlákno  spuštěno");
        synchronized (this.mnozina) {
            paralell.zpracuj(this.mnozina, this.getName());
        }
    }

    public static void main(String[] args) {
//Vlakno a = new Vlakno("Moje",);
//a.start();
        int number = 8;

        ArrayList<String>[] outer = new ArrayList[number];
        for (int i = 0; i < number; i++) {
            outer[i] = new ArrayList<String>();
        }

        outer[2].add("a");
        outer[0].add("a");
        outer[2].add("b");

        for (int i = 0; i < outer[2].size(); i++) {
            System.out.println(outer[2].get(i));
        }
    }

}
