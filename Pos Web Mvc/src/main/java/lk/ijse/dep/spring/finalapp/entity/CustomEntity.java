package lk.ijse.dep.spring.finalapp.entity;

public class CustomEntity extends SuperEntity{
    private String maxId;
    private String id;
    private String customer_Id;
    private String date;
    private double sum;

    public CustomEntity(String id, String customer_Id, String date, double sum) {
        this.id = id;
        this.customer_Id = customer_Id;
        this.date = date;
        this.sum = sum;
    }

    public CustomEntity() {
    }

    public CustomEntity(String maxId) {
        this.maxId = maxId;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customer_Id;
    }

    public void setCustomerId(String customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "maxId='" + maxId + '\'' +
                ", id='" + id + '\'' +
                ", customer_Id='" + customer_Id + '\'' +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                '}';
    }
}
