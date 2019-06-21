package lk.ijse.dep.spring.finalapp.repository;


import lk.ijse.dep.spring.finalapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {

@Query(value = "SELECT c.id from Customer c ORDER BY c.id DESC limit 1",nativeQuery = true)
String getTopCustomerId();


//@Query(value = "SELECT * from customer c where c.name like :#{#name + '%'} and c.address like :#{#address + '%'} ORDER BY c.id DESC ",nativeQuery = true)
//List<Customer> customQuery(String name, String address);
}
