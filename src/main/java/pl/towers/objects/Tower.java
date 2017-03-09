package pl.towers.objects;
import pl.towers.player.PlayerEnum;

import java.awt.*;

/**
 * Klasa opisuj�ca wiez�, jej cechy i mozliwe zachowanie
 * (np. kolizja z pociskiem spowoduje zmniejszenie zycia).
 * @author Micha�
 *
 */
public class Tower extends Launcher {
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int INITIAL_LIFE = 100;
	protected final int TOWER_WIDTH = 50;
	protected final int TOWER_HEIGHT = 90;
	protected final int LEFT_TOWER_X = 25;
	protected final int RIGHT_TOWER_X = Board.WIDTH -100;
	private final int TOWER_Y = Board.HEIGHT -TOWER_HEIGHT;
	private final int BRICK_LENGTH = 12;
	private final int BRICK_HEIGHT = 5;
	private final int TOWER_END = 40; //uzyte przy rysowaniu cegie� aby w co drugim rz�dzie narysowa� na koncu po��wke ceg�y
	private final int TOWER_END2 = 30;
	
	protected int life;	//poziom �ycia dla wie�y przypisujemy 100 i zmniejszamy w zaleznosci od pocisku ktory w nas trafil
	
	public Tower(PlayerEnum playerNumber){
		super(playerNumber);
		life=INITIAL_LIFE;

	}
	
	
	
	public void paint(Graphics2D g){
		super.paint(g);
		boolean i=true; //zmienna pomocnicza przy rysowaniu cegie� tak aby nie by�y w jednym rz�dzie
		if (player==PlayerEnum.LEFT){
			g.setColor(Color.gray);
			if(!debugMode) g.fillRect(LEFT_TOWER_X, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
			g.setColor(Color.blue);
			g.drawRect(LEFT_TOWER_X, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
			g.setColor(Color.black);
			for(int x=LEFT_TOWER_X;x<LEFT_TOWER_X+TOWER_END;x+=BRICK_LENGTH){
				for(int y=TOWER_Y;y<TOWER_Y+TOWER_HEIGHT;y+=BRICK_HEIGHT){
					if(i==true){
						g.drawRect(x, y, BRICK_LENGTH, BRICK_HEIGHT);
						i=false;
					}
					else{
						i=true;
						if(x>LEFT_TOWER_X+TOWER_END2) continue;
						g.drawRect(x+BRICK_LENGTH/2, y, BRICK_LENGTH, BRICK_HEIGHT);
						
					}
				}
			}
		}
		else {
			g.setColor(Color.gray);
			if(!debugMode) g.fillRect(RIGHT_TOWER_X, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
			g.setColor(Color.blue);
			g.drawRect(RIGHT_TOWER_X, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
			g.setColor(Color.black);
			for(int x=RIGHT_TOWER_X;x<RIGHT_TOWER_X+TOWER_END;x+=BRICK_LENGTH){
				for(int y=TOWER_Y;y<TOWER_Y+TOWER_HEIGHT;y+=BRICK_HEIGHT){
					if(i==true){
						g.drawRect(x, y, BRICK_LENGTH, BRICK_HEIGHT);
						i=false;
					}
					else{
						i=true;
						if(x>RIGHT_TOWER_X+TOWER_END2) continue;
						g.drawRect(x+BRICK_LENGTH/2, y, BRICK_LENGTH, BRICK_HEIGHT);
						
					}
				}
			}
		}
	}
	/**
	 * Metoda zmiejszaj�ca �ywotnosc wiezy jako parametr
	 * jest podawana liczba o jak� zycie ma zostac zmnejszone
	 * @param value
	 */
	public void decreaseLife(int value){
		life-=value;
	}
	
	/**
	 * Metoda zwraca ilo�� �ycia
	 * @return
	 */
	public int getLife(){
		return life;
	}
	
}
