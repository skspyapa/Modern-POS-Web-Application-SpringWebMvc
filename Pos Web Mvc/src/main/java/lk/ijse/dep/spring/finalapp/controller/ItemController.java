package lk.ijse.dep.spring.finalapp.controller;

import lk.ijse.dep.spring.finalapp.dto.ItemDTO;
import lk.ijse.dep.spring.finalapp.service.custom.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems() {
        return itemService.getAll();
    }

    @GetMapping(value = "{code:P\\d{3}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> getItem(@PathVariable String code) {
        ItemDTO itemDTO = null;
        if (itemService.isItemExists(code)) {
            itemDTO = itemService.get(code);
        }
        return new ResponseEntity<ItemDTO>(itemDTO, (itemDTO != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveItem(@RequestBody ItemDTO itemDTO) {
        if (itemDTO.getCode().isEmpty() || itemDTO.getDescription().isEmpty() || String.valueOf(itemDTO.getQtyOnHand()).isEmpty() || String.valueOf(itemDTO.getUnitPrice()).isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        if (!itemService.isItemExists(itemDTO.getCode())) {
            String itemCode = itemService.save(itemDTO);
            return new ResponseEntity<String>("\""+itemCode+"\"", HttpStatus.CREATED);
        } else {
            throw new RuntimeException("Item Already In The List");
        }
    }

    @PutMapping(value = "{code:P\\d{3}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@PathVariable("code") String code, @RequestBody ItemDTO itemDTO) {
        if (!itemService.isItemExists(code)) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        if (itemDTO.getCode().isEmpty() || itemDTO.getDescription().isEmpty() || String.valueOf(itemDTO.getQtyOnHand()).isEmpty() || String.valueOf(itemDTO.getUnitPrice()).isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            itemService.save(itemDTO);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = "{code:P\\d{3}}")
    public ResponseEntity<Void> deleteItem(@PathVariable("code") String code) {
        if (!itemService.isItemExists(code)) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            itemService.remove(code);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

    }
}
