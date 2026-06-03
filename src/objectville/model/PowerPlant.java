package objectville.model;

public class PowerPlant extends Cell {
   private static final int DEFAULT_SUPPLY = 100;
    private int electricitySupply;
    public PowerPlant(int x, int y) {
        super(x, y, 'P');
        electricitySupply = DEFAULT_SUPPLY;
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

        electricitySupply = DEFAULT_SUPPLY;
    }
}
