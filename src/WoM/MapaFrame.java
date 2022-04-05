package WoM;

import Backpack.Shop;
import Caractere.Character;
import Caractere.Enemy;
import Caractere.Warrior;
import Mapa.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class MapaFrame implements ActionListener {

    final int n = 5, m = 5;
    JFrame frame;
    JTable mapa;
    JButton inventory;
    DefaultTableModel mapaGame;
    Grid harta;
    JPanel label;
    JLabel money, health, mana;
    int banuti, exp, inanimicDoborati, experienta;
    JDialog inventar, shop, fight;
    Action upAction, downAction, leftAction, rightAction;

    public MapaFrame(Character player) {
        frame = new JFrame();
        inventory = new JButton();
        mapaGame = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        mapa = new JTable(mapaGame) {
            public Class getColumnClass(int column) {
                return Icon.class;
            }
        };
        mapa.setFocusable(false);
        mapa.setRowSelectionAllowed(false);
        harta = Grid.generateMap(n, m, player);
        label = new JPanel();
        label.setLayout(null);
        upAction = new UpAction();
        downAction = new DownAction();
        leftAction = new LeftAction();
        rightAction = new RightAction();
        money = new JLabel(new ImageIcon("src\\Img\\icons8-money-30.png"));
        health = new JLabel(new ImageIcon("src\\Img\\icons8-heart-30.png"));
        mana = new JLabel(new ImageIcon("src\\Img\\icons8-mana-48.png"));
    }

    public JFrame run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < n; i++)
            mapaGame.addColumn("");
        for (int i = 0; i < n; i++) {
            Vector<ImageIcon> row = new Vector<>();
            for (int j = 0; j < m; j++)
                row.add(new ImageIcon("src\\Img\\icons8-question-mark-48.png"));
            mapaGame.addRow(row);
        }

        mapaGame.setValueAt(harta.caracter.toCharacterFrame(), 0, 0);
        mapa.setBounds(100, 100, 600, 400);
        mapa.setRowHeight(80);

        inventory.setIcon(harta.caracter.inventar.toCharacterFrame());
        inventory.setBounds(700, 15, 50, 48);
        inventory.setOpaque(false);
        inventory.setContentAreaFilled(false);
        inventory.setBorderPainted(false);
        inventory.addActionListener(this);

        health.setText(String.valueOf(harta.caracter.currentLife));
        health.setBounds(0, 0, 75, 75);
        mana.setText(String.valueOf(harta.caracter.currentMana));
        mana.setBounds(75, 0, 75, 75);
        money.setText(String.valueOf(harta.caracter.inventar.money));
        money.setBounds(0, 75, 75, 75);
        label.add(health);
        label.add(mana);
        label.add(money);
        label.add(mapa);
        label.add(inventory);

        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), "upAction");
        frame.getRootPane().getActionMap().put("upAction", upAction);
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "rightAction");
        frame.getRootPane().getActionMap().put("rightAction", rightAction);
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "downAction");
        frame.getRootPane().getActionMap().put("downAction", downAction);
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "leftAction");
        frame.getRootPane().getActionMap().put("leftAction", leftAction);
        frame.add(label);
        frame.setVisible(true);
        frame.setTitle("World Of Marcel");
        frame.setBounds(20, 20, 800, 600);
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inventar = harta.caracter.inventar.createInventar(frame, harta.caracter);
        inventar.setVisible(true);
        mana.setText(String.valueOf(harta.caracter.currentMana));
        health.setText(String.valueOf(harta.caracter.currentLife));
    }

    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int x = harta.caracter.x, y = harta.caracter.y;
            harta.goNorth();
            mapaGame.setValueAt(harta.caracter.toCharacterFrame(), harta.caracter.x, harta.caracter.y);
            mapaGame.setValueAt(harta.get(x).get(y).gen.toCharacterFrame(), x, y);
            if (harta.get(x - 1).get(y).gen.toCharacter().equals("S")) {
                shop = ((Shop) harta.get(x - 1).get(y).gen).createShop(frame, harta.caracter);
                shop.setVisible(true);
            } else if (harta.get(x - 1).get(y).gen.toCharacter().equals("E")) {

                inanimicDoborati++;
                fight = ((Enemy) harta.get(x - 1).get(y).gen).createEnemy(frame, harta.caracter);
                fight.setVisible(true);
                health.setText(String.valueOf(harta.caracter.currentLife));
                mana.setText(String.valueOf(harta.caracter.currentMana));

            } else if (harta.get(x - 1).get(y).gen.toCharacter().equals("F")) {
                FinalFrame finish = new FinalFrame(inanimicDoborati, harta.caracter.inventar.money, experienta + 100, harta.caracter.level);
                JFrame ceva = finish.run();
                frame.setVisible(false);
                ceva.setVisible(true);
            }
            money.setText(String.valueOf(harta.caracter.inventar.money));
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int x = harta.caracter.x, y = harta.caracter.y;
            harta.goSouth();
            mapaGame.setValueAt(harta.caracter.toCharacterFrame(), harta.caracter.x, harta.caracter.y);
            mapaGame.setValueAt(harta.get(x).get(y).gen.toCharacterFrame(), x, y);
            if (harta.get(x + 1).get(y).gen.toCharacter().equals("S")) {
                shop = ((Shop) harta.get(x + 1).get(y).gen).createShop(frame, harta.caracter);
                shop.setVisible(true);
            } else if (harta.get(x + 1).get(y).gen.toCharacter().equals("E")) {

                fight = ((Enemy) harta.get(x + 1).get(y).gen).createEnemy(frame, harta.caracter);
                fight.setVisible(true);
                health.setText(String.valueOf(harta.caracter.currentLife));
                mana.setText(String.valueOf(harta.caracter.currentMana));

            } else if (harta.get(x + 1).get(y).gen.toCharacter().equals("F")) {
                FinalFrame finish = new FinalFrame(inanimicDoborati, harta.caracter.inventar.money, experienta + 100, harta.caracter.level);
                JFrame ceva = finish.run();
                frame.setVisible(false);
                ceva.setVisible(true);
            }
            money.setText(String.valueOf(harta.caracter.inventar.money));
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int x = harta.caracter.x, y = harta.caracter.y;
            harta.goEast();
            mapaGame.setValueAt(harta.caracter.toCharacterFrame(), harta.caracter.x, harta.caracter.y);
            mapaGame.setValueAt(harta.get(x).get(y).gen.toCharacterFrame(), x, y);
            if (harta.get(x).get(y + 1).gen.toCharacter().equals("S")) {
                shop = ((Shop) harta.get(x).get(y + 1).gen).createShop(frame, harta.caracter);
                shop.setVisible(true);
            } else if (harta.get(x).get(y + 1).gen.toCharacter().equals("E")) {
                fight = ((Enemy) harta.get(x).get(y + 1).gen).createEnemy(frame, harta.caracter);
                fight.setVisible(true);
                health.setText(String.valueOf(harta.caracter.currentLife));
                mana.setText(String.valueOf(harta.caracter.currentMana));

            } else if (harta.get(x).get(y + 1).gen.toCharacter().equals("F")) {
                FinalFrame finish = new FinalFrame(inanimicDoborati, harta.caracter.inventar.money, experienta + 100, harta.caracter.level);
                JFrame ceva = finish.run();
                frame.setVisible(false);
                ceva.setVisible(true);
            }
            money.setText(String.valueOf(harta.caracter.inventar.money));
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int x = harta.caracter.x, y = harta.caracter.y;
            harta.goWest();
            mapaGame.setValueAt(harta.get(x).get(y).gen.toCharacterFrame(), x, y);
            mapaGame.setValueAt(harta.caracter.toCharacterFrame(), harta.caracter.x, harta.caracter.y);
            if (harta.get(x).get(y - 1).gen.toCharacter().equals("S")) {
                shop = ((Shop) harta.get(x).get(y - 1).gen).createShop(frame, harta.caracter);
                shop.setVisible(true);
            } else if (harta.get(x).get(y - 1).gen.toCharacter().equals("E")) {
                fight = ((Enemy) harta.get(x).get(y - 1).gen).createEnemy(frame, harta.caracter);
                fight.setVisible(true);
                health.setText(String.valueOf(harta.caracter.currentLife));
                mana.setText(String.valueOf(harta.caracter.currentMana));

            } else if (harta.get(x).get(y - 1).gen.toCharacter().equals("F")) {
                FinalFrame finish = new FinalFrame(inanimicDoborati, harta.caracter.inventar.money, experienta + 100, harta.caracter.level);
                JFrame ceva = finish.run();
                frame.setVisible(false);
                ceva.setVisible(true);
            }
            money.setText(String.valueOf(harta.caracter.inventar.money));
        }
    }

}
