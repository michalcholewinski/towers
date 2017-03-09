package pl.towers.additions;

import java.awt.*;
import java.util.*;

import pl.towers.objects.Board;

/**
 * Klasa odpowiedzialna za rysowanie chmur
 * @author Micha�
 *
 */
public class Clouds {
	private final int NUMBER_OF_CLOUDS = 7;
	private final int DISTANCE_FROM_GROUND = 500;
	private final int MIN_WIDTH = 70;
	private final int MAX_WIDTH = 100;
	private final int MIX_HEIGHT = 15;
	private final int MAX_HEIGHT = 40;

	private int[] tabX=new int[NUMBER_OF_CLOUDS];
	private int[] tabY=new int[NUMBER_OF_CLOUDS];
	private int[] width=new int[NUMBER_OF_CLOUDS];
	private int[] height=new int[NUMBER_OF_CLOUDS];
	private Random rand = new Random();

	public Clouds() {
		for (int i = 0; i < NUMBER_OF_CLOUDS; i++) {

			tabX[i] = rand.nextInt(Board.SZEROKOSC);//(int)Math.random()*Board.SZEROKOSC;
			tabY[i] = rand.nextInt(Board.WYSOKOSC - DISTANCE_FROM_GROUND);
			width[i] = MIN_WIDTH + rand.nextInt(MAX_WIDTH);
			height[i] = MIX_HEIGHT + rand.nextInt(MAX_HEIGHT);

		}
	}
	
	
	/**
	 * Rysowanie chmur. Ilo�� chmur okre�lona jest w sta�ej NUMBER_OF_CLOUDS
	 * @param g
	 */
	public void paint(Graphics2D g) {
		g.setColor(Color.white);
		for (int i = 0; i < NUMBER_OF_CLOUDS; i++) {
			g.fillOval(tabX[i], tabY[i], width[i], height[i]);
		}
	}

}
