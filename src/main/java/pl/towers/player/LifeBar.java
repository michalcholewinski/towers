package pl.towers.player;

import pl.towers.objects.Board;

import java.awt.*;

/**
 * Klasa opisuj�ca pasek zycia dla wiezy
 * 
 * @author Micha�
 * 
 */
public class LifeBar {

	private final int LIFE_INDICATOR_LENGTH = 100;
	private final int LIFE_INDICATOR_WIDTH = 15;
	private final int LIFE_INDICATOR_X = 10;
	private final int LIFE_INDICATOR_Y = 20;
	private final int PLAYER_NAME_Y = 15;

	private final int SMALL_AMOUNT_OF_LIFE = 30;
	private final int LARGE_AMOUNT_OF_LIFE = 60;

	private PlayerEnum player;
	private int life;
	private String playerName;
	private boolean night;
	private boolean debugMode=false;

	public LifeBar(PlayerEnum player, String playerName) {
		this.player = player;
		this.playerName=playerName;
		night=false;
	}

	/**
	 * Rysowanie paska zycia, tu b�dzie wywolana jakas metoda odpowiedzialna za
	 * rysowanie 2 prostokatow jeden na drugim z czego jeden bedzie sie
	 * zmniejszal w zaleznosci od ilosci zycia
	 */
	public void paint(Graphics2D g) {
		if (player == PlayerEnum.LEFT) {
			paintLifeBar(g, LIFE_INDICATOR_X,2);
		} else {
            paintLifeBar(g, Board.WIDTH -(LIFE_INDICATOR_X+LIFE_INDICATOR_LENGTH),-25);
		}
	}

	private void paintLifeBar(Graphics2D g, int life_indicator_x, int offset) {
		if (life >= LARGE_AMOUNT_OF_LIFE)
            g.setColor(Color.green);
        else if (life >= SMALL_AMOUNT_OF_LIFE
                && life < LARGE_AMOUNT_OF_LIFE)
            g.setColor(Color.pink);
        else if (life < SMALL_AMOUNT_OF_LIFE)
            g.setColor(Color.red);

		if(!debugMode){
            g.fillRect(life_indicator_x, LIFE_INDICATOR_Y, life, LIFE_INDICATOR_WIDTH);
            g.setColor(Color.red);
        }
        else{
            g.setColor(Color.black);
            g.drawRect(life_indicator_x, LIFE_INDICATOR_Y, life, LIFE_INDICATOR_WIDTH);
        }
		g.drawRect(life_indicator_x, LIFE_INDICATOR_Y, LIFE_INDICATOR_LENGTH, LIFE_INDICATOR_WIDTH);

		g.setColor(Color.black);
		if(night){
            if(!debugMode) g.setColor(Color.white);
        }
		g.drawString(playerName, life_indicator_x, PLAYER_NAME_Y);
		g.drawString(Integer.toString(life), life_indicator_x +LIFE_INDICATOR_LENGTH+offset,LIFE_INDICATOR_Y);
	}

	/**
	 * Ustawienie zycia na konkretna wartosc
	 * @param life
	 */
	public void setLife(int life) {
		this.life = life;
	}
	
	/**
	 * Ustawienie true jesli jest sceneria nocna
	 * @param night
	 */
	public void setNight(boolean night){
		this.night=night;
	}

	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode){
		this.debugMode=debugMode;
	}
}
