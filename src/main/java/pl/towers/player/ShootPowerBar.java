package pl.towers.player;

import pl.towers.objects.Board;
import pl.towers.objects.TimeOfTheDay;

import java.awt.*;

import static pl.towers.objects.TimeOfTheDay.DAY;
import static pl.towers.objects.TimeOfTheDay.NIGHT;

/**
 * Klasa opisuj�ca pasek mocy strza�u dla gracza
 * 
 * @author Micha�
 * 
 */
public class ShootPowerBar {// extends Thread{
	private final int INDICATOR_WIDTH = 10;
	private final int INDICATOR_HEIGTH = 100;
	private final int LEFT_POWER_INDICATOR_X = 1;
	private final int RIGHT_POWER_INDICATOR_X = Board.WIDTH
			- INDICATOR_WIDTH - 10;
	private final int POWER_INDICATOR_Y = Board.HEIGHT - 130;
	private final int FILL_POWER_INDICATOR_Y = Board.HEIGHT - 30;
	private final int MAX_POWER = 100;
	private final int DOWN = 0;
	private final int UP = 1;
	private final int INDICATOR_INFO_Y = POWER_INDICATOR_Y - 4;
	private final int DELAY = 2;

	private int power;
	private PlayerEnum player;
	private TimeOfTheDay timeOfTheDay;
	private boolean debugMode=false;
	private int direction = DOWN, delay = 0;

	public ShootPowerBar(PlayerEnum player) {
		this.player = player;
		this.timeOfTheDay = DAY;
		if (player == PlayerEnum.LEFT) {
			power = MAX_POWER;
		} else {
			power = MAX_POWER - 50;
		}
	}

	/**
	 * Metoda aktualizuj�ca pasek tak aby by� animowany
	 */
	public void update() {
		delay++;
		if (delay % DELAY == 0) {
			if (direction == DOWN) {
				power--;
				if (power < 0) {
					power = 0;
					direction = UP;
				}
			}
			if (direction == UP) {
				power++;
				if (power > MAX_POWER) {
					power = MAX_POWER;
					direction = DOWN;
				}
			}
		}
	}

	public void paint(Graphics2D g) {
		if (player == PlayerEnum.LEFT) {
			paintPowerBar(g, LEFT_POWER_INDICATOR_X);

		} else {
			paintPowerBar(g, RIGHT_POWER_INDICATOR_X);
		}
	}

	private void paintPowerBar(Graphics2D g, int powerIndicatorX) {
		g.setColor(Color.black);
		if (timeOfTheDay==NIGHT && !debugMode)
            g.setColor(Color.white);

		g.drawRect(powerIndicatorX, POWER_INDICATOR_Y,
                INDICATOR_WIDTH, INDICATOR_HEIGTH);
		g.setColor(Color.YELLOW);
		if(!debugMode) g.fillRect(powerIndicatorX, FILL_POWER_INDICATOR_Y - power,
                INDICATOR_WIDTH, INDICATOR_HEIGTH);
        else {
            g.setColor(Color.black);
            g.drawRect(powerIndicatorX, FILL_POWER_INDICATOR_Y - power,
                    INDICATOR_WIDTH, INDICATOR_HEIGTH);
        }
		g.setColor(Color.black);
		if(debugMode) g.drawString(Integer.toString(power), powerIndicatorX,
                INDICATOR_INFO_Y);
	}

	/**
	 * Metoda zwracaj�ca moc jak� aktualnie wskazuje pasek mocy strza�u
	 * 
	 * @return power
	 */
	public int getPower() {
		return power;
	}

	public void setTimeOfTheDay(TimeOfTheDay timeOfTheDay){
		this.timeOfTheDay=timeOfTheDay;
	}
	
	/**
	 * Ustwienie trybu debug
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode){
		this.debugMode=debugMode;
	}
}
