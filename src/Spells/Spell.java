package Spells;

import javax.swing.*;

public abstract class Spell implements Visitor {
    int dmg, cost;

    public abstract int getDamage();
    public abstract int getMannaPrice();

    public abstract ImageIcon getImage();

}
