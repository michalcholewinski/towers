package pl.towers.player;

import pl.towers.objects.Board;
import pl.towers.player.tower.Tower;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Tower {

    private final int DEC_LIFE = 10; //okresla o ile zmniejsza sie zycie po dostaniu kulk�

    private String playerName;

    private boolean shot = false; // zmienna okreslaj�ca czy wystrzelono

    private boolean decreaseLife = false;
    private boolean keyReleased = true;

    public Player(PlayerEnum player, String playerName) {
        super(player);
        this.playerName = playerName;
    }

    public void paint(Graphics2D g) {
        super.paint(g);
        if (shot && debugMode) {
            if (player == PlayerEnum.LEFT) {
                g.drawString("Wystrzelono przez " + playerName, 150, 150);
            } else {
                g.drawString("Wystrzelono przez " + playerName, 550, 150);
            }
        }
    }

    /**
     * Obni�anie celownika
     *
     * @param player
     */
    public void viewfinderDown(final PlayerEnum player) {
        viewfinder--;
        if (viewfinder < 0) {
            viewfinder = 0;
        } else {
            if (player == PlayerEnum.RIGHT) {
                polX[0]--;
                polX[2] -= 2;
                polY[0] += 2;
                polY[2]++;
            } else {
                polX[0] += 2;
                polX[2]++;
                polY[0]++;
                polY[2] += 2;
            }
        }
    }

    /**
     * Pozostawienie celownika w tym samym miejscu
     */
    private void doNothingWithViewFinder() {
        polX[0] = polX[0];
        polX[2] = polX[2];
        polY[0] = polY[0];
        polY[2] = polY[2];
    }

    /**
     * Podnoszenie celownika
     *
     * @param player
     */
    public void viewfinderUp(final PlayerEnum player) {
        viewfinder++;
        if (viewfinder > 29) {
            viewfinder = 29;
        } else {
            if (player == PlayerEnum.RIGHT) {
                polX[0]++;
                polX[2] += 2;
                polY[0] -= 2;
                polY[2]--;
            } else {
                polX[0] -= 2;
                polX[2]--;
                polY[0]--;
                polY[2] -= 2;
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        keyReleased = true;
        if (player == PlayerEnum.RIGHT) {
            secondPlayerKeyReleased(e);
        } else {
            firstPlayerKeyReleased(e);
        }

    }

    private void secondPlayerKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                doNothingWithViewFinder();
                break;

            case KeyEvent.VK_UP:
                doNothingWithViewFinder();
                break;
            case KeyEvent.VK_CONTROL:
                shot = false;
                break;
        }
    }

    private void firstPlayerKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
                doNothingWithViewFinder();
                break;

            case KeyEvent.VK_W:
                doNothingWithViewFinder();
                break;
            case KeyEvent.VK_F:
                shot = false;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        keyReleased = false;
        if (player == PlayerEnum.RIGHT) {
            secondPlayerKeyPressed(e);
        } else {
            firstPlayerKeyPressed(e);
        }
    }

    private void secondPlayerKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                viewfinderDown(player);
                break;
            case KeyEvent.VK_UP:
                viewfinderUp(player);
                break;
            case KeyEvent.VK_CONTROL:
                shot = true;
                break;
        }
    }

    private void firstPlayerKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
                viewfinderDown(player);
                break;
            case KeyEvent.VK_W:
                viewfinderUp(player);
                break;
            case KeyEvent.VK_F:
                shot = true;
                break;
        }
    }

    /**
     * Ustawienie pola decreaseLife
     *
     * @param decreaseLife
     */
    public void setDecreaseLife(boolean decreaseLife) {
        this.decreaseLife = decreaseLife;
    }

    /**
     * Metoda zwraca true jesli zycie moze zostac zmniejszone
     *
     * @return
     */
    public boolean isDecreaseLife() {
        return decreaseLife;
    }

    /**
     * Metoda zwraca true jesli pocisk zosta� wystrzelony
     *
     * @return strzal
     */
    public boolean isWystrzelono() {
        return shot;
    }

    /**
     * Metoda zwracaj�ca nazwe Gracza
     *
     * @return nazwa Gracza
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Metoda zwracaj�ca obiekt Rectangle kt�ry ma wielkosc wiezy
     *
     * @return
     */
    public Rectangle getBounds() {
        int whichTower = 0;
        if (player == PlayerEnum.LEFT) {
            whichTower = LEFT_TOWER_X;
        } else {
            whichTower = RIGHT_TOWER_X;
        }
        return new Rectangle(whichTower, POINT_Y1, TOWER_WIDTH, Board.HEIGHT - POINT_Y1);

    }

    /**
     * Metoda odpowiedzialna za operacje zwi�zane z kolizj�
     */
    public void collision() {
        if (decreaseLife) {
            if (life > 0) {
                life -= DEC_LIFE;
            }
        }
        decreaseLife = false;
    }

    /**
     * Metoda zwracaj�ca watrosc true gdy graczowi sko�czy si� �ycie
     *
     * @return
     */
    public boolean isEndOfLife() {
        if (life == 0) {
            return true;
        } else return false;
    }

    /**
     * Sprawdzenie czy przycisk jest nadal wcisni�ty
     *
     * @return keyReleased
     */
    public boolean isKeyReleased() {
        return keyReleased;
    }


}
