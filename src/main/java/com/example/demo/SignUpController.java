package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignUpController implements Initializable
{
    @FXML
    private RadioButton radioAdmin;

    @FXML
    private RadioButton radioEmp;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        ToggleGroup tg = new ToggleGroup();
        radioAdmin.setToggleGroup(tg);
        radioEmp.setToggleGroup(tg);
    }

    @FXML
    private void LoadSignIn() throws IOException {
        Stage stage = HelloApplication._stage;
        var d = SignInController.class.getResource("sign-in.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(d);
        Scene scene = new Scene(fxmlLoader.load(), 320, 500);
        stage.setScene((scene));
        stage.show();
    }

    @FXML
    private void OnSignUp() throws SQLException {
        String usernameStr = username.getText();
        String passwordStr = password.getText();
        Boolean isAdmin = radioAdmin.isSelected();
        Boolean isEmp = radioEmp.isSelected();

        if(usernameStr.equals("") || passwordStr.equals("") || (!isAdmin && !isEmp))
        {
            errorLabel.setText("Credentials error");
            return;
        }

        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        String type = isAdmin? "admin" : "employee";
        String query = "INSERT INTO `javadb`.`users`\n" +
                "(\n" +
                "`username`,\n" +
                "`password`,\n" +
                "`usertype`)\n" +
                "VALUES\n" +
                "(\n" +
                "\"" + usernameStr + "\"" + ",\n" +
                "\"" + passwordStr + "\"" + ",\n" +
                "\"" + type + "\"" + ");";
        statement.executeUpdate(query);
    }
}