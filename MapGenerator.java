import java.nio.channels.FileChannel.MapMode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
/**
 * MapGenerator
 */
public class MapGenerator{
    public int map [][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col) {
       map = new int[row][col];
       for (int []mapl : map) {
           for (int i = 0; i < map[0].length; i++) {
               mapl[i] = 1;
           }
       } 
       brickWidth = 540/col;
       brickHeight = 240/row;
    }
    
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0) {
                    g.setColor(Color.PINK);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBricksValue(int value,int row,int col)
    {
        map[row][col] = value;

    }
}