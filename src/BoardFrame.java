import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardFrame extends JFrame {
    private static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1000;
    private static final int RED_TIMER_POS_X = 830;
    private static final int RED_TIMER_POS_Y = 200;
    private static final int WHITE_TIMER_POS_X = 830;
    private static final int WHITE_TIMER_POS_Y = 760;
    public static boolean GAME = true;
    public static String MOVE = "WHITE";
    public static String WINNER;
    BoardFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Checkers");
        setResizable(false);
        setLayout(null);
        add(new Board());
        add(new MoveTimer(WHITE_TIMER_POS_X, WHITE_TIMER_POS_Y, "WHITE"));
        add(new MoveTimer(RED_TIMER_POS_X, RED_TIMER_POS_Y, "RED"));
        setVisible(true);
        WaitForEndOfTheGame(this);
    }
    public void WaitForEndOfTheGame(JFrame parent) {

        Timer timer = new Timer(1000, e -> {
            if(!BoardFrame.GAME) {
                new EndGamePanel(parent);
                ((Timer)e.getSource()).stop();

            }
        });
        timer.start();
    }
}
