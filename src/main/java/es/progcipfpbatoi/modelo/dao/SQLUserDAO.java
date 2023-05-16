package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.User;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class SQLUserDAO implements UserDAO {
    private              Connection connection;
    private static final String     TABLE_NAME = "users";

    @Override
    public ArrayList<User> findAll() throws DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s", TABLE_NAME );

        ArrayList<User> tareas = new ArrayList<>();
        connection = new MySqlConnection( "localhost", "users_db", "root", "1234" ).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery( sql );
        ) {

            while ( resultSet.next() ) {
                User tarea = geUserFromResultset( resultSet );
                tareas.add( tarea );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en la conexión o acceso a la base de datos (select)" );
        }

        return tareas;

    }

    @Override
    public ArrayList<User> findAll(String email) {
        ArrayList<User> userContaisnEmailList = new ArrayList<>();
        try {
            for ( User user : findAll() ) {
                if ( user.hasSame( email ) ) {
                    userContaisnEmailList.add( user );
                }
            }
        } catch ( DatabaseErrorException e ) {
            System.out.println( e.getMessage() );
        }

        return userContaisnEmailList;

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

    private User geUserFromResultset(ResultSet rs) throws SQLException {
        String    name        = rs.getString( "name" );
        String    surname     = rs.getString( "surname" );
        String    dni         = rs.getString( "dni" );
        String    email       = rs.getString( "email" );
        String    zipCde      = rs.getString( "zipCode" );
        String    mobilePhone = rs.getString( "mobilePhone" );
        LocalDate birthday    = rs.getDate( "birthday" ).toLocalDate();
        String    password    = rs.getString( "password" );
        return new User( name, surname, dni, email, zipCde, mobilePhone, birthday, password );
    }

}
