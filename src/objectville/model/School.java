package objectville.model;

public class School extends Cell {
    public School(int x, int y) {
        super(x, y, 'S');
    }

    @Override
    public char getType() {
        return 'S';
    }
    @Override
    public void tick() {
        //TODO
    }
}
