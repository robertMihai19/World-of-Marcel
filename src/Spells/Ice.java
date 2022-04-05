package Spells;

import Caractere.Entity;

import javax.swing.*;
import java.awt.*;

public class Ice extends Spell {

    public Ice() {
        dmg = 20;
        cost = 30;
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
        return "Ice Spell";
    }

    @Override
    public ImageIcon getImage() {
        return new ImageIcon("src\\Img\\icons8-ice-48.png");
    }

    @Override
    public void visit(Entity entity) {
        if (entity.iceResistance)
            entity.receiveDamage(0);
        else entity.receiveDamage(dmg);
    }
}
