package pl.towers.objects;

import pl.towers.player.PlayerEnum;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * Klasa opisuj�ca gracza
 * 
 * @author Micha�
 * 
 */
public class Player extends Tower {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int DEC_LIFE = 10; //okresla o ile zmniejsza sie zycie po dostaniu kulk�

	private String playerName;

	private boolean shot = false; // zmienna okreslaj�ca czy wystrzelono
									// pocisk
	private boolean decreaseLife=false;
	private boolean keyReleased = true;

	public Player(PlayerEnum player, String playerName) {
		super(player);
		this.playerName = playerName;
	}

	public void paint(Graphics2D g) {
		super.paint(g);
		if (player == PlayerEnum.LEFT) {

			if (shot == true)
				if(debugMode) g.drawString("Wystrzelono przez " + playerName, 150, 150);
		} else {

			if (shot== true)
				if(debugMode) g.drawString("Wystrzelono przez " + playerName, 550, 150);
		}
	}
	/**
	 * Obni�anie celownika
	 * @param player
	 */
	public void viewfinderDown(final PlayerEnum player){
		if(player==PlayerEnum.RIGHT){
			viewfinder--;
			if (viewfinder < 0) {
				viewfinder = 0;
			}
			else{
				polX[0]--;
				polX[2] -= 2;
				polY[0] += 2;
				polY[2]++;
			}
		}
		else {
			viewfinder--;
			if (viewfinder < 0) {
				viewfinder = 0;
				
			}
			else{
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
	private void doNothingWithViewFinder(){
		polX[0] = polX[0];
		polX[2] = polX[2];
		polY[0] = polY[0];
		polY[2] = polY[2];
	}
	
	/**
	 * Podnoszenie celownika
	 * @param player
	 */
	public void viewfinderUp(final PlayerEnum player){
		if(player==PlayerEnum.RIGHT){
			viewfinder++;
			if (viewfinder > 29) {
				viewfinder = 29;
			}
			else{
				polX[0]++;
				polX[2] += 2;
				polY[0] -= 2;
				polY[2]--;
			}
		}
		else {
			viewfinder++;
			if (viewfinder > 29) {
				viewfinder = 29;
			}
			else{
				polX[0] -= 2;
				polX[2]--;
				polY[0]--;
				polY[2] -= 2;
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		keyReleased=true;
		if (player == PlayerEnum.RIGHT) {
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
		} else {
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

	}

	public void keyPressed(KeyEvent e) {
		keyReleased=false;
		if (player == PlayerEnum.RIGHT) {
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
		} else {
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
	}
	
	/**
	 * Ustawienie pola decreaseLife
	 * @param decreaseLife
	 */
	public void setDecreaseLife(boolean decreaseLife){
		this.decreaseLife=decreaseLife;
	}

	/**
	 * Metoda zwraca true jesli zycie moze zostac zmniejszone
	 * @return
	 */
	public boolean isDecreaseLife(){
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
	 * @return nazwa Gracza
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Metoda zwracaj�ca obiekt Rectangle kt�ry ma wielkosc wiezy
	 * @return
	 */
	public Rectangle getBounds(){
		int whichTower=0;
		if(player == PlayerEnum.LEFT){
			whichTower=LEFT_TOWER_X;
		}
		else{
			whichTower=RIGHT_TOWER_X;
		}	
		return new Rectangle(whichTower, POINT_Y1,TOWER_WIDTH, Board.HEIGHT -POINT_Y1);

	}
	
	/**
	 * Metoda odpowiedzialna za operacje zwi�zane z kolizj�
	 */
	public void collision(){
		if(decreaseLife){
			if(life>0){
				life-=DEC_LIFE;
			}
		}
		decreaseLife=false;
	}
	/**
	 * Metoda zwracaj�ca watrosc true gdy graczowi sko�czy si� �ycie
	 * @return
	 */
	public boolean isEndOfLife(){
		if(life==0){
			return true;
		}
		else return false;
	}
	
	/**
	 * Sprawdzenie czy przycisk jest nadal wcisni�ty
	 * @return keyReleased
	 */
	public boolean isKeyReleased(){
		return keyReleased;
	}
	
	
}
