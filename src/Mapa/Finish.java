package Mapa;

import javax.swing.*;

public class Finish implements CellElement {


    public Finish() {

    }

    @Override
    public String toCharacter() {
        return "F";
    }

    @Override
    public ImageIcon toCharacterFrame() {
        return new ImageIcon("src\\Img\\icons8-finish-flag-48.png");
    }
}