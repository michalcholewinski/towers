package pl.towers.objects;

import java.awt.*;

/**
 * Klasa opisuj�ca pasek mocy strza�u dla gracza
 * 
 * @author Micha�
 * 
 */
public class ShootPowerBar {// extends Thread{
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int INDICATOR_WIDTH = 10;
	private final int INDICATOR_HEIGTH = 100;
	private final int LEFT_POWER_INDICATOR_X = 1;
	private final int RIGHT_POWER_INDICATOR_X = Board.SZEROKOSC
			- INDICATOR_WIDTH - 10;
	private final int POWER_INDICATOR_Y = Board.WYSOKOSC - 130;
	private final int FILL_POWER_INDICATOR_Y = Board.WYSOKOSC - 30;
	private final int MAX_POWER = 100;
	private final int DOWN = 0;
	private final int UP = 1;
	private final int INDICATOR_INFO_Y = POWER_INDICATOR_Y - 4;
	private final int DELAY = 2;

	private int power;
	private int playerNumber;
	private boolean night;
	private boolean debugMode=false;
	private int direction = DOWN, delay = 0;

	public ShootPowerBar(int playerNumber) {
		this.playerNumber = playerNumber;
		if (playerNumber == GRACZ1) {
			power = MAX_POWER;
		} else
			power = MAX_POWER - 50;
		night = false;
	}

	/**
	 * Metoda aktualizuj�ca pasek tak aby by� animowany
	 */
	public void update() {
		delay++;
		if (delay % DELAY == 0) {
			if (direction == DOWN) {
				power--;
				if (power < 0) {
					power = 0;
					direction = UP;
				}
			}
			if (direction == UP) {
				power++;
				if (power > MAX_POWER) {
					power = MAX_POWER;
					direction = DOWN;
				}
			}
		}
	}

	public void paint(Graphics2D g) {
		if (playerNumber == GRACZ1) {
			g.setColor(Color.black);
			if (night)
				if(!debugMode) g.setColor(Color.white);
			g.drawRect(LEFT_POWER_INDICATOR_X, POWER_INDICATOR_Y,
					INDICATOR_WIDTH, INDICATOR_HEIGTH);
			g.setColor(Color.YELLOW);
			if(!debugMode) g.fillRect(LEFT_POWER_INDICATOR_X, FILL_POWER_INDICATOR_Y - power,
					INDICATOR_WIDTH, INDICATOR_HEIGTH);
			else {
				g.setColor(Color.black);
				g.drawRect(LEFT_POWER_INDICATOR_X, FILL_POWER_INDICATOR_Y - power,
						INDICATOR_WIDTH, INDICATOR_HEIGTH);
			}
			g.setColor(Color.black);
			if(debugMode) g.drawString(Integer.toString(power), LEFT_POWER_INDICATOR_X,
					INDICATOR_INFO_Y);

		} else if (playerNumber == GRACZ2) {
			g.setColor(Color.black);
			if (night)
				if(!debugMode) g.setColor(Color.white);
			g.drawRect(RIGHT_POWER_INDICATOR_X, POWER_INDICATOR_Y,
					INDICATOR_WIDTH, INDICATOR_HEIGTH);
			g.setColor(Color.YELLOW);
			if(!debugMode) g.fillRect(RIGHT_POWER_INDICATOR_X, FILL_POWER_INDICATOR_Y - power,
					INDICATOR_WIDTH, INDICATOR_HEIGTH);
			else{
				g.setColor(Color.black);
				g.drawRect(RIGHT_POWER_INDICATOR_X, FILL_POWER_INDICATOR_Y - power,
						INDICATOR_WIDTH, INDICATOR_HEIGTH);
				
			}
			g.setColor(Color.black);
			if(debugMode) g.drawString(Integer.toString(power), RIGHT_POWER_INDICATOR_X,
					INDICATOR_INFO_Y);
		}
	}

	/**
	 * Metoda zwracaj�ca moc jak� aktualnie wskazuje pasek mocy strza�u
	 * 
	 * @return power
	 */
	public int getPower() {
		return power;
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
