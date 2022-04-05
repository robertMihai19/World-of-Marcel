package Caractere;

import Backpack.Inventory;
import Mapa.CellElement;
import Spells.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Warrior extends Character implements CellElement {

    public Warrior(String name, String level, int experience) {
        this.name = name;
        this.profession = "Warrior";
        this.level = Integer.parseInt(level);
        this.experience = experience;
        abilitati = new ArrayList<>();
        inventar = new Inventory(20);
        x = y = 0;
        str = 6 * this.level;
        dex = 3 * this.level;
        chr = this.level;
        currentLife = maxLife = 200 * this.level;
        currentMana = maxMana = 100 * this.level;
        fireResistance = true;
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
        return new ImageIcon("src\\Img\\icons8-iron-age-warrior-48.png");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
