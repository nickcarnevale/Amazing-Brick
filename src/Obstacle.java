import java.util.Random;

public class Obstacle {
    
    Random random = new Random();
    private int leftX, rightX, y;

    public Obstacle(int S_WIDTH){
        leftX = (int) (Math.random()*450)+S_WIDTH*(3/4);
        rightX = leftX + 125;
        y = 0;
    }

    public int getLeftX(){return leftX;}
    public int getRightX(){return rightX;}
    public int getY(){return y;};

    public boolean end(int height){
        if((y+60) == height){
            return false;
        }
        return true;
    }

    public boolean update(int height){
        if(end(height)){
            y += 10;
            return true;
        }
        return false;
    }

}
