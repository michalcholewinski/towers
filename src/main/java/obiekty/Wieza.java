package obiekty;
import java.awt.*;

/**
 * Klasa opisuj¹ca wiezê, jej cechy i mozliwe zachowanie
 * (np. kolizja z pociskiem spowoduje zmniejszenie zycia).
 * @author Micha³
 *
 */
public class Wieza extends Wyrzutnia{
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int INITIAL_LIFE = 100;
	protected final int TOWER_WIDTH = 50;
	protected final int TOWER_HEIGHT = 90;
	protected final int LEFT_TOWER_X = 25;
	protected final int RIGHT_TOWER_X = Plansza.SZEROKOSC-100;
	private final int TOWER_Y = Plansza.WYSOKOSC-TOWER_HEIGHT;	
	private final int BRICK_LENGTH = 12;
	private final int BRICK_HEIGHT = 5;
	private final int TOWER_END = 40; //uzyte przy rysowaniu cegie³ aby w co drugim rzêdzie narysowaæ na koncu po³ówke ceg³y
	private final int TOWER_END2 = 30;
	
	protected int life;	//poziom ¿ycia dla wie¿y przypisujemy 100 i zmniejszamy w zaleznosci od pocisku ktory w nas trafil
						
	public int playerNumber; //0-dla wie¿y znajdujacej sie po lewej stronie
					//1-dla wie¿y znajduj¹cej siê po prawej stronie
	
	public Wieza(int playerNumber){
		super(playerNumber);
		life=INITIAL_LIFE;
		this.playerNumber=playerNumber;

	}
	
	
	
	public void paint(Graphics2D g){
		super.paint(g);
		boolean i=true; //zmienna pomocnicza przy rysowaniu cegie³ tak aby nie by³y w jednym rzêdzie
		if (playerNumber==GRACZ1){
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
		else if(playerNumber==GRACZ2){
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
	 * Metoda zmiejszaj¹ca ¿ywotnosc wiezy jako parametr
	 * jest podawana liczba o jak¹ zycie ma zostac zmnejszone
	 * @param wartosc
	 */
	public void decreaseLife(int value){
		life-=value;
	}
	
	/**
	 * Metoda zwraca iloœæ ¿ycia
	 * @return
	 */
	public int getLife(){
		return life;
	}
	
}
