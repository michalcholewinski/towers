package pl.towers;

import pl.towers.additions.Pause;
import pl.towers.sound.ShotSound;
import pl.towers.sound.SimpleAudioPlayer;
import pl.towers.objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Towers extends Canvas implements Board, KeyListener, Runnable {

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
	private Hill hill;
	private WindArrow wiatr;
	private Player gracz, gracz1;
	private Bullet bullet, bullet1;
	private Background background;
	private LifeBar pasek1, pasek2;
	private ShootPowerBar pasekMocy, pasekMocy1;
	private EndScreen end;
	private SimpleAudioPlayer nuta;
	private ShotSound uderzenie;
	private Pause ekranPauza;
	JFrame okno;
	private boolean pauza;

	public Towers() {
		okno = new JFrame(">>>TOWERS<<<");
		JPanel panel = (JPanel) okno.getContentPane();
		setBounds(0, 0, Board.SZEROKOSC, Board.WYSOKOSC);
		panel.setPreferredSize(new Dimension(Board.SZEROKOSC,
				Board.WYSOKOSC));
		panel.setLayout(null);
		panel.add(this);
		okno.setBounds(0, 0, Board.SZEROKOSC, Board.WYSOKOSC);
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
		hill = new Hill();
		wiatr = new WindArrow();
		wiatr.start();
		gracz = new Player(GRACZ1, gracz1imie);
		gracz1 = new Player(GRACZ2, gracz2imie);
		bullet = new Bullet(GRACZ1);
		bullet1 = new Bullet(GRACZ2);
		background = new Background();
		pasek1 = new LifeBar(GRACZ1, gracz.getPlayerName());
		pasek2 = new LifeBar(GRACZ2, gracz1.getPlayerName());
		pasekMocy = new ShootPowerBar(GRACZ1);
		pasekMocy1 = new ShootPowerBar(GRACZ2);
		end = new EndScreen();
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
		Rectangle bullet = bullet1.getBounds();
		Rectangle playerBound1 = gracz1.getBounds();
		Rectangle bullet1 = this.bullet.getBounds();
		Polygon hill = this.hill.getBounds();
		if (bullet.intersects(playerBound)) {
			if (this.bullet1.isFired()) {
				gracz.setDecreaseLife(true);
			}

		}
		if (bullet1.intersects(playerBound1)) {
			if (this.bullet.isFired()) {
				gracz1.setDecreaseLife(true);
			}

		}
		if (bullet1.intersects(bullet)) {
			this.bullet.collision(BULLET);
			this.bullet1.collision(BULLET);
		}

		if (hill.intersects(bullet)) {
			this.hill.collision(this.bullet1.getPolozenieX(), this.bullet1.getPolozenieY());
			this.bullet1.collision(HILL);
			uderzenie.setFilename(HILL);
			uderzenie.setShot(true);
		}
		if (hill.intersects(bullet1)) {
			this.hill.collision(this.bullet.getPolozenieX(), this.bullet.getPolozenieY());
			this.bullet.collision(HILL);
			uderzenie.setFilename(HILL);
			uderzenie.setShot(true);
		}
	}

	public void setDebugMode() {
		hill.setDebugMode(debugMode);
		gracz.setDebugMode(debugMode);
		gracz1.setDebugMode(debugMode);
		wiatr.setDebugMode(debugMode);
		pasek1.setDebugMode(debugMode);
		pasek2.setDebugMode(debugMode);
		background.setDebugMode(debugMode);
		pasekMocy.setDebugMode(debugMode);
		pasekMocy1.setDebugMode(debugMode);
		bullet.setDebugMode(debugMode);
		bullet1.setDebugMode(debugMode);
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
		background.paint(g);

		if (debugMode) {
			g.setColor(Color.black);
			g.drawString(
					"K�t nachylenia: " + Integer.toString((int) bullet.angle),
					60, 250);
			g.drawString(
					"K�t nachylenia: " + Integer.toString((int) bullet1.angle),
					640, 250);
		}
		hill.paint(g);
		wiatr.paint(g);
		gracz.paint(g);
		gracz1.paint(g);
		bullet.paint(g);
		bullet1.paint(g);
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
			background.update();
			hill.update();
			wiatr.setNight(background.isNight());
			if (gracz.isWystrzelono()) {
				if (!bullet.isCollision()) {
					bullet.setFired(true);
				} else {
					bullet.setCollision(gracz.isKeyReleased());
				}
			}
			if (gracz1.isWystrzelono()) {
				if (!bullet1.isCollision()) {
					bullet1.setFired(true);
				} else {
					bullet1.setCollision(gracz1.isKeyReleased());
				}
			}
			if (gracz.isDecreaseLife()) {
				gracz.collision();
				bullet1.collision(PLAYER);
				uderzenie.setFilename(PLAYER);
				uderzenie.setShot(true);
			}
			if (gracz1.isDecreaseLife()) {
				gracz1.collision();
				bullet.collision(PLAYER);
				uderzenie.setFilename(PLAYER);
				uderzenie.setShot(true);
			}
			bullet.ustawPolozeniePoczatkowe(gracz.getCenterline());
			bullet.setSpeed(pasekMocy.getPower());
			bullet.update();
			bullet.calculateBulletSpeed(wiatr.getPower(), wiatr.getDirection());
			bullet.angle = gracz.getCelownik();
			bullet.setNight(background.isNight());

			bullet1.ustawPolozeniePoczatkowe(gracz1.getCenterline());
			bullet1.setSpeed(pasekMocy1.getPower());
			bullet1.update();
			bullet1.calculateBulletSpeed(wiatr.getPower(), wiatr.getDirection());
			bullet1.angle = gracz1.getCelownik();
			bullet1.setNight(background.isNight());

			pasek1.setLife(gracz.getLife());

			pasek2.setLife(gracz1.getLife());
			pasek1.setNight(background.isNight());
			pasek2.setNight(background.isNight());

			if (!end.isShow()) {
				pasekMocy.update();
				pasekMocy1.update();
				pasekMocy.setNight(background.isNight());
				pasekMocy1.setNight(background.isNight());
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
				Thread.sleep(Board.SZYBKOSC);
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
