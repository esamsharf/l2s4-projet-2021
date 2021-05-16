package game.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.board.RandomBoard;
import game.exception.GameException;
import game.exception.UnknownTileException;
import game.game.Game;
import game.player.Player;
import game.player.WarPlayer;
import game.tile.OceanTile;
import game.tile.Tile;
import game.unit.Army;
import game.unit.Unit;
import game.unit.Worker;

public class WarDeployActionTest {
	private RandomBoard b;
	private Army unitToDeploy;
	private Player p1;
	/**
	 * Enemy player
	 */
	private Player p2;
	
	@Before
	public void setUp() {
		b = new RandomBoard(10, 10);
		unitToDeploy = new Army(0);
	}
    @Test
    public void canConstruct() {
    	Unit u = new Worker();
        new WarDeployAction(0, 0, u);
        p1 = new WarPlayer("Gentil");
        p2 = new WarPlayer("Méchant");
    }
    
    /**
     * @param b a board well constructed
     * @return a tile from <code>b</code> which is not
     * an OceanTile
     */
    static private Tile findNotOceanTile(RandomBoard b) {
    	for (int x = 0; x < b.getWidth(); x++) {
    		for (int y = 0; y < b.getHeight(); y++) {
    			Tile current = null;
    			try {
    				current = b.tileAt(x, y);
    			}
    			catch (UnknownTileException e) {
    				// nothing
    			}
    			if (!(current instanceof OceanTile)) {
    				return current;
    			}
    		}
    	}
    	return null;
    }

    
	/*
	 * When the enemy's military strength is less than the deployed
	 * army's military strength and greater than 1.
	 */
	@Test
	public void deployWithInferiorEnnemyBesideAndNoCapture() throws GameException {
	   int initialSize = 2;
	   Army enemy = new Army(initialSize);
	   unitToDeploy = new Army(5);
	   Tile tileForEnemy = b.tileAt(1,  1);
	   
	   
	   new WarDeployAction(tileForEnemy.getX(), tileForEnemy.getY(), enemy)
	   		.execute(b, p2);
	   
	   Tile tileForAlly = b.tileAt(2, 1);
	   new WarDeployAction(tileForAlly.getX(), tileForAlly.getY(), unitToDeploy)
	   		.execute(b, p1);
	   
	   // enemy's size should be halved
	   assertSame(initialSize / 2, enemy.getSize());
	}
	
	/*
	 * When the enemys's military strength is less than the deployed
	 * ally army's military strength and also less or equal than 1.
	 */
	@Test
	public void deployWithCapture() throws GameException {
		Army enemy = new Army(1);
		unitToDeploy = new Army(3);
		Tile tileForEnemy = b.tileAt(1, 1); // A mountain tile
		int initialGold = p1.getGold();
		
		new WarDeployAction(tileForEnemy.getX(), tileForEnemy.getY(), enemy)
   		.execute(b, p2);
		
		Tile tileForAlly = b.tileAt(2, 1);
		
		new WarDeployAction(tileForAlly.getX(), tileForAlly.getY(), unitToDeploy)
   		.execute(b, p1);
		
		// the enemy should be captured by p1
		assertTrue(p1.hasUnit(enemy));
		
		// p1 receive 2 gold
		assertSame(initialGold + 2, p1.getGold());
	}
	
	/*
	 * When deploying beside an ally which has less size than the
	 * deployed ally army.
	 */
	@Test
	public void deployWithReinforcingAllyBeside() throws GameException {
		Army ally1 = new Army(1);
		Army ally2 = new Army(4);
		int initSize1 = ally1.getSize(); // for ally1
		int initSize2 = ally2.getSize(); // for ally2
		unitToDeploy = new Army(5);
		
		Tile tileForAlly1 = b.tileAt(1, 3);
		Tile tileForAlly2 = b.tileAt(2, 4);
		Tile tileForAlly = b.tileAt(2, 3); // for unitToDeploy
		
		new WarDeployAction(tileForAlly1.getX(), tileForAlly1.getY(), ally1)
   		.execute(b, p1);
		
		new WarDeployAction(tileForAlly2.getX(), tileForAlly2.getY(), ally2)
   		.execute(b, p1);
		
		new WarDeployAction(tileForAlly.getX(), tileForAlly.getY(), unitToDeploy)
   		.execute(b, p1);
		
		// ally1 and ally2's size increased by 1
		assertSame(initSize1 + 1, ally1.getSize());
		assertSame(initSize2 + 1, ally2.getSize());
	}
	
	/*
	 * When deploying beside an enemy with greater military strength.  
	 */
	@Test
	public void deployWithSuperiorEnnemyBeside() throws GameException {
		Army enemy = new Army(5);
		int initSizeEnemy = enemy.getSize();
		unitToDeploy = new Army(3);
		
		Tile tileForEnemy = b.tileAt(2, 3); // mountain tile
		Tile tileForUnit = b.tileAt(1, 3); // for unitToDeploy, plain tile
		
		new WarDeployAction(tileForEnemy.getX(), tileForEnemy.getY(), enemy)
   		.execute(b, p2);
		
		new WarDeployAction(tileForUnit.getX(), tileForUnit.getY(), unitToDeploy)
   		.execute(b, p1);
		
		
		// no effect on enemy's size
		assertSame(initSizeEnemy, enemy.getSize());
	}
	
	/*
	 * When deploying beside an ally with greater size.  
	 */
	@Test
	public void deployWithSuperiorAllyBeside() throws GameException {
		Army ally = new Army(5);
		int initSizeAlly = ally.getSize();
		unitToDeploy = new Army(3);
		
		Tile tileForAlly = b.tileAt(2, 3); // mountain tile
		Tile tileForUnit = b.tileAt(1, 3); // for unitToDeploy, plain tile
		
		new WarDeployAction(tileForAlly.getX(), tileForAlly.getY(), ally)
   		.execute(b, p2);
		
		new WarDeployAction(tileForUnit.getX(), tileForUnit.getY(), unitToDeploy)
   		.execute(b, p1);
		
		
		// no effect on ally size
		assertSame(initSizeAlly, ally.getSize());
	}
}
