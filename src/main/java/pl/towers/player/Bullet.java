package pl.towers.player;

import pl.towers.objects.Board;
import pl.towers.additions.ObliqueThrow;
import pl.towers.objects.TimeOfTheDay;

import java.awt.*;

import static pl.towers.objects.TimeOfTheDay.DAY;
import static pl.towers.objects.TimeOfTheDay.NIGHT;

/**
 * Klasa opisuj�ca pocisk
 * 
 * @author Micha�
 * 
 */
public class Bullet {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int LEFT = 0;
	private final int RIGHT = 1;
	private final int PLAYER1_INFO_X = 60;
	private final int PLAYER2_INFO_X = Board.WIDTH - 160;
	private final int BULLET_SPEED_INFO_Y = 200;
	private final int BULLET_COORDINATES_INFO = 270;
	private final int STANDARD_DISPLAY_WIDTH = 800;
	private final int STANDARD_BULLET_SPEED = 30;
	private final int MINIMAL_BULLET_SPEED = (Board.WIDTH * STANDARD_BULLET_SPEED)
			/ STANDARD_DISPLAY_WIDTH;
	private final int NUMBER_OF_POINTS = 4;
	private final int HILL = 0;

	private int polozenieX;
	private int polozenieY;
	int Xpocz, Ypocz; // wpolrzedne pocz�tkowe
	int playerNumber;
	private boolean fired = false; // zmienna okreslaj�ca czy pocisk ma byc
									// wyrysowany czy nie (dok�adnie oznacza czy
									// wystrzelono pocisk czy nie)
	ObliqueThrow rzut;

	double time = 0.1; // Uzyta przy rzucie ukosnym
	public double angle;// Kat nachylenia dzialka
	private int bulletSpeed;// Predkosc pocisku
	private int speed;
	private TimeOfTheDay timeOfTheDay;
	private boolean collision;
	private boolean debugMode = false;

	public Bullet(int playerNumber) {
		this.playerNumber = playerNumber;
		timeOfTheDay = DAY;
		rzut = new ObliqueThrow();
		collision = false;
	}

	/**
	 * Przeliczanie predkosci kulki
	 * @param wind
	 * @param windDirection
	 */
	public void calculateBulletSpeed(int wind, int windDirection) {
		if (playerNumber == GRACZ1) {
			if (windDirection == LEFT) {
				bulletSpeed = speed + wind + MINIMAL_BULLET_SPEED;
			} else if (windDirection == RIGHT) {
				bulletSpeed = speed - wind + MINIMAL_BULLET_SPEED;
			}
		} else if (playerNumber == GRACZ2) {
			if (windDirection == LEFT) {
				bulletSpeed = speed - wind + MINIMAL_BULLET_SPEED;
			} else if (windDirection == RIGHT) {
				bulletSpeed = speed + wind + MINIMAL_BULLET_SPEED;
			}
		}
	}

	/**
	 * Ustawienie Pocz�tkowe Kulki (miejsce z kt�rego b�dzie startowa�)
	 * 
	 * @param int[] polozenie - dwuelementowa tablica ze wsp�rzednymi X i Y
	 */
	public void ustawPolozeniePoczatkowe(int[] polozenie) {
		if (fired != true) {
			/* polozenieX */Xpocz = polozenie[0];
			/* polozenieY */Ypocz = polozenie[1];
			rzut.calculateTheParameters(angle, bulletSpeed);
		}
	}

	public void paint(Graphics2D g) {
		if (fired) {
			if (timeOfTheDay==NIGHT) {
				g.setColor(Color.white);
				g.drawOval(polozenieX - (Board.DIAMETER / 2), polozenieY
						- (Board.DIAMETER / 2), Board.DIAMETER,
						Board.DIAMETER);
			}
			g.setColor(Color.DARK_GRAY);
			g.fillOval(polozenieX - (Board.DIAMETER / 2), polozenieY
					- (Board.DIAMETER / 2), Board.DIAMETER,
					Board.DIAMETER);
		}
		g.setColor(Color.black);
		paintInDebugMode(g);
	}

	private void paintInDebugMode(Graphics2D g) {
		if(debugMode){
			if (playerNumber == GRACZ2) {
				 g.drawString("X: " + Integer.toString(polozenieX) + " Y: "
						+ Integer.toString(polozenieY), PLAYER2_INFO_X,
						BULLET_COORDINATES_INFO);
				g.drawString(
						"SZYBKOSC POCISKU: " + Integer.toString(bulletSpeed),
						PLAYER2_INFO_X, BULLET_SPEED_INFO_Y);

			} else if (playerNumber == GRACZ1) {
				g.drawString("X: " + Integer.toString(polozenieX) + " Y: "
						+ Integer.toString(polozenieY), PLAYER1_INFO_X,
						BULLET_COORDINATES_INFO);
				g.drawString(
						"SZYBKOSC POCISKU: " + Integer.toString(bulletSpeed),
						PLAYER1_INFO_X, BULLET_SPEED_INFO_Y);
			}
		}
	}

	/**
	 * Zmiana Po�o�enia kulki
	 */
	public void update() {
		if (fired == true) {
			if (playerNumber == GRACZ2) {
				/*
				 * polozenieX--; polozenieY--;
				 */
				polozenieX = ((Board.WIDTH) - ((int) rzut.getXfor(time)))
						- (Board.WIDTH - Xpocz);
				polozenieY = (((int) rzut.getYfor(time)) - (Board.HEIGHT - Ypocz));
			} else if (playerNumber == GRACZ1) {
				/*
				 * polozenieX++; polozenieY--;
				 */
				polozenieX = (((int) rzut.getXfor(time)) + Xpocz);
				polozenieY = (((int) rzut.getYfor(time)) - (Board.HEIGHT - Ypocz));
			}
			time += 0.1;
		}
		if ((polozenieX <= 0) || (polozenieX > Board.WIDTH)
				|| (polozenieY > Board.HEIGHT)) {
			fired = false;
			time = 0.1;
		}
	}

	/**
	 * Metoda zwraca true jesli pocisk zosta� wystrzelony
	 * 
	 * @return fired
	 */
	public boolean isFired() {
		return fired;
	}

	/**
	 * Metoda ustawiaj�ca wartosc pola fired
	 * 
	 * @param fired
	 */
	public void setFired(boolean fired) {
		this.fired = fired;
	}

	/**
	 * Metoda do ustawiania pr�dko�ci
	 * 
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Metoda zwraca obiekt Rectangle b�d�cy kwadratem w kt�re wpisana jest
	 * kulka
	 * 
	 * @return
	 */
	public Rectangle getBounds() {
		return new Rectangle(polozenieX - (Board.DIAMETER / 2), polozenieY
				- (Board.DIAMETER / 2), Board.DIAMETER, Board.DIAMETER);

	}

	/**
	 * Metoda zwraca obiekt Polygon b�d�cy kwadratem w kt�re wpisana jest kulka
	 * 
	 * @return
	 */
	public Polygon getPolygonBounds() {
		int tabX[] = new int[NUMBER_OF_POINTS];
		int tabY[] = new int[NUMBER_OF_POINTS];
		tabX[0] = polozenieX - (Board.DIAMETER / 2);
		tabY[0] = polozenieY - (Board.DIAMETER / 2);
		tabX[1] = polozenieX - (Board.DIAMETER / 2) + Board.DIAMETER;
		tabY[1] = polozenieY - (Board.DIAMETER / 2);
		tabX[2] = polozenieX - (Board.DIAMETER / 2) + Board.DIAMETER;
		tabY[2] = polozenieY - (Board.DIAMETER / 2) + Board.DIAMETER;
		tabX[3] = polozenieX - (Board.DIAMETER / 2);
		tabY[3] = polozenieY - (Board.DIAMETER / 2) + Board.DIAMETER;
		return new Polygon(tabX, tabY, NUMBER_OF_POINTS);
	}

	/**
	 * Metoda wykonuj�ca operacje po kolizji
	 */
	public void collision(int which) {
		if (which == HILL)
			collision = true;
		fired = false;
		if (playerNumber == GRACZ1) {
			polozenieX = Board.WIDTH + 1;
		} else
			polozenieX = 0;
		polozenieY = Board.HEIGHT + 1;
	}

	/**
	 * Ustawienie true jesli jest sceneria nocna
	 * 
	 * @param timeOfTheDay
	 */
	public void setTimeOfTheDay(TimeOfTheDay timeOfTheDay) {
		this.timeOfTheDay = timeOfTheDay;
	}

	/**
	 * Zwraca wspolrzedna X kulki
	 * 
	 * @return polozenieX
	 */
	public int getPolozenieX() {
		return polozenieX;
	}

	/**
	 * Zwaraca wspolrzedn� Y kulki
	 * 
	 * @return polozenieY
	 */
	public int getPolozenieY() {
		return polozenieY;
	}

	/**
	 * Metoda do sprawdzania czy wyst�pi�a kolizja
	 * 
	 * @return collision
	 */
	public boolean isCollision() {
		return collision;
	}

	/**
	 * Metoda ustawiaj�ca kolizj�
	 * 
	 * @param collision
	 */
	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
}
