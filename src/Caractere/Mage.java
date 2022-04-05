package Caractere;

import Backpack.Inventory;
import Spells.*;

import javax.swing.*;
import java.util.*;

public class Mage extends Character {

    public Mage(String name, String level, int experience) {
        this.name = name;
        this.profession = "Mage";
        this.level = Integer.parseInt(level);
        this.experience = experience;
        abilitati = new ArrayList<>();
        inventar = new Inventory(10);
        x = y = 0;
        int nivel = Integer.parseInt(level);
        str = nivel;
        dex = 4 * nivel;
        chr = 6 * nivel;
        currentLife = maxLife = 150 * nivel;
        currentMana = maxMana = 300 * nivel;
        earthResistance = true;
        Random resitantece = new Random();
        int no_Spells = resitantece.nextInt(2, 5);
        for (int i = 0; i < no_Spells; i++) {
            int spell = resitantece.nextInt(3);
            if (spell == 0)
                abilitati.add(new Earth());
            else if (spell == 1)
                abilitati.add(new Fire());
            else abilitati.add(new Ice());
        }
    }

    @Override
    public void receiveDamage(int dmg) {
        Random dodge = new Random();
        if (dodge.nextInt(0, 2) == 0) {
            System.out.println("Ai suferit " + dmg + " daune");
            currentLife -= dmg;
        } else System.out.println("Ai reusit sa eviti atacul inamicului");
    }

    @Override
    public int getDamage() {
        Random critica = new Random();
        if (critica.nextInt(0, 2) == 0) {
            System.out.println("Ai prin un atac normal de:" + (str * 5 + dex * 3 + chr));
            return str * 5 + dex * 3 + chr;
        } else {
            System.out.println("Ai prins un atac critica de: " + (2 * (str * 5 + dex * 3 + chr)));
            return 2 * (str * 5 + dex * 3 + chr);
        }
    }

    @Override
    public String toCharacter() {
        return "P";
    }

    @Override
    public ImageIcon toCharacterFrame() {
        return new ImageIcon("src\\Img\\icons8-mage-emoji-48.png");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
