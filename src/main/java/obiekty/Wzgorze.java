package obiekty;

import java.awt.*;

import static java.lang.Math.*;

public class Wzgorze {
	private final int START_PROCENT = 16;
	private final int MAX_PROCENT = 100;
	private final int NUMBER_OF_POINTS = (Plansza.SZEROKOSC * START_PROCENT)
			/ MAX_PROCENT;
	private final int START_Y = Plansza.WYSOKOSC - 30;
	private final int MAX_NUMBER_OF_BALLS = 200;
	private final int START_X = (Plansza.SZEROKOSC * START_PROCENT)
			/ MAX_PROCENT;
	private final int HILL_LENGTH = START_X + (4 * NUMBER_OF_POINTS);

	int[] wspX = new int[NUMBER_OF_POINTS];// wspó³rzêdne X krawêdzi wzgórza
	int[] wspY = new int[NUMBER_OF_POINTS];// wspó³rzêdne Y krawêdzi wzgórza
	
	private int[] tableX = new int[MAX_NUMBER_OF_BALLS];
	private int[] tableY = new int[MAX_NUMBER_OF_BALLS];
	private int numberOfBalls;
	private boolean debugMode=false;

	/**
	 * Konstruktor zajmuje sie wygenerowaniem punktów które wyznaczaj¹ krawêdzie
	 * wzgórza
	 */
	public Wzgorze() {
		int zm = 0, i = 0, x, pom = 0, pom1 = 0; // zmienne pomocnicze przy
													// rysowaniu wzgórza

		for (x = START_X; x < HILL_LENGTH; x += 4) {
			wspX[i] = x;
			if ((i == 0) || (i == NUMBER_OF_POINTS - 1)) {
				wspY[i] = START_Y;
			} else {
				if (i < (int) (NUMBER_OF_POINTS) / 2) {
					zm = (int) (Math.random() * 10) + pom;
					pom = zm;
					wspY[i] = START_Y - zm;
				} else {

					pom1 = zm - (int) (Math.random() * 10);
					zm = pom1;
					wspY[i] = START_Y - abs(pom1);

				}
			}
			i++;
		}
		
		numberOfBalls = 0;
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		g.drawPolygon(wspX, wspY, NUMBER_OF_POINTS);
		g.setColor(Color.green);
		if(!debugMode) g.fillPolygon(wspX, wspY, NUMBER_OF_POINTS);
		
		if (numberOfBalls < MAX_NUMBER_OF_BALLS) {
			for (int i = 0; i < numberOfBalls; i++) {
				g.setColor(Color.black);
				if(debugMode) g.drawString("LICZBA KULEK: " + numberOfBalls, 400, 220);
				g.setColor(Color.DARK_GRAY);
				g.fillOval(tableX[i] - (Plansza.SREDNICA / 2), tableY[i]
						- (Plansza.SREDNICA / 2), Plansza.SREDNICA,
						Plansza.SREDNICA);
			}
		}

	}

	/**
	 * Aktualizacja 
	 */
	public void update() {
		if (numberOfBalls > 130) {
			int tmpNOB = 0;
			boolean znaleziono;
			int[] tmpX = new int[MAX_NUMBER_OF_BALLS];
			int[] tmpY = new int[MAX_NUMBER_OF_BALLS];

			for (int i = 0; i < numberOfBalls; i++) {
				znaleziono = false;
				for (int j = 0; j < tmpNOB; j++) {
					if (tmpX[j] == tableX[i] && tmpY[j] == tableY[i]) {
						znaleziono = true;
					} else if (( tmpX[j] + 1 == tableX[i]
							|| tmpX[j] + 2 == tableX[i]
							|| tmpX[j] + 3 == tableX[i] 
							|| tmpX[j] + 4 == tableX[i]
							|| tmpX[j] + 5 == tableX[i])
							&&(tmpY[j] + 1 == tableY[i]
							|| tmpY[j] + 2 == tableY[i]
							|| tmpY[j] + 3 == tableY[i] 
							|| tmpY[j] + 4 == tableY[i]
							|| tmpY[j] + 5 == tableY[i])) {
						znaleziono = true;
					}
					else if (( tmpX[j] - 1 == tableX[i]
							|| tmpX[j] - 2 == tableX[i]
							|| tmpX[j] - 3 == tableX[i] 
							|| tmpX[j] - 4 == tableX[i]
							|| tmpX[j] - 5 == tableX[i])
							&&(tmpY[j] - 1 == tableY[i]
							|| tmpY[j] - 2 == tableY[i]
							|| tmpY[j] - 3 == tableY[i] 
							|| tmpY[j] - 4 == tableY[i]
							|| tmpY[j] - 5 == tableY[i])) {
						znaleziono = true;
					}
					else if (( tmpX[j] + 1 == tableX[i]
							|| tmpX[j] + 2 == tableX[i]
							|| tmpX[j] + 3 == tableX[i] 
							|| tmpX[j] + 4 == tableX[i]
							|| tmpX[j] + 5 == tableX[i])
							&&(tmpY[j] - 1 == tableY[i]
							|| tmpY[j] - 2 == tableY[i]
							|| tmpY[j] - 3 == tableY[i] 
							|| tmpY[j] - 4 == tableY[i]
							|| tmpY[j] - 5 == tableY[i])) {
						znaleziono = true;
					}
					else if (( tmpX[j] - 1 == tableX[i]
							|| tmpX[j] - 2 == tableX[i]
							|| tmpX[j] - 3 == tableX[i] 
							|| tmpX[j] - 4 == tableX[i]
							|| tmpX[j] - 5 == tableX[i])
							&&(tmpY[j] + 1 == tableY[i]
							|| tmpY[j] + 2 == tableY[i]
							|| tmpY[j] + 3 == tableY[i] 
							|| tmpY[j] + 4 == tableY[i]
							|| tmpY[j] + 5 == tableY[i])) {
						znaleziono = true;
					}

				}
				if (!znaleziono) {
					tmpX[tmpNOB] = tableX[i];
					tmpY[tmpNOB] = tableY[i];
					tmpNOB++;

				}
			}
			for (int i = 0; i < tmpNOB; i++) {
				tableX[i] = tmpX[i];
				tableY[i] = tmpY[i];
			}
			numberOfBalls = tmpNOB;
		}
	}

	/**
	 * Metoda zwraca obiekt Polygon
	 * 
	 * @return
	 */
	public Polygon getBounds() {
		return new Polygon(wspX, wspY, NUMBER_OF_POINTS);
	}

	public void collision(int ballX, int ballY) {
		tableX[numberOfBalls] = ballX;
		tableY[numberOfBalls] = ballY;
		numberOfBalls++;

	}

	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode){
		this.debugMode=debugMode;
	}
}
