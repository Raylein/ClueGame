package Experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
    //Make a TestBoard
    public TestBoard(){}
    //Calculate targets
    public void calcTargets(TestBoardCell startCell, int pathlength){}
    //Get the current cell
    public TestBoardCell getCell(int row, int col){
        TestBoardCell testCell = new TestBoardCell(row,col);
        return testCell;
    }
    //Get all the targets from calcTargets();
    public Set<TestBoardCell> getTargets(){
        Set<TestBoardCell> HashSet =new HashSet<TestBoardCell>();
        return HashSet;
    }
}
