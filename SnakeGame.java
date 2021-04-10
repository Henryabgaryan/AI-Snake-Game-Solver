import gameObjects.Apple;
import gameObjects.GameObject;
import gameObjects.Pear;
import gameObjects.Snake;
import search.*;
import slither.Board;
import slither.Cell;
import slither.CurrentState;
import slither.SnakeGameGoalTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class SnakeGame {

    public static Stack<Character> returnActionsForUI(Node finalNode){

        Node currentNode = finalNode;
        Stack<Character> actionStack = new Stack<>();
        CurrentState finalState = (CurrentState)finalNode.state ;
        actionStack.push(finalState.getSNAKE().getDirection());

        while (currentNode.parent.parent != null){
            currentNode = currentNode.parent;
            CurrentState currentState = (CurrentState)currentNode.state ;
            actionStack.add(currentState.getSNAKE().getDirection());
        }
            return actionStack;
    }

    public static void main(String[] args) throws IOException {



        Apple apple1 = new Apple(4,10);
        Apple apple2 = new Apple(4,10);
        Apple apple3 = new Apple(4,10);
        Apple apple4 = new Apple(4,10);

        Pear pear1 = new Pear(5,10);

//
//        GameObject appleObject1 = new GameObject();
//        appleObject1.setFRUIT(apple1);
//        GameObject appleObject2 = new GameObject();
//        appleObject2.setFRUIT(apple2);
//        GameObject appleObject3 = new GameObject();
//        appleObject3.setFRUIT(apple3);
//        GameObject appleObject4 = new GameObject();
//        appleObject4.setFRUIT(apple4);
//
//        GameObject pearObject5 = new GameObject();
//        appleObject4.setFRUIT(pear1);
//
//        Cell c1 = new Cell(0,0);
//        Cell c2 = new Cell(1,0,appleObject4);
//        Cell c3 = new Cell(2,0);
//        Cell c4 = new Cell(3,0);
//        Cell c5 = new Cell(0,1);
//        Cell c6 = new Cell(1,1);
//        Cell c7 = new Cell(2,1, pearObject5);
//        Cell c8 = new Cell(3,1);
//        Cell c9 = new Cell(0,2);
//        Cell c10 = new Cell(1,2);
//        Cell c11 = new Cell(2,2,appleObject2);
//        Cell c12 = new Cell(3,2);
//        Cell c13 = new Cell(0,3);
//        Cell c14 = new Cell(1,3);
//        Cell c15 = new Cell(2,3);
//        Cell c16 = new Cell(3,3,appleObject4);
//
//
//        Board board = new Board(4,4);
//        ArrayList<Cell> cellArrayList = new ArrayList<>();
//        cellArrayList.add(c1);
//        cellArrayList.add(c2);
//        cellArrayList.add(c3);
//        cellArrayList.add(c4);
//        cellArrayList.add(c5);
//        cellArrayList.add(c6);
//        cellArrayList.add(c7);
//        cellArrayList.add(c8);
//        cellArrayList.add(c9);
//        cellArrayList.add(c10);
//        cellArrayList.add(c11);
//        cellArrayList.add(c12);
//        cellArrayList.add(c13);
//        cellArrayList.add(c14);
//        cellArrayList.add(c15);
//        cellArrayList.add(c16);
//
//        board.setBoard(cellArrayList);
//        board.addNeighbour();
//        int SCREEN_WIDTH    = 600;
//        int SCREEN_HEIGHT   = 600;
//        int UNIT_SIZE       = 150;




        int height = 10;
        int width = 10;
        int maxFruitNumber = height*width/10, counter = 0;
        Board board = new Board(height,width);


        ArrayList<Cell> cellArrayList = new ArrayList<>();
        Random rd = new Random();
        float bias = 0.2f;

        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
                Cell tempCell;
                if((rd.nextFloat() < bias) && counter++ <= maxFruitNumber){
                    GameObject tempObj = new GameObject();
                    if(rd.nextBoolean()){
                        Apple tempApple = new Apple(4,10);
                        tempObj.setFRUIT(tempApple);
                    } else{
                        Pear tempPear = new Pear(5, 8);
                        tempObj.setFRUIT(tempPear);
                    }

                    tempCell = new Cell(j, i, tempObj);
                } else{
                    tempCell = new Cell(j,i);
                }

                cellArrayList.add(tempCell);
            }
        }


        board.setBoard(cellArrayList);
        board.addNeighbour();
        int SCREEN_WIDTH    = 600;
        int SCREEN_HEIGHT   = 600;
        int UNIT_SIZE       = 60;


        int x[] = new int[SCREEN_WIDTH*SCREEN_HEIGHT/UNIT_SIZE];
        int y[] = new int[SCREEN_WIDTH*SCREEN_HEIGHT/UNIT_SIZE];

      //  ArrayList<Cell> fruitCell = board.getFruitCells();

/*
        for (int i = 0; i < fruitCell.size() ; i++) {
            System.out.println("fruit X: " + fruitCell.get(i).getX());
            System.out.println("fruit Y: " + fruitCell.get(i).getY());


        }
*/




        Snake snake = new Snake(x,y,1,'R');
        CurrentState currentState =  new CurrentState(board,snake);

        int threshold = 13;

        SnakeGameGoalTest goal = new SnakeGameGoalTest(threshold);


        // heuristic withManhattan Distance/( 0.2*|fruitValue - min(fruitValue)| + 1)
        AStarHeuristicWithDivisionByFruitValue aStarHeuristicWithDivisionByFruitValue = new AStarHeuristicWithDivisionByFruitValue();

        //greedy heuristic with the closest fruit distance(Manhattan)
        GreedyHeuristicShortestFruitDistance greedyHeuristicShortestFruitDistance = new GreedyHeuristicShortestFruitDistance();

        //greedy heuristic with the most valuable fruit
        GreedyHeuristicMostValuableFruit greedyHeuristicMostValuableFruit = new GreedyHeuristicMostValuableFruit();

        AStarFunction aStarFunction = new AStarFunction(aStarHeuristicWithDivisionByFruitValue);


        Frontier BestFirstFrontierForAStar = new BestFirstFrontier(aStarFunction);
        TreeSearch treeSearchForAStar = new TreeSearch(BestFirstFrontierForAStar);

        Node nodeForAStarTreeSearch = treeSearchForAStar.solution(currentState,goal);

        //System.out.println("The Final Output Node data:" + nodeForAStarTreeSearch.);
        System.out.println("fsyo, nice of you");
        // snake.goUp();

        //  snake.goRight();
      // snake.goLeft();

//        snake.goDown();
//        System.out.println(snake.getDirection());;


        CurrentState currentStateForUI = new CurrentState(currentState.getCURRENT_BOARD().makeBoardCopy(), currentState.getSNAKE().makeCopySnake(), currentState.getCURRENT_SCORE());

        new GameFrame(currentStateForUI,returnActionsForUI(nodeForAStarTreeSearch),threshold,UNIT_SIZE,SCREEN_HEIGHT,SCREEN_WIDTH);

        Stack<Character> c = returnActionsForUI(nodeForAStarTreeSearch);
       /* while (!c.isEmpty()) {
            System.out.println(c.pop());
        }*/



    }


}
