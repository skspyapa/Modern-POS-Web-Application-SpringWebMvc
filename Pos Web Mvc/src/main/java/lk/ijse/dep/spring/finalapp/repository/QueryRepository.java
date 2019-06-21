package lk.ijse.dep.spring.finalapp.repository;

import lk.ijse.dep.spring.finalapp.dto.OrdersDTO;
import lk.ijse.dep.spring.finalapp.entity.CustomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueryRepository {
    List<CustomEntity> getOrders(String ordId);
}
