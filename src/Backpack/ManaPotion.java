package Backpack;

import javax.swing.*;

public class ManaPotion implements Potion {

    private boolean type;
    private int weight, regValue, price;

    public ManaPotion() {
        type = false;
        this.weight = 1;
        this.regValue = 30;
        this.price = 5;
    }

    @Override
    public boolean regenType() {
        return type;
    }

    @Override
    public int price() {
        return price;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int regenValue() {
        return regValue;
    }

    @Override
    public String toString() {
        return "Mana Potion" + " " + price();
    }

    @Override
    public ImageIcon image(){
        return new ImageIcon("src\\Img\\mana-potion.png");
    }
}
