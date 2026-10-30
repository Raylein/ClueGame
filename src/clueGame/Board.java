package clueGame;

import java.io.*;
import java.util.*;

public class Board {
    private final Set<BoardCell> targets = new HashSet<>();
    private int numRows=0;
    private int numColumns;
    private BoardCell[][] grid;
    private String layoutConfigFile;
    private String setupConfigFile;
    private Map<Character,Room> roomMap=new HashMap<>();
    private static Board theInstance;


    public Board() {
        super();
    }

    public void calcTargets(BoardCell startCell, int pathlength) {
        if (pathlength < 0) {
            throw new IllegalArgumentException("Path length cannot be negative");
        }
        targets.clear();
        findTargets(startCell, pathlength, new HashSet<>(Set.of(startCell)));
    }

    private void findTargets(BoardCell currentCell, int remainingSteps,
                             Set<BoardCell> visitedCells) {
        if (remainingSteps == 0) {
            targets.add(currentCell);
            return;
        }

        for (BoardCell adjacentCell : currentCell.getAdjList()) {
            if (adjacentCell.getOccupied() || visitedCells.contains(adjacentCell)) {
                continue;
            }

            if (adjacentCell.getRoom() || remainingSteps == 1) {
                targets.add(adjacentCell);
                continue;
            }

            visitedCells.add(adjacentCell);
            findTargets(adjacentCell, remainingSteps - 1, visitedCells);
            visitedCells.remove(adjacentCell);
        }
    }

    public BoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public Set<BoardCell> getTargets() {
        return targets;
    }

    public int getNumRows(){return numRows;}
    public int getNumColumns(){return numColumns;}
    public Map<Character,Room> getRoomMap(){return roomMap;}

    public static Board getInstance(){
        if(theInstance==null){
            theInstance=new Board();
        }
        return theInstance;
    }

    public void setConfigFiles(String layoutConfigFile,String setupConfigFile){
        this.layoutConfigFile=layoutConfigFile;
        this.setupConfigFile=setupConfigFile;
    }

    public void loadSetupConfig() throws FileNotFoundException {
        try(Scanner sc = new Scanner(new File(setupConfigFile))){
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.isEmpty()) continue;
                if(line.contains("//")||line.contains("Space")) continue;

                String[] parts = line.split(", ");
                Room room = new Room(parts[2]);
                roomMap.put(parts[3].charAt(0),room);
            }
            sc.close();
        }
    }
    public void loadLayoutConfig() throws FileNotFoundException{
        try(Scanner sc = new Scanner(new File(layoutConfigFile))){
            int tempColumn=0;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.isEmpty()){throw new BadConfigFormatException("Bad Format");}
                String[] parts = line.split(",");
                numRows+=1;
                numColumns = parts.length;
                if(numRows>1 && numColumns!=tempColumn){
                    throw new BadConfigFormatException("Bad Format");
                }
                tempColumn=parts.length;

                for(int i=0;i<parts.length;i++){
                    BoardCell cell = new BoardCell(numRows,i);
                    if(parts[i].contains("*")){
                        cell.setRoomCenter(true);
                    }
                    if(parts[i].contains("#")){
                        cell.setRoomLabel(true);
                    }
                    switch(parts[i]){
                        case String s when s.contains("^")->{
                            cell.setDoorDirection("Up");
                        }
                        case String s when s.contains("v")->{
                            cell.setDoorDirection("Down");
                        }
                        case String s when s.contains("<")->{
                            cell.setDoorDirection("Left");
                        }
                        case String s when s.contains(">")-> {
                            cell.setDoorDirection("Right");
                        }
                        default -> {
                            cell.setDoorDirection("None");
                        }
                    }
                    grid[numRows-1][i]=cell;
                }
                
                
            }
        } catch (BadConfigFormatException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() throws FileNotFoundException {
        loadSetupConfig();
        loadLayoutConfig();
    }
}
