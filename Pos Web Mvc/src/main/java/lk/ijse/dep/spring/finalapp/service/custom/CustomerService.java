package lk.ijse.dep.spring.finalapp.service.custom;


import lk.ijse.dep.spring.finalapp.dto.CustomerDTO;
import lk.ijse.dep.spring.finalapp.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService<CustomerDTO,String> {
     List<CustomerDTO> getCustomerId();

     String getMaxCustId();

     boolean isCustomerExists(String id);
}
