package clueGame;

import java.io.*;
import java.util.*;

public class Board {
    private final Set<BoardCell> targets = new HashSet<>();
    private int numRows;
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

            if (adjacentCell.getIsRoom() || remainingSteps == 1) {
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
                if(line.contains("//")) continue;

                String[] parts = line.split(", ");
                Room room = new Room(parts[1]);
                roomMap.put(parts[2].charAt(0),room);
            }
            sc.close();
        }
    }
    public void setGrid() throws FileNotFoundException, BadConfigFormatException {
        numRows=0;
        int tempColumn=-1;
        try(Scanner sc = new Scanner(new File(layoutConfigFile))){
            while(sc.hasNextLine()){
                String[] parts = sc.nextLine().split(",");
                numRows+=1;

                if(tempColumn == -1){
                    tempColumn = parts.length;
                }else if(parts.length != tempColumn){
                    throw new BadConfigFormatException();
                }
                numColumns=tempColumn;
            }
            sc.close();
            grid=new BoardCell[numRows][numColumns];
        } catch (BadConfigFormatException e) {
            throw new BadConfigFormatException();
        }
    }
    public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException{
        setGrid();
        int tempRow=0;
        try(Scanner sc = new Scanner(new File(layoutConfigFile))){
            while(sc.hasNextLine()){
                String[] cells = sc.nextLine().split(",");
                for(int i=0;i<numColumns;i++){
                    BoardCell cell = new BoardCell(tempRow,i);
                    cell.setInitial(cells[i].charAt(0));
                    cell.setRoom(roomMap.get(cells[i].charAt(0)));
                    if(!roomMap.containsKey(cells[i].charAt(0))){
                        throw new BadConfigFormatException();
                    }
                    if(cells[i].length()>=2){
                        switch (cells[i].charAt(1)){
                            case '*'-> {
                                cell.setRoomCenter(true);
                                cell.getRoom().setCenterCell(cell);
                            }
                            case '#'-> {
                                cell.setRoomLabel(true);
                                cell.getRoom().setLabelCell(cell);
                            }
                            case '^'-> cell.setDoorDirection(DoorDirection.UP);
                            case '<'-> cell.setDoorDirection(DoorDirection.LEFT);
                            case 'v'-> cell.setDoorDirection(DoorDirection.DOWN);
                            case '>'-> cell.setDoorDirection(DoorDirection.RIGHT);
                            default -> cell.setSecretPassage(cells[i].charAt(1));
                        }
                    }
                    grid[tempRow][i]=cell;
                }
                tempRow+=1;
            }
            sc.close();
        } catch (BadConfigFormatException e){
            throw new BadConfigFormatException();
        }

    }
    public void initialize() throws FileNotFoundException, BadConfigFormatException {
            loadSetupConfig();
            loadLayoutConfig();
    }
    public Room getRoom(char room){
        return roomMap.get(room);
    }
    public Room getRoom(BoardCell cell){
        return cell.getRoom();
    }
}
