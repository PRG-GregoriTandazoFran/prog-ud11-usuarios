package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.User;

import java.util.ArrayList;

public interface UserDAO {
    ArrayList<User> findAll() throws DatabaseErrorException;
    ArrayList<User> findAll(String email);

    User getById(String dni) throws NotFoundException, DatabaseErrorException;

    void save(User user) throws DatabaseErrorException;
    void remove(User user) throws NotFoundException;
}
