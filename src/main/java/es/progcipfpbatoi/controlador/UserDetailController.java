package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.User;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.util.AlertMessages;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailController extends UserFormController {

    private User user;

    public UserDetailController(
            User user,
            UserDAO userDAO,
            Initializable controladorPadre,
            String vistaPadre) {

        super( userDAO, controladorPadre, vistaPadre);
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldDNI.setText(user.getDni());
        textFieldName.setText(user.getName());
        textFieldSurname.setText(user.getSurname());
        textFieldEmail.setText(user.getEmail());
        textFieldPhone.setText(user.getMobilePhone());
        datePickerBirthDate.setValue(user.getBirthday());
        textFieldZipCode.setText(user.getZipCode());
        passwordField1.setText(user.getPassword());
        passwordField2.setText(user.getPassword());
    }

    @Override
    protected void saveUser(User user) throws DatabaseErrorException {
        userDAO.save(user);
        AlertMessages.mostrarAlertInformacion("Cambios guardados con éxito");
    }
}
