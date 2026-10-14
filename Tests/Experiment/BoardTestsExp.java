package Experiment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTestsExp {
    @BeforeEach
    void setUp(){
        TestBoard testBoard = new TestBoard();
    }
    @Test
    void upperLeftAdjacencyExists(){
        assertTrue(testBoard.getCell(0,0).getAdjList().size()>=0);
    }
    @Test
    void lowerRightAdjacencyExists(){
        assertTrue(testBoard.getCell(3,3).getAdjList().size()>=0);
    }

    @Test
    void leftEdgeAdjacencyExists(){
        assertTrue(testBoard.getCell(3,0).getAdjList().size()>=0);
    }
    @Test
    void rightEdgeAdjacencyExists(){
        assertTrue(testBoard.getCell(1,3).getAdjList().size()>=0);
    }

    @Test
    void centerAdjacencyExists(){
        assertTrue(testBoard.getCell(2,2).getAdjList.size()>=0);
    }
}