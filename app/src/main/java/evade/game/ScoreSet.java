package evade.game;

import util.animation.util.AEColor;

public class ScoreSet implements Comparable<ScoreSet> {
    private final  AEColor color;
    private final int gamePoints;
    public AEColor getColor() {
        return color;
    }
    public int getPoints() {
        return gamePoints;
    }

    public ScoreSet() {
        this.gamePoints = -1;
        this.color = AEColor.WHITE;
    }

    public ScoreSet(int gamePoints, AEColor color) {
        this.gamePoints = gamePoints;
        this.color = color;
    }

    public int compareTo(ScoreSet o) {
        return o.gamePoints - this.gamePoints;
    }

    public boolean equals(Object obj) {
        return this.gamePoints == ((ScoreSet) obj).gamePoints && this.color == ((ScoreSet) obj).color;
    }

    public String toString() {
        return "score: " + this.gamePoints;
    }
}
