import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frame {

    // variables declaration and initialization
    private static JButton play = new JButton("PLAY");
    private static JButton quit = new JButton("QUIT");
    private static JButton enter = new JButton("ENTER");

    private static JLabel lblGuess = new JLabel("Enter your guess:");
    private static JLabel errorMsg = new JLabel("");

    private static JTextField guessField = new JTextField(10);

    private static JLabel interval = new JLabel();
    private static JFrame main, f2;

    static int tries, start, end, randomNum;
    static Interval ivl;

    private static void GUI() {

        main = new JFrame("Game");
        f2 = new JFrame("Game");
        main.setSize(280, 150);
        f2.setSize(600, 330);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // buttons layout
        main.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
        main.add(play);
        main.add(quit);
        main.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        // make buttons work
        makePlayWork();
        makeQuitWork();
        makeEnterWork();

        // center windows
        main.setLocationRelativeTo(null);
        f2.setLocationRelativeTo(null);

        main.setVisible(true);
    }

    // place buttons, text labels, and text fields
    private static void placeStuff() {

        f2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        gbc.insets = new Insets(35, 0, 30, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        f2.add(interval, gbc);

        gbc.insets = new Insets(35, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        f2.add(lblGuess, gbc);

        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;
        gbc.gridx = 0;
        gbc.gridy = 3;
        f2.add(guessField, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        f2.add(errorMsg, gbc);

        gbc.insets = new Insets(15, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        f2.add(enter, gbc);
    }

    private static void secondFrame() {

        main.dispose();
        placeStuff();

        // accepts only numeric digits and no other keys
        guessField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if ((c < '0') || (c > '9')) {
                    ke.consume();
                    if (Character.isLetter(c)) {
                        errorMsg.setText("Invalid input");
                        errorMsg.setForeground(Color.RED);
                    }
                } else errorMsg.setForeground(f2.getBackground());
            }
        });

        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setFont(new Font("Arial", Font.PLAIN, 20));
        interval.setFont(new Font("Arial", Font.BOLD, 25));
        ivl = new Interval(0, 100);
        start = ivl.getStart();
        end = ivl.getEnd();
        randomNum = (int) (Math.random() * (end - start + 1) + start);
        updateInterval();
        JOptionPane.showMessageDialog(f2, "Welcome to BINGO! A random number has been " +
                "generated 1 and 100.", "BINGO! Welcome Message", JOptionPane.INFORMATION_MESSAGE, null);
        playSound("start.wav");
        f2.setVisible(true);
        f2.getRootPane().setDefaultButton(enter);
    }

    // from http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    private static void playSound(String soundName)
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    // action listener for play button
    private static void makePlayWork() {
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound("start.wav");
                playSound("logon.wav");
                secondFrame();
            }
        });
    }

    // action listener for quit button
    private static void makeQuitWork() {
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound("start.wav");
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure" +
                        " you want to quit the game?", "Exit Game?", JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    playSound("start.wav");
                    playSound("logoff.wav");
                    main.dispose();
                } else playSound("start.wav");
            }
        });
    }

    // action listener for enter button
    private static void makeEnterWork() {
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound("start.wav");
                if (!guessField.getText().equals("")) {
                    int guessNum = Integer.parseInt(guessField.getText());
                    if (ivl.withinBoundary(guessNum)) {
                        if (guessNum != randomNum) {
                            playSound("chord.wav");
                            tries++;
                            ivl.updateInterval(guessNum, randomNum);
                            JOptionPane.showMessageDialog(null, "WRONG!", "ERROR", JOptionPane.ERROR_MESSAGE);
                            updateInterval();
                        } else {
                            playSound("tada.wav");
                            JOptionPane.showMessageDialog(null, "BINGO! The secret number is " + guessNum + ". And it took you " + tries + " trial(s).");
                            playSound("ding.wav");
                            int res = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "One More?", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.YES_OPTION) {
                                playSound("start.wav");
                                gameReset();
                                playSound("logon.wav");
                                JOptionPane.showMessageDialog(null, "A new game has started. Good Luck!");
                                playSound("start.wav");
                            } else {
                                playSound("start.wav");
                                JOptionPane.showMessageDialog(null, "Thanks for your participation! Please come again!");
                                playSound("start.wav");
                                playSound("logoff.wav");
                                System.exit(0);
                            }
                        }
                    } else {
                        playSound("critical-stop.wav");
                        JOptionPane.showMessageDialog(null, "OUT OF BOUNDARY!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        playSound("start.wav");
                    }
                    guessField.setText("");
                }
            }
        });
    }

    // update interval displayed
    private static void updateInterval() {
        interval.setText(ivl.getStart() + "         -         " + ivl.getEnd());
    }

    // function for resetting game
    private static void gameReset() {
        tries = 0;
        ivl = new Interval(0, 100);
        updateInterval();
    }

    public static void main(String[] args) { GUI(); }


}
