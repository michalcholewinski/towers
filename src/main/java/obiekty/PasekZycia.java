package obiekty;

import java.awt.*;

/**
 * Klasa opisuj¹ca pasek zycia dla wiezy
 * 
 * @author Micha³
 * 
 */
public class PasekZycia {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int LIFE_INDICATOR_LENGTH = 100;
	private final int LIFE_INDICATOR_WIDTH = 15;
	private final int LIFE_INDICATOR_X = 10;
	private final int LIFE_INDICATOR_Y = 20;
	private final int PLAYER_NAME_Y = 15;

	private final int SMALL_AMOUNT_OF_LIFE = 30;
	private final int LARGE_AMOUNT_OF_LIFE = 60;

	private int playerNumber;
	private int life;
	private String playerName;
	private boolean night;
	private boolean debugMode=false;

	public PasekZycia(int playerNumber,String playerName) {
		this.playerNumber = playerNumber;
		this.playerName=playerName;
		night=false;
	}

	/**
	 * Rysowanie paska zycia, tu bêdzie wywolana jakas metoda odpowiedzialna za
	 * rysowanie 2 prostokatow jeden na drugim z czego jeden bedzie sie
	 * zmniejszal w zaleznosci od ilosci zycia
	 */
	public void paint(Graphics2D g) {
		if (playerNumber == GRACZ1) {
			if (life >= LARGE_AMOUNT_OF_LIFE)
				g.setColor(Color.green);
			else if (life >= SMALL_AMOUNT_OF_LIFE
					&& life < LARGE_AMOUNT_OF_LIFE)
				g.setColor(Color.pink);
			else if (life < SMALL_AMOUNT_OF_LIFE)
				g.setColor(Color.red);
			
			if(!debugMode){
				g.fillRect(LIFE_INDICATOR_X, LIFE_INDICATOR_Y, life, LIFE_INDICATOR_WIDTH);
				g.setColor(Color.red);
			}
			else{
				g.setColor(Color.black);
				g.drawRect(LIFE_INDICATOR_X, LIFE_INDICATOR_Y, life, LIFE_INDICATOR_WIDTH);
			} 
			g.drawRect(LIFE_INDICATOR_X, LIFE_INDICATOR_Y, LIFE_INDICATOR_LENGTH, LIFE_INDICATOR_WIDTH);
			
			g.setColor(Color.black);
			if(night){
				if(!debugMode) g.setColor(Color.white);
			}
			g.drawString(playerName, LIFE_INDICATOR_X, PLAYER_NAME_Y);
			g.drawString(Integer.toString(life),LIFE_INDICATOR_X+LIFE_INDICATOR_LENGTH+2,LIFE_INDICATOR_Y);
			
			

		} else if (playerNumber == GRACZ2) {

			if (life >= LARGE_AMOUNT_OF_LIFE)
				g.setColor(Color.green);
			else if (life >= SMALL_AMOUNT_OF_LIFE && life < LARGE_AMOUNT_OF_LIFE)
				g.setColor(Color.pink);
			else if (life < SMALL_AMOUNT_OF_LIFE)
				g.setColor(Color.red);
			
			if(!debugMode){ 
				g.fillRect(Plansza.SZEROKOSC-(LIFE_INDICATOR_X+LIFE_INDICATOR_LENGTH), LIFE_INDICATOR_Y, life, LIFE_INDICATOR_WIDTH);
				g.setColor(Color.red);
			}
			else{
				g.setColor(Color.black);
				g.drawRect(Plansza.SZEROKOSC-(LIFE_INDICATOR_X+LIFE_INDICATOR_LENGTH), LIFE_INDICATOR_Y, life, LIFE_INDICATOR_WIDTH);
			}
			g.drawRect(Plansza.SZEROKOSC-(LIFE_INDICATOR_X+LIFE_INDICATOR_LENGTH), LIFE_INDICATOR_Y, LIFE_INDICATOR_LENGTH, LIFE_INDICATOR_WIDTH);
			
			g.setColor(Color.black);
			if(night){
				if(!debugMode) g.setColor(Color.white);
			}
			g.drawString(playerName, Plansza.SZEROKOSC-(LIFE_INDICATOR_X+LIFE_INDICATOR_LENGTH), PLAYER_NAME_Y);	
			g.drawString(Integer.toString(life),Plansza.SZEROKOSC-(LIFE_INDICATOR_X+LIFE_INDICATOR_LENGTH+25),LIFE_INDICATOR_Y);
		}
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
