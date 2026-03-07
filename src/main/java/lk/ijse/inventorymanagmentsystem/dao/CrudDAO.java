package lk.ijse.inventorymanagmentsystem.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{

    public boolean save (T DTO) throws SQLException ;

    public boolean update(T DTO) throws SQLException ;

//    public boolean delete(T DTO) throws SQLException ;

    public List<T> getAll() throws SQLException;

    public List<T> search(String search) throws SQLException;





}
