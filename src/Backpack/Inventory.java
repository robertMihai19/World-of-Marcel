package Backpack;

import Caractere.Entity;
import Mapa.CellElement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Inventory implements CellElement {
    public ArrayList<Potion> licori;
    public int currentWeight, maxWeight, money;

    public Inventory(int maxWeight) {
        licori = new ArrayList<>();
        this.maxWeight = maxWeight;
    }

    public boolean addPotion(Potion licoare) {
        if (currentWeight + licoare.getWeight() < maxWeight && money >= licoare.price()) {
            licori.add(licoare);
            money -= licoare.price();
            currentWeight += licoare.getWeight();
            return true;
        }
        return false;
    }

    public Potion removePotion(int index) {
        Potion licoare = licori.get(index);
        licori.remove(index);
        currentWeight -= licoare.getWeight();
        return licoare;
    }

    public float freeWeight() {
        return maxWeight - currentWeight;
    }

    @Override
    public String toCharacter() {
        return "I";
    }

    public ImageIcon toCharacterFrame() {
        return new ImageIcon("src\\Img\\icons8-backpack-48.png");
    }

    @Override
    public String toString() {
        return licori.toString() + " " + money + " banuti";
    }

    public JDialog createInventar(JFrame frame, Entity player) {
        JDialog ceva = new JDialog(frame, "Inventory", Dialog.ModalityType.DOCUMENT_MODAL);
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
                if (licori.size() > 5 * i + j)
                    row.add(licori.get(5 * i + j).image());
                else
                    row.add(null);
            inventarGame.addRow(row);
        }
        inventar.setBounds(0, 0, 200, 200);
        inventar.setRowHeight(50);
        inventar.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        inventar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int row = inventar.rowAtPoint(event.getPoint());
                int column = inventar.columnAtPoint(event.getPoint());
                if (row * 5 + column < licori.size()) {
                    Potion licoare = removePotion(5 * row + column);
                    if (licoare.getClass().getSimpleName().equals("HealthPotion")) {
                        player.regenLife(licoare.regenValue());
                    } else player.regenMana(licoare.regenValue());

                    for (int i = 5 * row + column; i < licori.size(); i++)
                        inventarGame.setValueAt(licori.get(i).image(), i / 5, i % 5);
                    inventarGame.setValueAt(null, (licori.size()) / 5, (licori.size()) % 5);
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
