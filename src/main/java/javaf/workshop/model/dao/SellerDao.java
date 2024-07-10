package javaf.workshop.model.dao;

import java.util.List;

import javaf.workshop.model.entities.Department;
import javaf.workshop.model.entities.Seller;

public interface SellerDao {

    void insert(Seller obj);

    void update(Seller obj);

    void deleteById(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();

    List<Seller> findByDepartment(Department department);

}
