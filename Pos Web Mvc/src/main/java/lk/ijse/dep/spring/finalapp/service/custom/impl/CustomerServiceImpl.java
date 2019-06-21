package lk.ijse.dep.spring.finalapp.service.custom.impl;

import lk.ijse.dep.spring.finalapp.dto.CustomerDTO;
import lk.ijse.dep.spring.finalapp.entity.Customer;
import lk.ijse.dep.spring.finalapp.repository.CustomerRepository;
import lk.ijse.dep.spring.finalapp.service.custom.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAll(){

        List<CustomerDTO> collect = customerRepository.findAll().stream().map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary())).collect(Collectors.toList());
        return collect;

    }

    public String save(CustomerDTO dto) {
        System.out.println(dto);
        return customerRepository.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary())).getId();

    }

    public void remove(String id){
        customerRepository.deleteById(id);

    }

    public void update(CustomerDTO dto){
        customerRepository.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));

    }

    public CustomerDTO get(String id){
        Customer customer = customerRepository.getOne(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary());
    }

    public List<CustomerDTO> getCustomerId(){
        List<CustomerDTO> collect = customerRepository.findAll().stream().map(customer -> new CustomerDTO(customer.getId())).collect(Collectors.toList());
        return collect;
    }

    public String getMaxCustId(){
        String maxCustId = customerRepository.getTopCustomerId();
        return maxCustId;
    }

    @Override
    public boolean isCustomerExists(String id){

        return customerRepository.existsById(id);
    }
}
