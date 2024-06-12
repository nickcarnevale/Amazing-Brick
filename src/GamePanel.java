import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener{
    
    static final int S_WIDTH = 600;
    static final int S_HEIGHT = 800;
    static final int UNIT_SIZE = 15;
    static final int DELAY = 70;
    char direction = 'U';
    Random random;
    Timer timer;
    boolean running = false;
    int brickX;
    int brickY;
    ArrayList<Obstacle> obstacles;
    int score;

    int obstacleY;

    public GamePanel(){
        random = new Random();
        score = 0;
        this.setPreferredSize(new Dimension(S_WIDTH,S_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        obstacles = new ArrayList<Obstacle>(); 
        newObstacle();
        running = true;
        timer = new Timer(DELAY, this);
        brickX = S_WIDTH/2;
        brickY = S_HEIGHT-1; 
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running){
            g.setColor(new Color(72, 50, 72));
            g.fillRect(brickX, brickY, UNIT_SIZE, UNIT_SIZE);

            checkCollision();
            for(int i = 0; i < obstacles.size();i++){
                g.setColor(new Color(178,34,34));
                g.fillRect(0,obstacles.get(i).getY(),obstacles.get(i).getLeftX(),UNIT_SIZE);
                g.fillRect(obstacles.get(i).getRightX(),obstacles.get(i).getY(),S_WIDTH-obstacles.get(i).getRightX(),UNIT_SIZE);  
            }
            g.setColor(Color.black);
            g.drawString("Score: " + score, 270, 10);
        
        }else{
            gameOver(g);
        }


    }

    public void gameOver(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
            FontMetrics metrics2 = getFontMetrics(g.getFont()); 
            g.drawString("Game Over :(", (S_WIDTH - metrics2.stringWidth("Game Over :("))/2, S_HEIGHT/2);
    }

    public void newObstacle(){
        obstacles.add(new Obstacle(S_WIDTH));
    }

    public void move(){
        for(int i = 0; i < obstacles.size();i++){
            if(!obstacles.get(i).update(S_HEIGHT)){
                obstacles.remove(i);
                score++;
            }
            if(obstacles.get(i).getY()==250){
                newObstacle();
            }
        }

        switch(direction){
            case 'U':
                brickY -= UNIT_SIZE;
                break;
            case 'D':
                brickY += UNIT_SIZE;
                break;
            case 'L':
                brickX -= UNIT_SIZE;
                break;
            case 'R':
                brickX += UNIT_SIZE;    
                break;
        }
    }

    public void checkCollision(){
        //check if the brick hits obstacle
        for(int i = 0; i < obstacles.size();i++){
            if((brickY == (obstacles.get(i).getY()+UNIT_SIZE)) && (brickX <= obstacles.get(i).getLeftX() || brickX >= obstacles.get(i).getRightX())){
                running = false;
            }else if(((brickY+UNIT_SIZE) == (obstacles.get(i).getY())) && (brickX <= obstacles.get(i).getLeftX() || brickX >= obstacles.get(i).getRightX())){
                running = false;
            }
        }

        //check if the brick hits the corner
        if(brickX < 0){
            running = false;
        }
        if(brickX > S_WIDTH){
            running = false;
        }
        if(brickY < 0){
            running = false;
        }
        if(brickY > S_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                        direction = 'L';
                        brickX -= 3;
                    break;
                case KeyEvent.VK_RIGHT:
                        direction = 'R';
                        brickX += 3;    
                    break;
                case KeyEvent.VK_UP:
                        direction = 'U';
                        brickY -= 1;
                    break;
                case KeyEvent.VK_DOWN:
                        direction = 'D';
                        brickY += 4;
                    break;
            }
        }
    }
}
