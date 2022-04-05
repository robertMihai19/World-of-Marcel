package Backpack;

import Caractere.Character;
import Mapa.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Shop implements CellElement {

    public ArrayList<Potion> potiuni;

    public Shop() {
        potiuni = new ArrayList<>();
        Random raft = new Random();
        int noPotiuni = raft.nextInt(2, 5);
        for (int i = 0; i < noPotiuni; i++) {
            int licoare = raft.nextInt(0, 2);
            if (licoare == 0)
                potiuni.add(new ManaPotion());
            else
                potiuni.add(new HealthPotion());
        }
    }

    public Potion buyPotion(int index) {
        Potion potiune = potiuni.get(index);
        potiuni.remove(index);
        return potiune;
    }

    @Override
    public String toCharacter() {
        return "S";
    }

    @Override
    public ImageIcon toCharacterFrame() {
        return new ImageIcon("src\\Img\\icons8-shop-40.png");
    }

    @Override
    public String toString() {
        return potiuni.toString();
    }


    public JDialog createShop(JFrame frame, Character caracter) {
        JDialog ceva = new JDialog(frame, "La Vasile", Dialog.ModalityType.DOCUMENT_MODAL);
        ceva.setBounds(250, 150, 400, 400);
        Container container = ceva.getContentPane();
        container.setLayout(new BorderLayout());
        DefaultTableModel inventarGame = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable inventar = new JTable(inventarGame) {
            public Class getColumnClass(int column) {
                return Icon.class;
            }
        };

        for (int i = 0; i < 5; i++)
            inventarGame.addColumn("");

        for (int i = 0; i < 5; i++) {
            Vector<ImageIcon> row = new Vector<>();
            for (int j = 0; j < 5; j++)
                if (potiuni.size() > 5 * i + j)
                    row.add(potiuni.get(5 * i + j).image());
                else
                    row.add(null);
            inventarGame.addRow(row);
        }
        inventar.setBounds(0, 0, 200, 200);
        inventar.setRowHeight(50);
        inventar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int row = inventar.rowAtPoint(event.getPoint());
                int column = inventar.columnAtPoint(event.getPoint());
                if (row * 5 + column < potiuni.size()) {
                    caracter.inventar.addPotion(potiuni.get(5 * row + column));
                    buyPotion(5 * row + column);
                    for (int i = 5 * row + column; i < potiuni.size(); i++)
                        inventarGame.setValueAt(potiuni.get(i).image(), i / 5, i % 5);
                    inventarGame.setValueAt(null, (potiuni.size()) / 5, (potiuni.size()) % 5);
                }
            }
        });
        JPanel panou = new JPanel();
        panou.setLayout(new FlowLayout());
        JButton butonas = new JButton("Close");
        butonas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ceva.setVisible(false);
            }
        });
        panou.add(inventar);
        panou.add(butonas);
        container.add(panou);
        return ceva;
    }
}
