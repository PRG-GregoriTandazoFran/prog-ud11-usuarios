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
        try {
            return this.userRepository.findAll();
        } catch ( DatabaseErrorException e ) {
            throw new RuntimeException( e );
        }
    }

    public ArrayList<User> findAll(String email) {
        return this.userRepository.findAll( email );
    }

    public User getById(String dni) throws NotFoundException {
        try {
            return this.userRepository.getByDni( dni );
        } catch ( DatabaseErrorException e ) {
            throw new RuntimeException( e );
        }
    }

    public void save(User user) throws DatabaseErrorException {
        this.userRepository.save( user );
    }

    public void remove(User user) throws NotFoundException {
        this.userRepository.remove( user );
    }
}
