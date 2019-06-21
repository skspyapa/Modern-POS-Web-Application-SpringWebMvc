package lk.ijse.dep.spring.finalapp.repository.impl;

import lk.ijse.dep.spring.finalapp.dto.OrdersDTO;
import lk.ijse.dep.spring.finalapp.entity.CustomEntity;
import lk.ijse.dep.spring.finalapp.repository.QueryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QueryDAOImpl implements QueryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<CustomEntity> getOrders(String ordId) {

        List<Object[]> list= em.createNativeQuery("SELECT o.id,o.customer_Id,o.date,sum(i.qty*i.unitPrice) from orders o INNER join itemdetail i where o.id=i.orderId and o.id like ?1 group by o.id")
                .setParameter(1, ordId+"%")
                .getResultList();
        List<CustomEntity> al = new ArrayList<>();

        for (Object[] objects : list) {
            al.add(new CustomEntity((String) objects[0],(String) objects[1],String.valueOf(objects[2]),(double)objects[3]));
        }

        return al;
    }
}
