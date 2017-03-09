package dodatki;
import java.awt.*;

import obiekty.Plansza;

public class Pause {
	private final int TRANSPARENT_DEGREE = 8; // Skala przezroczystosci t³a
	private final int BG_COMPONENT_RED = 86; // Sk³adowe RGB dla koloru t³a
	private final int BG_COMPONENT_GREEN = 153;
	private final int BG_COMPONENT_BLUE = 156;
	private final int FIRST_ARC_X = 15;
	private final int FIRST_ARC_Y = 15;
	private final int SECOND_ARC_X = 15;
	private final int SECOND_ARC_Y = Plansza.WYSOKOSC - 80;
	private final int THIRD_ARC_X = Plansza.SZEROKOSC - 60;
	private final int THIRD_ARC_Y = Plansza.WYSOKOSC -80;
	private final int FOURTH_ARC_X = Plansza.SZEROKOSC - 60;
	private final int FOURTH_ARC_Y = 15;
	private final int ARC_DIMENSION = 50;
	private final int START_ANGLE = 0;
	
	
	private PauseString pauza;
	private int angle;
	public Pause(){
		pauza = new PauseString();
	}
	public void update(){
		angle++;
		if(angle>360){
			angle=0;
		}
		
	}
	
	public void paint(Graphics2D g){
		
			g.setColor(new Color(BG_COMPONENT_RED, BG_COMPONENT_GREEN,BG_COMPONENT_BLUE));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,TRANSPARENT_DEGREE * 0.1f));
			g.fillRect(0, 0, Plansza.SZEROKOSC, Plansza.WYSOKOSC);
			g.setColor(Color.yellow);
			g.fillArc(FIRST_ARC_X, FIRST_ARC_Y, ARC_DIMENSION, ARC_DIMENSION,START_ANGLE, angle);
			g.fillArc(SECOND_ARC_X, SECOND_ARC_Y, ARC_DIMENSION, ARC_DIMENSION,START_ANGLE, angle);
			g.fillArc(THIRD_ARC_X, THIRD_ARC_Y, ARC_DIMENSION, ARC_DIMENSION,START_ANGLE, angle);
			g.fillArc(FOURTH_ARC_X,FOURTH_ARC_Y, ARC_DIMENSION, ARC_DIMENSION,START_ANGLE, angle);
		pauza.paint(g);
	
	}
	
	

}
