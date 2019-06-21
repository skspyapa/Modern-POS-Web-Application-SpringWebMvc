package lk.ijse.dep.spring.finalapp.service;

import java.util.List;

public interface SuperService<T,ID> {
     List<T> getAll() ;

     String save(T dto);

     void remove(ID dtoId);

     void update(T dtoId);

     T get(ID dtoId);
}
