/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooting;

/**
 *
 * @author melissaeklund
 */
public class Shooting {

  
    public static void main(String[] args) {
        
        int sofieW = 0;
        int alvinW = 0;
        int juliaW = 0;
        
        for (int i = 0; i < 10000; i++) {
            Person sofie = new Person("Sofie", 0.0, 0);
            Person alvin = new Person("Alvin", 0.5, 0);
            Person julia = new Person("Julia", 1.0, 0);
            Person[] players = {sofie, alvin, julia};
            Duel duel = new Duel(players, sofie);
            duel.duel();
            Person winner = duel.getWinner();
            if (winner.equals(sofie))
                sofieW++;
            else if (winner.equals(alvin))
                alvinW++;
            else 
                juliaW++;
        }
        
        System.out.println("Sofie vann: " + sofieW);
        System.out.println("Alvin vann: " + alvinW);
        System.out.println("Julia vann: " + juliaW);
        
    }
    
}