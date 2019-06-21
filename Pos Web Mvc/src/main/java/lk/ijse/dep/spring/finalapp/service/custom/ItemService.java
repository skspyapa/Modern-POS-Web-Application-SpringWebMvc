package lk.ijse.dep.spring.finalapp.service.custom;


import lk.ijse.dep.spring.finalapp.dto.ItemDTO;
import lk.ijse.dep.spring.finalapp.service.SuperService;

public interface ItemService extends SuperService<ItemDTO,String> {

    String getMaxItemCode() throws Exception;

    boolean isItemExists(String code);
}
