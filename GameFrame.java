import slither.CurrentState;

import javax.swing.*;
import java.io.IOException;
import java.util.Stack;

public class GameFrame extends JFrame {


    //constructor
    GameFrame(CurrentState currentState, Stack<Character> actionsCharStack, int treshold, int unitSize, int height, int width) throws IOException {

        //creating the window
            this.add(new GamePanel(currentState,actionsCharStack,treshold,unitSize,height,width));
            this.setTitle("Snake");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
         //to make window appear at the center of the screen
            this.setLocationRelativeTo(null);


    }

}
