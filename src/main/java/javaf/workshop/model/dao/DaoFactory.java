package javaf.workshop.model.dao;


import javaf.workshop.db.DB;
import javaf.workshop.model.dao.impl.DepartmentDaoJDBC;
import javaf.workshop.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }


    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
