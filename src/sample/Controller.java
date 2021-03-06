package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Controller {


    //textfield
    public javafx.scene.control.TextField textField;
    //textlabel
    public Label textLabel;
    //list view
    public ListView <String> listView = new ListView<String>();

    //database connection class
    DatabaseConnection databaseConnection = new DatabaseConnection();

   //observable list to store the names
    public static final ObservableList names = FXCollections.observableArrayList();



    public void insert(ActionEvent actionEvent) {

        addRecords();
    }

    public void view(ActionEvent actionEvent) {
        viewRecords();
    }

    public void delete(ActionEvent actionEvent) {
        deleteRecords();
    }




    public void addRecords(){

        //variable to find the status of the execution of query
        int status = -1;

        //getting the name from the text field
        String name = textField.getText();
        //query
        String query = "insert into names(name) values(?)";

        try {
            //prepared statement
            PreparedStatement preparedStatement = databaseConnection.con.prepareStatement(query);
            preparedStatement.setString(1,name);

            //executing the prepared statement
           status = preparedStatement.executeUpdate();

           if(status == 1){
               textLabel.setText("Success !!! Name Added");
           }else{
               textLabel.setText("Failure !!! user has not added ");
           }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }







    }

    public void viewRecords(){

        try {
            //query
            String query = "select * from names";
            //SQL Statement
            Statement statement = databaseConnection.con.createStatement();
            //result set
            ResultSet resultset;
            resultset = statement.executeQuery(query);

            //adding objects to the arraylist
            while (resultset.next()){

                String name = resultset.getString("name");
                //avoiding duplicates in the arraylist
                if(!names.contains(name)){
                    names.add(name);
                }

            }

            listView.setPrefWidth(200);
            listView.setPrefHeight(200);
            listView.setItems(names);



        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }

    public void deleteRecords(){

        //variable to check the status of the execution update
        int status = -1;

        String name = textField.getText();

        //query
        String query = "delete from names where name = ?";

        try {
            //prepared statement
            PreparedStatement preparedStatement = databaseConnection.con.prepareStatement(query);
            //setting the values
            preparedStatement.setString(1,name);

            //executing the statement
          status = preparedStatement.executeUpdate();
          //remove the value from the array list
          names.remove(name);

          if(status == 1){
              textLabel.setText("Name has been deleted successfully");
          }else{
              textLabel.setText("Name deletion failed");
          }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


    public void open(ActionEvent actionEvent) {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My new Window");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
