package Mapa;

import javax.swing.*;

public class Empty implements CellElement {

    public Empty() {
    }

    @Override
    public String toCharacter() {
        return "N";
    }

    @Override
    public ImageIcon toCharacterFrame(){
        return null;
    }
}
