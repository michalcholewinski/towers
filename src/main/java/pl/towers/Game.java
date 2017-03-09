package pl.towers;

import dodatki.BufferedInputFile;
import obiekty.Plansza;

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
    private JButton nowaGra = new JButton("NOWA GRA");
    private JButton pomoc = new JButton("POMOC");
    private JButton autorzy = new JButton("AUTORZY");
    private JButton koniec = new JButton("KONIEC");
    private JButton powrot = new JButton("POWROT");
    private JButton start = new JButton("START");
    private JTextArea textArea = new JTextArea("POMOC", 4, 20);
    private JTextArea textAreaAuthors = new JTextArea("AUTORZY", 4, 20);

    private JPanel panel;
    private String imieGracz1;
    private String imieGracz2;

    private JLabel gracz1String = new JLabel(" Gracz 1", SwingConstants.LEFT);
    private JTextField nameFieldPlayer1 = new JTextField("Gracz1");
    private JLabel gracz2String = new JLabel(" Gracz2", SwingConstants.RIGHT);
    private JTextField nameFieldPlayer2 = new JTextField("Gracz2");

    public Game() {
        super("TOWERS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Plansza.SZEROKOSC, Plansza.WYSOKOSC);
        nameFieldPlayer1.setPreferredSize(new Dimension(Plansza.SZEROKOSC / 4,
                20));
        nameFieldPlayer2.setPreferredSize(new Dimension(Plansza.SZEROKOSC / 4,
                20));
        start.setPreferredSize(new Dimension(100, 50));
        powrot.setPreferredSize(new Dimension(100, 50));
        start.addActionListener(this);
        powrot.addActionListener(this);

        nowaGra.setPreferredSize(new Dimension((Plansza.SZEROKOSC / 4) - 10,
                100));
        pomoc.setPreferredSize(new Dimension((Plansza.SZEROKOSC / 4) - 10, 100));
        autorzy.setPreferredSize(new Dimension((Plansza.SZEROKOSC / 4) - 10,
                100));
        koniec.setPreferredSize(new Dimension((Plansza.SZEROKOSC / 4) - 10, 100));

        nowaGra.addActionListener(this);
        pomoc.addActionListener(this);
        autorzy.addActionListener(this);
        koniec.addActionListener(this);

    }

    /**
     * Nasłuch który przycisk został wcisnięty
     */
    public void actionPerformed(ActionEvent evt) {
        Object zrodlo = evt.getSource();
        if (zrodlo == nowaGra) {
            nowaGra();
        } else if (zrodlo == pomoc)

            pomoc();
        else if (zrodlo == autorzy) {
            autorzy();
        } else if (zrodlo == koniec)
            System.exit(0);
        else if (zrodlo == start) {
            imieGracz1 = nameFieldPlayer1.getText();
            imieGracz2 = nameFieldPlayer2.getText();
            if (imieGracz1.length() == 0 || imieGracz2.length() == 0) {
                nowaGra();
            } else {
                Towers towers = new Towers();
                towers.init(imieGracz1, imieGracz2);
                Thread t = new Thread(towers);
                t.setPriority(10);
                t.start();
            }
        } else if (zrodlo == powrot) {
            menuGlowne();
        }

		/*
		 * panel.remove(nowaGra); panel.remove(pomoc); panel.remove(autorzy);
		 * panel.remove(koniec);
		 */
        repaint();
    }

    /**
     * Metoda odpowiedzialna za wyswietlenie menu głównego
     */
    @SuppressWarnings("deprecation")
    public void menuGlowne() {
        panel = new JPanel();

        panel.add(nowaGra);
        panel.add(pomoc);
        panel.add(autorzy);
        panel.add(koniec);

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

        panel.add(gracz1String);
        panel.add(nameFieldPlayer1);
        panel.add(gracz2String);
        panel.add(nameFieldPlayer2);

        panel.add(start);
        panel.add(powrot);
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
            text = BufferedInputFile.read("plik.txt");
        } catch (IOException e) {
            text = new String("Brak pliku z tekstem pomocy!");
        }
        textArea.setText(text);

        panel.add(textArea);
        panel.add(powrot);
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
            text = BufferedInputFile.read("autorzy.txt");
        } catch (IOException e) {
            text = new String("Brak pliku z tekstem pomocy!");
        }
        textAreaAuthors.setText(text);

        panel.add(textAreaAuthors);
        panel.add(powrot);
        setContentPane(panel);
        show();

    }

    public static void main(String[] arg) {
        Game game = new Game();
        game.menuGlowne();
    }
}
