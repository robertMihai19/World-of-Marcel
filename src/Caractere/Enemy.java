package Caractere;

import Spells.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Enemy extends Entity {

    public Enemy(int life, int mana) {
        maxLife = life;
        currentLife = maxLife;
        currentMana = maxMana = mana;
        Random resitantece = new Random();
        fireResistance = resitantece.nextBoolean();
        earthResistance = resitantece.nextBoolean();
        iceResistance = resitantece.nextBoolean();
        abilitati = new ArrayList<>();
        int no_Spells = resitantece.nextInt(2, 5);
        for (int i = 0; i < no_Spells; i++) {
            int spell = resitantece.nextInt(3);
            if (spell == 0)
                abilitati.add(new Earth());
            else if (spell == 1)
                abilitati.add(new Fire());
            else abilitati.add(new Ice());
        }
    }

    @Override
    public String toCharacter() {
        return "E";
    }

    @Override
    public ImageIcon toCharacterFrame() {
        return new ImageIcon("src\\Img\\icons8-brutus-48.png");
    }

    @Override
    public void receiveDamage(int dmg) {
        Random dodge = new Random();
        if (dodge.nextInt(0, 2) == 0) {
            System.out.println("Inamicul a suferit " + dmg + " daune");
            currentLife -= dmg;
        } else System.out.println("Inamicul a reusit sa eviti atacul inamicului");
    }

    @Override
    public int getDamage() {
        Random critica = new Random();
        if (critica.nextInt(0, 2) == 0) {
            System.out.println("Inamicul a prin un atac normal de:" + 20);
            return 20;
        } else {
            System.out.println("Inamicul a prins un atac critica de: " + 20);
            return 40;
        }
    }


    @Override
    public String toString() {
        String sumar = "\n" + "Are rezistenta la:";
        if (fireResistance)
            sumar += "Foc ";
        if (iceResistance)
            sumar += "Gheata ";
        if (earthResistance)
            sumar += "Pamant ";
        sumar += "\nViata curenta:" + currentLife + " Mana curenta:" + currentMana;
        return sumar;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public JDialog createEnemy(JFrame frame, Character player) {
        Enemy inamic = this;
        JButton hit = new JButton("HIT");
        JButton spell = new JButton("Use Spell");
        JButton openInventory = new JButton("Open\n Inventory");
        JButton startFight = new JButton("Start");
        JLabel instructions = new JLabel("<html>Apasa pe butonul de start pentru a incepe lupta</html>");
        instructions.setBounds(150, 125, 300, 100);

        JDialog ceva = new JDialog(frame, "Batalia cu Mosneagul", Dialog.ModalityType.DOCUMENT_MODAL);
        ceva.setBounds(125, 125, 600, 450);
        Container container = ceva.getContentPane();
        JPanel panou = new JPanel(null);
        JLabel playerHealth, playerMana, enemyHealth, enemyMana;
        playerHealth = new JLabel(new ImageIcon("src\\Img\\icons8-heart-30.png"));
        playerHealth.setText(String.valueOf(player.currentLife));
        playerHealth.setBounds(0, 0, 75, 75);
        enemyHealth = new JLabel(new ImageIcon("src\\Img\\icons8-heart-30.png"));
        enemyHealth.setText(String.valueOf(this.currentLife));
        enemyHealth.setBounds(500, 0, 75, 75);

        playerMana = new JLabel(new ImageIcon("src\\Img\\icons8-mana-48.png"));
        playerMana.setText(String.valueOf(player.currentMana));
        playerMana.setBounds(75, 0, 75, 75);
        enemyMana = new JLabel(new ImageIcon("src\\Img\\icons8-mana-48.png"));
        enemyMana.setText(String.valueOf(this.currentMana));
        enemyMana.setBounds(425, 0, 75, 75);

        ArrayList<JLabel> resistancePlayer, resistanceEnemy;
        ArrayList<JButton> vrajeste = new ArrayList<>();
        resistanceEnemy = new ArrayList<>();
        resistancePlayer = new ArrayList<>();
        if (player.earthResistance) {
            resistancePlayer.add(new JLabel(new ImageIcon("src\\Img\\icons8-rock-50.png")));
        }
        if (player.fireResistance) {
            resistancePlayer.add(new JLabel(new ImageIcon("src\\Img\\icons8-fire-48.png")));
        }
        if (player.iceResistance) {
            resistancePlayer.add(new JLabel(new ImageIcon("src\\Img\\icons8-ice-48.png")));
        }

        for (JLabel smth : resistancePlayer) {
            smth.setBounds(0, 75, 50, 75);
            panou.add(smth);
        }

        if (this.earthResistance) {
            resistanceEnemy.add(new JLabel(new ImageIcon("src\\Img\\icons8-rock-50.png")));
        }
        if (this.fireResistance) {
            resistanceEnemy.add(new JLabel(new ImageIcon("src\\Img\\icons8-fire-48.png")));
        }
        if (this.iceResistance) {
            resistanceEnemy.add(new JLabel(new ImageIcon("src\\Img\\icons8-ice-48.png")));
        }

        for (JLabel smth : resistanceEnemy) {
            int size = resistanceEnemy.size() - resistanceEnemy.indexOf(smth);
            smth.setBounds(600 - 60 * size, 75, 50, 75);
            panou.add(smth);
        }

        for (Spell vraja : player.abilitati) {
            int size = player.abilitati.size() - player.abilitati.indexOf(vraja);
            size--;
            JLabel smth = new JLabel(vraja.getImage());
            smth.setBounds(0, 150 + 50 * size, 50, 50);
            JLabel daune = new JLabel(String.valueOf(vraja.getDamage()));
            daune.setBounds(55, 155 + 50 * size, 25, 25);
            daune.setForeground(Color.red);
            JLabel mana = new JLabel(String.valueOf(vraja.getMannaPrice()));
            mana.setBounds(75, 155 + 50 * size, 25, 25);
            mana.setForeground(Color.BLUE);
            JButton ability = new JButton(vraja.toString());
            ability.setBounds(105 * (size + 1), 350, 100, 50);
            ability.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand());
                    instructions.setText("<html>Poti apasa pe butonul de hit sa dai un basic <br/>pe butonul de Spell sa alegi ce Spell vrei sa folosesti<br/>sau pe butonul de invetory sa-ti deschizi inventarul</html>");
                    for (JButton magie : vrajeste) {
                        magie.setVisible(false);
                    }
                    for (Spell magie : player.abilitati) {
                        if (magie.toString().equals(e.getActionCommand())) {
                            if (!player.useSpell(magie, inamic))
                                System.out.println("Insuficienta mana");
                            break;
                        }
                    }
                    if (currentLife <= 0) {
                        if (new Random().nextInt(0, 5) < 4) {
                            player.inventar.money += 20;
                        }
                        ceva.setVisible(false);
                    }
                    Random decide = new Random();
                    if (decide.nextInt(0, 5) == 0) {
                        inamic.useSpell(inamic.abilitati.get(decide.nextInt(0, inamic.abilitati.size())), player);
                    } else {
                        player.receiveDamage(inamic.getDamage());
                    }
                    playerMana.setText(String.valueOf(player.currentMana));
                    playerHealth.setText(String.valueOf(player.currentLife));
                    enemyHealth.setText(String.valueOf(currentLife));
                    enemyMana.setText(String.valueOf(currentMana));
                    if (player.currentLife <= 0)
                        ceva.setVisible(false);
                    hit.setVisible(true);
                    openInventory.setVisible(true);
                    spell.setVisible(true);
                }
            });
            openInventory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog inventar = player.inventar.createInventar(frame, player);
                    inventar.setVisible(true);
                    playerMana.setText(String.valueOf(player.currentMana));
                    playerHealth.setText(String.valueOf(player.currentLife));
                }
            });

            ability.setVisible(false);
            panou.add(ability);
            vrajeste.add(ability);
            panou.add(mana);
            panou.add(daune);
            panou.add(smth);
        }

        for (Spell vraja : abilitati) {
            int size = abilitati.size() - abilitati.indexOf(vraja);
            size--;
            JLabel smth = new JLabel(vraja.getImage());
            smth.setBounds(540, 150 + 50 * size, 50, 50);
            JLabel daune = new JLabel(String.valueOf(vraja.getDamage()));
            daune.setBounds(450, 155 + 50 * size, 25, 25);
            daune.setForeground(Color.red);
            JLabel mana = new JLabel(String.valueOf(vraja.getMannaPrice()));
            mana.setBounds(500, 155 + 50 * size, 25, 25);
            mana.setForeground(Color.BLUE);
            panou.add(mana);
            panou.add(daune);
            panou.add(smth);
        }

        JLabel resistance = new JLabel("--Resistance--");
        resistance.setBounds(250, 25, 120, 120);
        resistance.setFont(new Font("Serif", Font.PLAIN, 16));
        JLabel spelluri = new JLabel("--Spells--");
        spelluri.setBounds(260, 75, 120, 120);
        spelluri.setFont(new Font("Serif", Font.PLAIN, 16));
        panou.add(resistance);
        panou.add(spelluri);
        panou.add(playerHealth);
        panou.add(playerMana);
        panou.add(enemyHealth);
        panou.add(enemyMana);


        hit.setVisible(false);
        hit.setBounds(250, 350, 100, 50);

        spell.setVisible(false);
        spell.setBounds(125, 350, 100, 50);

        openInventory.setBounds(375, 350, 100, 50);
        openInventory.setVisible(false);

        hit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inamic.receiveDamage(player.getDamage());
                if (inamic.currentLife <= 0) {
                    if (new Random().nextInt(0, 5) < 4) {
                        player.inventar.money += 20;
                    }
                    ceva.setVisible(false);
                }

                Random decide = new Random();
                if (decide.nextInt(0, 5) == 0) {
                    inamic.useSpell(inamic.abilitati.get(decide.nextInt(0, inamic.abilitati.size())), player);
                } else {
                    player.receiveDamage(inamic.getDamage());
                }
                if (player.currentLife <= 0)
                    ceva.setVisible(false);
                playerMana.setText(String.valueOf(player.currentMana));
                playerHealth.setText(String.valueOf(player.currentLife));
                enemyHealth.setText(String.valueOf(currentLife));
                enemyMana.setText(String.valueOf(currentMana));
            }
        });

        spell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < vrajeste.size(); i++) {
                    vrajeste.get(i).setVisible(true);
                }
                hit.setVisible(false);
                spell.setVisible(false);
                openInventory.setVisible(false);
            }
        });


        startFight.setBounds(250, 350, 100, 50);
        startFight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructions.setText("<html>Poti apasa pe butonul de hit sa dai un basic <br/>pe butonul de Spell sa alegi ce Spell vrei sa folosesti<br/>sau pe butonul de invetory sa-ti deschizi inventarul</html>");
                startFight.setVisible(false);
                hit.setVisible(true);
                openInventory.setVisible(true);
                spell.setVisible(true);
            }
        });

        container.add(instructions);
        container.add(startFight);
        container.add(hit);
        container.add(spell);
        container.add(openInventory);
        container.add(panou);
        return ceva;
    }

}
