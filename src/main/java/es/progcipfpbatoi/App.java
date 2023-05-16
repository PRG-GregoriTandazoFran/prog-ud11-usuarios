package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.UserController;
import es.progcipfpbatoi.modelo.dao.InMemoryUserDAO;
import es.progcipfpbatoi.modelo.dao.SQLUserDAO;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.repositories.UserRepository;
import es.progcipfpbatoi.services.MySqlConnection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //InMemoryUserDAO inMemoryUserRepository = new InMemoryUserDAO();
        SQLUserDAO     sqlUserDAO = new SQLUserDAO();
        UserRepository userDAO    = new UserRepository( sqlUserDAO );

        UserController userController = new UserController( userDAO );
        ChangeScene.change( stage, userController, "/vistas/user_list.fxml" );

        // Cerramos la conexión al cerrar la aplicación
        stage.setOnCloseRequest( new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                System.out.println( "App closed" );
                new MySqlConnection( SQLUserDAO.IP_HOST, SQLUserDAO.DATABASE_NAME, SQLUserDAO.USERNAME, "1234" ).closeConnection();
            }
        } );

    }

    public static void main(String[] args) {
        launch();
    }

}