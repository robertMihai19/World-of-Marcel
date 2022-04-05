package WoM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseCharacter implements ActionListener {

    JFrame frame;
    JButton caracter1, caracter2, caracter3;
    Container container;
    Account cont;

    public ChooseCharacter(Account cont) {
        this.cont = cont;
        frame = new JFrame();
        caracter1 = new JButton(cont.getCharacters().get(0).toCharacterFrame());
        caracter2 = new JButton(cont.getCharacters().get(1).toCharacterFrame());
        caracter3 = new JButton(cont.getCharacters().get(2).toCharacterFrame());
        container = frame.getContentPane();
        container.setLayout(null);
    }

    public JFrame run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        caracter1.setBounds(50, 100, 200, 300);
        caracter1.setText("<html>Name:" + cont.getCharacters().get(0).name + "<br/>Level:" + cont.getCharacters().get(0).level + "</html>");
        caracter2.setBounds(300, 100, 200, 300);
        caracter2.setText("<html>Name:" + cont.getCharacters().get(1).name + "<br/>Level:" + cont.getCharacters().get(1).level + "</html>");
        caracter3.setBounds(550, 100, 200, 300);
        caracter3.setText("<html>Name:" + cont.getCharacters().get(2).name + "<br/>Level:" + cont.getCharacters().get(2).level + "</html>");
        caracter1.addActionListener(this);
        caracter2.addActionListener(this);
        caracter3.addActionListener(this);
        container.add(caracter1);
        container.add(caracter2);
        container.add(caracter3);
        frame.setBounds(10, 10, 800, 600);
        frame.setTitle("World of Marcel");
        frame.setVisible(true);
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == caracter1) {
            MapaFrame mapa = new MapaFrame(cont.getCharacters().get(0));
            JFrame finalFrame = mapa.run();
            frame.setVisible(false);
            finalFrame.setVisible(true);
        } else if (e.getSource() == caracter2) {
            MapaFrame mapa = new MapaFrame(cont.getCharacters().get(1));
            JFrame finalFrame = mapa.run();
            frame.setVisible(false);
            finalFrame.setVisible(true);
        } else {
            MapaFrame mapa = new MapaFrame(cont.getCharacters().get(2));
            JFrame finalFrame = mapa.run();
            frame.setVisible(false);
            finalFrame.setVisible(true);

        }
    }
}
