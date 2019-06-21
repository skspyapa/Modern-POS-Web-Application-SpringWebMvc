package lk.ijse.dep.spring.finalapp.service.custom;


import lk.ijse.dep.spring.finalapp.dto.OrdersDTO;
import lk.ijse.dep.spring.finalapp.entity.CustomEntity;
import lk.ijse.dep.spring.finalapp.service.SuperService;

import java.util.List;

public interface OrdersService extends SuperService<OrdersDTO,String> {

    String getdMaxId();

    boolean placeOrder(OrdersDTO dto) throws Exception;

    boolean isOrderExists(String id);

    List<OrdersDTO> getOrders(String ordId);
}
