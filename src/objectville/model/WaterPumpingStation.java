package objectville.model;

public class WaterPumpingStation extends Cell {
    private static final int DEFAULT_SUPPLY = 100;
    private int waterSupply = DEFAULT_SUPPLY;
    public WaterPumpingStation(int x, int y) {

        super(x, y, 'W');
    }

    public int getWaterSupply() {
        return waterSupply;
    }

    public void setWaterSupply(int waterSupply) {
        this.waterSupply = waterSupply;
    }

    @Override
    public char getType() {
        return 'W';
    }
    @Override
    public void tick() {

        waterSupply = DEFAULT_SUPPLY;
    }
}
