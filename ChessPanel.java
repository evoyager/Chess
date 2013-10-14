/**
 * Created by Evgeniy Gusar
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ChessPanel extends JPanel implements MouseListener, MouseMotionListener, java.lang.Runnable {
    //MediaTracker controler;
    /*int [] board = {
            14, 12,	13,	15,	16, 13,	12,	14,
            11,	11,	11,	11,	11,	11,	11,	11,
            99,	99,	99,	99,	99,	99,	99,	99,
            99,	99,	99,	99,	99,	99,	99,	99,
            99,	99,	99,	99,	99,	99,	99,	99,
            99,	99,	99,	99,	99,	99,	99,	99,
            1,	1,	1,	1,	1,	1,	1,	1,
            4,  2,	3,	5,	6,  3,	2,	4 }; */
    int [] board = new int [64];
    Image [] images = new Image [18];

    final String picturePath = "d:\\!Java\\images-new\\";

    private int imageID;

    enum ChessColor {BLACK, WHITE}
    ChessColor current_figure_color = null;
    ChessColor diagonal_figure_color = null;
    ChessColor current_step = ChessColor.WHITE;

    int code = 0,
        x = 0,
        y = 0,
        start = 0,
        alt = 0,
        end = 0;

    Color dunkel = new Color (156, 121, 90);
    Color hell = new Color (231, 190, 156);
    Color red = new Color (0xCC0000);
    Color green = new Color (0x009900);
    Color blue = new Color (0x000099);

    public ChessPanel()
    {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(320,320));


        newgame ();

        //load images
        MediaTracker mediaTracker = new MediaTracker (this);

        images [1] = getImage (picturePath + "white_pawn.png");
        images [2] = getImage (picturePath + "white_knight.png");
        images [3] = getImage (picturePath + "white_bishop.png");
        images [4] = getImage (picturePath + "white_rook.png");
        images [5] = getImage (picturePath + "white_queen.png");
        images [6] = getImage (picturePath + "white_king.png");

        images [11] = getImage (picturePath + "black_pawn.png");
        images [12] = getImage (picturePath + "black_knight.png");
        images [13] = getImage (picturePath + "black_bishop.png");
        images [14] = getImage (picturePath + "black_rook.png");
        images [15] = getImage (picturePath + "black_queen.png");
        images [16] = getImage (picturePath + "black_king.png");

        addMouseListener (this);
        addMouseMotionListener (this);
    }

    //check if move is valid
    public boolean isvalid (int move) {
        diagonal_figure_color = figureColor(end);

        switch (board [start] % 10) {
        //pawn
        case  1: {
            //Denide to move forward
            if ((current_figure_color == ChessColor.BLACK) & (end < start))
                return false;
            if ((current_figure_color == ChessColor.WHITE) & (end > start))
                return false;
            //move diagonally if enemy is here
            if ( ((move == 9) || (move == 7)) & ((current_figure_color != diagonal_figure_color) & (diagonal_figure_color != null)) )
                return true;
            //step up or down if it's empty
            else if ((move == 8) & (board [end] == 0))
                return true;
            if ( (current_figure_color == ChessColor.BLACK) & ( ((start > 7) & (start < 16) ) & (end - start == 16))  )
                return true;
            if ( (current_figure_color == ChessColor.WHITE) & ( ((start > 47) & (start < 56) ) & (start - end == 16)) )
                return true;
            else return false;
        }
        //rook
        case 4:
            if (current_figure_color != diagonal_figure_color) {
                //move left or right
                if (move < 8) {
                    //check if figure is between start & end
                    //left (←)
                    if ((end < start) & ((move) > 1)) {
                        for (int j = 1; j <= (move-1); j++) {
                            //figure is present between start & end
                            if (board[start - j] != 0)
                                return false;
                        }
                    }
                    //right (→)
                    if ((end > start) & ((move) > 1)) {
                        for (int j = 1; j <= (move-1); j++) {
                            //figure is present between start & end
                            if (board[start + j] != 0)
                                return false;
                        }
                    }
                    return true;
                }
                //move up (↑) or down (↓)
                if (move % 8 == 0) {
                    //check if figure is between start & end
                    //up (↑)
                    if ((end < start) & ((move / 8) > 1)) {
                        for (int j = 1; j <= (move / 8 - 1); j++) {
                            //figure is present between start & end
                            if (board[start - j*8] != 0)
                                return false;
                        }
                    }
                    //down (↓)
                    if ((end > start) & ((move / 8) > 1)) {
                        for (int j = 1; j <= (move / 8 - 1); j++) {
                            //figure is present between start & end
                            if (board[start + j*8] != 0)
                                return false;
                        }
                    }
                    return true;
                }
            }
            break;
        //bishop
        case 3:
            if (current_figure_color != diagonal_figure_color) {
                //left-up (←↑) or left-down (←↓)
                if (move % 9 == 0) {
                    //check if figure is between start & end
                    //left-up (←↑)
                    if ((end < start) & ((move / 9) > 1)) {
                        for (int j = 1; j <= (move / 9 - 1); j++) {
                            //figure is present between start & end
                            if (board[start - j*9] != 0)
                                return false;
                        }
                    }
                    //left-down (←↓)
                    if ((end > start) & ((move / 9) > 1)) {
                        for (int j = 1; j <= (move / 9 - 1); j++) {
                            //figure is present between start & end
                            if (board[start + j*9] != 0)
                                return false;
                        }
                    }
                    return true;
                }
                //right-up (↑→) or right-down (↓→)
                if (move % 7 == 0) {
                    //check if figure is between start & end
                    //right-up (↑→)
                    if ((end < start) & ((move / 7) > 1)) {
                        for (int j = 1; j <= (move / 7 - 1); j++) {
                            //figure is present between start & end
                            if (board[start - j*7] != 0)
                                return false;
                        }
                    }
                    //right-down (↓→)
                    if ((end > start) & ((move / 7) > 1)) {
                        for (int j = 1; j <= (move / 7 - 1); j++) {
                            //figure is present between start & end
                            if (board[start + j*7] != 0)
                                return false;
                        }
                    }
                    return true;
                }
            }
            break;
        //queen
        case 5:
            if (current_figure_color != diagonal_figure_color) {
                //rook side

                //move left or right
                if ( (move < 8) & ( (start / 8) == (end / 8) ) ) {
                    //check if figure is between start & end
                    //left (←)
                    if ((end < start) & ((move) > 1)) {
                        for (int j = 1; j <= (move-1); j++) {
                            //figure is present between start & end
                            if (board[start - j] != 0)
                                return false;
                        }
                    }
                    //right (→)
                    if ((end > start) & ((move) > 1)) {
                        for (int j = 1; j <= (move-1); j++) {
                            //figure is present between start & end
                            if (board[start + j] != 0)
                                return false;
                        }
                    }
                    return true;
                }
                //move up (↑) or down (↓)
                if (move % 8 == 0) {
                    //check if figure is between start & end
                    //up (↑)
                    if ((end < start) & ((move / 8) > 1)) {
                        for (int j = 1; j <= (move / 8 - 1); j++) {
                            //figure is present between start & end
                            if (board[start - j*8] != 0)
                                return false;
                        }
                    }
                    //down (↓)
                    if ((end > start) & ((move / 8) > 1)) {
                        for (int j = 1; j <= (move / 8 - 1); j++) {
                            //figure is present between start & end
                            if (board[start + j*8] != 0)
                                return false;
                        }
                    }
                    return true;
                }

                //bishop side

                //left-up (←↑) or left-down (←↓)
                if (move % 9 == 0) {
                    //check if figure is between start & end
                    //left-up (←↑)
                    if ((end < start) & ((move / 9) > 1)) {
                        for (int j = 1; j <= (move / 9 - 1); j++) {
                            //figure is present between start & end
                            if (board[start - j*9] != 0)
                                return false;
                        }
                    }
                    //left-down (←↓)
                    if ((end > start) & ((move / 9) > 1)) {
                        for (int j = 1; j <= (move / 9 - 1); j++) {
                            //figure is present between start & end
                            if (board[start + j*9] != 0)
                                return false;
                        }
                    }
                    return true;
                }

                //right-up (↑→) or right-down (↓→)
                if (move % 7 == 0) {
                    //check if figure is between start & end
                    //right-up (↑→)
                    if ((end < start) & ((move / 7) > 1)) {
                        for (int j = 1; j <= (move / 7 - 1); j++) {
                            //figure is present between start & end
                            if (board[start - j*7] != 0)
                                return false;
                        }
                    }
                    //right-down (↓→)
                    if ((end > start) & ((move / 7) > 1)) {
                        for (int j = 1; j <= (move / 7 - 1); j++) {
                            //figure is present between start & end
                            if (board[start + j*7] != 0)
                                return false;
                        }
                    }
                    return true;
                }
            }
            break;
        case 2:
            if (current_figure_color != diagonal_figure_color) {
                if ((move == 15) || (move == 17) || ( (move == 6) & ((start / 8) != (end / 8)) ) || (move == 10)  )
                    return true;
            }
            break;
        case 6:
            if (current_figure_color != diagonal_figure_color) {
                if ((move == 1) || (move == 8) || ( (move == 7) & ((start / 8) != (end / 8)) ) || (move == 9))
                    return true;
            }
        }




        return false;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        x = e.getX() / 40;
        if (x < 0 )
            x = 0;
        if (x > 7 )
            x = 7;

        y = e.getY() / 40;
        if (y < 0)
            y = 0;
        if (y > 7 )
            y = 7;

        end = y*8 + x;

        if ( end != alt)
        {
            //rebuild old field
            if	(alt != start)
                paintField(alt, g);

            if ( end != start)
            {	//mark new field
                if ( canStep(start) && (isvalid (Math.abs(start - end)) ))
                    g.setColor (green);
                else
                    g.setColor (red);

                g.fillRect (x * 40, y * 40, 40, 40);
                try {
                    if(board[end] != 0)
                        g.drawImage (images [board [end]], x * 40, y * 40, 40, 40, this);
                } catch (ArrayIndexOutOfBoundsException ex) {}
            }

            alt = end;
        }
    }

    public boolean canStep(int index) {
        if  (current_step == figureColor(index))
            return true;
        else return false;
    }
    public ChessColor figureColor(int index) {
        if (board[index] / 10 == 1)
            return ChessColor.BLACK;
        if ((board[index] / 10 == 0) & (board[index] % 10 != 0))
            return ChessColor.WHITE;
        return null;
    }

    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        Graphics g = getGraphics();
        x = e.getX() / 40;
        if (x < 0)
            x = 0;
        if (x > 7)
            x = 7;

        y = e.getY() / 40;
        if (y < 0)
            y = 0;
        if (y > 7)
            y = 7;

        start = y*8 + x;
        current_figure_color = figureColor(start);
        alt = start;
        end = start;

        //mark start field
        g.setColor (blue);
        g.fillRect (x * 40, y * 40, 40, 40);
        try {
            if(board[start] != 0)
                g.drawImage (images [board [start]], x * 40, y * 40, 40, 40, this);
        } catch (ArrayIndexOutOfBoundsException ex) {}
    }

    public void mouseReleased(MouseEvent e) {
        //erase marks
        Graphics g = getGraphics();
        paintField (start, g);
        paintField (end, g);
        //execute move if valid
        if (canStep(start) && (isvalid (Math.abs(start - end)) ))
            execute (start, end);
    }

    //execute a move
    public void execute (int start, int end)
    {
        Graphics g = getGraphics();
        board [end] = board [start];
        board [start] = 0;
        paintField (end, g);
        paintField (start, g);
        changeStep();
    }
    public void changeStep() {
        if (current_step == ChessColor.BLACK)
            current_step = ChessColor.WHITE;
        else
            current_step = ChessColor.BLACK;
    }

    public void newgame ()
    {
        //load start position
        int [] org = {
                14, 12,	13,	15,	16, 13,	12,	14,
                11,	11,	11,	11,	11,	11,	11,	11,
                0,  0,  0,  0,  0,  0,  0,  0,
                0,  0,  0,  0,  0,  0,  0,  0,
                0,  0,  0,  0,  0,  0,  0,  0,
                0,  0,  0,  0,  0,  0,  0,  0,
                1,	1,	1,	1,	1,	1,	1,	1,
                4,  2,	3,	5,	6,  3,	2,	4 };

        for (int i=0; i < 64; i++)
            board [i] = org [i];

        //repaint ();
    }

    /*public void update(Graphics g){
        paint(g);
    }*/

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for ( int i = 0; i < 64; i++)
        {
            paintField (i, g);
        }
    }

    public void paintField (int index, Graphics g)
    {
        int x = index % 8;
        int y = index / 8;
        if ((x + y)%2 == 0)
            g.setColor(hell);
        else g.setColor(dunkel);
        g.fillRect(x * 40, y * 40, 40,40);

        //paint image
        try {
            if(board[index] != 0)
                g.drawImage (images [board [index]], x * 40, y * 40, 40, 40, this);
        } catch (ArrayIndexOutOfBoundsException e) {}
    }

    public Image getImage(String filename)
    {
        Image im = Toolkit.getDefaultToolkit().getImage(filename);
        //repaint();
        return im;
    }
    public void run() {
    }
}
