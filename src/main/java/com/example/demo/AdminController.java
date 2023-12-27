package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;


public class AdminController
{
    public class Item
    {
        public SimpleStringProperty column1;
        public SimpleStringProperty column2;

        public Item(String column1, String column2) {
            this.column1 = new SimpleStringProperty(column1);
            this.column2 = new SimpleStringProperty(column2);
        }

        public String getColumn1()
        {
            return column1.get();
        }
        public String getColumn2()
        {
            return column2.get();
        }
    }

    @FXML
    private TextField addStudentName;
    @FXML
    private TextField addStudentLevel;
    @FXML
    private TextField addDepartmentDep;
    @FXML
    private TextField addSubjectSubject;
    @FXML
    private TextField addSubjectDepartment;
    @FXML
    private TextField deleteStudentName;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField type;
    @FXML
    private TextField ssStudent;
    @FXML
    private TextField ssSubject;

    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> column1;
    @FXML
    private TableColumn<Item, String> column2;
    @FXML
    private void OnAddStudent() throws SQLException {
        String name = addStudentName.getText();
        String level = addStudentLevel.getText();

        if(name.equals("") || level.equals(""))
        {
            errorLabel.setText("Credentials error");
            return;
        }

        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        String query = "INSERT INTO `javadb`.`students`\n" +
                "(\n" +
                "`name`,\n" +
                "`level`)\n" +
                "VALUES\n" +
                "(\n" +
                "\"" + name + "\"" + ",\n" +
                "\"" + level + "\"" + "\n" +");\n";
        statement.executeUpdate(query);

        addStudentName.clear();
        addStudentLevel.clear();
    }

    @FXML
    private void OnAddDep() throws SQLException {
        String dep = addDepartmentDep.getText();
        if(dep.equals(""))
        {
            errorLabel.setText("Credentials error");
            return;
        }

        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        String query = "INSERT INTO `javadb`.`departments`\n" +
                "(`depname`)\n" +
                "VALUES\n" +
                "(\"" + dep + "\");\n";
        statement.executeUpdate(query);
        addDepartmentDep.clear();
    }

    @FXML
    private void OnAddSubject() throws SQLException {
        String subject = addSubjectSubject.getText();
        String dep = addSubjectDepartment.getText();
        if(dep.equals("") || subject.equals(""))
        {
            errorLabel.setText("Credentials error");
            return;
        }

        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        String query = "INSERT INTO `javadb`.`subjects`\n" +
                "(\n" +
                "`subjectname`,\n" +
                "`depcode`)\n" +
                "VALUES\n" +
                "(\n" +
                "\"" + subject + "\",\n" +
                dep + ");\n";
        statement.executeUpdate(query);
        addSubjectSubject.clear();
        addSubjectDepartment.clear();

    }

    @FXML
    private void OnDeleteStudent() throws SQLException {
        String name = deleteStudentName.getText();
        if(name.equals(""))
        {
            errorLabel.setText("Credentials error");
            return;
        }

        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        String query = "delete from students where name=\"" + name +"\"";
        statement.executeUpdate(query);
    }

    @FXML
    private void OnShowStudents() throws SQLException {
        column1.setCellValueFactory (new PropertyValueFactory<Item, String>("column1"));
        column2.setCellValueFactory (new PropertyValueFactory<Item, String>("column2"));
        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("select * from students");
        ObservableList<Item> items = FXCollections.observableArrayList();
        while (rs.next())
        {
            String name = rs.getString("name");
            String level = rs.getString("level");
            System.out.println(name + " " + level);
            Item newItem = new Item(name, level);
            items.add(newItem);
        }
        tableView.setItems(items);
    }

    @FXML
    private void OnAddEmployee() throws SQLException
    {
        String usernameStr = username.getText();
        String passwordStr = password.getText();
        String typeStr = type.getText();
        if(usernameStr.equals("") || passwordStr.equals("") || typeStr.equals(""))
        {
            errorLabel.setText("Credentials error");
            return;
        }

        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        String query = "INSERT INTO `javadb`.`users`\n" +
                "(\n" +
                "`username`,\n" +
                "`password`,\n" +
                "`usertype`)\n" +
                "VALUES\n" +
                "(\n" +
                "\"" + usernameStr +"\",\n" +
                "\"" + passwordStr + "\",\n" +
                "\"" + typeStr + "\");\n";
        statement.executeUpdate(query);
    }

    @FXML
    private void OnAddSubjectToStudent() throws SQLException {
        String name = ssStudent.getText();
        String subject = ssSubject.getText();
        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        String query = "INSERT INTO `javadb`.`studentsSubjects`\n" +
                "(`studentname`,\n" +
                "`subjectname`)\n" +
                "VALUES\n" +
                "(\"" + name + "\",\n" +
                "\"" + subject + "\");\n";
        statement.executeUpdate(query);
    }

    @FXML
    private void OnGenerate() throws SQLException {
        //create file
        try
        {
            File myObj = new File("/Users/abdallahabotaleb/Desktop/Generated.txt");
            myObj.createNewFile();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //student name:subject
        Connection c = DBManager.GetConncection();
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("select * from studentsSubjects");
        StringBuilder res = new StringBuilder();
        while(rs.next())
        {
            String name = rs.getString("studentname");
            String subject = rs.getString("subjectname");
            res.append(name + ":" + subject + "\n");
        }
        try
        {
            FileWriter myWriter = new FileWriter("/Users/abdallahabotaleb/Desktop/Generated.txt");
            myWriter.write(res.toString());
            myWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

