package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.UserController;
import es.progcipfpbatoi.modelo.dao.InMemoryUserDAO;
import es.progcipfpbatoi.modelo.dao.SQLUserDAO;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.repositories.UserRepository;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        InMemoryUserDAO inMemoryUserRepository = new InMemoryUserDAO();
        SQLUserDAO     sqlUserDAO     = new SQLUserDAO();
        UserRepository userDAO        = new UserRepository( sqlUserDAO );

        UserController userController = new UserController( userDAO );
        ChangeScene.change( stage, userController, "/vistas/user_list.fxml" );
    }

    public static void main(String[] args) {
        launch();
    }

}