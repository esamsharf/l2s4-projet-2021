package game;

/**
 * A class to represent units in different board games
 * Units are defined by the tile they occupy, the player
 * who owns them, and an amount of gold they possess
 * @version 22/02/2021
 */
public abstract class Unit {
    /**
     * Construct a unit defined by a tile and a player
     * @param tile The tile occupied by the unit
     * @param player The player the unit belongs to
     */
    public Unit(Tile tile, Player player) {
        this.tile = tile;
        this.player = player;
        this.goldQuantity = 0;
    }

    /**
     * Set the tile occupied by the unit
     * @param tile New tile occupied by the unit
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Get the tile occupied by the unit
     * @return The tile occupied
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Set the player the unit belongs to
     * @param player The player the unit belongs to
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the player the unit belongs to
     * @return The player the unit belongs to
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Increment the amount of gold owned by the unit
     * @param gold Amount to increment the quantity of gold owned
     */
    public void receiveGold(int gold) {
        this.goldQuantity += gold;
    }

    /**
     * Get the amount of gold owned by the unit
     * @return Amount of gold
     */
    public int getGold() {
        return this.goldQuantity;
    }

    /**
     * Destroy a unit and perform clean-up actions
     */
    public void destroy() {
        this.tile.setPlayer(null); // TODO check
    }

    /**
     * Return a reference to a resource corresponding to the
     * tile occupied by the unit
     * @return A reference to a resource object corresponding
     * to the occupied tile
     */
    public Resource provideResource() {
        return this.tile.getResource(); // TDOD check
    }

    /**
     * Return the maintenance associated with the unit
     * @return Maintenance cost of the unit
     */
    public abstract int cost();

    /**
     * Return the number of points the unit is worth
     * @return Number of points corresponding to the unit
     */
    public int points() {
        return this.goldQuantity;
    }

    /** Tile occupied by the unit */
    protected Tile place;

    /** Player to which the unit belongs */
    protected Player player;

    /** Amount of gold possessed by the unit */
    protected int goldQuantity;
}