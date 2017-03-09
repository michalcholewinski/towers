package obiekty;

import java.awt.*;

/**
 * Klasa opisuj¹ca pocisk
 * 
 * @author Micha³
 * 
 */
public class Pocisk {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int LEFT = 0;
	private final int RIGHT = 1;
	private final int PLAYER1_INFO_X = 60;
	private final int PLAYER2_INFO_X = Plansza.SZEROKOSC - 160;
	private final int BULLET_SPEED_INFO_Y = 200;
	private final int BULLET_COORDINATES_INFO = 270;
	private final int STANDARD_DISPLAY_WIDTH = 800;
	private final int STANDARD_BULLET_SPEED = 30;
	private final int MINIMAL_BULLET_SPEED = (Plansza.SZEROKOSC * STANDARD_BULLET_SPEED)
			/ STANDARD_DISPLAY_WIDTH;
	private final int NUMBER_OF_POINTS = 4;
	private final int HILL = 0;

	private int polozenieX;
	private int polozenieY;
	int Xpocz, Ypocz; // wpolrzedne pocz¹tkowe
	int playerNumber;
	private boolean fired = false; // zmienna okreslaj¹ca czy pocisk ma byc
									// wyrysowany czy nie (dok³adnie oznacza czy
									// wystrzelono pocisk czy nie)
	RzutUkosny rzut;

	double time = 0.1; // Uzyta przy rzucie ukosnym
	public double angle;// Kat nachylenia dzialka
	private int bulletSpeed;// Predkosc pocisku
	private int speed;
	private boolean night;
	private boolean collision;
	private boolean debugMode = false;

	public Pocisk(int playerNumber) {
		this.playerNumber = playerNumber;
		night = false;
		rzut = new RzutUkosny();
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
	 * Ustawienie Pocz¹tkowe Kulki (miejsce z którego bêdzie startowaæ)
	 * 
	 * @param int[] polozenie - dwuelementowa tablica ze wspó³rzednymi X i Y
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
				g.drawOval(polozenieX - (Plansza.SREDNICA / 2), polozenieY
						- (Plansza.SREDNICA / 2), Plansza.SREDNICA,
						Plansza.SREDNICA);
			}
			g.setColor(Color.DARK_GRAY);
			g.fillOval(polozenieX - (Plansza.SREDNICA / 2), polozenieY
					- (Plansza.SREDNICA / 2), Plansza.SREDNICA,
					Plansza.SREDNICA);
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
	 * Zmiana Po³o¿enia kulki
	 */
	public void update() {
		if (fired == true) {
			if (playerNumber == GRACZ2) {
				/*
				 * polozenieX--; polozenieY--;
				 */
				polozenieX = ((Plansza.SZEROKOSC) - ((int) rzut.getXfor(time)))
						- (Plansza.SZEROKOSC - Xpocz);
				polozenieY = (((int) rzut.getYfor(time)) - (Plansza.WYSOKOSC - Ypocz));
			} else if (playerNumber == GRACZ1) {
				/*
				 * polozenieX++; polozenieY--;
				 */
				polozenieX = (((int) rzut.getXfor(time)) + Xpocz);
				polozenieY = (((int) rzut.getYfor(time)) - (Plansza.WYSOKOSC - Ypocz));
			}
			time += 0.1;
		}
		if ((polozenieX <= 0) || (polozenieX > Plansza.SZEROKOSC)
				|| (polozenieY > Plansza.WYSOKOSC)) {
			fired = false;
			time = 0.1;
		}
	}

	/**
	 * Metoda zwraca true jesli pocisk zosta³ wystrzelony
	 * 
	 * @return fired
	 */
	public boolean isFired() {
		return fired;
	}

	/**
	 * Metoda ustawiaj¹ca wartosc pola fired
	 * 
	 * @param fired
	 */
	public void setFired(boolean fired) {
		this.fired = fired;
	}

	/**
	 * Metoda do ustawiania prêdkoœci
	 * 
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Metoda zwraca obiekt Rectangle bêd¹cy kwadratem w które wpisana jest
	 * kulka
	 * 
	 * @return
	 */
	public Rectangle getBounds() {
		return new Rectangle(polozenieX - (Plansza.SREDNICA / 2), polozenieY
				- (Plansza.SREDNICA / 2), Plansza.SREDNICA, Plansza.SREDNICA);

	}

	/**
	 * Metoda zwraca obiekt Polygon bêd¹cy kwadratem w które wpisana jest kulka
	 * 
	 * @return
	 */
	public Polygon getPolygonBounds() {
		int tabX[] = new int[NUMBER_OF_POINTS];
		int tabY[] = new int[NUMBER_OF_POINTS];
		tabX[0] = polozenieX - (Plansza.SREDNICA / 2);
		tabY[0] = polozenieY - (Plansza.SREDNICA / 2);
		tabX[1] = polozenieX - (Plansza.SREDNICA / 2) + Plansza.SREDNICA;
		tabY[1] = polozenieY - (Plansza.SREDNICA / 2);
		tabX[2] = polozenieX - (Plansza.SREDNICA / 2) + Plansza.SREDNICA;
		tabY[2] = polozenieY - (Plansza.SREDNICA / 2) + Plansza.SREDNICA;
		tabX[3] = polozenieX - (Plansza.SREDNICA / 2);
		tabY[3] = polozenieY - (Plansza.SREDNICA / 2) + Plansza.SREDNICA;
		return new Polygon(tabX, tabY, NUMBER_OF_POINTS);
	}

	/**
	 * Metoda wykonuj¹ca operacje po kolizji
	 */
	public void collision(int which) {
		if (which == HILL)
			collision = true;
		fired = false;
		if (playerNumber == GRACZ1) {
			polozenieX = Plansza.SZEROKOSC + 1;
		} else
			polozenieX = 0;
		polozenieY = Plansza.WYSOKOSC + 1;
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
	 * Zwaraca wspolrzedn¹ Y kulki
	 * 
	 * @return polozenieY
	 */
	public int getPolozenieY() {
		return polozenieY;
	}

	/**
	 * Metoda do sprawdzania czy wyst¹pi³a kolizja
	 * 
	 * @return collision
	 */
	public boolean isCollision() {
		return collision;
	}

	/**
	 * Metoda ustawiaj¹ca kolizjê
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
