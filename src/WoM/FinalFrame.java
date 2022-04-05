package WoM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinalFrame implements ActionListener {

    JFrame frame;
    JLabel statistici;
    JButton end;
    Container container;
    int inamiciOmorati = 0, monede = 0, experienta = 0, nivel = 0;
    Character caracter;

    public FinalFrame(int inamiciOmorati, int monede, int experienta, int nivel) {
        this.inamiciOmorati += inamiciOmorati;
        this.monede += monede;
        this.experienta += experienta;
        this.nivel += nivel;
        frame = new JFrame();
        statistici = new JLabel();
        container = new Container();
        end = new JButton("End");
    }

    public JFrame run() {
        statistici.setText("<html>Inamici omorati" + inamiciOmorati + "<br/>Monede dobandite:" + monede + "<br/>Experienta curenta:" + experienta + "<br/>Nivel curent:" + nivel + " <html/>");
        statistici.setFont(new Font("Serif", Font.PLAIN, 24));
        end.addActionListener(this);
        statistici.setBounds(300, 0, 300, 300);
        end.setBounds(350, 450, 100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.add(statistici);
        container.add(end);

        frame.setBounds(10, 10, 800, 600);
        frame.setTitle("World of Marcel");
        frame.add(container);
        frame.setVisible(true);
        return frame;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == end) frame.setVisible(false);
    }
}
