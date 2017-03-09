package pl.towers.objects;

import java.awt.*;

import pl.towers.additions.WinnerString;

/**
 * Klasa odpwiedzialna za pokazanie Ekranu koncowego z napisem WINNER i strza�k� wskazuj�c� na gracza kt�ry wygra�
 * @author Micha�
 *
 */
public class EndScreen {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int RIGHT_WINNER_STRING_X = Board.WIDTH - 120; // wsp�rzedna
																		// X
																		// napisu
																		// z
																		// imieniem
																		// GRACZA2
																		// je�li
																		// wygra�
	private final int LEFT_WINNER_STRING_X = 30; // wsp�rzedna X napisu z
													// imieniem GRACZA1 jesli
													// wygra�
	private final int WINNER_STRING_Y = Board.HEIGHT - 200; // wsp�rzedna Y
																// napisu z
																// imieniem
																// gracza kt�ry
																// wygra�
	private final int TRANSPARENT_DEGREE = 8; // Skala przezroczystosci t�a
	private final int BG_COMPONENT_RED = 128; // Sk�adowe RGB dla koloru t�a
	private final int BG_COMPONENT_GREEN = 107;
	private final int BG_COMPONENT_BLUE = 165;
	private final int ARROW_COMPONENT_RED = 163; // Sk�adowe RGB dla koloru
													// strza�ki
	private final int ARROW_COMPONENT_GREEN = 236;
	private final int ARROW_COMPONENT_BLUE = 144;
	private final int NUMBER_OF_POINTS = 8;
	private final int DELAY = 2; // Opoznienie, im wieksze tym strza�ka wolniej
									// sie porusza
	private final int RIGHT = 1;
	private final int LEFT = 0;
	private final int BOUND = 25; // Skok strza�ki
	private final int POINT_Y1L = Board.HEIGHT - 45,
			POINT_Y2L = Board.HEIGHT - 45,
			POINT_Y3L = Board.HEIGHT - 35,
			POINT_Y4L = Board.HEIGHT - 60,
			POINT_Y5L = Board.HEIGHT - 85,
			POINT_Y6L = Board.HEIGHT - 75,
			POINT_Y7L = Board.HEIGHT - 75, POINT_Y8L = 45;
	private final int POINT_Y1R = Board.HEIGHT - 60,
			POINT_Y2R = Board.HEIGHT - 35,
			POINT_Y3R = Board.HEIGHT - 45,
			POINT_Y4R = Board.HEIGHT - 45,
			POINT_Y5R = Board.HEIGHT - 75,
			POINT_Y6R = Board.HEIGHT - 75,
			POINT_Y7R = Board.HEIGHT - 85,
			POINT_Y8R = Board.HEIGHT - 60;
	private final int MIDDLE = Board.WIDTH / 2;
	private final int POINT_X1L = MIDDLE - 30, POINT_X2L = MIDDLE + 1,
			POINT_X3L = MIDDLE + 30;
	private final int POINT_X1R = MIDDLE - 30, POINT_X2R = MIDDLE - 1,
			POINT_X3R = MIDDLE + 30;

	private int playerNumber;
	private String playerName;
	private boolean show;
	private int delay = 0, shift = 0, direction;
	private WinnerString winnerString;

	private int[] strzalkaLewoY = { POINT_Y1L, POINT_Y2L, POINT_Y3L, POINT_Y4L,
			POINT_Y5L, POINT_Y6L, POINT_Y7L, POINT_Y8L };
	private int[] strzalkaLewoX = { POINT_X1L, POINT_X2L, POINT_X2L, POINT_X3L,
			POINT_X2L, POINT_X2L, POINT_X1L, POINT_X1L };
	private int[] strzalkaPrawoY = { POINT_Y1R, POINT_Y2R, POINT_Y3R,
			POINT_Y4R, POINT_Y5R, POINT_Y6R, POINT_Y7R, POINT_Y8R };
	private int[] strzalkaPrawoX = { POINT_X1R, POINT_X2R, POINT_X2R,
			POINT_X3R, POINT_X3R, POINT_X2R, POINT_X2R, POINT_X1R };

	public EndScreen() {
		show = false;
		direction = RIGHT;
		winnerString = new WinnerString(BG_COMPONENT_RED, BG_COMPONENT_GREEN,
				BG_COMPONENT_BLUE, TRANSPARENT_DEGREE);

	}

	/**
	 * Aktualizacja polozenia strzalki wskazuj�cej ktory gracz wygra�
	 */
	public void update() {
		winnerString.update();
		delay++;
		if (delay % DELAY == 0) {
			if (direction == RIGHT) {
				shift++;

				if (shift >= BOUND) {
					direction = LEFT;
				}
			} else {
				shift--;
				if (shift <= 0) {
					direction = RIGHT;
				}
			}
			if (playerNumber == GRACZ1) {
				strzalkaPrawoX[0] = POINT_X1R + shift;
				strzalkaPrawoX[1] = POINT_X2R + shift;
				strzalkaPrawoX[2] = POINT_X2R + shift;
				strzalkaPrawoX[3] = POINT_X3R + shift;
				strzalkaPrawoX[4] = POINT_X3R + shift;
				strzalkaPrawoX[5] = POINT_X2R + shift;
				strzalkaPrawoX[6] = POINT_X2R + shift;
				strzalkaPrawoX[7] = POINT_X1R + shift;

			}
			if (playerNumber == GRACZ2) {
				strzalkaLewoX[0] = POINT_X1L + shift;
				strzalkaLewoX[1] = POINT_X2L + shift;
				strzalkaLewoX[2] = POINT_X2L + shift;
				strzalkaLewoX[3] = POINT_X3L + shift;
				strzalkaLewoX[4] = POINT_X2L + shift;
				strzalkaLewoX[5] = POINT_X2L + shift;
				strzalkaLewoX[6] = POINT_X1L + shift;
				strzalkaLewoX[7] = POINT_X1L + shift;
			}
		}
	}

	public void paint(Graphics2D g) {
		if (show) {

			g.setColor(new Color(BG_COMPONENT_RED, BG_COMPONENT_GREEN,
					BG_COMPONENT_BLUE));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					TRANSPARENT_DEGREE * 0.1f));
			g.fillRect(0, 0, Board.WIDTH, Board.HEIGHT);

			g.setColor(Color.black);
			if (playerNumber == GRACZ2) {
				g.drawPolygon(strzalkaLewoX, strzalkaLewoY,
						NUMBER_OF_POINTS - 1);
				g.setColor(new Color(ARROW_COMPONENT_RED,
						ARROW_COMPONENT_GREEN, ARROW_COMPONENT_BLUE));
				g.fillPolygon(strzalkaLewoX, strzalkaLewoY, NUMBER_OF_POINTS);

				g.setColor(Color.white);
				g.drawString("" + playerName, RIGHT_WINNER_STRING_X,
						WINNER_STRING_Y);
			} else if (playerNumber == GRACZ1) {
				g.drawPolygon(strzalkaPrawoX, strzalkaPrawoY,
						NUMBER_OF_POINTS - 1);
				g.setColor(new Color(ARROW_COMPONENT_RED,
						ARROW_COMPONENT_GREEN, ARROW_COMPONENT_BLUE));
				g.fillPolygon(strzalkaPrawoX, strzalkaPrawoY, NUMBER_OF_POINTS);

				g.setColor(Color.white);
				g.drawString("" + playerName, LEFT_WINNER_STRING_X,
						WINNER_STRING_Y);
			}
			winnerString.paint(g);
		}
	}

	/**
	 * Ustawienie numeru gracza kt�ry wygra�
	 * 
	 * @param playerNumber
	 */
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	/**
	 * Ustawienie imienia gracza kt�ry wygra�
	 * 
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Zasygnalizowanie ze gra zosta�a zako�czona aby wyswietlic ekran koncowy
	 * 
	 * @param show
	 */
	public void setShow(boolean show) {
		this.show = show;
	}

	/**
	 * Sprawdzenie czy ekran koncowy zosta� wyswietlony
	 * 
	 * @return show
	 */
	public boolean isShow() {
		return show;
	}

}
