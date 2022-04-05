package Spells;

import Caractere.Entity;

import javax.swing.*;

public class Earth extends Spell {

    public Earth() {
        dmg = 10;
        cost = 20;
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
        return "Earth Spell";
    }

    @Override
    public ImageIcon getImage() {
        return new ImageIcon("src\\Img\\icons8-rock-50.png");
    }

    @Override
    public void visit(Entity entity) {
        if (entity.earthResistance)
            entity.receiveDamage(0);
        else entity.receiveDamage(dmg);
    }
}
