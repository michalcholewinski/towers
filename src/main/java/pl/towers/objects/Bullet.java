package pl.towers.objects;

import java.awt.*;

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
	private final int PLAYER2_INFO_X = Board.SZEROKOSC - 160;
	private final int BULLET_SPEED_INFO_Y = 200;
	private final int BULLET_COORDINATES_INFO = 270;
	private final int STANDARD_DISPLAY_WIDTH = 800;
	private final int STANDARD_BULLET_SPEED = 30;
	private final int MINIMAL_BULLET_SPEED = (Board.SZEROKOSC * STANDARD_BULLET_SPEED)
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
	private boolean night;
	private boolean collision;
	private boolean debugMode = false;

	public Bullet(int playerNumber) {
		this.playerNumber = playerNumber;
		night = false;
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
			if (night) {
				g.setColor(Color.white);
				g.drawOval(polozenieX - (Board.SREDNICA / 2), polozenieY
						- (Board.SREDNICA / 2), Board.SREDNICA,
						Board.SREDNICA);
			}
			g.setColor(Color.DARK_GRAY);
			g.fillOval(polozenieX - (Board.SREDNICA / 2), polozenieY
					- (Board.SREDNICA / 2), Board.SREDNICA,
					Board.SREDNICA);
		}
		g.setColor(Color.black);
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
				polozenieX = ((Board.SZEROKOSC) - ((int) rzut.getXfor(time)))
						- (Board.SZEROKOSC - Xpocz);
				polozenieY = (((int) rzut.getYfor(time)) - (Board.WYSOKOSC - Ypocz));
			} else if (playerNumber == GRACZ1) {
				/*
				 * polozenieX++; polozenieY--;
				 */
				polozenieX = (((int) rzut.getXfor(time)) + Xpocz);
				polozenieY = (((int) rzut.getYfor(time)) - (Board.WYSOKOSC - Ypocz));
			}
			time += 0.1;
		}
		if ((polozenieX <= 0) || (polozenieX > Board.SZEROKOSC)
				|| (polozenieY > Board.WYSOKOSC)) {
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
		return new Rectangle(polozenieX - (Board.SREDNICA / 2), polozenieY
				- (Board.SREDNICA / 2), Board.SREDNICA, Board.SREDNICA);

	}

	/**
	 * Metoda zwraca obiekt Polygon b�d�cy kwadratem w kt�re wpisana jest kulka
	 * 
	 * @return
	 */
	public Polygon getPolygonBounds() {
		int tabX[] = new int[NUMBER_OF_POINTS];
		int tabY[] = new int[NUMBER_OF_POINTS];
		tabX[0] = polozenieX - (Board.SREDNICA / 2);
		tabY[0] = polozenieY - (Board.SREDNICA / 2);
		tabX[1] = polozenieX - (Board.SREDNICA / 2) + Board.SREDNICA;
		tabY[1] = polozenieY - (Board.SREDNICA / 2);
		tabX[2] = polozenieX - (Board.SREDNICA / 2) + Board.SREDNICA;
		tabY[2] = polozenieY - (Board.SREDNICA / 2) + Board.SREDNICA;
		tabX[3] = polozenieX - (Board.SREDNICA / 2);
		tabY[3] = polozenieY - (Board.SREDNICA / 2) + Board.SREDNICA;
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
			polozenieX = Board.SZEROKOSC + 1;
		} else
			polozenieX = 0;
		polozenieY = Board.WYSOKOSC + 1;
	}

	/**
	 * Ustawienie true jesli jest sceneria nocna
	 * 
	 * @param night
	 */
	public void setNight(boolean night) {
		this.night = night;
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
