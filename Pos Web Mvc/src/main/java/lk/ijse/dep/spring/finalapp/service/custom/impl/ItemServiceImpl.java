package lk.ijse.dep.spring.finalapp.service.custom.impl;


import lk.ijse.dep.spring.finalapp.dto.ItemDTO;
import lk.ijse.dep.spring.finalapp.entity.Item;
import lk.ijse.dep.spring.finalapp.repository.ItemRepository;
import lk.ijse.dep.spring.finalapp.service.custom.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

public List<ItemDTO> getAll(){

        List<ItemDTO> collect = itemRepository.findAll().stream().map(item -> new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand())).collect(Collectors.toList());
        return collect;

}
    public String save(ItemDTO dto){
       return itemRepository.save(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand())).getCode();
    }

    public void remove(String  code) {
        itemRepository.deleteById(code);

    }

    public void update(ItemDTO dto){

        itemRepository.save(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));

    }
    public ItemDTO get(String code){
            Item item = itemRepository.getOne(code);
            return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());


    }

    public String getMaxItemCode() throws Exception {
            String maxItemCode = itemRepository.getTopItemCode();
         return maxItemCode;
    }

    @Override
    public boolean isItemExists(String code) {
        return itemRepository.existsById(code);
    }
}
