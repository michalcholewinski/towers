package pl.towers;

import pl.towers.additions.BufferedInputFile;
import pl.towers.objects.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Interfejs - Głowne Menu
 *
 * @author Michał
 *
 */
public class Game extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    public static final String NEW_GAME = "NOWA GRA";
    public static final String HELP = "POMOC";
    public static final String AUTHOR = "AUTORZY";
    public static final String END = "KONIEC";
    public static final String BACK = "POWROT";
    public static final String START = "START";
    public static final String FIRST_PLAYER_TEXT = " Player 1";
    public static final String SECOND_PLAYER_TEXT = " Player 2";
    public static final String FIRST_PLAYER_NAME_PLACEHOLDER = "Gracz1";
    public static final String SECOND_PLAYER_NAME_PLACEHOLDER = "Gracz2";

    private JButton newGame = new JButton(NEW_GAME);
    private JButton help = new JButton(HELP);
    private JButton author = new JButton(AUTHOR);
    private JButton end = new JButton(END);
    private JButton back = new JButton(BACK);
    private JButton start = new JButton(START);
    private JTextArea textArea = new JTextArea(HELP, 4, 20);
    private JTextArea textAreaAuthors = new JTextArea(AUTHOR, 4, 20);

    private JPanel panel;
    private String firstPlayersName;
    private String secondPlayersName;

    private JLabel firstPlayerString = new JLabel(FIRST_PLAYER_TEXT, SwingConstants.LEFT);
    private JTextField nameFieldFirstPlayer = new JTextField(FIRST_PLAYER_NAME_PLACEHOLDER);
    private JLabel secondPlayerString = new JLabel(SECOND_PLAYER_TEXT, SwingConstants.RIGHT);
    private JTextField nameFieldSecondPlayer = new JTextField(SECOND_PLAYER_NAME_PLACEHOLDER);

    public Game() {
        super("TOWERS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Board.WIDTH, Board.HEIGHT);
        nameFieldFirstPlayer.setPreferredSize(new Dimension(Board.WIDTH / 4,
                20));
        nameFieldSecondPlayer.setPreferredSize(new Dimension(Board.WIDTH / 4,
                20));
        start.setPreferredSize(new Dimension(100, 50));
        back.setPreferredSize(new Dimension(100, 50));
        start.addActionListener(this);
        back.addActionListener(this);

        newGame.setPreferredSize(new Dimension((Board.WIDTH / 4) - 10,
                100));
        help.setPreferredSize(new Dimension((Board.WIDTH / 4) - 10, 100));
        author.setPreferredSize(new Dimension((Board.WIDTH / 4) - 10,
                100));
        end.setPreferredSize(new Dimension((Board.WIDTH / 4) - 10, 100));

        newGame.addActionListener(this);
        help.addActionListener(this);
        author.addActionListener(this);
        end.addActionListener(this);

    }

    /**
     * Nasłuch który przycisk został wcisnięty
     */
    public void actionPerformed(ActionEvent evt) {
        Object zrodlo = evt.getSource();
        if (zrodlo == newGame) {
            nowaGra();
        } else if (zrodlo == help)

            pomoc();
        else if (zrodlo == author) {
            autorzy();
        } else if (zrodlo == end)
            System.exit(0);
        else if (zrodlo == start) {
            firstPlayersName = nameFieldFirstPlayer.getText();
            secondPlayersName = nameFieldSecondPlayer.getText();
            if (firstPlayersName.length() == 0 || secondPlayersName.length() == 0) {
                nowaGra();
            } else {
                Towers towers = new Towers();
                towers.init(firstPlayersName, secondPlayersName);
                Thread t = new Thread(towers);
                t.setPriority(10);
                t.start();
            }
        } else if (zrodlo == back) {
            menuGlowne();
        }

		/*
		 * panel.remove(newGame); panel.remove(help); panel.remove(author);
		 * panel.remove(end);
		 */
        repaint();
    }

    /**
     * Metoda odpowiedzialna za wyswietlenie menu głównego
     */
    @SuppressWarnings("deprecation")
    public void menuGlowne() {
        panel = new JPanel();

        panel.add(newGame);
        panel.add(help);
        panel.add(author);
        panel.add(end);

        setContentPane(panel);
        show();
    }

    /**
     * Metoda odpowiedzialna za pobranie nazw graczy i uruchomienie własciwej
     * gry
     */
    @SuppressWarnings("deprecation")
    public void nowaGra() {
        panel = new JPanel();

        panel.add(firstPlayerString);
        panel.add(nameFieldFirstPlayer);
        panel.add(secondPlayerString);
        panel.add(nameFieldSecondPlayer);

        panel.add(start);
        panel.add(back);
        setContentPane(panel);
        show();
    }

    /**
     * Metoda odpowiedzialna za wyswietlenie informacji o grze ( sterowanie itp)
     */
    @SuppressWarnings("deprecation")
    public void pomoc() {
        panel = new JPanel();
        String text = "";
        try {
            text = BufferedInputFile.read("help.txt");
        } catch (IOException e) {
            text = new String("Brak pliku z tekstem pomocy!");
        }
        textArea.setText(text);

        panel.add(textArea);
        panel.add(back);
        setContentPane(panel);
        show();

    }

    /**
     * Metoda odpowiedzialna za wyswietlenie informacji o autorach
     */
    @SuppressWarnings("deprecation")
    public void autorzy() {
        panel = new JPanel();
        String text = "";
        try {
            text = BufferedInputFile.read("author.txt");
        } catch (IOException e) {
            text = new String("Brak pliku z tekstem pomocy!");
        }
        textAreaAuthors.setText(text);

        panel.add(textAreaAuthors);
        panel.add(back);
        setContentPane(panel);
        show();

    }

    public static void main(String[] arg) {
        Game game = new Game();
        game.menuGlowne();
    }
}
