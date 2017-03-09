package obiekty;

import java.awt.*;

/**
 * Klasa Wyrzutnia (Armatka) opisuje mozliwe zachowanie armatki z której pocisk
 * bedzie wystrzeliwany
 * 
 * @author Micha³
 * 
 */
public class Wyrzutnia {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int LEFT_POINT_X1 = 43;
	private final int LEFT_POINT_X2 = 50;
	private final int LEFT_POINT_X3 = 59;
	private final int RIGHT_POINT_X1 = Plansza.SZEROKOSC - 82;
	private final int RIGHT_POINT_X2 = Plansza.SZEROKOSC - 75;
	private final int RIGHT_POINT_X3 = Plansza.SZEROKOSC - 66;
	protected final int POINT_Y1 = Plansza.WYSOKOSC - 150;
	private final int POINT_Y2 = Plansza.WYSOKOSC - 90;
	private final int START_ANGLE = 0;
	private final int ARC_ANGLE = 180;
	private final int ARC_DIMENSION = 50;
	private final int LEFT_ARC_X = 25;
	private final int RIGHT_ARC_X = Plansza.SZEROKOSC - 100;
	private final int ARC_Y = Plansza.WYSOKOSC - 115;
	private final int GUN_NUMBER_OF_POINTS = 3;
	private final int AUXILIARY_LINE_LENGTH = 1;

	protected int celownik; // 30-sto stopniowa skala, 0 -poziomo, 30-pionowo
	// wyjasniaj¹c inaczej 0-strzal w lewo, 30- w górê
	public int player; // 0 ten po lewej, 1 po prawej
	protected boolean debugMode=false;
	protected int polX[] = new int[3];
	protected int polY[] = new int[3];
	protected int[] centerline = new int[2]; /*
											 * srodek odcinka na
											 * wyrzutni.Wspó³rzedne srodka
											 * miejsca z którego kulka bêdzie
											 * wystrzeliwana. Indeks 0
											 * wspórzedna X, indeks 1
											 * wspó³rzedna Y
											 */

	private int[] auxiliaryLine = new int[2]; /*
												 * wspórzêdna linii pomocniczej
												 * pierwsz¹ wspó³rzêdn¹ jest
												 * srodek wyznaczony wczesniej
												 * Indeks 0 wspórzedna X, indeks
												 * 1 wspó³rzedna Y
												 */

	public Wyrzutnia(int playerNumber) {
		this.player = playerNumber;
		celownik = 29;
		if (playerNumber == GRACZ1) {

			polX[0] = LEFT_POINT_X1;
			polX[1] = LEFT_POINT_X2;
			polX[2] = LEFT_POINT_X3;
			polY[0] = POINT_Y1;
			polY[1] = POINT_Y2;
			polY[2] = POINT_Y1;
		} else if (playerNumber == GRACZ2) {

			polX[0] = RIGHT_POINT_X1;
			polX[1] = RIGHT_POINT_X2;
			polX[2] = RIGHT_POINT_X3;
			polY[0] = POINT_Y1;
			polY[1] = POINT_Y2;
			polY[2] = POINT_Y1;
		}
	}

	/**
	 * Wyznaczenie wspó³rzednych srodka odcinka znajduj¹cego sie w dzia³ku.
	 * Potrzebne zeby umiescic kulke na koñcu dzia³ka. Jako parametry przyjmuje
	 * wspó³rzêde punktów miêdzy którymi chcemy wyznaczyæ œrodek.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void designationCenterline(int x1, int y1, int x2, int y2) {
		//wzór na wyznaczenie srodka odcinka
		centerline[0] = (x1 + x2) / 2;
		centerline[1] = (y1 + y2) / 2;

	}
	
	/**
	 * Metoda zwaraca wspó³rzedne srodka odcinka. Z tego miejsca bedzie
	 * startowa³a kulka
	 * 
	 * @return srodekOdcinka
	 */
	public int[] getCenterline() {
		return centerline;
	}

	/**
	 * Rekurencyjna metoda do wyznaczania wspó³rzêdnych pomocniczej linii.
	 * Parametry x1 i y1 oznaczaj¹ wspó³rzêdne srodka tej linii (Podajemy
	 * wspó³rzedne srodka odcinka), x2 i y2 to wspó³rzêdne pocz¹tku linii
	 * pomocniczej
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void getAuxiliaryLine(int x1, int y1, int x2, int y2, int licz) {
		int[] pom = new int[2];

		licz++;
		//przekszta³cony wzór na wyznaczenie srodka odcinka
		pom[0] = ((2 * x1) - x2);
		pom[1] = ((2 * y1) - y2);
		if (licz < AUXILIARY_LINE_LENGTH) { // Warunek zakonczenia
			getAuxiliaryLine(pom[0], pom[1], x2, y2, licz);
		} else {
			auxiliaryLine[0] = pom[0];
			auxiliaryLine[1] = pom[1];
		}

	}

	

	public void paint(Graphics2D g) {
		designationCenterline(polX[0], polY[0], polX[2], polY[2]);
		getAuxiliaryLine(centerline[0], centerline[1], polX[1], polY[1],
				0);
		if (player == GRACZ1) {
			g.setColor(Color.gray);
			g.drawPolygon(polX, polY, GUN_NUMBER_OF_POINTS);
			if(!debugMode) g.fillPolygon(polX, polY, GUN_NUMBER_OF_POINTS);
			g.drawArc(LEFT_ARC_X, ARC_Y, ARC_DIMENSION, ARC_DIMENSION,
					START_ANGLE, ARC_ANGLE);
			if(!debugMode) g.fillArc(LEFT_ARC_X, ARC_Y, ARC_DIMENSION, ARC_DIMENSION,
					START_ANGLE, ARC_ANGLE);
			g.setColor(Color.LIGHT_GRAY);
			g.drawOval(centerline[0] - (Plansza.SREDNICA / 2), centerline[1]
					- (Plansza.SREDNICA / 2), Plansza.SREDNICA,
					Plansza.SREDNICA);
			g.setColor(Color.DARK_GRAY);
			if(!debugMode) g.fillOval(centerline[0] - (Plansza.SREDNICA / 2), centerline[1]
					- (Plansza.SREDNICA / 2), Plansza.SREDNICA,
					Plansza.SREDNICA);
			g.setColor(Color.pink);
			g.drawLine(centerline[0], centerline[1], auxiliaryLine[0],
					auxiliaryLine[1]);
		} else if (player == GRACZ2) {
			g.setColor(Color.gray);
			g.drawPolygon(polX, polY, GUN_NUMBER_OF_POINTS);
			if(!debugMode) g.fillPolygon(polX, polY, GUN_NUMBER_OF_POINTS);
			g.drawArc(RIGHT_ARC_X, ARC_Y, ARC_DIMENSION, ARC_DIMENSION,
					START_ANGLE, ARC_ANGLE);
			if(!debugMode) g.fillArc(RIGHT_ARC_X, ARC_Y, ARC_DIMENSION, ARC_DIMENSION,
					START_ANGLE, ARC_ANGLE);
			g.setColor(Color.LIGHT_GRAY);
			g.drawOval(centerline[0] - (Plansza.SREDNICA / 2), centerline[1]
					- (Plansza.SREDNICA / 2), Plansza.SREDNICA,
					Plansza.SREDNICA);
			g.setColor(Color.DARK_GRAY);
			if(!debugMode) g.fillOval(centerline[0] - (Plansza.SREDNICA / 2), centerline[1]
					- (Plansza.SREDNICA / 2), Plansza.SREDNICA,
					Plansza.SREDNICA);
			g.setColor(Color.pink);
			g.drawLine(centerline[0], centerline[1], auxiliaryLine[0],
					auxiliaryLine[1]);

		}

	}

	/**
	 * Metoda zwraca polozenie celownika w stopniach
	 * 
	 * @return celownik
	 */
	public double getCelownik() {
		return (celownik * 2.4) + 18;
	}
	
	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode){
		this.debugMode=debugMode;
	}
}
