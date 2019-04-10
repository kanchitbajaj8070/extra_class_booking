package extra_class_bookingsystem.Controllers;
import extra_class_bookingsystem.Alert_maker.Alert_handler;


import java.sql.*;
import extra_class_bookingsystem.Mail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class LoginController {
    static String uname;
    static String password;
    static String fname;
    public static String email;
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Label btnForgot;

    @FXML
    void handleButtonAction(MouseEvent event) throws Exception {
        sign_in_action();

    }
    public void update_email() {
        try {

            String sql = "SELECT email_id FROM employees Where  username = ?";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, txtUsername.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() == false) {
                Alert_handler.showErrorMessage("wrong username", "ENTER CORRECT USERNAME", "");
                return;
            } else {
                do {
                    email = resultSet.getString("email_id");
                    System.out.println(email);

                } while (resultSet.next());
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void sign_in_action() throws Exception {
        uname = txtUsername.getText();
        password = txtPassword.getText();

        System.out.println(uname + "  " + password);
        String sql = "SELECT * FROM authentication Where  username = ? and BINARY password = ?";
        String sql_fname = "SELECT name FROM employees Where  username = ?";



        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, password);
            PreparedStatement stmt_fname = con.prepareStatement(sql_fname);
            stmt_fname.setString(1,uname);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet result_fname=stmt_fname.executeQuery();
            if(result_fname.next()==true) {
                String names = result_fname.getString(1);
                System.out.println(names);
                int t1=names.indexOf(' ');
                if(t1!=-1)
                fname = names.substring(0, t1);
                else
                    fname= names;
                System.out.println(fname);
            }
            if (!resultSet.next()) {
                Alert_handler.showErrorMessage("WRONG CREDENTIALS","PLEASE ENTER CORRECT LOGIN DETAILS"," YOU HAVE ENTERED WRONG USERNAME OR PASSWORD");

            } else {
                update_email();
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/dashboard.fxml"));
                primaryStage.setTitle("bookIT-extra class booking system");
                primaryStage.setScene(new Scene(root));
                primaryStage.setResizable(false);
                primaryStage.show();
                primaryStage.setOnCloseRequest(e->
                {
                    e.consume();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText("ARE YOU SURE YOU WANT TO PROCEED");
                    a.setHeaderText(" YOU ARE ABOUT TO CLOSE THE APPLICATION ");
                    a.setTitle(" CLOSE APPLICATION CONFORMATION");
                    a.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            primaryStage.close();
                            System.exit(1);
                        }
                    });
                });

                Stage current = (Stage) txtUsername.getScene().getWindow();
                current.close();

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }


    }
    public void forgot_password_function(MouseEvent event) throws Exception
    {
        if(txtUsername.getText().length()==0) {
            Alert_handler.showErrorMessage("", "enter username", "");
            return;

        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("FORGOT PASSWORD ");
        alert.setHeaderText("WE WILL SEND A NEW PASSWORD TO YOUR REGISTERED  MAIL \n AND YOU WILL NOT BE ABLE TO USE YOUR OLD PASSOWRD ");
        alert.setContentText(" ARE U SURE YOU WANT TO PROCEED ?");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
                try {

                    String sql = "SELECT email_id FROM employees Where  username = ?";
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, txtUsername.getText());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next() == false) {
                        Alert_handler.showErrorMessage("wrong username", "ENTER CORRECT USERNAME", "");
                        return;
                    } else {
                        do {
                            email = resultSet.getString("email_id");
                            System.out.println(email);

                        } while (resultSet.next());
                    }
                }
                    catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    update_password();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Mail.send("kanchitbajaj8070@gmail.com","hhtpzdbpwrtmxwsz",email,"New password ","your new password is test@123 (all small) . kindly change it at the earliest from change password feature in app");
            }});

    }
public void update_password() throws Exception {
    try {
        String sql = "update authentication set password= ? where username =? ";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, "test@123");
        preparedStatement.setString(2, txtUsername.getText());
        preparedStatement.executeUpdate();
        LoginController.password="test@123";

    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}
    public void handle_enter_key_action(KeyEvent key_event) throws Exception {
        {
            if (key_event.getCode() == KeyCode.ENTER) {
                System.out.println(key_event.getCode());
                sign_in_action();

            }
        }
    }
}
