package Database.GetterSetter;

public class Bill_items {
    private int id;
    private int bill_id;
    private int product_id;
    private int quantity;

    public Bill_items(int id, int bill_id, int product_id, int quantity) {
        this.id = id;
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    // getters & setters

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setBillid(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getbillId() {
        return bill_id;
    }

    public void productId(int product_id) {
        this.product_id = product_id;
    }

    public int getproductId() {
        return product_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}