package objectville.model;

public class School extends Cell implements ServiceProvider {
    public School(int x, int y) {
        super(x, y, 'S');
    }

    @Override
    public char getType() {
        return 'S';
    }
    @Override
    public void tick() {}

    @Override
    public int getRadius() {
        return 4;
    }

    @Override
    public String getServiceType() {
        return "Education";
    }
}
