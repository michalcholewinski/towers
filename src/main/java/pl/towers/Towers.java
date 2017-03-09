package pl.towers;

import dodatki.Pause;
import muzyka.ShotSound;
import muzyka.SimpleAudioPlayer;
import obiekty.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Towers extends Canvas implements Plansza, KeyListener, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int GRACZ1 = 0;
	private final int GRACZ2 = 1;
	private final int END = 1000;
	private final int HILL = 0; // s�u�y do sprawdzania czy by�a kolizja ze
								// wzg�rzem,
	private final int PLAYER = 1; // czy z jedn� z wie�
	private final int BULLET = 2; // czy z inn� kulk�
	private final String SOUND_NAME = "w.wav";

	private int endGame = 0;
	private boolean debugMode = false;

	public BufferStrategy strategia; // zmienna u�ywana przy buforowaniu
	private Wzgorze wzgorze;
	private StrzalkaWiatru wiatr;
	private Gracz gracz, gracz1;
	private Pocisk pocisk, pocisk1;
	private Tlo tlo;
	private PasekZycia pasek1, pasek2;
	private PasekMocyStrzalu pasekMocy, pasekMocy1;
	private EkranKoncowy end;
	private SimpleAudioPlayer nuta;
	private ShotSound uderzenie;
	private Pause ekranPauza;
	JFrame okno;
	private boolean pauza;

	public Towers() {
		okno = new JFrame(">>>TOWERS<<<");
		JPanel panel = (JPanel) okno.getContentPane();
		setBounds(0, 0, Plansza.SZEROKOSC, Plansza.WYSOKOSC);
		panel.setPreferredSize(new Dimension(Plansza.SZEROKOSC,
				Plansza.WYSOKOSC));
		panel.setLayout(null);
		panel.add(this);
		okno.setBounds(0, 0, Plansza.SZEROKOSC, Plansza.WYSOKOSC);
		okno.setVisible(true);
		okno.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			public void windowClosing(WindowEvent e) {
				nuta.stop();
				endGame=1001;

			}
		});
		okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		okno.setResizable(false);
		createBufferStrategy(2);// buforowanie
		strategia = getBufferStrategy();// buforowanie
		requestFocus();
		addKeyListener(this);

		
	}
	
	
	public void init(String gracz1imie, String gracz2imie){
		wzgorze = new Wzgorze();
		wiatr = new StrzalkaWiatru();
		wiatr.start();
		gracz = new Gracz(GRACZ1, gracz1imie);
		gracz1 = new Gracz(GRACZ2, gracz2imie);
		pocisk = new Pocisk(GRACZ1);
		pocisk1 = new Pocisk(GRACZ2);
		tlo = new Tlo();
		pasek1 = new PasekZycia(GRACZ1, gracz.getPlayerName());
		pasek2 = new PasekZycia(GRACZ2, gracz1.getPlayerName());
		pasekMocy = new PasekMocyStrzalu(GRACZ1);
		pasekMocy1 = new PasekMocyStrzalu(GRACZ2);
		end = new EkranKoncowy();
		nuta = new SimpleAudioPlayer(SOUND_NAME);
		uderzenie = new ShotSound();
		wiatr.setPriority(1);
		nuta.setPriority(1);
		uderzenie.setPriority(1);
		ekranPauza = new Pause();
		pauza = false;
		
		
	}

	public void checkColission() {
		Rectangle playerBound = gracz.getBounds();
		Rectangle bullet = pocisk1.getBounds();
		Rectangle playerBound1 = gracz1.getBounds();
		Rectangle bullet1 = pocisk.getBounds();
		Polygon hill = wzgorze.getBounds();
		if (bullet.intersects(playerBound)) {
			if (pocisk1.isFired()) {
				gracz.setDecreaseLife(true);
			}

		}
		if (bullet1.intersects(playerBound1)) {
			if (pocisk.isFired()) {
				gracz1.setDecreaseLife(true);
			}

		}
		if (bullet1.intersects(bullet)) {
			pocisk.collision(BULLET);
			pocisk1.collision(BULLET);
		}

		if (hill.intersects(bullet)) {
			wzgorze.collision(pocisk1.getPolozenieX(), pocisk1.getPolozenieY());
			pocisk1.collision(HILL);
			uderzenie.setFilename(HILL);
			uderzenie.setShot(true);
		}
		if (hill.intersects(bullet1)) {
			wzgorze.collision(pocisk.getPolozenieX(), pocisk.getPolozenieY());
			pocisk.collision(HILL);
			uderzenie.setFilename(HILL);
			uderzenie.setShot(true);
		}
	}

	public void setDebugMode() {
		wzgorze.setDebugMode(debugMode);
		gracz.setDebugMode(debugMode);
		gracz1.setDebugMode(debugMode);
		wiatr.setDebugMode(debugMode);
		pasek1.setDebugMode(debugMode);
		pasek2.setDebugMode(debugMode);
		tlo.setDebugMode(debugMode);
		pasekMocy.setDebugMode(debugMode);
		pasekMocy1.setDebugMode(debugMode);
		pocisk.setDebugMode(debugMode);
		pocisk1.setDebugMode(debugMode);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			if(pauza){
				pauza=false;
			}
			else pauza=true;
		}
		if (!end.isShow() || !pauza) {
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				if (debugMode == true) {
					debugMode = false;
				} else
					debugMode = true;
			}
			gracz.keyPressed(e);
			gracz1.keyPressed(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (!end.isShow() || !pauza) {
			gracz.keyReleased(e);
			gracz1.keyReleased(e);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void paintWorld() {
		Graphics2D g = (Graphics2D) strategia.getDrawGraphics();
		tlo.paint(g);

		if (debugMode) {
			g.setColor(Color.black);
			g.drawString(
					"K�t nachylenia: " + Integer.toString((int) pocisk.angle),
					60, 250);
			g.drawString(
					"K�t nachylenia: " + Integer.toString((int) pocisk1.angle),
					640, 250);
		}
		wzgorze.paint(g);
		wiatr.paint(g);
		gracz.paint(g);
		gracz1.paint(g);
		pocisk.paint(g);
		pocisk1.paint(g);
		pasek1.paint(g);
		pasek2.paint(g);
		pasekMocy.paint(g);
		pasekMocy1.paint(g);
		if (end.isShow()) {
			end.paint(g);
		}
		if(pauza)
			ekranPauza.paint(g);
		strategia.show();
		
		
	}

	public void updateWorld() {
		nuta.setPause(pauza);
		if (!pauza) {
			setDebugMode();
			tlo.update();
			wzgorze.update();
			wiatr.setNight(tlo.isNight());
			if (gracz.isWystrzelono()) {
				if (!pocisk.isCollision()) {
					pocisk.setFired(true);
				} else {
					pocisk.setCollision(gracz.isKeyReleased());
				}
			}
			if (gracz1.isWystrzelono()) {
				if (!pocisk1.isCollision()) {
					pocisk1.setFired(true);
				} else {
					pocisk1.setCollision(gracz1.isKeyReleased());
				}
			}
			if (gracz.isDecreaseLife()) {
				gracz.collision();
				pocisk1.collision(PLAYER);
				uderzenie.setFilename(PLAYER);
				uderzenie.setShot(true);
			}
			if (gracz1.isDecreaseLife()) {
				gracz1.collision();
				pocisk.collision(PLAYER);
				uderzenie.setFilename(PLAYER);
				uderzenie.setShot(true);
			}
			pocisk.ustawPolozeniePoczatkowe(gracz.getCenterline());
			pocisk.setSpeed(pasekMocy.getPower());
			pocisk.update();
			pocisk.calculateBulletSpeed(wiatr.getPower(), wiatr.getDirection());
			pocisk.angle = gracz.getCelownik();
			pocisk.setNight(tlo.isNight());

			pocisk1.ustawPolozeniePoczatkowe(gracz1.getCenterline());
			pocisk1.setSpeed(pasekMocy1.getPower());
			pocisk1.update();
			pocisk1.calculateBulletSpeed(wiatr.getPower(), wiatr.getDirection());
			pocisk1.angle = gracz1.getCelownik();
			pocisk1.setNight(tlo.isNight());

			pasek1.setLife(gracz.getLife());

			pasek2.setLife(gracz1.getLife());
			pasek1.setNight(tlo.isNight());
			pasek2.setNight(tlo.isNight());

			if (!end.isShow()) {
				pasekMocy.update();
				pasekMocy1.update();
				pasekMocy.setNight(tlo.isNight());
				pasekMocy1.setNight(tlo.isNight());
			} else {
				endGame++;
			}
			if (gracz.isEndOfLife()) {

				end.setShow(true);
				end.setPlayerName(gracz1.getPlayerName());
				end.setPlayerNumber(GRACZ2);
				end.update();
			}
			if (gracz1.isEndOfLife()) {

				end.setShow(true);
				end.setPlayerName(gracz.getPlayerName());
				end.setPlayerNumber(GRACZ1);
				end.update();
			}
		}
		else ekranPauza.update();
	}

	@SuppressWarnings("deprecation")
	public void run() {
		nuta.start();
		uderzenie.start();
		while (endGame <= END/* isVisible() */) {
			updateWorld();
			checkColission();
			paintWorld();
			try {
				Thread.sleep(Plansza.SZYBKOSC);
			} catch (InterruptedException e) {
			}
		}
		okno.setVisible(false);
		nuta.stop();
		//System.exit(0);
	}

	/*public static void main(String[] args) {
		Towers towers = new Towers();
		towers.init();
		Thread t = new Thread(towers);
		t.setPriority(10);
		t.start();

	}*/

}
