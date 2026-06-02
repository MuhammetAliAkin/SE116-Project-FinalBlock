import java.util.ArrayList;
import java.util.List;

public class SimulationManager {

    private final Cell[][] grid;
    private final ServiceEngine serviceEngine = new ServiceEngine();
    private final InfrastructureManager infraManager = new InfrastructureManager();

    private int pooledPopulation = 0;
    private int pooledGoods      = 0;
    private int pooledLifestyle  = 0;

    public SimulationManager(Cell[][] grid) {
        this.grid = grid;
    }

    public void run(int totalTicks) {
        for (int tick = 1; tick <= totalTicks; tick++) {
            System.out.println("=== Tick " + tick + " ===");
            executeTick();
            printGrid(tick);
        }
    }

    private void executeTick() {
// Step 1: Services
        forEachZone(z -> z.resetServices());
        serviceEngine.updateServices(grid);
        // Step 2: Utilities
        forEachZone(z -> z.resetUtilities());
        forEachCell(c -> { if (c instanceof PowerPlant || c instanceof WaterPumpingStation || c instanceof InternetHub) c.tick(); });
        infraManager.distributeInfrastructure(grid);
// Step 3: Distribute last tick's resources
        distributeResources();

        // Step 4: Update zone levels
        forEachZone(z -> z.tick());

// Step 5: Accumulate new production
        pooledPopulation = sumOutput(Housing.class);
        pooledGoods      = sumOutput(Industrial.class);
        pooledLifestyle  = sumOutput(Commercial.class);

        forEachZone(z -> z.resetPool());
    }

    private void distributeResources() {
        List<Industrial> ind = getZones(Industrial.class);
        List<Commercial> com = getZones(Commercial.class);
        List<Housing>    hou = getZones(Housing.class);

        int workerZones = ind.size() + com.size();
        int popPerZone  = workerZones > 0 ? pooledPopulation / workerZones : 0;
        ind.forEach(z -> z.setPopulationReceived(popPerZone));
        com.forEach(z -> z.setPopulationReceived(popPerZone));

        int goodsPerCom = com.size() > 0 ? pooledGoods / com.size() : 0;
        com.forEach(z -> z.setGoodsReceived(goodsPerCom));

        int lifestylePerHou = hou.size() > 0 ? pooledLifestyle / hou.size() : 0;
        hou.forEach(z -> z.setLifestyleReceived(lifestylePerHou));
    }



    private void forEachCell(java.util.function.Consumer<Cell> action) {
        for (Cell[] row : grid)
            for (Cell c : row)
                action.accept(c);
    }

    private void forEachZone(java.util.function.Consumer<Zone> action) {
        forEachCell(c -> { if (c instanceof Zone) action.accept((Zone) c); });
    }

    private <T extends Zone> List<T> getZones(Class<T> type) {
        List<T> list = new ArrayList<>();
        forEachCell(c -> { if (type.isInstance(c)) list.add(type.cast(c)); });
        return list;
    }

    private <T extends Zone> int sumOutput(Class<T> type) {
        int total = 0;
        for (Cell[] row : grid)
            for (Cell c : row)
                if (type.isInstance(c)) total += type.cast(c).getCurrentOutput();
        return total;
    }

    private void printGrid(int tick) {
        System.out.println("--- Grid after Tick " + tick + " ---");
        for (Cell[] row : grid) {
            StringBuilder sb = new StringBuilder();
            for (Cell c : row) sb.append(c.getType());
            System.out.println(sb);
        }
        System.out.println("Population pool: " + pooledPopulation
                + " | Goods pool: " + pooledGoods
                + " | Lifestyle pool: " + pooledLifestyle);
        System.out.println();
    }
}