public class PowerPlant extends Cell {
    private int electricitySupply;
    public PowerPlant(int x, int y) {
        super(x, y, 'P');
    }

    public int getElectricitySupply() {
        return electricitySupply;
    }

    public void setElectricitySupply(int electricitySupply) {
        this.electricitySupply = electricitySupply;
    }

    @Override
    public char getType() {
        return 'P';
    }
    @Override
    public void tick() {
        electricitySupply = 100;
    }
}
