package com.racinggame.topdownracinggame;

/**
 * Holds the choices made on the selection screen.
 * Passed from Menu -> Main -> GameUI (and later to RaceManager).
 */
public class RaceSettings {
    private final String player1Vehicle;
    private final String player2Vehicle;
    private final int laps;

    public RaceSettings(String player1Vehicle, String player2Vehicle, int laps) {
        this.player1Vehicle = player1Vehicle;
        this.player2Vehicle = player2Vehicle;
        this.laps = laps;
    }

    public String getPlayer1Vehicle() { return player1Vehicle; }
    public String getPlayer2Vehicle() { return player2Vehicle; }
    public int getLaps() { return laps; }
}
