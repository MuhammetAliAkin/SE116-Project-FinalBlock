package objectville.model;

public class Hospital extends Cell implements ServiceProvider {
    public Hospital(int x, int y) {
        super(x, y, 'D');
    }

    @Override
    public char getType() {
        return 'D';
    }
    @Override
    public void tick() {}
    @Override
    public String getServiceType() {
        return "Health";
    }
    @Override
    public int getRadius() {
        return 3;
    }
}
