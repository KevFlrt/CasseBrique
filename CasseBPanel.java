
/**
 *
 * @author Kevin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.StringTokenizer;

public class CasseBPanel extends JPanel implements KeyListener {

    private int billeX = 400;
    private int billeY = 480;
    private final int ligneBas = 490;
    private int sc;
    private int vies = 3;
    private int cmb;
    private int niv;
    public boolean debtJeu = false;
    private final int brikW = 50;
    private final int brikH = 10;
    private final int barW = 70;
    private final int barH = 10;
    private int barX = 365;
    private int barY = 500;
    private final Brik[][] tabBriques;

    public CasseBPanel() throws Exception {
        this.setVisible(true);
        this.addKeyListener(this);
        this.tabBriques = new Brik[11][5];
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(236, 238, 239));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.red);
        g.fillOval(billeX, billeY, 10, 10);
        g.setColor(Color.YELLOW);
        int xB = 100, yB = 90;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                if (this.tabBriques[j][i] != null)
                    g.fill3DRect(xB, yB, this.brikW, this.brikH, true);
                xB += 55;
            }
            xB = 100;
            yB += 12;
        }

        g.fill3DRect(this.barX, this.barY, this.barW, this.barH, true);
    }

    public void setBilleX(int x) {
        billeX = x;
    }

    public int getBilleX() {
        return billeX;
    }

    public void setBilleY(int y) {
        billeY = y;
    }

    public int getBilleY() {
        return billeY;
    }

    public void chargerJeu(int niveau, int vie) {
        niv = niveau;
        billeX = 400;
        billeY = 490;
        debtJeu = false;
        sc = 0;
        vies = vie;
        cmb = 0;
        barX = 365;
        barY = 500;
        this.chargeNiv(niveau);
    }

    public int scoreActuel() {
        return this.sc;
    }

    public int getNiveau() {
        return this.niv;
    }

    public int getVies() {
        return this.vies;
    }

    public void updateScore(int cmbo) {
        this.cmb = cmbo;
        this.sc += this.cmb * 10;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            this.barX -= 3;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            this.barX += 3;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            this.barX -= 3;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            this.barX += 3;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            this.barX -= 3;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            this.barX += 3;
    }

    public int ligneBas() {
        return this.ligneBas;
    }

    public boolean horsLimites() {
        boolean etat = false;
        if (billeY > ligneBas && ((billeX < barX - 10) || (billeX > barX + barW + 10)))
            etat = true;
        return etat;
    }

    public boolean verifiePlaceBille() {
        boolean touche = false;
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 5; j++)
                if (this.tabBriques[i][j] != null)
                    if (!(this.billeX < this.tabBriques[i][j].getX() || this.billeX > this.tabBriques[i][j].getX() + 50
                            || this.billeY < this.tabBriques[i][j].getY()
                            || this.billeY > this.tabBriques[i][j].getY() + 12)) {
                        this.tabBriques[i][j] = null;
                        touche = true;
                    }
        return touche;
    }

    public boolean partieGagnee() {
        boolean gagne = true;
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 5; j++)
                if (this.tabBriques[i][j] != null)
                    gagne = false;
        return gagne;
    }

    private void chargeNiv(int niv) {
        // if (niv == 1) {
        //     for (int i = 0; i < 11; i++) {
        //         for (int j = 0; j < 4; j++)
        //             this.tabBriques[i][j] = null;
        //         this.tabBriques[i][4] = new Brik(100 + i * 55, 138);
        //     }
        // } else if (niv == 2) {
        //     for (int i = 0; i < 11; i++) {
        //         for (int j = 0; j < 5; j++)
        //             if (i != j && j != 11 - i - 1)
        //                 this.tabBriques[i][j] = null;
        //             else
        //                 this.tabBriques[i][j] = new Brik(100 + i * 55, 90 + j * 12);
        //     }
        // }
        FileReader fr = new FileReader("levels.txt");
        BufferedReader br = new BufferedReader(fr);
        
        String levels = "";
        while (br.read() != -1) {
            levels += br.readLine();
        }

        StringTokenizer st1 = new StringTokenizer(levels, "#");
        while (st1.hasMoreTokens()) {
            String lvl = st1.nextToken();
            String[] lines = lvl.split("\n");
            for(int j = 0; j < lines.length; j++) {
                for(int i = 0; i < lines[j].length; i++) {
                    if (lines[j].charAt(i) == '-')
                        this.tabBriques[i][j] = null;
                    else
                        this.tabBriques[i][j] = new Brik(100 + i * 55, 90 + j * 12);
                }
            }
        }

        br.close();
        fr.close();
    }

    class Brik {
        private final int x, y;

        public Brik(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
