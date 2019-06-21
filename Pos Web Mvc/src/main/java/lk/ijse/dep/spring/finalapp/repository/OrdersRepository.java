package lk.ijse.dep.spring.finalapp.repository;


import lk.ijse.dep.spring.finalapp.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepository extends JpaRepository<Orders,String> {

    @Query(value = "SELECT o.id from Orders o ORDER BY o.id DESC limit 1",nativeQuery = true )
    String getTopOrdersId();
}
