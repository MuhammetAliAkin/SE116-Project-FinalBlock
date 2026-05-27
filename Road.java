public class Road extends Cell {
    private int electricityReceived;
    private int waterReceived;
    private int internetReceived;
    public Road(int x, int y) {
        super(x, y, 'R');
    }

    public int getElectricityReceived() {
        return electricityReceived;
    }

    public void setElectricityReceived(int electricityReceived) {
        this.electricityReceived = electricityReceived;
    }

    public int getWaterReceived() {
        return waterReceived;
    }

    public void setWaterReceived(int waterReceived) {
        this.waterReceived = waterReceived;
    }

    public int getInternetReceived() {
        return internetReceived;
    }

    public void setInternetReceived(int internetReceived) {
        this.internetReceived = internetReceived;
    }

    @Override
    public char getType() {
        return 'R';
    }
    @Override
    public void tick() {
        electricityReceived = 0;
        waterReceived = 0;
        internetReceived = 0;
    }
    public void receiveWater(int amount){
        waterReceived += amount;
    }
    public void receiveElectricity(int amount){
        electricityReceived += amount;
    }
    public void receiveInternet(int amount){
        internetReceived += amount;
    }
}
