package lk.ijse.dep.spring.finalapp.service.custom.impl;


import lk.ijse.dep.spring.finalapp.dto.ItemDetailDTO;
import lk.ijse.dep.spring.finalapp.dto.OrdersDTO;
import lk.ijse.dep.spring.finalapp.entity.CustomEntity;
import lk.ijse.dep.spring.finalapp.entity.Item;
import lk.ijse.dep.spring.finalapp.entity.ItemDetail;
import lk.ijse.dep.spring.finalapp.entity.Orders;
import lk.ijse.dep.spring.finalapp.repository.*;
import lk.ijse.dep.spring.finalapp.service.custom.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemDetailRepository itemDetailRepository;

    @Autowired
    private QueryRepository queryRepository;

    public List<OrdersDTO> getAll(){

        List<Orders> allOrders = ordersRepository.findAll();
        List<OrdersDTO> dtos = new ArrayList<>();
        for (Orders orders : allOrders) {
            OrdersDTO ordersDTO = new OrdersDTO(orders.getId(), orders.getDate(), orders.getCustomer().getId());
            dtos.add(ordersDTO);
        }
        return dtos;
    }

    public String save(OrdersDTO dto){

        Orders orders = new Orders(dto.getId(), dto.getDate(),customerRepository.getOne(dto.getCustomer_id()));
        return ordersRepository.save(orders).getId();
    }

    public void remove(String id){
        ordersRepository.deleteById(id);

    }

    public void update(OrdersDTO dto){
        Orders orders = new Orders(dto.getId(), dto.getDate(),customerRepository.getOne(dto.getCustomer_id()));
        ordersRepository.save(orders);

    }

    @Override
    public OrdersDTO get(String id){
        Orders orders = ordersRepository.getOne(id);
        List<ItemDetailDTO> itemDetailDTOS=new ArrayList<>();

        for (ItemDetail itemDetail:orders.getItemDetails()) {
            itemDetailDTOS.add(new ItemDetailDTO(itemDetail.getOrders().getId(),itemDetail.getItem().getCode(),itemDetail.getQty(),itemDetail.getUnitPrice()));
        }
        return new OrdersDTO(orders.getId(), orders.getDate(), orders.getCustomer().getId(),itemDetailDTOS);
    }





    public String getdMaxId() {
        String maxId = ordersRepository.getTopOrdersId();
        return maxId;
    }
    @Override
    public boolean isOrderExists(String id) {
        return ordersRepository.existsById(id);
    }

    @Override
    public List<OrdersDTO> getOrders(String ordId) {
        List<CustomEntity> orders = queryRepository.getOrders(ordId);
        System.out.println(orders);
        List<OrdersDTO> ordersDTOS=new ArrayList<>();
        for (CustomEntity customEntity:orders) {

            ordersDTOS.add(new OrdersDTO(customEntity.getId(),customEntity.getCustomerId(),customEntity.getDate(),customEntity.getSum()));
        }
        return ordersDTOS;
    }


    public boolean placeOrder(OrdersDTO dto) throws Exception {


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date parse = sdf.parse(dto.getDate());
        sdf.applyPattern("yyyy-MM-dd");
        String format = sdf.format(parse);
        ordersRepository.save(new Orders(dto.getId(), format,customerRepository.getOne(dto.getCustomer_id())));

        for (ItemDetailDTO itemDetailDTO : dto.getItemDetailDTOS()) {

            ItemDetail save = itemDetailRepository.save(new ItemDetail(itemDetailDTO.getOrderId(), itemDetailDTO.getItemCode(), itemDetailDTO.getQty(), itemDetailDTO.getUnitPrice()));
            Item item = itemRepository.getOne(itemDetailDTO.getItemCode());

            int qty = item.getQtyOnHand() - itemDetailDTO.getQty();

            item.setQtyOnHand(qty);

            itemRepository.save(item);
        }
        return true;
//        Connection connection = DBConnection.getInstance().getConnection();
//        boolean result = false;
//        try {
//            connection.setAutoCommit(false);
//            result = ordersDAO.save(new Orders(lk.ijse.dep.dto.getId(), lk.ijse.dep.dto.getDate(), lk.ijse.dep.dto.getCustomer_id()));
//            if (!result) {
//                connection.rollback();
//                return false;
//            }
//            for (ItemDetailDTO itemDetailDTO : lk.ijse.dep.dto.getItemDetailDTOS()) {
//                result = itemDetailDAO.save(new ItemDetail(itemDetailDTO.getId(), itemDetailDTO.getItemCode(), itemDetailDTO.getQty(), itemDetailDTO.getUnitPrice()));
//                if (!result) {
//                    connection.rollback();
//                    return false;
//                }
//                Item item = itemDAO.find(itemDetailDTO.getItemCode());
//                int qty = item.getQtyOnHand() - itemDetailDTO.getQty();
//                item.setQtyOnHand(qty);
//                result = itemDAO.update(item);
//                if (!result) {
//                    connection.rollback();
//                    return false;
//                }
//            }
//            connection.commit();
//            return true;
//        } catch (SQLException ex) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            ex.printStackTrace();
//            return false;
//        } catch (Throwable t) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            t.printStackTrace();
//            return false;
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return true;
    }

}
