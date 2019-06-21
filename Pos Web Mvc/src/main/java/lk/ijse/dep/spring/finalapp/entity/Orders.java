package lk.ijse.dep.spring.finalapp.entity;

import lk.ijse.dep.spring.finalapp.dto.CustomerDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`Orders`")
public class Orders extends SuperEntity{
    @Id
    private String id;
    private String date;
    @ManyToOne
    @JoinColumn(name = "customer_Id",referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy ="orders",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ItemDetail> itemDetails=new ArrayList<>();

    public Orders() {
    }

    public Orders(String id, String date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer=customer;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", customer=" + customer +
                '}';
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }
    public void addItemDetail(ItemDetail itemDetail){
        itemDetail.setOrders(this);
        this.getItemDetails().add(itemDetail);
    }
}
