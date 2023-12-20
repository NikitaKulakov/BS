package game;

public class Ship {

    Coordinate a;
    Coordinate b;
    Coordinate c;

    boolean aHit;
    boolean bHit;
    boolean cHit;

    Ship(Coordinate a,Coordinate b,Coordinate c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean compareShip(Ship ship){
        if(ship.getA().compareCoord(this.a) && ship.getB().compareCoord(this.b) && ship.getC().compareCoord(this.c)){
            return true;
        }
        return false;
    }

    public Coordinate getA() {
        return a;
    }

    public Coordinate getB() {
        return b;
    }

    public Coordinate getC() {
        return c;
    }

    // Sets booleans to true if the
    public boolean isPointHit(Coordinate hit){
        if(hit.getX() == a.getX()&&hit.getY() == a.getY()){
            return true;
        }
        else if(hit.getX() == b.getX()&&hit.getY() == b.getY()){
            return true;
        }
        else if(hit.getX() == c.getX()&&hit.getY() == c.getY()){
            return true;
        }
        return false;
    }

    public void Hit(Coordinate hit){
        if(hit.getY() == a.getY() && hit.getX() == a.getX()){
            aHit =true;
        }
        if(hit.getY() == b.getY() && hit.getX() == b.getX()){
            bHit =true;
        }
        if(hit.getY() == c.getY() && hit.getX() == c.getX()){
            cHit =true;
        }
    }

    public boolean isShipSunk(){
        if(aHit && bHit && cHit){
            return true;
        }
        else{
            return false;
        }
    }

    public void printShip(){
        String result = "A("+a.getX()+", "+a.getY()+")"+" B("+b.getX()+", "+b.getY()+") C("+c.getX()+", "+c.getY()+")";
        System.out.print(result);
    }
}