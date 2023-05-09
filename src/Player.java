import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.Socket;

public class Player extends JPanel implements MouseListener, MouseMotionListener {
    private static final int FIELD_SIZE = 80;
    private static final int PAWN_SIZE = 60;
    private static final int PAWN_OFFSET = 10;
    private static final int SMALL_PAWN_SIZE = 20;
    private static final int SMALL_PAWN_OFFSET = 30;
    private static final int QUEEN_SIZE = 30;
    private static final int QUEEN_OFFSET = 25;
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private static final int BOARD_ARRAY_SIZE = 7;
    private static final int BOARD_SIZE = BOARD_ARRAY_SIZE * FIELD_SIZE;
    public static final String WHITE_COLOR = "WHITE";
    public static final String RED_COLOR = "RED";
    private static final int RED_TIMER_POS_X = 830;
    private static final int RED_TIMER_POS_Y = 200;
    private static final int WHITE_TIMER_POS_X = 830;
    private static final int WHITE_TIMER_POS_Y = 760;


    private int SELECTED_PAWN_X;
    private int SELECTED_PAWN_Y;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private BoardFrame boardFrame;

    private Pawn[][] pawn = new Pawn[ROWS][COLUMNS];
    private String color;
    private String move;

    private MoveTimer redTimer;
    private MoveTimer whiteTimer;


    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintFields(g2d);
        paintPawns(g2d);
    }

    private void paintFields(Graphics g2d) {
         for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(j * FIELD_SIZE, i * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                } else {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(j * FIELD_SIZE, i * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                }
            }
        }
    }

    private void paintPawns(Graphics g2d) {
        if(color.equals(RED_COLOR)){
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (pawn[i][j] != null)
                    {
                        if(!pawn[i][j].isQueen()) {
                            if (pawn[i][j].getColor().equals(RED_COLOR)) {
                                g2d.setColor(Color.RED);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + PAWN_OFFSET, BOARD_SIZE - pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                            } else {
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + PAWN_OFFSET, BOARD_SIZE - pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                            }
                        }
                        else
                        {
                            if (pawn[i][j].getColor().equals(RED_COLOR)) {
                                g2d.setColor(Color.RED);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + PAWN_OFFSET, BOARD_SIZE -  pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + QUEEN_OFFSET , BOARD_SIZE -  pawn[i][j].getY() + QUEEN_OFFSET, QUEEN_SIZE, QUEEN_SIZE);
                                g2d.setColor(Color.RED);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + SMALL_PAWN_OFFSET, BOARD_SIZE - pawn[i][j].getY() + SMALL_PAWN_OFFSET, SMALL_PAWN_SIZE, SMALL_PAWN_SIZE);
                            } else {
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + PAWN_OFFSET, BOARD_SIZE - pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                                g2d.setColor(Color.RED);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + QUEEN_OFFSET, BOARD_SIZE - pawn[i][j].getY() + QUEEN_OFFSET, QUEEN_SIZE, QUEEN_SIZE);
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(BOARD_SIZE - pawn[i][j].getX() + SMALL_PAWN_OFFSET, BOARD_SIZE - pawn[i][j].getY() + SMALL_PAWN_OFFSET, SMALL_PAWN_SIZE, SMALL_PAWN_SIZE);

                            }
                        }
                    }
                }
            }

        }
        else{
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (pawn[i][j] != null)
                    {
                        if(!pawn[i][j].isQueen()) {
                            if (pawn[i][j].getColor().equals(RED_COLOR)) {
                                g2d.setColor(Color.RED);
                                g2d.fillOval(pawn[i][j].getX() + PAWN_OFFSET, pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                            } else {
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(pawn[i][j].getX() + PAWN_OFFSET, pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                            }
                        }
                        else
                        {
                            if (pawn[i][j].getColor().equals(RED_COLOR)) {
                                g2d.setColor(Color.RED);
                                g2d.fillOval(pawn[i][j].getX() + PAWN_OFFSET, pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(pawn[i][j].getX() + QUEEN_OFFSET , pawn[i][j].getY() + QUEEN_OFFSET, QUEEN_SIZE, QUEEN_SIZE);
                                g2d.setColor(Color.RED);
                                g2d.fillOval(pawn[i][j].getX() + SMALL_PAWN_OFFSET , pawn[i][j].getY() + SMALL_PAWN_OFFSET, SMALL_PAWN_SIZE, SMALL_PAWN_SIZE);
                            } else {
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(pawn[i][j].getX() + PAWN_OFFSET, pawn[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                                g2d.setColor(Color.RED);
                                g2d.fillOval(pawn[i][j].getX() + QUEEN_OFFSET , pawn[i][j].getY() + QUEEN_OFFSET, QUEEN_SIZE, QUEEN_SIZE);
                                g2d.setColor(Color.WHITE);
                                g2d.fillOval(pawn[i][j].getX() + SMALL_PAWN_OFFSET , pawn[i][j].getY() + SMALL_PAWN_OFFSET, SMALL_PAWN_SIZE, SMALL_PAWN_SIZE);

                            }
                        }
                    }
                }
            }
        }
    }

    public void sendData(Move move){
        try {
            objectOutputStream.writeObject(move);
        } catch (IOException ex) {
            System.out.println("Server is not reachable!");
            throw new RuntimeException(ex);
        }
    }

    public void createWindow(){
        setBounds(180, 180, 640, 640);
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        boardFrame.add(this);
        if(color.equals("RED")){
            redTimer.changePosition(WHITE_TIMER_POS_X, WHITE_TIMER_POS_Y);
            whiteTimer.changePosition(RED_TIMER_POS_X, RED_TIMER_POS_Y);
        }
    }

    public void getData(){

        try{
            Object receivedData = objectInputStream.readObject();
            System.out.println("OTRZYMALEM OBIEKT");
            if(receivedData instanceof BoardInfo){
                System.out.println("OTRZYMALEM PLANSZE");
                setPawn(((BoardInfo) receivedData).getPawn());
                setMove(((BoardInfo) receivedData).getMove());
                setColor(((BoardInfo) receivedData).getColor());
                repaint();
            }
            else if(receivedData instanceof MoveLeftTime){
                System.out.println("OTRZYMALEM CZAS");
                redTimer.updateTimer(((MoveLeftTime) receivedData).getRedLeftTime());
                whiteTimer.updateTimer(((MoveLeftTime) receivedData).getWhiteLeftTime());
                whiteTimer.repaint();
                redTimer.repaint();
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*BoardInfo Data = null;
        try {
            Data = (BoardInfo) objectInputStream.readObject();
            System.out.println("ODEBRALEM DANE!");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        player.setPawn(Data.getPawn());
        player.setMove(Data.getMove());
        player.repaint();*/
    }

    public void connectToServer() throws IOException {
        while(true){
            System.out.println("LACZE SIE!");
            Socket connection = new Socket("127.0.0.1", 5036);
            if(connection.isConnected()){

                boardFrame = new BoardFrame();
                redTimer = boardFrame.getRedTimer();
                whiteTimer = boardFrame.getWhiteTimer();

                objectInputStream = new ObjectInputStream(connection.getInputStream());
                objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
                getData();
                createWindow();
                break;
            }
        }

        Thread serverListener = new ServerListener(objectInputStream, this, whiteTimer, redTimer);
        serverListener.start();
    }


    public void setPawn(Pawn[][] pawn) {
        this.pawn = pawn;
    }

    public void setMove(String move) {
        this.move = move;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(color.equals(RED_COLOR)) {
            int x = BOARD_SIZE / FIELD_SIZE - e.getX() / FIELD_SIZE;
            int y = BOARD_SIZE / FIELD_SIZE - e.getY() / FIELD_SIZE;
            if(pawn[y][x] != null)
            {
                SELECTED_PAWN_Y = y; //BOARD_ARRAY_SIZE - y;
                SELECTED_PAWN_X = x; //BOARD_ARRAY_SIZE - x;
                System.out.println("SELECTED: " + x + " | "+ y);
            }
        }
        else{
            int x = e.getX() / FIELD_SIZE;
            int y = e.getY() / FIELD_SIZE;
            if(pawn[y][x] != null)
            {
                SELECTED_PAWN_Y = y;
                SELECTED_PAWN_X = x;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int x, y;
        if(color.equals(RED_COLOR)){
            x = BOARD_SIZE / FIELD_SIZE - e.getX() / FIELD_SIZE;
            y = BOARD_SIZE / FIELD_SIZE - e.getY() / FIELD_SIZE;
        }
        else{
            x = e.getX() / FIELD_SIZE;
            y = e.getY() / FIELD_SIZE;
        }

        System.out.println(SELECTED_PAWN_X + " | " + SELECTED_PAWN_Y);

        if(pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X] == null) {
            return;
        }
        System.out.println("PLAYER: RUCH: " + Game.getMove());
        if(move.equals(color)) {
            System.out.println("WYSYLAM RUCH! " + SELECTED_PAWN_X + " | " + SELECTED_PAWN_Y + " | " + x + " | " + y);
            Move move = new Move(SELECTED_PAWN_X, SELECTED_PAWN_Y, x, y);
            sendData(move);
        }
        else{
            System.out.println("COFAM! " + color);
            pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(SELECTED_PAWN_X * FIELD_SIZE);
            pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(SELECTED_PAWN_Y * FIELD_SIZE);
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X] != null) {
            if(color.equals(RED_COLOR)){
                System.out.println("PRZESUWAM: " + SELECTED_PAWN_X + " | " + SELECTED_PAWN_Y);
                pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(BOARD_SIZE - e.getX() + FIELD_SIZE / 2);
                pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(BOARD_SIZE - e.getY() + FIELD_SIZE / 2);
            }
            else{
                System.out.println("PRZESUWAM: " + SELECTED_PAWN_X + " | " + SELECTED_PAWN_Y);
                pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(e.getX() - FIELD_SIZE / 2);
                pawn[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(e.getY() - FIELD_SIZE / 2);
            }
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
