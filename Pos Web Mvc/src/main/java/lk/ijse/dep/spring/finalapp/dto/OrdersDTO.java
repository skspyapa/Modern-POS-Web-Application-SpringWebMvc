package lk.ijse.dep.spring.finalapp.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OrdersDTO{
private String id;
private String date;
@JsonProperty("customer_id")
private String customer_id;
private String maxId;
private double sum;

    private List<ItemDetailDTO> itemDetailDTOS= new ArrayList<>();
    public OrdersDTO() {
    }

    public OrdersDTO(String maxId) {
        this.maxId = maxId;
    }

    public OrdersDTO(String id, String date, String customer_id) {
        this.id = id;
        this.date = date;
        this.customer_id = customer_id;
    }

    public OrdersDTO(String id, String customer_id, String date, double sum) {
        this.id = id;
        this.date = date;
        this.customer_id = customer_id;
        this.sum = sum;
    }

    public OrdersDTO(String id, String date, String customer_id, List<ItemDetailDTO> itemDetailDTOS) {
        this.id = id;
        this.date = date;
        this.customer_id = customer_id;
        this.setItemDetailDTOS(itemDetailDTOS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_Id) {
        this.customer_id = customer_Id;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    public List<ItemDetailDTO> getItemDetailDTOS() {
        return itemDetailDTOS;
    }

    public void setItemDetailDTOS(List<ItemDetailDTO> itemDetailDTOS) {
        this.itemDetailDTOS = itemDetailDTOS;
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", customer_id='" + customer_id + '\'' +
                ", maxId='" + maxId + '\'' +
                ", itemDetailDTOS=" + itemDetailDTOS +
                '}';
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
