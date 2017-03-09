package dodatki;

import java.awt.*;
import java.util.*;

import obiekty.Plansza;
/**
 * Klasa odpowiedzialna za wyrysowanie gwiazdy
 * @author Micha³
 *
 */
public class Star {
	private final int POINTS = 11;
	Random rand;
	private int coordinateX;
	private int coordinateY;
	
	private int [] tabX=new int[POINTS];
	private int [] tabY=new int[POINTS];
	
	public Star(){
		rand=new Random();
		coordinateX=rand.nextInt(Plansza.SZEROKOSC);
		coordinateY=rand.nextInt(Plansza.WYSOKOSC);
		tabX[0]=coordinateX;
		tabY[0]=coordinateY;
		tabX[1]=coordinateX+4;
		tabY[1]=coordinateY;
		tabX[2]=coordinateX+6;
		tabY[2]=coordinateY-4;
		tabX[3]=coordinateX+8;
		tabY[3]=coordinateY;
		tabX[4]=coordinateX+12;
		tabY[4]=coordinateY;
		tabX[5]=coordinateX+9;
		tabY[5]=coordinateY+3;
		tabX[6]=coordinateX+11;
		tabY[6]=coordinateY+7;
		tabX[7]=coordinateX+6;
		tabY[7]=coordinateY+3;
		tabX[8]=coordinateX+1;
		tabY[8]=coordinateY+7;
		tabX[9]=coordinateX+5;
		tabY[9]=coordinateY+3;
		
		tabX[10]=coordinateX;
		tabY[10]=coordinateY;
	}

	public void paint(Graphics2D g){
		g.setColor(Color.yellow);
		g.fillPolygon(tabX,tabY,POINTS);
	}
	
	
}
