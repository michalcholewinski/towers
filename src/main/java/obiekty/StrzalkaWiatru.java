package obiekty;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class StrzalkaWiatru extends Wiatr {
	private final int POINT_Y1L = 40, POINT_Y2L = 40, POINT_Y3L = 35,
			POINT_Y4L = 47, POINT_Y5L = 60, POINT_Y6L = 55, POINT_Y7L = 55,
			POINT_Y8L = 40;
	private final int POINT_Y1R = 47, POINT_Y2R = 35, POINT_Y3R = 40,
			POINT_Y4R = 40, POINT_Y5R = 55, POINT_Y6R = 55, POINT_Y7R = 60,
			POINT_Y8R = 47;
	private final int MIDDLE = Plansza.SZEROKOSC / 2;
	private final int POINT_X1L = MIDDLE - 20, POINT_X2L = MIDDLE + 10,
			POINT_X3L = MIDDLE + 20;
	private final int POINT_X1R = MIDDLE - 20, POINT_X2R = MIDDLE - 10,
			POINT_X3R = MIDDLE + 20;

	private final int OVAL_Y = 17;
	private final int OVAL_X = MIDDLE - 30;
	private final int OVAL_DIMENSION = 60;
	private final int NUMBER_OF_POINTS = 8;
	private final int STRING_X = OVAL_X;
	private final int STRING_Y = 15;
	private final int REFRESH_TIME = 1000;
	private final int STRONG_WIND = 10;
	private final int MAX_TIME_STRONG_WIND = 5; //Maksymalnie wiatr moze wiaæ z si³¹ >10 przez 5 sekund
	
	
	private int[] wiatrLewoY = { POINT_Y1L, POINT_Y2L, POINT_Y3L, POINT_Y4L,
			POINT_Y5L, POINT_Y6L, POINT_Y7L, POINT_Y8L };
	private int[] wiatrLewoX = { POINT_X1L, POINT_X2L, POINT_X2L, POINT_X3L,
			POINT_X2L, POINT_X2L, POINT_X1L, POINT_X1L };
	private int[] wiatrPrawoY = { POINT_Y1R, POINT_Y2R, POINT_Y3R, POINT_Y4R,
			POINT_Y5R, POINT_Y6R, POINT_Y7R, POINT_Y8R };
	private int[] wiatrPrawoX = { POINT_X1R, POINT_X2R, POINT_X2R, POINT_X3R,
			POINT_X3R, POINT_X2R, POINT_X2R, POINT_X1R };
	private Random rand;
	private boolean night;
	private boolean debugMode=false;
	
	public StrzalkaWiatru() {
		super();
		rand = new Random();
		night=false;

	}

	public void run() {
		int zm=1,pom=0;
		while (true) {
			if(pom>=MAX_TIME_STRONG_WIND){
				zm-=2;
			}
			else{
				zm = rand.nextInt(2);
			}
			if(power>STRONG_WIND){
				pom++;
			}
			if(power<=2){
				pom=0;
			}
			if (power == 0 && zm == 1) { // Jesli power==0 i zm==1 zmieniamy
										// kierunek wiatru, jesli zm==0 to nie
										// zmieniamy
				if (direction == LEFT)
					direction = RIGHT;
				else
					direction = LEFT;
				zm = rand.nextInt(2);
			}
			if (zm == 1) { // Jesli zm==1 zwiekszamy moc wiatru w przeciwnym
							// wypadku zmniejszamy
				if (power < MAX_POWER)
					power+=2;
				else
					power = MAX_POWER;
			} else {
				if (power > 0)
					power-=2;
				else
					power = 0;
			}
			try {
				Thread.sleep(REFRESH_TIME);
			} catch (InterruptedException e) {
			}
		}
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		if (night)
			if(!debugMode) g.setColor(Color.white);
		g.drawOval(OVAL_X, OVAL_Y, OVAL_DIMENSION, OVAL_DIMENSION);
		g.setColor(Color.yellow);
		if(!debugMode) g.fillOval(OVAL_X, OVAL_Y, OVAL_DIMENSION, OVAL_DIMENSION);
		g.setColor(Color.black);
		if (night)
			if(!debugMode) g.setColor(Color.white);
		g.drawString(" Si³a Wiatru:  " + power, STRING_X, STRING_Y);
		g.setColor(Color.green);
		// strza³ka w prawo
		if (direction == LEFT) {
			if(!debugMode) g.fillPolygon(wiatrLewoX, wiatrLewoY, NUMBER_OF_POINTS);
			else{
				g.setColor(Color.black);
				g.drawPolygon(wiatrLewoX, wiatrLewoY, NUMBER_OF_POINTS);
			}
		}
		// strza³ka w lewo
		else {
			if(!debugMode) g.fillPolygon(wiatrPrawoX, wiatrPrawoY, NUMBER_OF_POINTS);
			else {
				g.setColor(Color.black);
				g.drawPolygon(wiatrPrawoX, wiatrPrawoY, NUMBER_OF_POINTS);
			}
		}
	}
	
	/**
	 * Ustawienie true jesli jest sceneria nocna
	 * @param night
	 */
	public void setNight(boolean night){
		this.night=night;
	}
	
	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode){
		this.debugMode=debugMode;
	}
}
