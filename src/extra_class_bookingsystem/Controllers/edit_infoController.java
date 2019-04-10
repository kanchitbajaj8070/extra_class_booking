package extra_class_bookingsystem.Controllers;

import extra_class_bookingsystem.Alert_maker.Alert_handler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class edit_infoController {

    @FXML
    private TextField txtnewName;

    @FXML
    private Button update_name_button;

    @FXML
    private TextField txtnewEmail;

    @FXML
    private Button update_email_button;


    public void handle_update_name() throws Exception {

        if (txtnewName.getLength() == 0) {
            Alert_handler.showErrorMessage(" NEW NAME EMPTY", " NEW NAME CANT BE EMPTY\n TYPE SOME VALUE", null);

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("UPDATE NAME ");
            alert.setHeaderText("YOUR NAME WILL BE UPDATED  ");
            alert.setContentText(" ARE U SURE YOU WANT TO PROCEED ?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        String sql = "update employees set name= ? where username =? ";
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection(
                                "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");

                        PreparedStatement preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setString(1, txtnewName.getText());
                        preparedStatement.setString(2, LoginController.uname);
                       int p= preparedStatement.executeUpdate();
                        System.out.println(p);
                        if(p==1)
                        {
                            String names = txtnewName.getText();
                            System.out.println(names);
                            int t1=names.indexOf(' ');
                            if(t1!=-1)
                                LoginController.fname = names.substring(0, t1);
                            else
                                LoginController.fname= names;
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("NAME UPDATE SUCCESFULL ");
                            alert1.setHeaderText("YOUR NAME HAS BEEN UPDATED SUCCESFULLY ");
                            alert1.showAndWait();


                        }
                        else
                        {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("NAME  UPDATE UNSUCCESFULL ");
                            alert1.setHeaderText("YOUR NAME CANT BE UPDATED   ");
                            alert1.showAndWait();
                        }
                        txtnewName.setText(null);

                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}




