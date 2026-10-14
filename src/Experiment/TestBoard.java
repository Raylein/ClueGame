package Experiment;

import java.util.Set;
import java.util.TreeSet;

public class TestBoard {
    //Make a TestBoard
    public TestBoard(){}
    //Calculate targets
    public void calcTargets(TestBoardCell startCell,int pathlength){}
    //Get the current cell
    public TestBoardCell getCell(int row, int col){
        TestBoardCell testCell = new TestBoardCell();
        return testCell;
    }
    //Get all the targets from calcTargets();
    public Set<TestBoardCell> getTargets(){
        Set<TestBoardCell> testSet=new TreeSet<TestBoardCell>();
        return testSet;
    }
}
