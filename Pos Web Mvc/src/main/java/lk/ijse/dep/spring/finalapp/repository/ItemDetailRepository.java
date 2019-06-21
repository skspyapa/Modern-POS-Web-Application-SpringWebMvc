package lk.ijse.dep.spring.finalapp.repository;


import lk.ijse.dep.spring.finalapp.entity.ItemDetail;
import lk.ijse.dep.spring.finalapp.entity.ItemDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, ItemDetailPK> {

}
