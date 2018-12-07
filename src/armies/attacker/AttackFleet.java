package armies.attacker;

import armies.Fleet;
import board.BattleField;
import machinery.MachineType;
import machinery.WarProfiteer;
import machinery.WarUnit;

import java.util.Random;

public class AttackFleet extends Fleet {
    private int nPlanes;

    private int nKamikazes;

    private int nShips;

    public AttackFleet(int nPlanes, int nKamikazes, int nShips) {
        this.nPlanes = nPlanes;
        this.nKamikazes = nKamikazes;
        this.nShips = nShips;
    }

    @Override
    public void run() {
        int iPlane = 0;
        int iKamikaze = 0;
        int iShip = 0;

        System.out.println("Attack fleet running");
        while((this.nPlanes > 0 || this.nKamikazes > 0 || this.nShips > 0) && BattleField.getInstance().isFighting()) {
            MachineType type = this.requestWarUnit();
            WarUnit warUnit = WarProfiteer.getInstance().getWarMachine(type);

            while(warUnit.getLife() > 0 && BattleField.getInstance().isFighting()) {
                switch (warUnit.getClass().getSimpleName()) {
                    case "Plane":
                        System.out.println("Plane " + iPlane + " defending");
                        break;

                    case "Kamikaze":
                        System.out.println("Kamikaze " + iKamikaze + " defending");
                        break;

                    case "Ship":
                        System.out.println("Ship " + iShip + " defending");
                        break;
                }

                BattleField.getInstance().attack(warUnit);
            }

            WarProfiteer.getInstance().returnWarMachine(warUnit);
            switch (warUnit.getClass().getSimpleName()) {
                case "Plane":
                    iPlane++;
                    break;

                case "Kamikaze":
                    iKamikaze++;
                    break;

                case "Ship":
                    iShip++;
                    break;
            }
        }

        BattleField.getInstance().setFighting(false);
        System.out.println("Attacker has " + ((this.nPlanes == 0 && this.nKamikazes == 0 && this.nShips == 0)? "lost" : "won"));
    }

    @Override
    protected MachineType requestWarUnit() {
        Random random = new Random();
        int[] availableMachines = { this.nPlanes, this.nKamikazes, this.nShips };
        int index;

        do {
            index = random.nextInt(3);
        } while(availableMachines[index] == 0);
        System.out.println("Chosen index: " + index);

        switch (index) {
            case 0:
                this.nPlanes--;
                return MachineType.AIR;

            case 1:
                this.nKamikazes--;
                return MachineType.ONE_TIME_ONLY;

            case 2:
                this.nShips--;
                return MachineType.SEA;

            default:
                System.out.println("Attacker says: NON EXISTING TYPE: " + index);
                return MachineType.AIR;
        }
    }
}
