package objectville.main;

import objectville.engine.InfrastructureManager;
import objectville.engine.ServiceEngine;
import objectville.model.*;

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
            System.out.println("Tick " + tick);
            executeTick();
            printGrid(tick);
        }
    }

    private void executeTick() {
        forEachZone(z -> {
            int oldLevel = z.getLevel();
            z.tick();
            int newLevel = z.getLevel();

            String className = z.getClass().getSimpleName();
            if (className.equals("Housing")) {
                className = "House";
            }
            int output = z.getCurrentOutput();
            if (output > 0) {
                String resourceName = "";
                if (z instanceof Housing) {
                    resourceName = "population";
                } else if (z instanceof Industrial) {
                    resourceName = "goods";
                } else if (z instanceof Commercial) {
                    resourceName = "lifestyle";
                }
                System.out.println(className + " at (" + z.getX() + "," + z.getY() + ") generated " + output + " " + resourceName);
            }
            if (newLevel > oldLevel) {
                System.out.println(className + " at (" + z.getX() + "," + z.getY() + ") levels up from " + oldLevel + " to " + newLevel);
            } else if (newLevel < oldLevel) {
                System.out.println(className + " at (" + z.getX() + "," + z.getY() + ") levels down from " + oldLevel + " to " + newLevel);
            }
        });
        serviceEngine.updateServices(grid);
        forEachZone(z -> z.resetUtilities());
        forEachCell(c -> { if (c instanceof PowerPlant || c instanceof WaterPumpingStation || c instanceof InternetHub) c.tick(); });
        infraManager.distributeInfrastructure(grid);
        distributeResources();

        forEachZone(z -> {
            int oldLevel = z.getLevel();
            z.tick();
            int newLevel = z.getLevel();

            if (newLevel != oldLevel) {
                String className = z.getClass().getSimpleName();
                if (className.equals("Housing")) {
                    className = "House";
                }

                System.out.println(className + " at (" + z.getX() + "," + z.getY()
                        + ") levels up from " + oldLevel + " to " + newLevel);
            }
        });
        pooledPopulation = sumOutput(Housing.class);
        pooledGoods      = sumOutput(Industrial.class);
        pooledLifestyle  = sumOutput(Commercial.class);
        forEachZone(z -> z.saveOutputForNextTick());
        forEachZone(z -> z.resetPool());
    }

    private void distributeResources() {
        List<Industrial> ind = getZones(Industrial.class);
        List<Commercial> com = getZones(Commercial.class);
        List<Housing>    hou = getZones(Housing.class);

        int workerZones = ind.size() + com.size();
        int popPerZone  = workerZones > 0 ? pooledPopulation / workerZones : 0;
        ind.forEach(z -> {
            z.setPopulationReceived(popPerZone);
            if(popPerZone > 0) System.out.println("Industrial at (" + z.getX() + "," + z.getY() + ") received " + popPerZone + " population");
        });
        com.forEach(z ->{
            z.setPopulationReceived(popPerZone);
            if(popPerZone > 0) System.out.println("Commercial at (" + z.getX() + "," + z.getY() + ") received " + popPerZone + " population");
        });

        int goodsPerCom = com.size() > 0 ? pooledGoods / com.size() : 0;
        com.forEach(z -> {
            z.setGoodsReceived(goodsPerCom);
            if(goodsPerCom > 0) System.out.println("Commercial at (" + z.getX() + "," + z.getY() + ") received " + goodsPerCom + " goods");
        });

        int lifestylePerHou = hou.size() > 0 ? pooledLifestyle / hou.size() : 0;
        hou.forEach(z -> {
            z.setLifestyleReceived(lifestylePerHou);
            if(lifestylePerHou > 0) System.out.println("House at (" + z.getX() + "," + z.getY() + ") received " + lifestylePerHou + " lifestyle");
        });
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
                if (type.isInstance(c)) {
                    T zone = type.cast(c);
                    int output = zone.getCurrentOutput();
                    total += output;

                    if (output > 0) {
                        String className = zone.getClass().getSimpleName();
                        if (className.equals("Housing")) {
                            className = "House";
                        }

                        String resourceName = "";
                        if (zone instanceof Housing) {
                            resourceName = "population";
                        } else if (zone instanceof Industrial) {
                            resourceName = "goods";
                        } else if (zone instanceof Commercial) {
                            resourceName = "lifestyle";
                        }

                        System.out.println(className + " at (" + zone.getX() + "," + zone.getY()
                                + ") generated " + output + " " + resourceName);
                    }
                }
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