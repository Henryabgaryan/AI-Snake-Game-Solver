import gameObjects.Snake;
import slither.Board;
import slither.Cell;
import slither.CurrentState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import static javax.imageio.ImageIO.read;


public class GamePanel extends JPanel implements ActionListener {

    //dimensions of the screen
    static  int SCREEN_WIDTH = 600;
    static  int SCREEN_HEIGHT = 600;

    //the size of the objects in the game
    static  int UNIT_SIZE = 150;

    //how many objects we can fit in the screen
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;

    //the speed of the game
    static final int DELAY = 720;
    // Arrays to keep the body parts of the snake
    //at most snake can be the size of the whole board


    int threshold;

    // array x will keep track of X coordinate of the snake body part
     int snakeXarray[] = new int[GAME_UNITS];

    // array y will keep track of Y coordinate of the snake body part
     int snakeYarray[] = new int[GAME_UNITS];


    ArrayList<Cell> fruitCoordinatesArrayList = new ArrayList<Cell>();

    //setting up initial body parts (number of squares that the body of snake will consist of)
    int bodyParts = 2;


    //number of already eaten apples, initially 0
    int applesEaten = 0;

    //number of already eaten fruits, initially 0
    int fruitsEaten = 0;

    //location of the apple
    int appleX;
    int appleY;

    Stack<Character> actionsCharStackForSnakeMove;

    //direction of where the snake is going
    /*
      R - Right
      L - Left
      U - Up
      D - Down
     */
    char direction = 'R';

    //creates snake object
    Snake snake;

    //shows if game is running or not
    boolean running = true;

    CurrentState state;

    Timer timer;
    Random random;



    //background image input
    File background = new File("background.jpg");
    BufferedImage backgroundImage = read(background);


    //constructor
    GamePanel(CurrentState initialState, Stack<Character> actionsCharStack, int threshold1,int unitSize, int height, int width) throws IOException {

        snake = initialState.getSNAKE();
        Board board = initialState.getCURRENT_BOARD();
        state = initialState;
        actionsCharStackForSnakeMove = actionsCharStack;
        threshold = threshold1;
        UNIT_SIZE = unitSize;
        SCREEN_HEIGHT = height;
        SCREEN_WIDTH = width;
/*
        for (int i = 0; i < actionsCharStackForSnakeMove.size(); i++) {
            System.out.println(actionsCharStack.pop());
        }*/

        //puts snake coordinates in the generic array
        for (int i = 0; i < snake.getBodyParts(); i++) {
            snakeXarray[i] = UNIT_SIZE * snake.getX()[i];
            snakeYarray[i] = UNIT_SIZE * snake.getY()[i];
        }

        //puts fruit coordinates in the generic array
        for (int i = 0; i < board.getFruitCells().size(); i++) {
            Cell fruitCell = new Cell(board.getFruiCells().get(i).getX()*UNIT_SIZE,board.getFruiCells().get(i).getY()*UNIT_SIZE);
            fruitCoordinatesArrayList.add(i,fruitCell);
        }


        random = new Random();

        //creates window of specified size
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));


        //setting up background color, currently BLACK
        //  this.setBackground(Color.BLACK);

        /*
        setFocusable() description

        Let's say you have implemented a dialog with several text fields and you want4
        the user to enter some text. When the user starts typing, one text field needs
        to have the focus of the application: it will be the field that receives the
        keyboard input.
        When you implement a focus traversal (a convenient way for the user to jump from
        one text field to the next, for example by using the tab button), the user can
        "jump" to the next text field. The application will try to gain the focus for the next
        field to prepare it to receive text. When the next field is not focusable, this request
        will be denied and the next field will be tested. For example, you wouldn't want a label
        to get the focus because you cannot enter text into it.
         */
        this.setFocusable(true);

        /*

        MyKeyAdapter()
        Report all regular key presses and the four arrow keys
        Ignore other key pressed events, key released events, special keys, etc.


         */
      //  this.addKeyListener(new MyKeyAdapter());

        startGame();

    }

    //specifies how to start the game
    public void startGame(){

        running = true;

        //create time object ?????????????????????
        timer = new Timer(DELAY,this);
        timer.start();



    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        //draws the background
        int iWidth2 = backgroundImage.getWidth(this)/2;
        int iHeight2 = backgroundImage.getHeight(this)/2;

        if (backgroundImage != null)
        {
            int x = this.getParent().getWidth()/2 - iWidth2;
            int y = this.getParent().getHeight()/2 - iHeight2;
            g.drawImage(backgroundImage,x,y,this);
        }


        try {
            draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //creating grid on the gameBoard
    public void createGrid(Graphics g){

        for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {

            // draws vertical lines
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);

            // draws horizontal lines
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

        }
    }


    public void drawSnakeBody(Graphics g) throws IOException {

        int snakeHeadSize = (int)(UNIT_SIZE + 0.8*UNIT_SIZE);


        int snakeHeadLocationShift = (int)(UNIT_SIZE/2 - 0.1*UNIT_SIZE);
        int snakeLeftandUpLocationShift = (int)(snakeHeadLocationShift + 0.4*UNIT_SIZE);



        File headUp = new File("SnakeHeadUp.png");
        BufferedImage upHeadImage = read(headUp);

        File headDown = new File("SnakeHeadDown.png");
        BufferedImage downHeadImage = read(headDown);

        File headLeft = new File("SnakeHeadLeft.png");
        BufferedImage leftHeadImage = read(headLeft);

        File headRight = new File("SnakeHeadRight.png");
        BufferedImage rightHeadImage = read(headRight);



        //getting the square image of the body
        File body = new File("body.png");
        BufferedImage bodyImage = read(body);


        for (int i = 0; i < bodyParts; i++) {


            //drawing the head of the snake
            if(i == 0){


                //for Up direction
                //for Down direction
                //for Left direction
                //for Right direction
                switch (direction) {
                    case 'U' -> g.drawImage(upHeadImage, snakeXarray[i] - snakeHeadLocationShift, snakeYarray[i] - snakeLeftandUpLocationShift, snakeHeadSize, snakeHeadSize, this);
                    case 'D' -> g.drawImage(downHeadImage, snakeXarray[i] - snakeHeadLocationShift, snakeYarray[i], snakeHeadSize, snakeHeadSize, this);
                    case 'L' -> g.drawImage(leftHeadImage, snakeXarray[i] - snakeLeftandUpLocationShift, snakeYarray[i] - snakeHeadLocationShift, snakeHeadSize, snakeHeadSize, this);
                    case 'R' -> g.drawImage(rightHeadImage, snakeXarray[i], snakeYarray[i] - snakeHeadLocationShift, snakeHeadSize, snakeHeadSize, this);
                }

               /* g.setColor(Color.cyan);
                g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);*/


                //drawing the rest of the body of the snake
            }else {

                g.drawImage(bodyImage,snakeXarray[i],snakeYarray[i],UNIT_SIZE,UNIT_SIZE,this);
            }

        }

        if(upHeadImage == null || downHeadImage == null || leftHeadImage == null
                || rightHeadImage == null|| bodyImage == null ) {
            throw new IOException("No Image File Found");
        }

    }




    public void drawFruit(Graphics g) throws IOException {

        File apple = new File("apple.png");
        BufferedImage appleImage = read(apple);

        File pear = new File("pear.png");
        BufferedImage pearImage = read(pear);

        File pinapple = new File("pinapple.png");
        BufferedImage pinappleImage = read(pinapple);


        BufferedImage fruitImage;

        for (int i = 0; i < fruitCoordinatesArrayList.size(); i++) {
            if(i == fruitCoordinatesArrayList.size()-1){
                g.drawImage(pearImage, fruitCoordinatesArrayList.get(i).getX(), fruitCoordinatesArrayList.get(i).getY(), UNIT_SIZE, UNIT_SIZE, this);

            }else {
                g.drawImage(appleImage, fruitCoordinatesArrayList.get(i).getX(), fruitCoordinatesArrayList.get(i).getY(), UNIT_SIZE, UNIT_SIZE, this);
            }
        }


    }


    public void draw(Graphics g) throws IOException {

        if(running) {

            //creates grid on the board
            createGrid(g);

            //draws apple at random locations
            //drawApple(g);

            //draws fruits
            drawFruit(g);



            //draws snake body
            drawSnakeBody(g);


            // show the score
            //set score text
            g.setColor(Color.yellow);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            int xPositionForText = (SCREEN_WIDTH - metrics.stringWidth("Score: " + fruitsEaten))/2;
            int yPositionForText = g.getFont().getSize();

            //draws Score text at the upper part of the screen
          //  g.drawString("Score: " + fruitsEaten,xPositionForText,yPositionForText);
            g.drawString("Threshold: " + threshold,xPositionForText-30,yPositionForText+40);




        }else {
            gameOver(g);
        }


    }



    public void move(){

        //  System.out.println(snake.getX()[0] + " " + snake.getY()[0]);
            for (int i = bodyParts; i > 0; i--) {
                snakeXarray[i] = snakeXarray[i - 1];
                snakeYarray[i] = snakeYarray[i - 1];
            }

            //for Up direction
            //for Down direction
            //for Left direction
            //for Right direction
            switch (direction) {
                case 'U' -> snakeYarray[0] = snakeYarray[0] - UNIT_SIZE;
                case 'D' -> snakeYarray[0] = snakeYarray[0] + UNIT_SIZE;
                case 'L' -> snakeXarray[0] = snakeXarray[0] - UNIT_SIZE;
                case 'R' -> snakeXarray[0] = snakeXarray[0] + UNIT_SIZE;
            }


    }


    public void checkFruit(){


        for (int i = 0; i < fruitCoordinatesArrayList.size(); i++) {

          //  System.out.println("fruit X: " + fruitCoordinatesArrayList.get(i).getX() + "fruit Y: " + fruitCoordinatesArrayList.get(i).getY() );


            //if snake head is at the same location as apple
            if((snakeXarray[0] == fruitCoordinatesArrayList.get(i).getX()) && (snakeYarray[0] == fruitCoordinatesArrayList.get(i).getY())){

                //make snake bigger
                bodyParts++;

                fruitsEaten+=5;//fruitCoordinatesArrayList.get(i).getFruitValue();

                //remove eaten fruit
                fruitCoordinatesArrayList.remove(i);

            }


        }



    }


    public void checkCollisions(){


        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {

            if((snakeXarray[0] == snakeXarray[i]) && (snakeYarray[0] == snakeYarray[i])){
                running = false;
            }

        }

        //checks if head touches left border
        if(snakeXarray[0] < 0){
            running = false;
        }

        //checks if head touches right border
        if(snakeXarray[0] > SCREEN_WIDTH){
            running = false;
        }

        //checks if head touches top border
        if(snakeYarray[0] < 0){
            running = false;
        }

        //checks if head touches top border
        if(snakeYarray[0] > SCREEN_HEIGHT){
            running = false;
        }

        //stops the timer
        if(!running){
            timer.stop();
        }

    }

    public void gameOver(Graphics g){

        //set gameover text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        int xPositionForGameOverText = (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2;
        int yPositionForGameOverText = (SCREEN_HEIGHT)/2;

        //draws Game Over text at the center of the screen
        g.drawString("Game Over",xPositionForGameOverText,yPositionForGameOverText);




        // show the score
        //set score text
        g.setColor(Color.yellow);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        int xPositionForScoreText = xPositionForGameOverText+150;
        int yPositionForScoreText = yPositionForGameOverText+50;

        //draws Score text at the upper part of the screen


        g.drawString("Score: " + fruitsEaten,xPositionForScoreText,yPositionForScoreText);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (running && (!actionsCharStackForSnakeMove.isEmpty())) {
            direction = actionsCharStackForSnakeMove.pop();
            System.out.println(direction);

                //moves the snake
                move();

                //checks if snake eats the apple
                checkFruit();

                //checks if snake hit the wall
                checkCollisions();


        }
        repaint();
    }


}
