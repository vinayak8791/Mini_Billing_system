package Database.GetterSetter;

public class Bill {
    private int id;
    private double total;
    private String date;

    public Bill(int id, double total, String date){
        this.id=id;
        this.total=total;
        this.date=date;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
