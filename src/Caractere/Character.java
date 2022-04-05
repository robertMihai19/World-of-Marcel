package Caractere;

import Backpack.*;

public abstract class Character extends Entity {
    public String name, profession;
    public int level;
    //level = int
    public Inventory inventar;
    public int x, y, experience;
    int str, chr, dex;

    public boolean buy(Potion potiune) {
        if (inventar.freeWeight() >= potiune.getWeight() && inventar.money >= potiune.price()) {
            inventar.currentWeight += potiune.getWeight();
            inventar.money -= potiune.price();
            return true;
        }
        return false;
    }

    public String toString() {
        return "Abilitati:" + abilitati.toString() + "\n" + "viata:" + currentLife + "/" + maxLife + " mana " + currentMana + "/" + maxMana + "\n" + inventar;
    }

}
