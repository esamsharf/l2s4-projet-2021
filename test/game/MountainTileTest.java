package game;


import static org.junit.Assert.*;
import org.junit.Test;
import game.*;


public class MountainTileTest {

    @Test
    public void getResourceTest(){
        MountainTile t1 = new MountainTile();
        assertSame(Rock, t1.getResource());
    }

}
