package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.User;

import java.util.ArrayList;

public class SQLUserDAO implements UserDAO {
    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public ArrayList<User> findAll(String email) {
        return null;
    }

    @Override
    public User getById(String dni) throws NotFoundException {
        return null;
    }

    @Override
    public void save(User user) throws DatabaseErrorException {

    }

    @Override
    public void remove(User user) throws NotFoundException {

    }
}
