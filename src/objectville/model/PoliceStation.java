package objectville.model;

public class PoliceStation extends Cell {
    public PoliceStation(int x, int y) {
        super(x, y, 'F');
    }

    @Override
    public char getType() {
        return 'F';
    }
    @Override
    public void tick() {
        //TODO
    }
}
