public class WaterPumpingStation extends Cell {
    private int waterSupply;
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
        waterSupply = 100;
    }
}
