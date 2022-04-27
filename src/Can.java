import java.time.LocalDate;

public class Can {
    private int id;
    private String type;
    private LocalDate expiryDate;

    private int binId;

    public Can() {
    }

    public Can(int id, String type, LocalDate expiryDate, int binId) {
        this.id = id;
        this.type = type;
        this.expiryDate = expiryDate;
        this.binId = binId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getBinId() {
        return binId;
    }

    public void setBinId(int binId) {
        this.binId = binId;
    }

    @Override
    public String toString() {
        return "Can{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public String[] asStringArray() {
        return new String[]{String.valueOf(this.id), this.type, String.valueOf(this.expiryDate), String.valueOf(this.binId)};
    }
}
