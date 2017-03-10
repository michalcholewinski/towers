package pl.towers.player.tower;

import pl.towers.objects.Board;
import pl.towers.player.PlayerEnum;

import java.awt.*;

import static pl.towers.objects.Board.DIAMETER;

/**
 * Klasa Launcher (Armatka) opisuje mozliwe zachowanie armatki z kt�rej pocisk
 * bedzie wystrzeliwany
 * 
 * @author Micha�
 * 
 */
public class Launcher {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int LEFT_POINT_X1 = 43;
	private final int LEFT_POINT_X2 = 50;
	private final int LEFT_POINT_X3 = 59;
	private final int RIGHT_POINT_X1 = Board.WIDTH - 82;
	private final int RIGHT_POINT_X2 = Board.WIDTH - 75;
	private final int RIGHT_POINT_X3 = Board.WIDTH - 66;
	protected final int POINT_Y1 = Board.HEIGHT - 150;
	private final int POINT_Y2 = Board.HEIGHT - 90;
	private final int START_ANGLE = 0;
	private final int ARC_ANGLE = 180;
	private final int ARC_DIMENSION = 50;
	private final int LEFT_ARC_X = 25;
	private final int RIGHT_ARC_X = Board.WIDTH - 100;
	private final int ARC_Y = Board.HEIGHT - 115;
	private final int GUN_NUMBER_OF_POINTS = 3;
	private final int AUXILIARY_LINE_LENGTH = 1;

	protected int viewfinder; // 30-sto stopniowa skala, 0 -poziomo, 30-pionowo
	// wyjasniaj�c inaczej 0-strzal w lewo, 30- w g�r�
	protected PlayerEnum player;
	protected boolean debugMode=false;
	protected int polX[] = new int[3];
	protected int polY[] = new int[3];
	protected int[] centerline = new int[2]; /*
											 * srodek odcinka na
											 * wyrzutni.Wsp�rzedne srodka
											 * miejsca z kt�rego kulka b�dzie
											 * wystrzeliwana. Indeks 0
											 * wsp�rzedna X, indeks 1
											 * wsp�rzedna Y
											 */

	private int[] auxiliaryLine = new int[2]; /*
												 * wsp�rz�dna linii pomocniczej
												 * pierwsz� wsp�rz�dn� jest
												 * srodek wyznaczony wczesniej
												 * Indeks 0 wsp�rzedna X, indeks
												 * 1 wsp�rzedna Y
												 */

	public Launcher(PlayerEnum player) {
		this.player = player;
		viewfinder = 29;
		if (player == PlayerEnum.LEFT) {
			setFirstPlayersLauncherCoordinates();
		}else {
			setSecondPlayerLauncherCoordinates();
		}
	}

	private void setFirstPlayersLauncherCoordinates() {
		polX[0] = LEFT_POINT_X1;
		polX[1] = LEFT_POINT_X2;
		polX[2] = LEFT_POINT_X3;
		polY[0] = POINT_Y1;
		polY[1] = POINT_Y2;
		polY[2] = POINT_Y1;
	}

	private void setSecondPlayerLauncherCoordinates() {
		polX[0] = RIGHT_POINT_X1;
		polX[1] = RIGHT_POINT_X2;
		polX[2] = RIGHT_POINT_X3;
		polY[0] = POINT_Y1;
		polY[1] = POINT_Y2;
		polY[2] = POINT_Y1;
	}

	/**
	 * Wyznaczenie wsp�rzednych srodka odcinka znajduj�cego sie w dzia�ku.
	 * Potrzebne zeby umiescic kulke na ko�cu dzia�ka. Jako parametry przyjmuje
	 * wsp�rz�de punkt�w mi�dzy kt�rymi chcemy wyznaczy� �rodek.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void designationCenterline(int x1, int y1, int x2, int y2) {
		//wz�r na wyznaczenie srodka odcinka
		centerline[0] = (x1 + x2) / 2;
		centerline[1] = (y1 + y2) / 2;

	}
	
	/**
	 * Metoda zwaraca wsp�rzedne srodka odcinka. Z tego miejsca bedzie
	 * startowa�a kulka
	 * 
	 * @return srodekOdcinka
	 */
	public int[] getCenterline() {
		return centerline;
	}

	/**
	 * Rekurencyjna metoda do wyznaczania wsp�rz�dnych pomocniczej linii.
	 * Parametry x1 i y1 oznaczaj� wsp�rz�dne srodka tej linii (Podajemy
	 * wsp�rzedne srodka odcinka), x2 i y2 to wsp�rz�dne pocz�tku linii
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
		//przekszta�cony wz�r na wyznaczenie srodka odcinka
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
		if (player==PlayerEnum.LEFT) {
			paintLauncher(g, LEFT_ARC_X);
		} else {
			paintLauncher(g, RIGHT_ARC_X);

		}

	}

	private void paintLauncher(Graphics2D g, int arc_x) {
		g.setColor(Color.gray);
		g.drawPolygon(polX, polY, GUN_NUMBER_OF_POINTS);
		if(!debugMode) g.fillPolygon(polX, polY, GUN_NUMBER_OF_POINTS);
		g.drawArc(arc_x, ARC_Y, ARC_DIMENSION, ARC_DIMENSION,
                START_ANGLE, ARC_ANGLE);
		if(!debugMode) g.fillArc(arc_x, ARC_Y, ARC_DIMENSION, ARC_DIMENSION,
                START_ANGLE, ARC_ANGLE);
		g.setColor(Color.LIGHT_GRAY);
		g.drawOval(centerline[0] - (DIAMETER / 2), centerline[1]
                - (DIAMETER / 2), DIAMETER,
                DIAMETER);
		g.setColor(Color.DARK_GRAY);
		if(!debugMode) g.fillOval(centerline[0] - (DIAMETER / 2), centerline[1]
                - (DIAMETER / 2), DIAMETER,
                DIAMETER);
		g.setColor(Color.pink);
		g.drawLine(centerline[0], centerline[1], auxiliaryLine[0],
                auxiliaryLine[1]);
	}

	/**
	 * Metoda zwraca polozenie celownika w stopniach
	 * 
	 * @return viewfinder
	 */
	public double getViewfinder() {
		return (viewfinder * 2.4) + 18;
	}
	
	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode){
		this.debugMode=debugMode;
	}
}
