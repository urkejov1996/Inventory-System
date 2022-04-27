import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CannedFoodWarehouse {
    private List<Bin> bins = new ArrayList<>();
    private List<Can> canList = new ArrayList<>();

    public CannedFoodWarehouse(List<Can> canList) {
        initBins();
        fillBins(canList);

    }

    private void fillBins(List<Can> canList) {
        for (Can can : canList) {
            boolean canAdded = false;
            for (Bin bin : bins) {
                if (bin.isBinEmpty()) {
                    bin.addToCans(can);
                    this.canList.add(can);
                    canAdded = true;
                    break;
                } else if (bin.getCans().get(0).getType().equals(can.getType())) {
                    bin.addToCans(can);
                    this.canList.add(can);
                    canAdded = true;
                    break;
                }
            }
            if (!canAdded) {
                System.out.println("No available bin for food type: " + can.getType());
            }
        }
    }

    private void initBins() {
        for (int i = 0; i < 10; i++) {
            Bin bin = new Bin();
            bin.setId(i + 1);
            bins.add(bin);
        }
    }

    public void listAllBeans() {
        for (Bin bin : bins) {
            System.out.println(bin);
        }
    }

    public void getSingleBin(int id) {
        for (Bin bin : bins) {
            if (bin.getId() == id) {
                if (bin.getCans().size() > 0) {
                    for (Can can : bin.getCans()) {
                        System.out.println(can);
                    }
                } else {
                    System.out.println("Bin is empty");
                }
            }
        }
    }

    public void removeAllExpiredFood() {
        for (int b = 0; b < bins.size(); b++) {
            Bin currentBin = bins.get(b);
            List<Can> cans = currentBin.getCans();
            if (!currentBin.isBinEmpty()) {
                currentBin.setMinExpDate(LocalDate.MAX);
            }
            for (int c = 0; c < cans.size(); c++) {
                Can can = cans.get(c);
                if (LocalDate.now().isAfter(can.getExpiryDate())) {
                    currentBin.getCans().remove(can);
                    c--;
                    continue;
                }
                if (currentBin.getMinExpDate().isAfter(can.getExpiryDate())) {
                    currentBin.setMinExpDate(can.getExpiryDate());
                }
            }
        }
    }

    public void addCanToBin(Can can) {
        boolean canAdded = false;
        for (Bin bin : bins) {
            if (!bin.isBinEmpty()) {
                String canFoodType = can.getType();
                String binFoodType = bin.getFoodType();
                if (canFoodType.equalsIgnoreCase(binFoodType)) {
                    can.setBinId(bin.getId());
                    bin.addToCans(can);
                    canAdded = true;
                    break;
                }
            }
        }
        if (!canAdded) {
            for (Bin bin : bins) {
                if (bin.isBinEmpty()) {
                    bin.addToCans(can);
                    canAdded = true;
                    break;
                }
            }
            if (!canAdded) {
                System.out.println("No Bin for food type: " + can.getType());
            }
        }
    }

    public void removeCan(int id) {
        for (Bin bin : bins) {
            for (Can can : bin.getCans()) {
                if (can.getId() == id) {
                    bin.getCans().remove(can);
                    this.canList.remove(can);
                    bins.remove(bin);
                    return;
                }
            }
        }
        System.out.println("This can does not exist");
    }

    public void printErrors(String error) {
        if (error.equals("")) {
            System.out.println("Oops! Something went wrong...");
        }
        System.out.println(error);
        System.out.println("============================");
    }

    public void printMenu() {
        System.out.println("===== WAREHOUSE MENU =====");
        System.out.println("1 - List all bins");
        System.out.println("2 - List a single bin");
        System.out.println("3 - Add food");
        System.out.println("4 - Remove food");
        System.out.println("5 - Remove all expired food");
        System.out.println("0 - Save and exit");
        System.out.print("Please enter your choice: ");
    }

    public void chooseAction() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            try {
                printMenu();
                choice = scanner.nextInt();
            } catch (Exception e) {
                printErrors("You chose wrong command, read more carefully...");
            }
            if (choice < 0 || choice > 5) {
                printErrors("Please enter valid number!");
            } else {
                switch (choice) {
                    case 1: //*
                        listAllBeans();
                        break;
                    case 2: //*
                        System.out.print("Please enter bin id: ");
                        int binId = -1;
                        try {
                            binId = scanner.nextInt();
                        } catch (Exception e) {
                            printErrors("Please enter a valid number.");
                        }
                        if (binId > -1 && binId < bins.size()) {
                            getSingleBin(binId);
                        } else {
                            printErrors("Out of bounds...");
                        }
                        break;
                    case 3: //*
                        try {
                            addFood(scanner);
                        } catch (Exception e) {
                            printErrors("");
                        }
                        break;
                    case 4: //*
                        try {
                            System.out.print("Please enter canned food id: ");
                            int canId = scanner.nextInt();
                            removeCan(canId);
                        } catch (Exception e) {
                            printErrors("Bad id sir....and/or mam!");
                        }
                        break;
                    case 5: //*
                        removeAllExpiredFood();
                        break;
                    case 0:
                        try {
                            writeCansToFile("src/canned_food.csv");
                        } catch (Exception e) {
                            printErrors("Save file error!\nTry again, maybe it will work this time(it won't)");
                        }
                        System.out.println("I'm out, peace!");
                        break;
                }
            }
        } while (choice != 0);
    }

    private void addFood(Scanner scanner) {
        System.out.print("Please enter food id: ");
        int foodID = scanner.nextInt();
        while (!isFoodIdNew(foodID)) {
            System.out.print("Food id already exists, enter new food Id: ");
            foodID = scanner.nextInt();
        }
        scanner.nextLine();
        System.out.print("Please enter food type: ");
        String foodType = scanner.nextLine();
        System.out.print("Please enter expiry date in YEAR-MONTH-DAY format: ");
        LocalDate expiryDate = LocalDate.parse(scanner.nextLine());
        addCanToBin(new Can(foodID, foodType, expiryDate, 0));
    }

    private boolean isFoodIdNew(int foodID) {
        for (Can can : this.canList) {
            if (can.getId() == foodID) {
                return false;
            }
        }
        return true;
    }

    public void writeCansToFile(String path) throws Exception {
        String[] header = {"id", "type", "expiry_date", "bin_id"};
        List<Can> cans = getCanList();
        CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
        csvWriter.writeNext(header);
        cans.forEach(can -> {
            csvWriter.writeNext(can.asStringArray());
        });
        csvWriter.close();
    }

    private List<Can> getCanList() {
        List<Can> cans = new ArrayList<>();
        for (Bin bin : bins) {
            cans.addAll(bin.getCans());
        }
        return cans;
    }
}
