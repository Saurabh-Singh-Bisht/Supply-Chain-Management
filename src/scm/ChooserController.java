/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package scm;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author SSB
 */
public class ChooserController implements Initializable {
    
    @FXML
    private AnchorPane anchorPaneSignIn;

    @FXML
    private AnchorPane anchorPaneSignUp;
    
    @FXML
    private TextField addressSignUp;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField emailSignIn;

    @FXML
    private TextField emailSignUp;

    @FXML
    private TextField nameSignUp;

    @FXML
    private PasswordField passwordSignIn;

    @FXML
    private PasswordField passwordSignUp;

    @FXML
    private TextField phoneSignUp;

    @FXML
    private Hyperlink signInHyperlink;

    @FXML
    private Hyperlink signUpHyperlink;
    
    @FXML
    private Button adminLogin;

    @FXML
    private Button customerLogin;
    
    @FXML
    private Label labelError;
    
    @FXML
    private Label labelSignUp;
    
    @FXML
    private AnchorPane anchorPaneAdminLogin;

    @FXML
    private TextField employeeId;

    @FXML
    private PasswordField employeePassword;

    @FXML
    private Label errorAdminLogin;

    @FXML
    private Button loginBtn;
    
    
    private double x =0;
    private double y =0;
    private Parent root;
    private Stage stage;
    private Scene scene;

    private Connection connect;    
    private ResultSet result;
    private PreparedStatement prepare;
    
    //TO Sign in Admin account
    public void adminLogin(ActionEvent event){
        String query = "select * from employee where employeeID = ? and employeePassword = ?";
        connect = DatabaseConnection.getStatement();
        try{
            Alert alert;
            if(employeeId.getText().isEmpty() || employeePassword.getText().isEmpty()){
                errorAdminLogin.setText("Please enter Employee ID or password");
                errorAdminLogin.setVisible(true);
            }
            else{
                prepare = connect.prepareStatement(query);
                prepare.setString(1, employeeId.getText());
                prepare.setString(2, employeePassword.getText());
                result = prepare.executeQuery();
                
                if(result.next()){
                    
                    
                    getAdminData.employeeId = Integer.valueOf(employeeId.getText());
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successfully");
                    alert.showAndWait();
                    
                    loginBtn.getScene().getWindow().hide();
                    
//                    root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
//                    stage = new Stage();
//                    scene = new Scene(root);
//                    
//                    stage.setScene(scene);
//                    stage.show();
                    root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);


                    stage.setScene(scene);
                    stage.show();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID and Password is incorrect");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //To Sign in customer account:
    public void customerSignIn(ActionEvent event){
        String query = "select * from customer where custEMAIL = ? and custPASSWORD = ?";
        connect = DatabaseConnection.getStatement();
        try {
            Alert alert;
            if(emailSignIn.getText().isEmpty() || passwordSignIn.getText().isEmpty()){
                labelError.setText("Please fill all blank feilds");
                labelError.setVisible(true);
            }
            else{
                
                String getEncrypted = getEncryptedPassword(passwordSignIn.getText());
                prepare = connect.prepareStatement(query);
                prepare.setString(1, emailSignIn.getText());
                prepare.setString(2, getEncrypted);
                result = prepare.executeQuery();
                
                if(result.next()){
                    
                    
                    getCustomerData.customerEmail = emailSignIn.getText();
                    
                    
                    root = FXMLLoader.load(getClass().getResource("customerDashboard.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);


                    stage.setScene(scene);
                    stage.show();
                    
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Customer ID and Password is incorrect");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Convert input int byte code
    private static byte[] getSha(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Convert byte into hexdecimal string
    private static String getEncryptedPassword(String password){
        try{
            BigInteger num = new BigInteger(getSha(password));
            StringBuilder hexString = new StringBuilder(num.toString(16));
            return hexString.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //To create customer account:
    public void customerSignUp(ActionEvent event){
        String query = "INSERT INTO customer(custName, custEMAIL, custPHONE, custADDRESS, custPASSWORD) VALUES(?,?,?,?,?)";
        connect = DatabaseConnection.getStatement();
        try{
            if(nameSignUp.getText().isEmpty() || emailSignUp.getText().isEmpty() || phoneSignUp.getText().isEmpty() || addressSignUp.getText().isEmpty() || passwordSignUp.getText().isEmpty()){
                labelSignUp.setVisible(true);
                labelSignUp.setTextFill(Color.RED);
                labelSignUp.setText("Please fill all blank fields");
                return;
            }
            else{
                String encryptedPassword = getEncryptedPassword(passwordSignUp.getText());
                
                
                prepare = connect.prepareStatement(query);
                prepare.setString(1, nameSignUp.getText());
                prepare.setString(2, emailSignUp.getText());
                prepare.setInt(3, Integer.parseInt(phoneSignUp.getText()));
                prepare.setString(4, addressSignUp.getText());
                prepare.setString(5, encryptedPassword);
                prepare.executeUpdate();
                
                labelSignUp.setText("Account created Sucessfully");
                labelSignUp.setVisible(true);
                labelSignUp.setTextFill(Color.GREEN);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //To switch between Admin and Customer panel:
    public void getCustomerOrAdminScene(ActionEvent event) throws IOException{
        if(event.getSource() == customerLogin){
            root = FXMLLoader.load(getClass().getResource("customer.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            
            
            
            
            
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource() == adminLogin){
            root = FXMLLoader.load(getClass().getResource("admin.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            
            
            

            stage.setScene(scene);
            stage.show();
        }
    }
    
    //In Customer panel switch between SIGN-IN and SIGN-UP:
    public void switchSignInUp(ActionEvent event){
        if(event.getSource() == signInHyperlink){
            anchorPaneSignIn.setVisible(false);
            anchorPaneSignUp.setVisible(true);
        }
        else if(event.getSource() == signUpHyperlink){
            anchorPaneSignUp.setVisible(false);
            anchorPaneSignIn.setVisible(true);
        }
    }
    
    //To close any panel which is currently using:
    public void close(MouseEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
