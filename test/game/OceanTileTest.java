package game;

import static org.junit.Assert.*;
import org.junit.Test;
import game.*;

public class OceanTileTest {

    OceanTile t1 = new OceanTile();
    Unit u1 = new Unit();

    @Test
    public void IsBusyTest(){
        assertSame(true , t1.isBusy());
    }

    @Test(excepted = IllegalGameActionException.class)
    public void getResourceTest() {

        OceanTile t1 = new OceanTile();
        t1.getResource();

    }

    @Test(excepted = IllegalGameActionException.class)
    public void setUnitTest() {

        OceanTile t1 = new OceanTile();
        Unit u1 = new Unit();
        t1.setUnit(u1);

    }




}