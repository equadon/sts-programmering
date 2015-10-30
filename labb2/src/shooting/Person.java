/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooting;


public class Person {
    public final String name;
    public double accuracy;
    private int hit;
    
    public Person(String name, double accuracy, int hitCount) {
        this.name = name;
        this.hit = hitCount;
        this.accuracy = accuracy;
        //hit = 0;
    }
    
    public boolean isHit() {
        return hit > 0;
    }
    
    public int hitCount() {
        return hit;
    }
    
    public boolean isAlive() {
        return hit < 3;
    }
    
    public void shoot(Person victim) {
        if(Math.random() < accuracy) {
            victim.hit();
           // System.out.println(name + " träffade " + victim.name);
        }
    }
    public void hit() {
        hit++;
    }

    void setAccuracy(double d) {
        accuracy = d;
               
                
    }
}