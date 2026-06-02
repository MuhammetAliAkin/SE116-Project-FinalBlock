public class InfrastructureManager {
    private static final int[][] DIRECTIONS = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };


    public void distributeInfrastructure(Cell[][] grid) {
        // This will scan the grid and start BFS from each utility source later.
        if (grid == null) {
            return;
        }

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Cell currentCell = grid[row][col];

                if (isSourceCell(currentCell)) {
                    runBfsFromSource(grid, row, col, currentCell);
                }
            }
        }
    }

    private void runBfsFromSource(Cell[][] grid, int startRow, int startCol, Cell sourceCell) {
        if (!isValidCoordinate(grid, startRow, startCol)) {
            return;
        }

        int totalCells = countTotalCells(grid);
        int[] rowQueue = new int[totalCells];
        int[] colQueue = new int[totalCells];
        int front = 0;
        int rear = 0;
        int remainingCapacity = getSourceCapacity(sourceCell);
        char resourceType = sourceCell.getType();
        boolean[][] visited = createVisitedArray(grid);


        visited[startRow][startCol] = true;
        rowQueue[rear] = startRow;
        colQueue[rear] = startCol;
        rear++;

        while (front < rear) {
            int currentRow = rowQueue[front];
            int currentCol = colQueue[front];
            front++;

            Cell currentCell = grid[currentRow][currentCol];
            if (currentCell instanceof Zone) {
                int demand = getDemandForResource(currentCell, resourceType);
                int usedAmount = Math.min(demand, remainingCapacity);
                applyResourceToZone(currentCell, resourceType, usedAmount);

                remainingCapacity -= usedAmount;

                if (remainingCapacity == 0) {
                    break;
                }
            }

            for (int i = 0; i < DIRECTIONS.length; i++) {
                int nextRow = currentRow + DIRECTIONS[i][0];
                int nextCol = currentCol + DIRECTIONS[i][1];

                if (!canVisit(grid, visited, nextRow, nextCol)) {
                    continue;
                }

                visited[nextRow][nextCol] = true;
                rowQueue[rear] = nextRow;
                colQueue[rear] = nextCol;
                rear++;
            }
        }
    }


    private int getDemandForResource(Cell cell, char resourceType) {
        if (!(cell instanceof Zone)) {
            return 0;
        }

        Zone zone = (Zone) cell;
        return zone.getUtilityDemand();
    }

    private void applyResourceToZone(Cell cell, char resourceType, int amount) {
        if (!(cell instanceof Zone)) {
            return;
        }

        Zone zone = (Zone) cell;

        if (resourceType == 'P') {
            zone.receiveElectricity(amount);
        } else if (resourceType == 'W') {
            zone.receiveWater(amount);
        } else if (resourceType == 'T') {
            zone.receiveInternet(amount);
        }
    }

    private boolean canFlowThrough(Cell cell) {
        if (cell == null) {
            return false;
        }

        if (cell instanceof EmptyCells) {
            return false;
        }

        if (cell instanceof Road) {
            return true;
        }

        if (cell instanceof Housing) {
            return true;
        }

        if (cell instanceof Industrial) {
            return true;
        }

        if (cell instanceof Commercial) {
            return true;
        }

        if (cell instanceof PowerPlant) {
            return true;
        }

        if (cell instanceof WaterPumpingStation) {
            return true;
        }

        if (cell instanceof InternetHub) {
            return true;
        }

        return false;
    }
    private int getSourceCapacity(Cell sourceCell) {
        if (sourceCell instanceof PowerPlant) {
            return ((PowerPlant) sourceCell).getElectricitySupply();
        }

        if (sourceCell instanceof WaterPumpingStation) {
            return ((WaterPumpingStation) sourceCell).getWaterSupply();
        }

        if (sourceCell instanceof InternetHub) {
            return ((InternetHub) sourceCell).getInternetSupply();
        }

        return 0;
    }

    private boolean[][] createVisitedArray(Cell[][] grid) {
        boolean[][] visited = new boolean[grid.length][];
        for (int row = 0; row < grid.length; row++) {
            visited[row] = new boolean[grid[row].length];
        }
        return visited;
    }

    private int countTotalCells(Cell[][] grid) {
        int totalCells = 0;
        for (int row = 0; row < grid.length; row++) {
            totalCells += grid[row].length;
        }
        return totalCells;
    }

    private boolean isValidCoordinate(Cell[][] grid, int row, int col) {
        if (grid == null || row < 0 || row >= grid.length) {
            return false;
        }

        return col >= 0 && col < grid[row].length;
    }

    private boolean canVisit(Cell[][] grid, boolean[][] visited, int row, int col) {
        if (!isValidCoordinate(grid, row, col)) {
            return false;
        }

        if (visited[row][col]) {
            return false;
        }

        return canFlowThrough(grid[row][col]);
    }


    private boolean isSourceCell(Cell cell) {
        // This checks if the cell is a utility provider for infrastructure.
        if (cell == null) {
            return false;
        }

        char type = cell.getType();
        return type == 'P' || type == 'W' || type == 'T';
    }
}
