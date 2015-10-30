/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooting;

public class Duel {
   private Person[] players;
   private Person sofie;

    public Duel(Person[] players, Person sofie) {
        this.players = players;
        this.sofie = sofie;
    }
    
    public void duel() {
        while (!endDuel()) {
            if (countDead() > 0) {
                sofie.setAccuracy(0.3);
            }
            
            for (Person person : players) {
                //System.out.printf("%s: hits=%d, accuracy=%.3f\n", person.name, person.hitCount(), person.accuracy);
                if (person.isAlive()) {
                    Person victim = chooseVictim(person);
                    person.shoot(victim);
                }    
            }
        }
    }
    
    public Person chooseVictim(Person ignore) {
        Person highest = null;
        
        for (Person person : players) {
            if (person != ignore) {
                if (highest == null) {
                    highest = person;
                } else if (person.isAlive() && person.accuracy > highest.accuracy) {
                    highest = person;
                }
            }
        }
        
        return highest;
    }
    
    public int countDead() {
        int count = 0;
        for (Person person : players)
            if (!person.isAlive())
                count++;
        return count;
    }
    
    public Person getWinner() {
        Person winner = null;
        int losers = 0;
        
        for (Person person : players) {
            if (person.hitCount() < 3)
                winner = person;
        }
        
        return winner;
    }
    
    private boolean endDuel() {
        int playerCount = 0;
        for (Person person : players) {
            if (person.hitCount() > 2) {
                playerCount++;
            }
        }
        return playerCount > 1;
    }
    
    private int countAlive() {
        int count = 0;
        for(Person person: players) 
            if (!person.isHit())
                count++;
        return count;
    }
   
}