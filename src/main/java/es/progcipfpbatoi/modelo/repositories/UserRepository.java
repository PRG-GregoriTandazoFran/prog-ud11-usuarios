package es.progcipfpbatoi.modelo.repositories;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.dto.User;

import java.util.ArrayList;

public class UserRepository {
    private UserDAO userRepository;

    public UserRepository(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> findAll() {
        return this.userRepository.findAll();
    }

    public ArrayList<User> findAll(String email) {
        return this.userRepository.findAll( email );
    }

    public User getById(String dni) throws NotFoundException {
        return this.userRepository.getById( dni );
    }

    public void save(User user) throws DatabaseErrorException {
        this.userRepository.save( user );
    }

    public void remove(User user) throws NotFoundException {
        this.userRepository.remove( user );
    }
}
