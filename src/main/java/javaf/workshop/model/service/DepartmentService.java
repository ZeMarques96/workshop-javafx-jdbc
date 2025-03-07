package javaf.workshop.model.service;

import javaf.workshop.model.dao.DaoFactory;
import javaf.workshop.model.dao.DepartmentDao;
import javaf.workshop.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll(){
        return dao.findAll();
    }

    public void saveOrUpdate(Department obj){
        if (obj.getId() == null){
            dao.insert(obj);
        }
        else{
            dao.update(obj);
        }
    }

    public void remove(Department obj){
        dao.deleteById(obj.getId());
    }
}
