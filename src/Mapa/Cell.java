package Mapa;


import Backpack.Shop;
import Caractere.Enemy;

import java.util.Random;

public class Cell {
    public int x, y;

    public enum type {EMPTY, ENEMY, SHOP, FINISH}

    public CellElement gen;
    public boolean checked;

    public Cell(int x, int y, type tip) {
        this.x = x;
        this.y = y;
        Random rand = new Random();
        checked = false;
        if (tip == type.SHOP) {
            gen = new Shop();
        } else if (tip == type.ENEMY) {
            gen = new Enemy(rand.nextInt(75, 150), rand.nextInt(50, 100));
        } else if (tip == type.FINISH) {
            gen = new Finish();
        } else if (tip == type.EMPTY) {
            gen = new Empty();
        }
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        String rez = gen.toCharacter();
        return rez;
    }

}

