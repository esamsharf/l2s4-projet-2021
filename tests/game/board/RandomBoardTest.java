package game.board;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import game.board.Board;
import game.board.RandomBoard;
import game.board.ExampleBoard;
import game.exception.GameException;
import game.exception.IllegalGameActionException;
import game.exception.UnknownTileException;
import game.player.Player;
import game.player.AgricolPlayer;
import game.tile.OceanTile;
import game.tile.*;
import game.unit.Unit;
import game.unit.Worker;

public class RandomBoardTest {

    @Test (expected = IllegalArgumentException.class)
    public void cannotBuildBoardIfHeightInferior1() {
        Board b = new RandomBoard(3, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBuildBoardIfWidthInferior2() {
        Board b = new RandomBoard(1, 12);
    }

    @Test
    public void canBuildBoardWithGoodArguments() {
        Board b = new RandomBoard(2, 1);
    }

    @Test
    public void boardFollowsTheOceanRules() throws UnknownTileException {
        Board b = new RandomBoard(10, 15);
        int cpt = 0;
        for (int x = 0; x < b.width; x++) {
            for (int y = 0; y < b.height; y++) {
                Tile current = null;
                current = b.tileAt(x, y);
                if (current instanceof OceanTile) {
                    cpt += 1;
                }
            }
        }
        int nbTile = b.getWidth() * b.getHeight();
        float rate = ((float) cpt) / nbTile;
        // Ocean tiles should occupy at least 2/3 of the board
        assertTrue(rate >= (float) (2/3));
    }

    /**
     * @param tiles a list of tiles
     * @return true iff there is at least one non OceanTile
     * among <code>tiles</code>
     */
    private static boolean anyNonOceanTile(List<Tile> tiles) {
        for(Tile t : tiles) {
            if (!(t instanceof OceanTile)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void boardFollowsTheTerrestrialRules() throws UnknownTileException {
        Board b = new RandomBoard(10, 15);
        for (int x = 0; x < b.width; x++) {
            for (int y = 0; y < b.height; y++) {
                Tile current = null;
                current = b.tileAt(x, y);
                // current is not an ocean tile
                if (!(current instanceof OceanTile)) {
                    List<Tile> adjTiles = null;
                    adjTiles = b.adjacentTiles(x, y);
                    // at least one neighbor of current should not be an OceanTile
                    assertTrue(anyNonOceanTile(adjTiles));
                }
            }
        }
    }

    @Test
    public void isFullWhenNotFull() throws UnknownTileException, IllegalGameActionException {
        /* Créer un plateau, sans rien faire de plus
         * et vérifier que isFull renvoie false */
        Board b = new ExampleBoard();
        assertFalse(b.isFull());

        /* Vérifier aussi en ajoutant une seule unité sur une seule tuile */
        b.setUnitAt(1, 1, new Worker());
        assertFalse(b.isFull());
    }

    @Test
    public void isFullWhenFull() throws UnknownTileException, IllegalGameActionException {
        Board b = new ExampleBoard();
        /* On itère sur toutes les tuiles et on met une
         * unité sur chaque cases qui n'est pas de type océan*/

        b.setUnitAt(1, 1, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(2, 1, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(4, 0, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(5, 0, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(4, 1, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(5, 1, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(1, 3, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(2, 3, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(3, 3, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(4, 3, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(1, 4, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(2, 4, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(3, 4, new Worker());
        assertFalse(b.isFull());
        b.setUnitAt(4, 4, new Worker());
        assertTrue(b.isFull());
    }

    @Test (expected = UnknownTileException.class)
    public void tileAtOutOfBounds() throws UnknownTileException {
        /* accéder à une tuile incorrecte (hors limte) */
        Board b = new RandomBoard(10, 15);
        b.tileAt(12, 22);
    }

    @Test
    public void tileAtOK() throws UnknownTileException {
        Board b = new RandomBoard(10, 15);
        for (int i = 0; i < b.height; i++) {
            for (int j = 0; j < b.width; j++) {
                Tile current = null;
                current = b.tileAt(j, i);
                assertSame(b.tiles[i][j], current);
            }
        }
    }

    @Test (expected = UnknownTileException.class)
    public void setUnitAtUnknownTile() throws GameException {
        Board b = new RandomBoard(10, 15);
        Tile t = null;
        Player p = null;
        b.setUnitAt(12, 22, new Worker(t, p));
    }

    @Test(expected = IllegalGameActionException.class)
    public void setUnitOnOceanTile() throws GameException {
        /* trouver une tuile de type OceanTile dans le tableau,
         * essayer de faire setUnit dessus */
        Board b = new ExampleBoard();
        Tile t = new PlainTile(0, 0);
        Player p = new AgricolPlayer("hop");
        b.setUnitAt(0, 0, new Worker(t, p));
    }

    @Test
    public void setUnitOK() throws GameException {
        /* trouver case correcte, faire setUnit,
         * vérifier avec assertSame */
        Board b = new ExampleBoard();
        Tile t = new PlainTile(0, 0);
        Player p = new AgricolPlayer("hop");
        Unit u = new Worker(t, p);
        b.setUnitAt(1, 1, u);
        assertSame(u, b.tileAt(1, 1).getUnit());
    }

    @Test (expected = UnknownTileException.class)
    public void adjacentTilesOutOfBounds() throws UnknownTileException {
        Board b = new RandomBoard(10, 15);
        b.adjacentTiles(12, 22);
    }

    @Test
    public void adjacentTilesMid() throws GameException { /* au milieu */
        Board b = new RandomBoard(15, 10);
        List<Tile> list = b.adjacentTiles(7, 7);
        assertEquals(4, list.size());
        assertTrue(list.contains(b.tileAt(7, 6)));
        assertTrue(list.contains(b.tileAt(8, 7)));
        assertTrue(list.contains(b.tileAt(7, 8)));
        assertTrue(list.contains(b.tileAt(6, 7)));
    }

    /* sur un bord du tableau qui n'est pas un coin*/
    @Test
    public void adjacentTilesForSides() throws GameException {
        Board b = new RandomBoard(15, 10);
        List<Tile> list = b.adjacentTiles(0, 7);
        assertEquals(3, list.size());
        assertTrue(list.contains(b.tileAt(0, 6)));
        assertTrue(list.contains(b.tileAt(1, 7)));
        assertTrue(list.contains(b.tileAt(0, 8)));
    }

    /* sur un coin */
    @Test
    public void adjacentTilesForCorner() throws GameException {
        Board b = new RandomBoard(15, 10);
        List<Tile> list = b.adjacentTiles(14, 0);
        assertEquals(2, list.size());
        assertTrue(list.contains(b.tileAt(13, 0)));
        assertTrue(list.contains(b.tileAt(14, 1)));
    }
}
