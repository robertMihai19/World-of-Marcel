package Backpack;

import javax.swing.*;

public class HealthPotion implements Potion {

    private boolean type;
    private int weight, regValue, price;

    public HealthPotion() {
        type = true;
        this.weight = 1;
        this.regValue = 50;
        this.price = 7;
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
        return "Health Potion" + " " + price();
    }

    @Override
    public ImageIcon image() {
        return new ImageIcon("src\\Img\\health-potion.png");
    }
}
