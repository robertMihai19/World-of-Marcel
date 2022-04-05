package Caractere;

import Mapa.CellElement;
import Spells.*;

import java.util.*;

public abstract class Entity implements CellElement, Element {
    public ArrayList<Spell> abilitati;
    public Integer currentLife, maxLife, currentMana, maxMana;
    public boolean fireResistance = false, earthResistance = false, iceResistance = false;

    public void regenLife(int viata) {
        currentLife += viata;
        if (currentLife > maxLife)
            currentLife = maxLife;
    }

    public void regenMana(int mana) {
        currentMana += mana;
        if (currentMana > maxMana)
            currentMana = maxMana;
    }

    public boolean useSpell(Spell vraja, Entity inamic) {
        if (currentMana - vraja.getMannaPrice() >= 0) {
            currentMana -= vraja.getMannaPrice();
            inamic.accept(vraja);
            return true;
        }
        return false;
    }

    public abstract void receiveDamage(int dmg);

    public abstract int getDamage();

}
