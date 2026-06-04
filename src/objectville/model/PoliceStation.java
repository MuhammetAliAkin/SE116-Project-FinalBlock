package objectville.model;

public class PoliceStation extends Cell implements ServiceProvider {
    public PoliceStation(int x, int y) {
        super(x, y, 'F');
    }

    @Override
    public char getType() {
        return 'F';
    }
    @Override
    public void tick() {}

    @Override
    public int getRadius() {
        return 5;
    }

    @Override
    public String getServiceType() {
        return "Security";
    }
}
