package com.kp.game.core;

public enum TileType {
    SEVEN("7", 150),
    BAR("BAR",100),
    CHERRY("C",70),
    PEAR("PE",40),
    PLUM("PL",30),
    BANANA("BA",20),
    BELL("BE",10);

    private String symbol;
    private int points;

    TileType(String symbol, int points) {
        this.symbol = symbol;
        this.points = points;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPoints(int amountSymbolsInRow){
        return switch (amountSymbolsInRow) {
            case 3 -> points;
            case 4 -> points * 2;
            case 5 -> points * 3;
            default -> 0;
        };
    }
}
