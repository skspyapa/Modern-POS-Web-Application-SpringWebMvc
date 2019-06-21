package lk.ijse.dep.spring.finalapp.controller;

import lk.ijse.dep.spring.finalapp.dto.CustomerDTO;
import lk.ijse.dep.spring.finalapp.service.custom.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(params="maxCustomerId=true",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMaxCustomerId(){

        return new ResponseEntity<String>("\""+customerService.getMaxCustId()+"\"",HttpStatus.OK);
    }


    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping(value = "/{id:C\\d{3}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") String id) {
        CustomerDTO customerDTO = null;
        if (customerService.isCustomerExists(id)) {
            customerDTO = customerService.get(id);
        }
        return new ResponseEntity<CustomerDTO>(customerDTO, (customerDTO != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerDTO customerDTO) {

        if (customerDTO.getId().isEmpty() || customerDTO.getAddress().isEmpty() || customerDTO.getName().isEmpty()){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        }
        if (!customerService.isCustomerExists(customerDTO.getId())){
            String customerId = customerService.save(customerDTO);
            System.out.println(customerId);
            return new ResponseEntity<String>("\"" + customerId +"\"", HttpStatus.CREATED);
        } else {
            throw new RuntimeException("Customer Already In The List");
        }
    }

    @DeleteMapping(value = "/{id:C\\d{3}}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id) {
        if (!customerService.isCustomerExists(id)) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            customerService.remove(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
    @PutMapping(value = "{id:C\\d{3}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDTO customerDTO) {
        if (!customerService.isCustomerExists(id)) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        if (customerDTO.getId().isEmpty() || customerDTO.getId().isEmpty() || customerDTO.getAddress().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            customerService.save(customerDTO);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

}
