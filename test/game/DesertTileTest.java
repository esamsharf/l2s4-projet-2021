package game;

import static org.junit.Assert.*;
import org.junit.Test;
import game.*;

public class DesertTileTest {

    @Test
    public void getResourceTest() {
        DesertTile t1 = new DesertTile();
        assertSame(Sand, t1.getResource());
    }

}

