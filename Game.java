import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.Timer;
/**
 * Game
 */
public class Game extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;

    private int totalbricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    
    private MapGenerator map;
    
    public Game() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // drawing map
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // the scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // the paddle
        g.setColor(Color.white);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // when you lose the game
        if (ballPosY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }

        // when you won the game
        if(totalbricks == 0){
            play = false;
            ballYDir = -2;
            ballXDir = -1;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);

        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) {
                ballYDir = -ballYDir;
                ballXDir = -2;
            }
            else if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
			{
				ballYDir = -ballYDir;
				ballXDir = ballXDir + 1;
			}
			else if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
			{
				ballYDir = -ballYDir;
			}

            // check map collision with the ball
            App:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int bricksWidth = map.brickWidth;
                        int bricksHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalbricks--;
                            score += 5;

                            // when ball hit right or left of brick
                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXDir = -ballXDir;
                            } 
                            // when ball hits top or bottom of brick
                            else {
                                ballYDir = -ballYDir;
                            }
                            break App;
                        }
                    }


                }
            }


            ballPosX += ballXDir;
            ballPosY += ballYDir;
            if (ballPosX < 0) {
                ballXDir = -ballXDir;
            }
            if (ballPosY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballPosX > 670) {
                ballXDir = -ballXDir;
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }


        }

        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}

        public void moveRight ()
        {
            play = true;
            playerX += 20;
        }
        public void moveLeft ()
        {
            play = true;
            playerX -= 20;
        }
        
    
    
}
