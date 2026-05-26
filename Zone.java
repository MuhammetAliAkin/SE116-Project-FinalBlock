public abstract class Zone extends Cell {
    protected int level;
    protected boolean hasSecurity;
    protected boolean hasHealth;
    protected boolean hasEducation;
    protected int electricityReceived;
    protected int waterReceived;
    protected int internetReceived;
    public Zone(int x, int y, char type) {
        super(x, y, type);
        level = 0;
    }

    @Override
    public void tick() {
        if ((electricityReceived == 0) || (waterReceived == 0) || (internetReceived == 0)){
            level = 0;
            return;
        }
        switch (level){
            case 0:
                if (getM() >= 1) level = 1;
                break;
            case 1:
                if((getM() >= 1) && hasSecurity && hasEducation && hasHealth) level = 2;
                break;
            case 2:
                if((getM() >= 1) && hasSecurity && hasEducation && hasHealth && canUpgradeToLevel3()) level = 3;
                else if (!hasSecurity || !hasEducation || !hasHealth) level = 1;
                break;
            case 3:
                if (getM() < 1 || !hasSecurity || !hasHealth || !hasEducation || !canUpgradeToLevel3()) level = 2;
                break;
            default:
                throw new IllegalStateException("Invalid level: " + level);
        }
    }
    public void resetUtilities() {
        electricityReceived = 0;
        waterReceived = 0;
        internetReceived = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isHasSecurity() {
        return hasSecurity;
    }

    public void setHasSecurity(boolean hasSecurity) {
        this.hasSecurity = hasSecurity;
    }

    public boolean isHasHealth() {
        return hasHealth;
    }

    public void setHasHealth(boolean hasHealth) {
        this.hasHealth = hasHealth;
    }

    public boolean isHasEducation() {
        return hasEducation;
    }

    public void setHasEducation(boolean hasEducation) {
        this.hasEducation = hasEducation;
    }
    public void resetServices(){
        this.hasEducation = false;
        this.hasHealth = false;
        this.hasSecurity = false;
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
    public int getM(){
        return Math.min(electricityReceived,Math.min(waterReceived,internetReceived));
    }
    public abstract int getCurrentOutput();
    public abstract int getUtilityDemand();
    public abstract boolean canUpgradeToLevel3();
    public abstract void resetPool();
}
