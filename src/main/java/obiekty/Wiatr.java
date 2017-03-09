package obiekty;
/**
 * Klasa opisuj¹ca wiatr. (Zmiana kierunku wiatru jest mozliwa tylko gdy moc wiatru spadnie do 0)
 * @author Micha³
 *
 */
public class Wiatr extends Thread{
	protected final int LEFT = 0;
	protected final int RIGHT = 1;
	protected final int MAX_POWER = 16;
	
	
	protected int direction;//0-w lewo, 1- w prawo
	protected int power;//moc wiatru w skali 0-5, 0 oznacza ze nie wieje
	
	/**
	 * Wiatr na "dzien dobry" nie wieje wcale;)
	 */
	public Wiatr(){
		direction=LEFT;
		power=0;
		
	}
	public void run(){}

	
	
	/**
	 * Zwraca kierunek wiatru
	 * @return kierunek 0-w lewo 1-w prawo
	 */
	public int getDirection(){
		return direction;
	}
	
	/**
	 * Zwraca moc wiatru
	 * @return moc
	 */
	public int getPower(){
		return power;
	}

}
