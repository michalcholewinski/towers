package pl.towers.objects;

import java.awt.*;
import java.util.*;
import pl.towers.additions.*;

public class Background {
	private final int GOOD_WEATHER = 0;
	private final int NIGHT = 1;
	private final int NUMBER_OF_SCENERY = 2;
	private final int NUMBER_OF_STARS = 100;

	Clouds clouds;
	Sun sun;
	Moon moon;
	Star[] star;
	Random rand;

	private boolean night = false;
	private boolean debugMode = false;

	private int bgNumber = 1;

	public Background() {
		rand= new Random();
		bgNumber=rand.nextInt(NUMBER_OF_SCENERY);
		if (bgNumber == GOOD_WEATHER) {
			clouds = new Clouds();
			sun = new Sun();
		} else if (bgNumber == NIGHT) {
			night = true;
			moon = new Moon();
			star = new Star[NUMBER_OF_STARS];
			for (int i = 0; i < NUMBER_OF_STARS; i++) {
				star[i] = new Star();
			}
		}

	}

	
	/**
	 * Aktualizacja t�a
	 */
	public void update() {
		if (bgNumber == GOOD_WEATHER) {
			sun.update();
		} else if (bgNumber == NIGHT) {
			moon.update();
		}
	}

	public void paint(Graphics2D g) {
		if (!debugMode) {
			if (bgNumber == GOOD_WEATHER) {
				g.setColor(Color.cyan);
				g.fillRect(0, 0, Board.WIDTH, Board.HEIGHT);
				sun.paint(g);
				clouds.paint(g);
			} else if (bgNumber == NIGHT) {
				g.setColor(Color.blue);
				g.fillRect(0, 0, Board.WIDTH, Board.HEIGHT);
				moon.paint(g);
				for (int i = 0; i < NUMBER_OF_STARS; i++) {
					star[i].paint(g);
				}

			}
		}
		else{
			g.setColor(Color.white);
			g.fillRect(0, 0, Board.WIDTH, Board.HEIGHT);
		}
	}

	/**
	 * Zwraca true jesli jest ustawiona sceneria nocna
	 * 
	 * @return night
	 */
	public boolean isNight() {
		return night;
	}

	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	/**
	 * Metoda zwraca true jesli uruchomiony jest tryb debug
	 * 
	 * @return debugMode
	 */
	public boolean isDebugMode() {
		return debugMode;
	}
	
	/**
	 * Metoda zwraca numer scenerii (Jesli 0 to �adna pogoda i dzien, jesli 1 to noc)
	 * @return bgNumber
	 */
	public int getBgNumber(){
		return bgNumber;
	}
}
