package pl.towers.player.tower;
import pl.towers.objects.Board;
import pl.towers.player.PlayerEnum;

import java.awt.*;

/**
 * Klasa opisuj�ca wiez�, jej cechy i mozliwe zachowanie
 * (np. kolizja z pociskiem spowoduje zmniejszenie zycia).
 * @author Micha�
 *
 */
public class Tower extends Launcher {
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
		boolean odd=true; //zmienna pomocnicza przy rysowaniu cegie� tak aby nie by�y w jednym rz�dzie
		if (player==PlayerEnum.LEFT){
			paintTower(g, LEFT_TOWER_X);
		}
		else {
			paintTower(g, RIGHT_TOWER_X);
//			g.setColor(Color.gray);
//			if(!debugMode) g.fillRect(RIGHT_TOWER_X, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
//			g.setColor(Color.blue);
//			g.drawRect(RIGHT_TOWER_X, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
//			g.setColor(Color.black);
//			for(int x=RIGHT_TOWER_X;x<RIGHT_TOWER_X+TOWER_END;x+=BRICK_LENGTH){
//				for(int y=TOWER_Y;y<TOWER_Y+TOWER_HEIGHT;y+=BRICK_HEIGHT){
//					if(odd){
//						g.drawRect(x, y, BRICK_LENGTH, BRICK_HEIGHT);
//						odd=false;
//					}
//					else{
//						odd=true;
//						if(x>RIGHT_TOWER_X+TOWER_END2) continue;
//						g.drawRect(x+BRICK_LENGTH/2, y, BRICK_LENGTH, BRICK_HEIGHT);
//
//					}
//				}
//			}
		}
	}

	private void paintTower(Graphics2D g, int tower_x) {
		boolean odd=true; //zmienna pomocnicza przy rysowaniu cegie� tak aby nie by�y w jednym rz�dzie

		g.setColor(Color.gray);
		if(!debugMode) g.fillRect(tower_x, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
		g.setColor(Color.blue);
		g.drawRect(tower_x, TOWER_Y, TOWER_WIDTH, TOWER_HEIGHT);
		g.setColor(Color.black);
		for(int x = tower_x; x< tower_x +TOWER_END; x+=BRICK_LENGTH){
            for(int y=TOWER_Y;y<TOWER_Y+TOWER_HEIGHT;y+=BRICK_HEIGHT){
                if(odd){
                    g.drawRect(x, y, BRICK_LENGTH, BRICK_HEIGHT);
                    odd=false;
                }
                else{
                    odd=true;
                    if(x> tower_x +TOWER_END2) continue;
                    g.drawRect(x+BRICK_LENGTH/2, y, BRICK_LENGTH, BRICK_HEIGHT);

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
