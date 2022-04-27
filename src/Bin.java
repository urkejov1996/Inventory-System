import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Bin {
    private int id;
    private String foodType;
    private LocalDate minExpDate;
    private List<Can> cans = new ArrayList<>();

    public Bin() {
    }

    public Bin(int binId, String foodType) {
        this.id = binId;
        this.foodType = foodType;
    }

    public void addToCans(Can can) {
        if (cans.size()==10) {
            System.out.println("Bin is full");
            return;
        }
        if (can.getExpiryDate().isBefore(getMinExpDate())) {
            setMinExpDate(can.getExpiryDate());
        }
        this.cans.add(can);
        this.foodType=can.getType();
    }

    public void setCans(List<Can> cans) {
        this.cans = cans;
    }

    public List<Can> getCans() {
        return cans;
    }

    public LocalDate getMinExpDate() {
        if (minExpDate==null) {
            return LocalDate.MAX;
        }
        return minExpDate;
    }

    public void setMinExpDate(LocalDate minExpDate) {
        this.minExpDate = minExpDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public boolean isBinEmpty () {
        return getCans().isEmpty();
    }

    @Override
    public String toString() {
        return "Bin : {" +
                "No. Bin : " + id +
                ", Type : " + foodType +
                ", No. Cans : " + cans.size() +
                ", Min. Expiry Date : " + minExpDate +
                '}';
    }
}
