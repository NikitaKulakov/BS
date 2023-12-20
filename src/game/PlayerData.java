package game;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerData {
    private PlayerScreen player;
    private int[][] attackData = new int[11][11];
    private int[][] selfData = new int[11][11];
    private int numberOfShipSunk = 0;
    private ArrayList<Ship> fleet = new ArrayList<>();

    PlayerData( PlayerScreen player){
        this.player = player;

    }

    public void addShip(Coordinate a,Coordinate b,Coordinate c){

        if(isEdge(a, b, c)){
            System.out.print("\nPreventing adding a ship: fleet is full or user clicked too close to the edge.");
        }
        else {
            if (isOvelap(a, b, c) ) {
                if(isOvelapAtRotatePoint(a,b,c) && !isShipCollision(a,b,c)){
                    addVerticalShip(a, b, c);
                }
                else {
                    System.out.print("\nYou should only click first block to rotate.");
                }

            } else if(shipsLeft() < 5){
                fleet.add(new Ship(a, b, c));
                setSelfData(a, b, c);
            }
        }
    }

    public void deleteShip(Coordinate a, Coordinate b, Coordinate c) {
        Ship temp = new Ship(a, b, c);
        for (int i = 0; i < fleet.size(); i++) {
            if (fleet.get(i).compareShip(temp)) {
                fleet.remove(i);
            }
        }
    }

    public void deleteShipInSelfGrid(Coordinate a, Coordinate b, Coordinate c) {
        selfData[a.getX()][a.getY()]=0;
        selfData[b.getX()][b.getY()]=0;
        selfData[c.getX()][c.getY()]=0;
    }

    public void addShipInSelfGrid(Coordinate a, Coordinate b, Coordinate c) {
        selfData[a.getX()][a.getY()]=1;
        selfData[b.getX()][b.getY()]=1;
        selfData[c.getX()][c.getY()]=1;
    }

    public void addVerticalShip(Coordinate a, Coordinate b, Coordinate c){
        Coordinate aNew = new Coordinate(a.getX(), a.getY());
        Coordinate bNew = new Coordinate(a.getX(), a.getY() + 1);
        Coordinate cNew = new Coordinate(a.getX(), a.getY() + 2);
        fleet.add(new Ship(aNew, bNew, cNew));
        setSelfData(aNew, bNew, cNew);
    }

    public void attackShip(Coordinate hitCord) {
        Iterator itr = fleet.iterator();
        while (itr.hasNext()){
            Ship temp = (Ship)itr.next();
            temp.Hit(hitCord); // Hit the point in the ship
        }
    }

    public int shipsLeft(){
        int temp = fleet.size();
        return temp;
    }

    public void setAttackData(int x, int y, String result) {
        if(result.equals("Success")){
            attackData[x][y] = 1;
        }
        else if(result.equals("Failure")){
            attackData[x][y] = 2;
        }
    }


    public void setSelfData(Coordinate a, Coordinate b, Coordinate c){
        selfData[a.getX()][a.getY()]=1;
        selfData[b.getX()][b.getY()]=1;
        selfData[c.getX()][c.getY()]=1;
    }

    public int getNumberOfOwnShipSunk() {
        return numberOfShipSunk;
    }

    public ArrayList<Ship> getFleet(){
        return fleet;
    }

    public int[][] getSelfData(){
        return selfData;
    }
    public int[][] getAttackData(){
        return attackData;
    }

    public int getDataFromCell(int x, int y){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if(i == x && j ==y){
                    return selfData[i][j];
                }
            }
        }
        return -1;
    }

    public boolean isHit(Coordinate point){
        for (int i=0;i<fleet.size();){
            Ship temp = fleet.get(i);
            if(temp.isPointHit(point)){
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }

    public boolean isPlayerLost(){
        if(fleet.size()==0){
            return true; // Player lost
        }
        else{
            return false;
        }
    }

    public boolean isSunk(Coordinate hitCord){
        for (int i=0;i<fleet.size();){
            Ship temp = fleet.get(i);
            if(temp.isShipSunk()){
                numberOfShipSunk++;
                fleet.remove(i);
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }

    public boolean isOvelapAtRotatePoint(Coordinate a, Coordinate b, Coordinate c){
        boolean isA = false;
        boolean isB = false;
        boolean isC = false;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++)
            {
                if((a.getX()==i&&a.getY()==j)){
                    if(getDataFromCell(i,j) == 1){
                        isA = true;
                    }
                    else{
                        isA = false;
                    }
                }
                if(b.getX()==i&&b.getY()==j){
                    if(getDataFromCell(i,j) == 1){
                        isB = true;
                    }
                    else{
                        isB = false;
                    }
                }
                if(c.getX()==i&&c.getY()==j){
                    if(getDataFromCell(i,j) == 1){
                        isC = true;
                    }
                    else{
                        isC = false;
                    }
                }
            }
        }
        if(isA && isB && isC){
            return true;
        }
        return false;
    }

    public boolean isEdge(Coordinate a,Coordinate b, Coordinate c){
        if(a.getX()==11||b.getX()==11||c.getX()==11 ||a.getY()==11||b.getY()==11||c.getY()==11){
            return true;
        }
        return false;
    }

    public boolean isShipCollision (Coordinate a, Coordinate b, Coordinate c){
        deleteShip(a,b,c);
        deleteShipInSelfGrid(a,b,c);

        Coordinate aNew = new Coordinate(a.getX(), a.getY());
        Coordinate bNew = new Coordinate(a.getX(), a.getY() + 1);
        Coordinate cNew = new Coordinate(a.getX(), a.getY() + 2);

        if(isEdge(aNew,bNew,cNew)){
            addShip(a,b,c);
            addShipInSelfGrid(a,b,c);
            return true;
        }

        for (int i = 0; i < fleet.size(); i++) {
            if(fleet.get(i).getA().compareCoord(bNew) || fleet.get(i).getB().compareCoord(bNew) || fleet.get(i).getC().compareCoord(bNew) ||
                    fleet.get(i).getA().compareCoord(cNew) || fleet.get(i).getB().compareCoord(cNew) || fleet.get(i).getC().compareCoord(cNew)){
                addShip(a,b,c);
                addShipInSelfGrid(a,b,c);
                return true;
            }
        }
        return false;

    }

    public boolean isOvelap(Coordinate a, Coordinate b, Coordinate c){
        boolean isA = false;
        boolean isB = false;
        boolean isC = false;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++)
            {
                if((a.getX()==i&&a.getY()==j)){
                    if(getDataFromCell(i,j) == 1){
                        isA = true;
                    }
                    else{
                        isA = false;
                    }
                }
                if(b.getX()==i&&b.getY()==j){
                    if(getDataFromCell(i,j) == 1){
                        isB = true;
                    }
                    else{
                        isB = false;
                    }
                }
                if(c.getX()==i&&c.getY()==j){
                    if(getDataFromCell(i,j) == 1){
                        isC = true;
                    }
                    else{
                        isC = false;
                    }
                }
            }
        }
        if(isA || isB || isC){
            return true;
        }
        return false;
    }

    public void printSelfData(){
        for (int i = 1; i < selfData.length; i++) {
            for (int j = 1; j < selfData[i].length; j++) {
                System.out.print(selfData[j][i] + " ");
            }
            System.out.println();
        }
    }
    public void printAttackData(){
        for (int i = 1; i < attackData.length; i++) {
            for (int j = 1; j < attackData[i].length; j++) {
                System.out.print(attackData[j][i] + " ");
            }
            System.out.println();
        }
    }

}