package pl.towers.additions;

import pl.towers.objects.Board;

import java.awt.*;

/**
 * Klasa odpowiedzalna za rysowanie i zmiane po�o�enia ksi�yca
 * @author Micha�
 *
 */
public class Moon {
	private final int MOON_DIMENSION = 100; 
	private final int COMPONENT_RED = 232; 
	private final int COMPONENT_GREEN = 254;
	private final int COMPONENT_BLUE = 102;
	private final int DELAY = 40;
	private final int START_POSITION = -100;
	private final int Y_POSITION = 20;
	private final int SHIFT = 20;
	
	private int coordinateX;
	private int coordinateY;
	private int delay=0;
	
	public Moon(){
		coordinateX=START_POSITION;
		coordinateY=Y_POSITION;
	}
	
	
	/**
	 * Metoda odpowiedzialna za aktualizacje po�o�enia ksi�yca
	 */
	public void update(){
		delay++;
		if (delay % DELAY == 0) {
			coordinateX++;
			if(coordinateX> Board.WIDTH){
				coordinateX=START_POSITION;
			}
		}
		
	}
	
	
	public void paint(Graphics2D g){
		g.setColor(new Color(COMPONENT_RED,COMPONENT_GREEN,
				COMPONENT_BLUE));
		g.fillOval(coordinateX,coordinateY,MOON_DIMENSION,MOON_DIMENSION);
		g.setColor(Color.blue);
		g.fillOval(coordinateX+SHIFT,coordinateY,MOON_DIMENSION,MOON_DIMENSION);
	}

}
