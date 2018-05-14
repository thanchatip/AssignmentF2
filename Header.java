import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class Header {
    private int x ;
    private int y;
    public Header(int x,int y){
        this.x = x;
        this.y = y;
    }
    public void draw(Graphics2D g){
        g.setColor(Color.PINK);
	g.fillRect(x, y, 400, 10);
    }
}
