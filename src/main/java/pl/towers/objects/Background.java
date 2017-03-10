package pl.towers.objects;

import pl.towers.additions.Clouds;
import pl.towers.additions.Moon;
import pl.towers.additions.Star;
import pl.towers.additions.Sun;

import java.awt.*;
import java.util.Random;

public final class Background {
	private final int NUMBER_OF_SCENERY = 2;
	private final int NUMBER_OF_STARS = 100;

	Clouds clouds;
	Sun sun;
	Moon moon;
	Star[] star;
	Random rand;

	private TimeOfTheDay timeOfTheDay = TimeOfTheDay.DAY;
	private boolean debugMode = false;

	public Background() {
		rand= new Random();
		timeOfTheDay= whichTimeOfTheDay();
		if (timeOfTheDay==TimeOfTheDay.DAY) {
			clouds = new Clouds();
			sun = new Sun();
		} else {
			moon = new Moon();
			star = new Star[NUMBER_OF_STARS];
			for (int i = 0; i < NUMBER_OF_STARS; i++) {
				star[i] = new Star();
			}
		}

	}

	private TimeOfTheDay whichTimeOfTheDay() {
		return rand.nextInt(NUMBER_OF_SCENERY)==0? TimeOfTheDay.DAY: TimeOfTheDay.NIGHT;
	}


	/**
	 * Aktualizacja t�a
	 */
	public void update() {
		if (timeOfTheDay==TimeOfTheDay.DAY) {
			sun.update();
		} else {
			moon.update();
		}
	}

	public void paint(Graphics2D g) {
		if (!debugMode) {
			if (timeOfTheDay==TimeOfTheDay.DAY) {
				g.setColor(Color.cyan);
				g.fillRect(0, 0, Board.WIDTH, Board.HEIGHT);
				sun.paint(g);
				clouds.paint(g);
			} else {
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
	 * @return timeOfTheDay
	 */
	public TimeOfTheDay getTimeOfTheDay() {
		return timeOfTheDay;
	}

	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

}
