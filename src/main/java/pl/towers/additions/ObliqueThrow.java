package pl.towers.additions;

import pl.towers.objects.Board;

/**
 * Klasa reprezentujaca rzut oblicza wszystkie niezbedne parametry, PRZEROBIONA
 * NA POTRZEBY PROJEKTU
 * 
 * @author Squall -Robert Kaszubowski
 * @version 1.00 2009/12/29
 */
public class ObliqueThrow {
	

	public double a;
	public double v;
	public double vx, vy;
	public double ymax, xmax, t0, t1, tmax;
	private final double G = 9.80665; // Przyspieszenie ziemskie

	/**
	 * Obliczenie wszystkich niezbednych parametr�w
	 * 
	 * @param angle
	 * @param speed
	 */
	public void calculateTheParameters(double angle, int speed) {
		v = speed;
		a = trim(Math.toRadians(angle)); // kat w radianach
		vx = (v * StrictMath.cos(a));
		vy = (v * StrictMath.sin(a));
		ymax = vy * vy / (2.0 * G);
		xmax = 2.0 * vx * vy / G;
		tmax = 2.0 * vy / G;
		t1 = vy / G;
	}

	/**
	 * Zwraca wartosc polozenia x dla chwili t
	 */

	public double getXfor(double t) {
		return (vx * t);
	}

	/**
	 * Zwraca wartosc polozenia y dla chwili t
	 */

	public double getYfor(double t) {
		return Board.HEIGHT - (vy * t - G * t * t / 2.0);
	}

	// Przycina kat do pierwszej cwiartki
	private double trim(double angle) {
		if (angle < 0)
			angle = -angle;
		while (angle > Math.PI / 2.0)
			angle = angle - Math.PI / 2.0;
		return angle;
	}
}