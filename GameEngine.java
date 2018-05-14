import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Coin> coins = new ArrayList<Coin>();
    private SpaceShipPlyr1 p1;	
    private SpaceShipPlyr2 p2;
	
	private Timer timer;
	
	private long score = 0;
	private long level = 0;
	private double difficulty = 0.1;
	private int lvTimer = 0;
	private boolean lifeP1 , lifeP2 = true;
    
    
    
	public GameEngine(GamePanel gp, SpaceShipPlyr1 p1, SpaceShipPlyr2 p2) {
		this.gp = gp;
        this.p1 = p1;	
        this.p2 = p2;	
		
        gp.sprites.add(p1);
        gp.sprites.add(p2);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
				lvTimer++;
				if(lvTimer >= 500){
					lvTimer = 0;
					difficulty += 0.1;
					level++;
				}
			}
		});
		timer.setRepeats(true); //เวลาวน
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
        Enemy e = new Enemy((int)(Math.random()*390), 30);
        gp.sprites.add(e);
		enemies.add(e);
    }
    
    private void generateCoin(){
        Coin c = new Coin((int)(Math.random()*390), 1);
        gp.sprites.add(c);
        coins.add(c);
    }
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
			generateCoin();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites. remove(e);
				score += 50;
				
			}
		}

		Iterator<Coin> c_iter = coins.iterator();
		while(c_iter.hasNext()){
			Coin c = c_iter.next();
			c.proceed();
			if(!c.isAlive()){
				c_iter.remove();
				gp.sprites. remove(c);
				score += 50;
				
			}
			
        }

		
		gp.updateGameUI(this,getLevel());
		
        Rectangle2D.Double P1r = p1.getRectangle();
        Rectangle2D.Double P2r = p2.getRectangle();
        
        Rectangle2D.Double enemyCheck;
		Rectangle2D.Double coinCheck;

        Iterator<Enemy> e_iter2 = enemies.iterator();
		while(e_iter2.hasNext()){
            Enemy e = e_iter2.next();
            enemyCheck = e.getRectangle();
			if(enemyCheck.intersects(P1r)){
                lifeP1 = false ;
				die();
				return;
            }
            if(enemyCheck.intersects(P2r)){
                lifeP2 = false ;
				die();
                return;
            }
        }
        
        Iterator<Coin> c_iter2 = coins.iterator();
		while(c_iter2.hasNext()){
			Coin c = c_iter2.next();
            coinCheck = c.getRectangle();
            if(coinCheck.intersects(P1r)){
                score+=500;
                c_iter2.remove();
                gp.sprites.remove(c);
				return;
            }
            if(coinCheck.intersects(P2r)){
                c_iter2.remove();
                gp.sprites.remove(c);
                return;
            }
		
		
		}
	}
	
	
	
	public void die(){
		if(!lifeP1 && !lifeP2){
		timer.stop();
		GameOver loose = new GameOver();
		}
		//pop-up game over window
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		//player1 keys
		case KeyEvent.VK_LEFT:
			p1.moveX(-1);
			break;
		case KeyEvent.VK_RIGHT:
			p1.moveX(1);
            break;
        case KeyEvent.VK_UP:
            p1.moveY(1);
            break;
        case KeyEvent.VK_DOWN:
            p1.moveY(-1);
            break;
		//player2 keys
        case KeyEvent.VK_A:
            p2.moveX(-1);
            break;
        case KeyEvent.VK_D:
            p2.moveX(1);
            break;
        case KeyEvent.VK_W:
            p2.moveY(1);
            break;
        case KeyEvent.VK_S:
            p2.moveY(-1);
            break;
		case KeyEvent.VK_L:
			difficulty += 0.1;
            level++;
			break;
		}
    }

	public long getScore(){
		if(lifeP1 && lifeP2)
		return score*2;
            else
		return score;
	}
	
	public long getLevel(){
		return level;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}