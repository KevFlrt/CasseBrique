
/**
 *
 * @author Kevin
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CasseBFrame extends JFrame implements KeyListener {

    public CasseBPanel gpan;
    private JMenuBar jmb;
    private JLabel lab;
    private JMenu menu;
    private JMenu aid;
    private JMenuItem nv;
    private JMenuItem choix;
    private JMenuItem help;
    private JMenuItem credit;
    private boolean partieLancee;

    public CasseBFrame() throws Exception {
        super();
        this.setTitle("Casse Brique");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gpan = new CasseBPanel();
        this.jmb = new JMenuBar();
        this.lab = new JLabel();
        this.jmb.setVisible(true);
        this.initMBar();
        this.setVisible(true);
        this.setJMenuBar(this.jmb);
        this.getContentPane().add(this.lab, BorderLayout.SOUTH);
        this.addKeyListener(this);
        this.partieLancee = false;
    }

    private void initMBar() {
        this.menu = new JMenu("Jeu");
        this.aid = new JMenu("?");
        this.nv = new JMenuItem("Nouveau");
        this.choix = new JMenuItem("Choisir niveau");
        this.help = new JMenuItem("Commandes");
        this.credit = new JMenuItem("A propos");

        this.menu.add(nv);
        this.menu.add(choix);
        this.aid.add(help);
        this.aid.add(credit);

        this.jmb.add(this.menu);
        this.jmb.add(this.aid);

        EcouteurMenu ecM = new EcouteurMenu();

        this.nv.addActionListener(ecM);
        this.choix.addActionListener(ecM);
        this.help.addActionListener(ecM);
        this.credit.addActionListener(ecM);

    }

    public void go() {
        int x = this.gpan.getBilleX(), y = this.gpan.getBilleY();
        int cmb = 0;

        boolean backX = false;
        boolean backY = false;

        if (this.gpan.debtJeu) {
            while (true) {
                if (this.gpan.horsLimites()) {
                    lab.setText("Echec, vous avez perdu une vie");
                    try {
                        Thread.sleep(200);
                        this.chargerJeu(gpan.getNiveau(), gpan.getVies() - 1);
                    } catch (Exception e) {
                    }

                    break;
                }

                if (this.gpan.partieGagnee()) {
                    lab.setText("Bravo vous avez réussi ce niveau. Score final : " + this.gpan.scoreActuel());
                    break;
                }

                if (x < 1) {
                    backX = false;
                }
                if (x > this.getWidth() - 10) {
                    backX = true;
                }
                if (y < 1) {
                    backY = false;
                }
                if (y > this.gpan.ligneBas()) {
                    backY = true;
                    this.gpan.updateScore(cmb = 0);
                }

                if (this.gpan.verifiePlaceBille() && ((y - 90) % 12 <= 11)) {
                    backY = !backY;
                    this.gpan.updateScore(++cmb);
                }

                if (this.gpan.verifiePlaceBille() && ((x - 100) % 55 <= 54)) {
                    backX = !backX;
                    this.gpan.updateScore(++cmb);
                }

                if (!backX)
                    this.gpan.setBilleX(++x);
                else
                    this.gpan.setBilleX(--x);
                if (!backY)
                    this.gpan.setBilleY(++y);
                else
                    this.gpan.setBilleY(--y);

                this.repaint();
                this.updateLabel();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private void chargerJeu(int niv, int vie) throws Exception {
        if (vie > 0) {
            this.gpan.chargerJeu(niv, vie);
            this.updateLabel();
            this.gpan.repaint();
        } else {
            this.lab.setText("Vous avez perdu. Reessayez une prochaine fois ! ");
        }
    }

    private void updateLabel() {
        this.lab.setText("Score : " + this.gpan.scoreActuel() + " Niveau : " + this.gpan.getNiveau() + " Vies : "
                + this.gpan.getVies());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (partieLancee)
            if (e.getKeyChar() == ' ')
                this.gpan.debtJeu = !this.gpan.debtJeu;
            else
                this.gpan.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (partieLancee)
            if (e.getKeyChar() == ' ')
                this.gpan.debtJeu = !this.gpan.debtJeu;
            else
                this.gpan.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (partieLancee)
            if (e.getKeyChar() == ' ')
                this.gpan.debtJeu = !this.gpan.debtJeu;
            else
                this.gpan.keyReleased(e);
    }

    class EcouteurMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String label = e.getActionCommand();
                if (label.equals("Nouveau")) {
                    getContentPane().add(gpan, BorderLayout.CENTER);
                    chargerJeu(1, 3);
                    partieLancee = true;
                    gpan.repaint();
                } else if (label.equals("Choisir niveau")) {
                    int niv = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrer le niveau", "Niveau",
                            JOptionPane.QUESTION_MESSAGE));
                    getContentPane().add(gpan, BorderLayout.CENTER);
                    chargerJeu(niv, 3);
                    partieLancee = true;
                    gpan.repaint();
                } else if (label.equals("Commandes")) {
                    String commandes = "DÃ©placer barre Ã  gauche : LEFT\nDÃ©placer barre Ã  droite : RIGHT\n\n"
                            + "Le but du jeu est de rÃ©ussir Ã  briser toutes les briques de l'Ã©cran en utilisant une bille et la barre.\n"
                            + "Vous disposez de seulement trois vies pour cela et devrez donc les Ã©conomiser pour rÃ©ussir le niveau";
                    JOptionPane.showMessageDialog(null, commandes, "Aide", JOptionPane.QUESTION_MESSAGE);
                } else {
                    String apropos = "Fleuriot Kevin.";
                    JOptionPane.showMessageDialog(null, apropos, "A propos", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
