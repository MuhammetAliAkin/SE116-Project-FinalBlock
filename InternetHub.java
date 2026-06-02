public class InternetHub extends Cell {
    private static final int DEFAULT_SUPPLY = 100;
    private int internetSupply;
    public InternetHub(int x, int y) {

        super(x, y, 'T');
        internetSupply = DEFAULT_SUPPLY;
    }

    public int getInternetSupply() {
        return internetSupply;
    }

    public void setInternetSupply(int internetSupply) {
        this.internetSupply = internetSupply;
    }

    @Override
    public char getType() {
        return 'T';
    }
    @Override
    public void tick() {

        internetSupply = DEFAULT_SUPPLY;
    }
}
