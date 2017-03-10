package pl.towers;

import pl.towers.additions.Pause;
import pl.towers.player.*;
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
    public static final String TITLE = ">>>TOWERS<<<";
    public static final int DOUBLE_BUFFERING = 2;
    public static final int LOW_PRIO = 1;
    private final int FIRST_PLAYER = 0;
	private final int SECOND_PLAYER = 1;
	private final int END = 1000;
	private final int HILL = 0; // s�u�y do sprawdzania czy by�a kolizja ze
								// wzg�rzem,
	private final int PLAYER = 1; // czy z jedn� z wie�
	private final int BULLET = 2; // czy z inn� kulk�
	private final String SOUND_NAME = "w.wav";

	private int endGame = 0;
	private boolean debugMode = false;

	public final BufferStrategy strategy;
	private Hill hill;
	private WindArrow windArrow;
	private Player firstPlayer, secondPlayer;
	private Bullet firstPlayersBullet, secondPlayersBullet;
	private Background background;
	private LifeBar firstPlayersLifeBar, secondPlayersLifeBar;
	private ShootPowerBar firstPlayersShootPowerBar, secondPlayersShootPowerBar;
	private EndScreen end;
	private SimpleAudioPlayer sound;
	private ShotSound hitSound;
	private Pause pauseScreen;
	private final JFrame window;
	private boolean pause;

	public Towers() {
        window = buildWindow();
		createBufferStrategy(DOUBLE_BUFFERING);
		strategy = getBufferStrategy();
		requestFocus();
		addKeyListener(this);

		
	}

    private JFrame buildWindow() {
        JFrame window = new JFrame(TITLE);
        JPanel panel = (JPanel) window.getContentPane();
        setBounds(0, 0, Board.WIDTH, Board.HEIGHT);
        panel.setPreferredSize(new Dimension(Board.WIDTH,
                Board.HEIGHT));
        panel.setLayout(null);
        panel.add(this);
        window.setBounds(0, 0, Board.WIDTH, Board.HEIGHT);
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @SuppressWarnings("deprecation")
            public void windowClosing(WindowEvent e) {
                sound.stop();
                endGame=1001;

            }
        });
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        return window;
    }


    public void init(String gracz1imie, String gracz2imie){
		hill = new Hill();
		windArrow = new WindArrow();
		windArrow.start();
		firstPlayer = new Player(PlayerEnum.LEFT, gracz1imie);
		secondPlayer = new Player(PlayerEnum.RIGHT, gracz2imie);
		firstPlayersBullet = new Bullet(FIRST_PLAYER);
		secondPlayersBullet = new Bullet(SECOND_PLAYER);
		background = new Background();
		firstPlayersLifeBar = new LifeBar(PlayerEnum.LEFT, firstPlayer.getPlayerName()); //TODO(mcholewi) move it to player class
		secondPlayersLifeBar = new LifeBar(PlayerEnum.RIGHT, secondPlayer.getPlayerName());
		firstPlayersShootPowerBar = new ShootPowerBar(PlayerEnum.LEFT);
		secondPlayersShootPowerBar = new ShootPowerBar(PlayerEnum.RIGHT);
		end = new EndScreen();
		sound = new SimpleAudioPlayer(SOUND_NAME);
		hitSound = new ShotSound();
		windArrow.setPriority(LOW_PRIO);
		sound.setPriority(LOW_PRIO);
		hitSound.setPriority(LOW_PRIO);
		pauseScreen = new Pause();
		pause = false;
		
		
	}

	public void checkColission() {
		Rectangle firstPlayerBounds = firstPlayer.getBounds();
        Rectangle secondPlayerBounds = secondPlayer.getBounds();
        Rectangle secondPlayersBulletBounds = secondPlayersBullet.getBounds();
		Rectangle firstPlayersBulletBounds = firstPlayersBullet.getBounds();

        checkIfFirstPlayerHasBeenHitted(firstPlayerBounds, secondPlayersBulletBounds);
        checkIfSecondPlayerHasBeenHitted(secondPlayerBounds, firstPlayersBulletBounds);
        checkIfBulletsHitsEachOther(secondPlayersBulletBounds, firstPlayersBulletBounds);
        checkIfThereIsColissionWithHill(secondPlayersBulletBounds, firstPlayersBulletBounds);
	}

    private void checkIfFirstPlayerHasBeenHitted(Rectangle playerBound, Rectangle bullet) {
        if (bullet.intersects(playerBound)) {
            if (this.secondPlayersBullet.isFired()) {
                firstPlayer.setDecreaseLife(true);
            }

        }
    }

    private void checkIfSecondPlayerHasBeenHitted(Rectangle playerBound1, Rectangle bullet1) {
        if (bullet1.intersects(playerBound1)) {
            if (this.firstPlayersBullet.isFired()) {
                secondPlayer.setDecreaseLife(true);
            }

        }
    }

    private void checkIfBulletsHitsEachOther(Rectangle bullet, Rectangle bullet1) {
        if (bullet1.intersects(bullet)) {
			this.firstPlayersBullet.collision(BULLET);
			this.secondPlayersBullet.collision(BULLET);
		}
    }

    private void checkIfThereIsColissionWithHill(Rectangle secondPlayersBullet, Rectangle firstPlayersBullet) {
        Polygon hillBounds = hill.getBounds();
        if (hillBounds.intersects(secondPlayersBullet)) {
            this.hill.collision(this.secondPlayersBullet.getPolozenieX(), this.secondPlayersBullet.getPolozenieY());
            this.secondPlayersBullet.collision(HILL);
            hitSound.setFilename(HILL);
            hitSound.setShot(true);
        }
        if (hillBounds.intersects(firstPlayersBullet)) {
            this.hill.collision(this.firstPlayersBullet.getPolozenieX(), this.firstPlayersBullet.getPolozenieY());
            this.firstPlayersBullet.collision(HILL);
            hitSound.setFilename(HILL);
            hitSound.setShot(true);
        }
    }

    public void setDebugMode() {
		hill.setDebugMode(debugMode);
		firstPlayer.setDebugMode(debugMode);
		secondPlayer.setDebugMode(debugMode);
		windArrow.setDebugMode(debugMode);
		firstPlayersLifeBar.setDebugMode(debugMode);
		secondPlayersLifeBar.setDebugMode(debugMode);
		background.setDebugMode(debugMode);
		firstPlayersShootPowerBar.setDebugMode(debugMode);
		secondPlayersShootPowerBar.setDebugMode(debugMode);
		firstPlayersBullet.setDebugMode(debugMode);
		secondPlayersBullet.setDebugMode(debugMode);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			if(pause){
				pause =false;
			}
			else pause =true;
		}
		if (!end.isShow() || !pause) {
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				if (debugMode == true) {
					debugMode = false;
				} else
					debugMode = true;
			}
			firstPlayer.keyPressed(e);
			secondPlayer.keyPressed(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (!end.isShow() || !pause) {
			firstPlayer.keyReleased(e);
			secondPlayer.keyReleased(e);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void paintWorld() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		background.paint(g);

		if (debugMode) {
			g.setColor(Color.black);
			g.drawString(
					"K�t nachylenia: " + Integer.toString((int) firstPlayersBullet.angle),
					60, 250);
			g.drawString(
					"K�t nachylenia: " + Integer.toString((int) secondPlayersBullet.angle),
					640, 250);
		}
		hill.paint(g);
		windArrow.paint(g);
		firstPlayer.paint(g);
		secondPlayer.paint(g);
		firstPlayersBullet.paint(g);
		secondPlayersBullet.paint(g);
		firstPlayersLifeBar.paint(g);
		secondPlayersLifeBar.paint(g);
		firstPlayersShootPowerBar.paint(g);
		secondPlayersShootPowerBar.paint(g);
		if (end.isShow()) {
			end.paint(g);
		}
		if(pause)
			pauseScreen.paint(g);
		strategy.show();
		
		
	}

	public void updateWorld() {
		sound.setPause(pause);
		if (!pause) {
			setDebugMode();
			background.update();
			hill.update();
			windArrow.setNight(background.isNight());
			if (firstPlayer.isWystrzelono()) {
				if (!firstPlayersBullet.isCollision()) {
					firstPlayersBullet.setFired(true);
				} else {
					firstPlayersBullet.setCollision(firstPlayer.isKeyReleased());
				}
			}
			if (secondPlayer.isWystrzelono()) {
				if (!secondPlayersBullet.isCollision()) {
					secondPlayersBullet.setFired(true);
				} else {
					secondPlayersBullet.setCollision(secondPlayer.isKeyReleased());
				}
			}
			if (firstPlayer.isDecreaseLife()) {
				firstPlayer.collision();
				secondPlayersBullet.collision(PLAYER);
				hitSound.setFilename(PLAYER);
				hitSound.setShot(true);
			}
			if (secondPlayer.isDecreaseLife()) {
				secondPlayer.collision();
				firstPlayersBullet.collision(PLAYER);
				hitSound.setFilename(PLAYER);
				hitSound.setShot(true);
			}
			firstPlayersBullet.ustawPolozeniePoczatkowe(firstPlayer.getCenterline());
			firstPlayersBullet.setSpeed(firstPlayersShootPowerBar.getPower());
			firstPlayersBullet.update();
			firstPlayersBullet.calculateBulletSpeed(windArrow.getPower(), windArrow.getDirection());
			firstPlayersBullet.angle = firstPlayer.getViewfinder();
			firstPlayersBullet.setNight(background.isNight());

			secondPlayersBullet.ustawPolozeniePoczatkowe(secondPlayer.getCenterline());
			secondPlayersBullet.setSpeed(secondPlayersShootPowerBar.getPower());
			secondPlayersBullet.update();
			secondPlayersBullet.calculateBulletSpeed(windArrow.getPower(), windArrow.getDirection());
			secondPlayersBullet.angle = secondPlayer.getViewfinder();
			secondPlayersBullet.setNight(background.isNight());

			firstPlayersLifeBar.setLife(firstPlayer.getLife());

			secondPlayersLifeBar.setLife(secondPlayer.getLife());
			firstPlayersLifeBar.setNight(background.isNight());
			secondPlayersLifeBar.setNight(background.isNight());

			if (!end.isShow()) {
				firstPlayersShootPowerBar.update();
				secondPlayersShootPowerBar.update();
				firstPlayersShootPowerBar.setNight(background.isNight());
				secondPlayersShootPowerBar.setNight(background.isNight());
			} else {
				endGame++;
			}
			if (firstPlayer.isEndOfLife()) {

				end.setShow(true);
				end.setPlayerName(secondPlayer.getPlayerName());
				end.setPlayerNumber(SECOND_PLAYER);
				end.update();
			}
			if (secondPlayer.isEndOfLife()) {

				end.setShow(true);
				end.setPlayerName(firstPlayer.getPlayerName());
				end.setPlayerNumber(FIRST_PLAYER);
				end.update();
			}
		}
		else pauseScreen.update();
	}

	@SuppressWarnings("deprecation")
	public void run() {
		sound.start();
		hitSound.start();
		while (endGame <= END/* isVisible() */) {
			updateWorld();
			checkColission();
			paintWorld();
			try {
				Thread.sleep(Board.SZYBKOSC);
			} catch (InterruptedException e) {
			}
		}
		window.setVisible(false);
		sound.stop();
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
