/**
 * Created by Evgeniy Gusar
 */

import javax.swing.*;
import java.awt.BorderLayout;

public class ChessFrame extends JFrame
{
    ChessPanel chessP = new ChessPanel();
    public ChessFrame()
    {

        this.setSize(335,358);/*@\label{setsize:line}@*/
        this.setLocation(500,200);/*@\label{setloc:line}@*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/*@\label{closeOp:line}@*/

        this.getContentPane().add(chessP, BorderLayout.SOUTH);

        /*ChessMouseListener cMouseAdpt = new ChessMouseListener();
        chessP.addMouseMotionListener(cMouseAdpt);
        chessP.addMouseListener(cMouseAdpt);*/

    }

    // Makes the frame visible.
    public void showIt(){
        this.setVisible(true);
    }

    // Makes the frame visible and sets the title text.
    public void showIt(String title){
        this.setTitle(title);
        this.setVisible(true);
    }
}