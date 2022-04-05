package Spells;

import Caractere.Entity;

import javax.swing.*;

public class Fire extends Spell {

    public Fire() {
        dmg = 15;
        cost = 25;
    }

    @Override
    public int getDamage() {
        return dmg;
    }

    @Override
    public int getMannaPrice() {
        return cost;
    }

    @Override
    public String toString() {
        return "Fire Spell";
    }

    @Override
    public ImageIcon getImage() {
        return new ImageIcon("src\\Img\\icons8-fire-48.png");
    }

    @Override
    public void visit(Entity entity) {
        if (entity.fireResistance)
            entity.receiveDamage(0);
        else entity.receiveDamage(dmg);
    }
}
