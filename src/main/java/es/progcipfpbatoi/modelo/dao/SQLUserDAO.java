package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.User;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SQLUserDAO implements UserDAO {
    private              Connection connection;
    private static final String     IP_HOST    = "ip";
    private static final String     TABLE_NAME = "users";

    @Override
    public ArrayList<User> findAll() throws DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s", TABLE_NAME );

        ArrayList<User> tareas = new ArrayList<>();
        connection = new MySqlConnection( IP_HOST, "users_db", "root", "1234" ).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery( sql )
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
    public User getById(String dni) throws NotFoundException, DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s WHERE dni = ?", TABLE_NAME );
        connection = new MySqlConnection( IP_HOST, "users_db", "root", "1234" ).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            statement.setString( 3, dni );
            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                User user = geUserFromResultset( resultSet );
                if ( user.getDni().equals( dni ) ) {
                    return user;
                }
            }

            throw new NotFoundException( "No existe un usuario con el dni " + dni );

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (select)" );
        }

    }

    @Override
    public void save(User user) throws DatabaseErrorException {

    }

    private User insert(User user) throws DatabaseErrorException {
        String sql = String.format( "INSERT INTO %s (name, surname, dni, email, zipCode, mobilePhone, birthday, password) VALUES (?,?,?,?,?,?,?,?)",
                TABLE_NAME );
        connection = new MySqlConnection( IP_HOST, "user_db", "root", "1234" ).getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            preparedStatement.setString( 1, user.getName() );
            preparedStatement.setString( 2, user.getSurname() );
            preparedStatement.setString( 3, user.getDni() );
            preparedStatement.setString( 4, user.getEmail() );
            preparedStatement.setString( 5, user.getZipCode() );
            preparedStatement.setString( 6, user.getMobilePhone() );
            preparedStatement.setDate( 7, Date.valueOf( user.getBirthday() ) );
            preparedStatement.setString( 8, user.getPassword() );
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if ( resultSet.next() ) {
                user.setDni( resultSet.getString( 3 ) );
            }

            return user;

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (insert)" );
        }
    }


    private User update(User user) throws DatabaseErrorException {
        String sql = String.format( "UPDATE %s SET name = ?, surname = ?, dni = ?, email = ?, zipCode = ?, mobilePhone = ?, birthday = ?, password = ? ",
                TABLE_NAME );

        try (
                Connection connection = new MySqlConnection( IP_HOST, "users_db", "root", "1234" ).getConnection();
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            statement.setString( 1, user.getName() );
            statement.setString( 2, user.getSurname() );
            statement.setString( 3, user.getDni() );
            statement.setString( 4, user.getEmail() );
            statement.setString( 5, user.getZipCode() );
            statement.setString( 6, user.getMobilePhone() );
            statement.setDate( 7, Date.valueOf( user.getBirthday() ) );
            statement.setString( 8, user.getPassword() );
            statement.executeUpdate();

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (update)" );
        }

        return user;
    }


    @Override
    public void remove(User user) throws NotFoundException {
        String sql = String.format( "DELETE FROM %s WHERE dni = ?", TABLE_NAME );
        connection = new MySqlConnection( IP_HOST, "users_db", "root", "1234" ).getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            statement.setString( 3, user.getDni() );
            statement.executeUpdate();

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new NotFoundException( "Usuario con " + user.getDni() + " no encontrado" );
        }

    }

    private User geUserFromResultset(ResultSet rs) throws SQLException {
        String    name        = rs.getString( "name" );
        String    surname     = rs.getString( "surname" );
        String    dni         = rs.getString( "dni" );
        String    email       = rs.getString( "email" );
        String    zipCode     = rs.getString( "zipCode" );
        String    mobilePhone = rs.getString( "mobilePhone" );
        LocalDate birthday    = rs.getDate( "birthday" ).toLocalDate();
        String    password    = rs.getString( "password" );
        return new User( name, surname, dni, email, zipCode, mobilePhone, birthday, password );
    }

}
