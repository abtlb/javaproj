package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SignInController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label error;

    @FXML
    private void OnClickSignin() throws SQLException, IOException {
        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("select * from users");
        while (rs.next())
        {
            Boolean usernameFound = rs.getString("username").equals(username.getText());
            Boolean passwordFound = rs.getString("password").equals(password.getText());
            if(usernameFound && passwordFound)
            {
                String userType = rs.getString("usertype");
                if(userType.equals("admin"))
                {
                    LoadAdmin();
                }
                else
                {
                    LoadEmp();
                }
                return;
            }
        }
        //display error in username or password
        error.setText("Credentials isn't correct");
    }

    @FXML
    private void LoadSignUp() throws IOException {
        Stage stage = HelloApplication._stage;
        var d = SignInController.class.getResource("sign-up.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(d);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene((scene));
        stage.show();

    }

    private void LoadAdmin() throws IOException
    {
        Stage stage = HelloApplication._stage;
        var d = SignInController.class.getResource("admin.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(d);
        Scene scene = new Scene(fxmlLoader.load(), 320, 500);
        stage.setScene((scene));
        stage.show();
    }

    private void LoadEmp() throws IOException {
        Stage stage = HelloApplication._stage;
        var d = SignInController.class.getResource("employee.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(d);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene((scene));
        stage.show();

    }


}