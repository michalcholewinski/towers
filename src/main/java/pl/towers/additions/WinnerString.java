package pl.towers.additions;

import java.awt.*;

import pl.towers.objects.Board;

/**
 * Klasa odpowiedzialna za narysowanie i animowanie napisu "WINNER" wyswietlanego po zwyci�stwie  jednego z graczy
 * @author Micha�
 *
 */
public class WinnerString {
	private final int MIDDLE = Board.WIDTH / 2;
	private final int BOTTOM = Board.HEIGHT - 40;
	private final int W_POINTS = 13;
	private final int I_POINTS = 5;
	private final int N_POINTS = 11;
	private final int E_POINTS = 13;
	private final int R_POINTS = 13;
	private final int EDGE_OF_THE_SQUARE = 10;
	private final int DELAY = 15;
	private final int RED_W = 0;
	private final int RED_I = 1;
	private final int RED_1STN = 2;
	private final int RED_2NDN = 3;
	private final int RED_E = 4;
	private final int RED_R = 5;

	private int[] tabWX = { MIDDLE - 160, MIDDLE - 145, MIDDLE - 140,
			MIDDLE - 135, MIDDLE - 130, MIDDLE - 125, MIDDLE - 110,
			MIDDLE - 117, MIDDLE - 132, MIDDLE - 135, MIDDLE - 138,
			MIDDLE - 153, MIDDLE - 160 };
	private int[] tabWY = { BOTTOM - 160, BOTTOM - 160, BOTTOM - 130,
			BOTTOM - 145, BOTTOM - 130, BOTTOM - 160, BOTTOM - 160,
			BOTTOM - 100, BOTTOM - 100, BOTTOM - 110, BOTTOM - 100,
			BOTTOM - 100, BOTTOM - 160 };

	private int[] tabIX = { MIDDLE - 105, MIDDLE - 90, MIDDLE - 90,
			MIDDLE - 105, MIDDLE - 105 };
	private int[] tabIY = { BOTTOM - 160, BOTTOM - 160, BOTTOM - 100,
			BOTTOM - 100, BOTTOM - 160 };

	private int[] tabNX = { MIDDLE - 85, MIDDLE - 70, MIDDLE - 65, MIDDLE - 65,
			MIDDLE - 50, MIDDLE - 50, MIDDLE - 65, MIDDLE - 70, MIDDLE - 70,
			MIDDLE - 85, MIDDLE - 85 };
	private int[] tabNY = { BOTTOM - 160, BOTTOM - 160, BOTTOM - 130,
			BOTTOM - 160, BOTTOM - 160, BOTTOM - 100, BOTTOM - 100,
			BOTTOM - 130, BOTTOM - 100, BOTTOM - 100, BOTTOM - 160 };

	private int[] tabN2X = { MIDDLE - 45, MIDDLE - 30, MIDDLE - 25,
			MIDDLE - 25, MIDDLE - 10, MIDDLE - 10, MIDDLE - 25, MIDDLE - 30,
			MIDDLE - 30, MIDDLE - 45, MIDDLE - 45 };
	private int[] tabN2Y = { BOTTOM - 160, BOTTOM - 160, BOTTOM - 130,
			BOTTOM - 160, BOTTOM - 160, BOTTOM - 100, BOTTOM - 100,
			BOTTOM - 130, BOTTOM - 100, BOTTOM - 100, BOTTOM - 160 };

	private int[] tabEX = { MIDDLE - 5, MIDDLE + 30, MIDDLE + 30, MIDDLE + 10,
			MIDDLE + 10, MIDDLE + 20, MIDDLE + 20, MIDDLE + 10, MIDDLE + 10,
			MIDDLE + 30, MIDDLE + 30, MIDDLE - 5, MIDDLE - 5 };
	private int[] tabEY = { BOTTOM - 160, BOTTOM - 160, BOTTOM - 148,
			BOTTOM - 148, BOTTOM - 136, BOTTOM - 136, BOTTOM - 124,
			BOTTOM - 124, BOTTOM - 112, BOTTOM - 112, BOTTOM - 100,
			BOTTOM - 100, BOTTOM - 160 };

	private int[] tabRX = { MIDDLE + 35, MIDDLE + 66, MIDDLE + 68, MIDDLE + 70,
			MIDDLE + 70, MIDDLE + 65, MIDDLE + 75, MIDDLE + 60, MIDDLE + 55,
			MIDDLE + 50, MIDDLE + 50, MIDDLE + 35, MIDDLE + 35 };
	private int[] tabRY = { BOTTOM - 160, BOTTOM - 160, BOTTOM - 158,
			BOTTOM - 156, BOTTOM - 140, BOTTOM - 135, BOTTOM - 100,
			BOTTOM - 100, BOTTOM - 125, BOTTOM - 125, BOTTOM - 100,
			BOTTOM - 100, BOTTOM - 160 };

	private int red;
	private int green;
	private int blue;
	private int transparent;
	private int otherColor;
	private int delay = 0;

	public WinnerString(int r, int g, int b, int transparent) {
		red = r;
		green = g;
		blue = b;
		this.transparent = transparent;
		otherColor = 0;
	}

	
	/**
	 * Aktualizacja kolor�w w napisie
	 */
	public void update() {
		delay++;
		if (delay % DELAY == 0) {
			otherColor++;
			if (otherColor > RED_R) {
				otherColor = RED_W;
			}
		}
	}

	public void paint(Graphics2D g) {
		// Literka W
		g.setColor(Color.yellow);
		if (otherColor == RED_W)
			g.setColor(Color.red);
		g.fillPolygon(tabWX, tabWY, W_POINTS);
		g.setColor(Color.BLACK);
		g.drawPolygon(tabWX, tabWY, W_POINTS);
		// Literka I
		g.setColor(Color.yellow);
		if (otherColor == RED_I)
			g.setColor(Color.red);
		g.fillPolygon(tabIX, tabIY, I_POINTS);
		g.setColor(Color.BLACK);
		g.drawPolygon(tabIX, tabIY, I_POINTS);

		// Literka N
		g.setColor(Color.yellow);
		if (otherColor == RED_1STN)
			g.setColor(Color.red);
		g.fillPolygon(tabNX, tabNY, N_POINTS);
		g.setColor(Color.BLACK);
		g.drawPolygon(tabNX, tabNY, N_POINTS);

		// Literka N
		g.setColor(Color.yellow);
		if (otherColor == RED_2NDN)
			g.setColor(Color.red);
		g.fillPolygon(tabN2X, tabN2Y, N_POINTS);
		g.setColor(Color.BLACK);
		g.drawPolygon(tabN2X, tabN2Y, N_POINTS);

		// Literka E
		g.setColor(Color.yellow);
		if (otherColor == RED_E)
			g.setColor(Color.red);
		g.fillPolygon(tabEX, tabEY, E_POINTS);
		g.setColor(Color.BLACK);
		g.drawPolygon(tabEX, tabEY, E_POINTS);

		// Literka R
		g.setColor(Color.yellow);
		if (otherColor == RED_R)
			g.setColor(Color.red);
		g.fillPolygon(tabRX, tabRY, R_POINTS);
		g.setColor(Color.BLACK);
		g.drawPolygon(tabRX, tabRY, R_POINTS);

		g.setColor(new Color(red, green, blue));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				transparent * 0.1f));
		g.fillRect(MIDDLE + 50, BOTTOM - 150, EDGE_OF_THE_SQUARE,
				EDGE_OF_THE_SQUARE);
		g.setColor(Color.BLACK);
		g.drawRect(MIDDLE + 50, BOTTOM - 150, EDGE_OF_THE_SQUARE,
				EDGE_OF_THE_SQUARE);

	}
}
