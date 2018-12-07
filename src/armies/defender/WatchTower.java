package armies.defender;

import armies.Fleet;
import board.BattleField;

import java.util.Random;

public class WatchTower implements Defender {
    private DefenseFleet defenseFleet;

    private WatchTower() { }

    private static class GeneralHolder {
        private static final WatchTower WATCH_TOWER = new WatchTower();
    }

    public static WatchTower getInstance() {
        return GeneralHolder.WATCH_TOWER;
    }

    public void defenseUp() {
        System.out.println("Watch tower raising its defenses");

        Random random = new Random();
        int planes = random.nextInt(10) + 5;
        int baseLands = random.nextInt(10) + 5;
        int ships = random.nextInt(10) + 5;

        this.defenseFleet = (DefenseFleet) Fleet.createFleet(this, planes, baseLands, ships);

        System.out.println("Defense fleet has " + planes + " planes, " + baseLands + " base lands and " + ships + " ships");
        System.out.println();

        BattleField.getInstance().bind(this, this.defenseFleet);
    }
}
