package Mapa;

import Backpack.Shop;
import Caractere.Character;
import Caractere.Enemy;
import Caractere.Warrior;

import java.util.*;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int n, m;
    public Character caracter;
    private static Grid ref;

    private Grid(int n, int m, Character character) {
        this.n = n;
        this.m = m;
        this.caracter = character;

        for (int i = 0; i < n; i++) {
            this.add(new ArrayList<>());
            for (int j = 0; j < m; j++)
                this.get(i).add(new Cell(i, j, Cell.type.EMPTY));
        }
        this.get(0).get(0).checked = true;
    }

    public void generateObjects(int n, Cell.type obiect) {
        int i = 0, x_poz, y_poz;
        Random ran = new Random();
        while (i < n) {
            x_poz = ran.nextInt(0, this.n);
            y_poz = ran.nextInt(0, m);
            if (x_poz + y_poz != 0 && x_poz + y_poz + 2 != this.n + this.m) {
                if (this.get(x_poz).get(y_poz).gen.toCharacter().equals("N")) {
                    i++;
                    Cell celula = new Cell(x_poz, y_poz, obiect);
                    this.get(x_poz).set(y_poz, celula);
                }
            }
        }
    }


    public static Grid generateMap(int n, int m, Character character) {
        ref = new Grid(n, m, character);
        ref.generateObjects(new Random().nextInt(2, 5), Cell.type.SHOP);
        ref.generateObjects(new Random().nextInt(4, 7), Cell.type.ENEMY);
        ref.generateObjects(1, Cell.type.FINISH);
        return ref;
    }

    public static Grid generateMapHardocat(int n, int m, Character character) {
        ref = new Grid(n, m, character);
        ref.get(0).set(3, new Cell(0, 3, Cell.type.SHOP));
        ref.get(1).set(3, new Cell(1, 3, Cell.type.SHOP));
        ref.get(2).set(0, new Cell(2, 0, Cell.type.SHOP));
        ref.get(3).set(4, new Cell(3, 4, Cell.type.ENEMY));
        ref.get(4).set(4, new Cell(4, 4, Cell.type.FINISH));
        return ref;
    }

    public boolean goNorth() {
        if (caracter.x - 1 >= 0) {
            caracter.x = caracter.x - 1;
            if (!this.get(caracter.x).get(caracter.y).checked) {
                Random rand = new Random();
                if (rand.nextInt(5) == 3) {
                    caracter.inventar.money += 10;
                }
                this.get(caracter.x).get(caracter.y).checked = true;
            }
        }
        return false;
    }

    public boolean goSouth() {
        if (caracter.x + 1 < n) {
            caracter.x = caracter.x + 1;
            if (!this.get(caracter.x).get(caracter.y).checked) {
                Random rand = new Random();
                if (rand.nextInt(5) == 3) {
                    caracter.inventar.money += 10;
                }
                this.get(caracter.x).get(caracter.y).checked = true;
            }
        }
        return false;
    }

    public boolean goEast() {
        if (caracter.y + 1 < m) {
            caracter.y = caracter.y + 1;
            if (!this.get(caracter.x).get(caracter.y).checked) {
                Random rand = new Random();
                if (rand.nextInt(5) == 3) {
                    caracter.inventar.money += 10;
                }
                this.get(caracter.x).get(caracter.y).checked = true;
            }
            return true;
        }
        return false;
    }

    public boolean goWest() {
        if (caracter.y - 1 >= 0) {
            caracter.y = caracter.y - 1;
            if (!this.get(caracter.x).get(caracter.y).checked) {
                Random rand = new Random();
                if (rand.nextInt(5) == 3) {
                    caracter.inventar.money += 10;
                }
                this.get(caracter.x).get(caracter.y).checked = true;
            }

            return true;
        }
        return false;
    }

}
