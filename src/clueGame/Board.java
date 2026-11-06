package clueGame;

import java.io.*;
import java.util.*;

public class Board {
    private final Set<BoardCell> targets = new HashSet<>();
    private int numRows,numColumns;
    private BoardCell[][] grid;
    private String layoutConfigFile, setupConfigFile;
    private Map<Character,Room> roomMap=new HashMap<>();
    private final Set<Character> roomInitials = new HashSet<>();
    private Card[] availableDeck,peopleCards,weaponCards,roomCards;
    private Set<Player> nonPlayerCharacters=new HashSet<>();
    private Player playerCharacter;

//Start of singleton pattern
    private static Board theInstance;
    private Board() {
        super();
    }

    public static Board getInstance(){
        if(theInstance==null){
            theInstance=new Board();
        }
        return theInstance;
    }

    public void initialize() throws FileNotFoundException, BadConfigFormatException {
        loadSetupConfig();
        loadLayoutConfig();
        calcAdjacencies();

    }
//end of singleton pattern

    public void calcTargets(BoardCell startCell, int pathlength) {
        if (pathlength < 0) {
            throw new IllegalArgumentException("Path length cannot be negative");
        }
        targets.clear();
        findTargets(startCell, pathlength, new HashSet<>(Set.of(startCell)));
    }
    //Finds what type of room it is and if it occupied
    private void findTargets(BoardCell currentCell, int remainingSteps,
                             Set<BoardCell> visitedCells) {
        if (remainingSteps == 0) {
            targets.add(currentCell);
            return;
        }

        for (BoardCell adjacentCell : currentCell.getAdjList()) {
            if (visitedCells.contains(adjacentCell)) {
                continue;
            }

            if (adjacentCell.getIsRoom()) {
                targets.add(adjacentCell);
                continue;
            }

            if(adjacentCell.getOccupied()){
                continue;
            }

            if (remainingSteps == 1){
                targets.add(adjacentCell);
                continue;
            }

            visitedCells.add(adjacentCell);
            findTargets(adjacentCell, remainingSteps - 1, visitedCells);
            visitedCells.remove(adjacentCell);
        }
    }

    //Adjacency function needed to be used for the tests
    private void calcAdjacencies() {
        Map<Character, BoardCell> centers = new HashMap<>();
        for (BoardCell[] row : grid) {
            for (BoardCell cell : row) {
                if (cell.isRoomCenter()) {
                    centers.put(cell.getInitial(), cell);
                }
            }
        }
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                BoardCell cell = grid[r][c];
                char initial = cell.getInitial();

                if (initial == 'W') {

                    for (int[] d : dirs) {
                        int nr = r + d[0], nc = c + d[1];
                        if (nr >= 0 && nr < numRows && nc >= 0 && nc < numColumns
                                && grid[nr][nc].getInitial() == 'W') {
                            cell.addAdjacency(grid[nr][nc]);
                        }
                    }

                    if (cell.isDoorway()) {
                        int nr = r, nc = c;
                        switch (cell.getDoorDirection()) {
                            case UP -> nr--;
                            case DOWN -> nr++;
                            case LEFT -> nc--;
                            case RIGHT -> nc++;
                            default -> { }
                        }
                        if (nr >= 0 && nr < numRows && nc >= 0 && nc < numColumns) {
                            BoardCell center = centers.get(grid[nr][nc].getInitial());
                            if (center != null) {
                                cell.addAdjacency(center);
                                center.addAdjacency(cell);
                            }
                        }
                    }
                } else if (cell.getSecretPassage() != '\0' && roomInitials.contains(initial)) {
                    BoardCell from = centers.get(initial);
                    BoardCell to = centers.get(cell.getSecretPassage());
                    if (from != null && to != null) {
                        from.addAdjacency(to);
                    }
                }
            }
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


    //Sets up the config
    public void setConfigFiles(String layoutConfigFile,String setupConfigFile){
        this.layoutConfigFile=layoutConfigFile;
        this.setupConfigFile=setupConfigFile;
    }

    //loads up the config
    public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
        availableDeck = new Card[21];
        roomCards = new Card[10];
        peopleCards = new Card[6];
        weaponCards = new Card[5];
        roomMap.clear();
        roomInitials.clear();
        try(Scanner sc = new Scanner(new File(setupConfigFile))){
            int peopleInt=0;
            int weaponsInt=0;
            int roomInt=0;
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.isEmpty()) continue;
                if(line.contains("//")) continue;
                String[] parts = line.split(", ");
                if((!parts[0].equalsIgnoreCase("Room"))&&(!parts[0].equalsIgnoreCase("Space"))&&
                        (!parts[0].equalsIgnoreCase("Character"))&&(!parts[0].equalsIgnoreCase("Weapon"))){
                    throw new BadConfigFormatException();
                }

                if(parts[0].equalsIgnoreCase("Room")||parts[0].equalsIgnoreCase("Space")) {
                    if(parts[0].equalsIgnoreCase(("Room"))){
                        roomCards[roomInt] = new Card(parts[1],CardType.ROOM);
                        roomInt+=1;
                    }
                    Room room = new Room(parts[1]);
                    roomMap.put(parts[2].charAt(0), room);
                    if (parts[0].equalsIgnoreCase("Room")) {
                        roomInitials.add(parts[2].charAt(0));
                    }
                }
                else if(parts[0].equalsIgnoreCase("Character")){
                    if (peopleInt==0){
                        playerCharacter = new HumanPlayer(parts[1],parts[2],0,0);
                    }
                    else{
                        ComputerPlayer computer = new ComputerPlayer(parts[1],parts[2],peopleInt,0);
                        nonPlayerCharacters.add(computer);
                    }
                    peopleCards[peopleInt]=new Card (parts[1], CardType.PERSON);
                    peopleInt +=1;
                }
                else if(parts[0].equalsIgnoreCase("Weapon")){
                    weaponCards[weaponsInt]=new Card(parts[1],CardType.WEAPON);
                    weaponsInt+=1;
                }
            }
            sc.close();
        } catch (BadConfigFormatException e) {
            throw new BadConfigFormatException();
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
                    cell.setIsRoom(roomInitials.contains(cells[i].charAt(0)));
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

    public Room getRoom(char room){
        return roomMap.get(room);
    }
    public Room getRoom(BoardCell cell){
        return cell.getRoom();
    }

    public Card[] getPeopleCards(){
        return peopleCards;
    }

    public Card[] getWeaponCards(){
        return weaponCards;
    }

    public Card[] getRoomCards(){
        return roomCards;
    }

    private void DeckSetup(){};
}
