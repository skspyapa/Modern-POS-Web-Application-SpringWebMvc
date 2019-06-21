package lk.ijse.dep.spring.finalapp.controller;


import lk.ijse.dep.spring.finalapp.dto.OrdersDTO;
import lk.ijse.dep.spring.finalapp.service.custom.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public List<OrdersDTO> getAllCustomers() {
        return ordersService.getAll();
    }

    // /api/v1/orders?maxOrderId=true

    @GetMapping(params="maxOrderId=true",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMaxOrderId(){
        return new ResponseEntity<String>("\""+ordersService.getdMaxId()+"\"",HttpStatus.OK);
    }
    @GetMapping(params="q",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrdersDTO>> getOrder(@RequestParam("q") String ordId) {
        List<OrdersDTO> orders = null;

        if(ordId.equalsIgnoreCase("a")){
            orders = ordersService.getOrders("D");

        }else if (!ordId.equalsIgnoreCase("a")) {
            System.out.println(ordId);
            orders = ordersService.getOrders(ordId);
        }
        return new ResponseEntity<List<OrdersDTO>>(orders, (orders != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

//    @GetMapping(value = "/{id:D\\d{3}}",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<OrdersDTO> getOrder(@PathVariable("id") String id){
//        OrdersDTO ordersDTO=null;
//        if(ordersService.isOrderExists(id)){
//            ordersDTO=ordersService.get(id);
//        }
//        return new ResponseEntity<OrdersDTO>(ordersDTO,(ordersDTO!=null)? HttpStatus.OK: HttpStatus.NOT_FOUND);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveOrder(@RequestBody OrdersDTO ordersDTO) throws Exception{
        System.out.println(ordersDTO);
        if(ordersDTO.getId().isEmpty() || ordersDTO.getCustomer_id().toString().isEmpty() || ordersDTO.getDate().toString().isEmpty() || ordersDTO.getItemDetailDTOS().isEmpty()){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }if(!ordersService.isOrderExists(ordersDTO.getId())){

            ordersService.placeOrder(ordersDTO);
            return new ResponseEntity<String>("\""+ordersDTO.getId()+"\"",HttpStatus.CREATED);
        }else {
            throw new RuntimeException("Order Already In The List");
        }
    }

//    @DeleteMapping(value ="/{id:D\\d{3}}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable("id") String id){
//        if(!ordersService.isOrderExists(id)){
//            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//        }else{
//            ordersService.remove(id);
//            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//        }
//    }
//
//    @PutMapping(path = "/{id:D\\d{3}}",consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> updateOrder(@PathVariable("id") String id,@RequestBody OrdersDTO ordersDTO){
//
//        if(!ordersService.isOrderExists(id)){
//            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//        }if(ordersDTO.getId().isEmpty() || ordersDTO.getCustomer_id().toString().isEmpty() || ordersDTO.getDate().toString().isEmpty() || ordersDTO.getItemDetailDTOS().isEmpty()) {
//            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//        }else {
//            ordersService.update(ordersDTO);
//            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//        }
//
//    }

}
