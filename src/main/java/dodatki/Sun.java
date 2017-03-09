package dodatki;

import java.awt.*;
import obiekty.*;
/**
 * Klasa odpowiedzialna za rysowania S³oñca
 * @author Micha³
 *
 */
public class Sun {
	private final int SUN_DIMENSION = 150; 
	private final int COMPONENT_RED = 232; 
	private final int COMPONENT_GREEN = 254;
	private final int COMPONENT_BLUE = 102;
	private final int DELAY = 35;
	private final int START_POSITION = -150;
	
	private int coordinateX;
	private int coordinateY;
	private int delay=0;
	
	public Sun(){
		coordinateX=START_POSITION;
		coordinateY=0;
	}
	
	
	/**
	 * Aktualizacja po³o¿enia s³oñca
	 */
	public void update(){
		delay++;
		if (delay % DELAY == 0) {
			coordinateX++;
			if(coordinateX>Plansza.SZEROKOSC){
				coordinateX=START_POSITION;
			}
		}
		
	}
	
	public void paint(Graphics2D g){
		g.setColor(new Color(COMPONENT_RED,COMPONENT_GREEN,
				COMPONENT_BLUE));
		g.fillOval(coordinateX,coordinateY,SUN_DIMENSION,SUN_DIMENSION);
	}
}
