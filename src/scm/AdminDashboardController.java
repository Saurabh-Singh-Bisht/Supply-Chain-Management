/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package scm;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author SSB
 */
public class AdminDashboardController implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane;
    
    @FXML
    private TableColumn<ProductsData, String> addBrandNameCol;

    @FXML
    private TableColumn<ProductsData, String> addPriceCol;

    @FXML
    private Button addProductBtn;

    @FXML
    private TableColumn<ProductsData, String> addProductCategoryCol;

    @FXML
    private TableColumn<ProductsData, String> addProductIdCol;

    @FXML
    private TableView<ProductsData> addProductListTableView;

    @FXML
    private TableColumn<ProductsData, String> addProductNameCol;

    @FXML
    private TableColumn<ProductsData, String> addStatusCol;

    @FXML
    private TextField brandNameTextBox;

    @FXML
    private Label customerHome;

    @FXML
    private Button deleteProductBtn;

    @FXML
    private Label employeeLabel;

    @FXML
    private Button homeBtn;

    @FXML
    private Label incomeHome;

    @FXML
    private Button logoutNavBtn;

    @FXML
    private Button ordersBtn;

    @FXML
    private Label ordersHome;

    @FXML
    private TextField priceTextBox;

    @FXML
    private ChoiceBox<?> productCategoryChoiceBox;

    @FXML
    private TextField productIdTextBox;

    @FXML
    private TextField productNameTextBox;

    @FXML
    private ChoiceBox<?> statusChoiceBox;


    @FXML
    private Button updateProductBtn;
    
    @FXML
    private Button homeNavBtn;
    
    @FXML
    private Button addProductNavBtn;
    
    @FXML
    private Button ordersNavBtn;
    
    @FXML
    private AnchorPane homeDashboard;
    
    @FXML
    private AnchorPane addProductDashboard;
    
    @FXML
    private AnchorPane ordersDashboard;
    
    @FXML
    private HBox homeHbox;
    
    @FXML
    private HBox addProductHbox;
    
    @FXML
    private HBox ordersHbox;
    
    @FXML
    private TextField searchProduct;
 
    @FXML
    private TableColumn<OrdersData, String> recCustomerID;

    @FXML
    private TableColumn<OrdersData, String> recOrderID;

    @FXML
    private TableView<OrdersData> recOrderTableView;

    @FXML
    private TableColumn<OrdersData, String> recProductID;

    @FXML
    private TableColumn<OrdersData, String> recProductCategory;

    @FXML
    private TableColumn<OrdersData, String> recQuantity;

    @FXML
    private TableColumn<OrdersData, String> recTotalAmount;

    @FXML
    private TableColumn<OrdersData, String> revBrandName;

    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public static String adminName;
    public static  int adminID;
    
    //To find admin name and Seller Category:
    public void findEmployeeID(){
        String query = "SELECT * FROM employee WHERE employeeID = '" + getAdminData.employeeId + "'";
        connect = DatabaseConnection.getStatement();
        try {
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();
            if(result.next()){
                getAdminData.employeeName = result.getString("employeeName");
                getAdminData.seller = result.getString("sellerOF");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //To add product into DataBase:
    public void addProductsAdd(){
        String query = "INSERT into products(productID, productCategory, brandName, productName, productPrice, productStatus) values(?,?,?,?,?,?)";
        connect = DatabaseConnection.getStatement();
        
        try {
            Alert alert;
            
            if(productIdTextBox.getText().isEmpty()
                    || productCategoryChoiceBox.getSelectionModel().getSelectedItem() == null
                    || brandNameTextBox.getText().isEmpty()
                    || productNameTextBox.getText().isEmpty()
                    || priceTextBox.getText().isEmpty()
                    || statusChoiceBox.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank feilds");
                alert.showAndWait();
            }
            else{
                String check = "SELECT * FROM PRODUCTS WHERE productID = '" + productIdTextBox.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID : " + productIdTextBox.getText() + " was already exisits");
                    alert.showAndWait();
                }
                else{
                    prepare = connect.prepareStatement(query);
                    prepare.setString(1, productIdTextBox.getText());
                    prepare.setString(2, (String)productCategoryChoiceBox.getSelectionModel().getSelectedItem());
                    prepare.setString(3, brandNameTextBox.getText());
                    prepare.setString(4, productNameTextBox.getText());
                    prepare.setString(5, priceTextBox.getText());
                    prepare.setString(6, (String)statusChoiceBox.getSelectionModel().getSelectedItem());
                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    addProductsShowData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clear();
    }
    
    //To update exisiting product:
    public void addProductUpdate(){
        String query = "UPDATE products SET productCategory = '" + productCategoryChoiceBox.getSelectionModel().getSelectedItem() +
                "', brandName = '" + brandNameTextBox.getText() +
                "', productName = '" + productNameTextBox.getText() +
                "', productPrice = '" + priceTextBox.getText() +
                "', productStatus = '" + statusChoiceBox.getSelectionModel().getSelectedItem() +
                "' WHERE productID = '" + productIdTextBox.getText() + "'";
        
        connect = DatabaseConnection.getStatement();
        try {
            Alert alert;
            
            if(productIdTextBox.getText().isEmpty()
                    || productCategoryChoiceBox.getSelectionModel().getSelectedItem() == null
                    || brandNameTextBox.getText().isEmpty()
                    || productNameTextBox.getText().isEmpty()
                    || priceTextBox.getText().isEmpty()
                    || statusChoiceBox.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank feilds");
                alert.showAndWait();
            }
            else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: " + productIdTextBox.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(query);
                    
                    addProductsShowData();
                }
                else{
                    return;
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        clear();
    }

    //To delete exixiting product:
    public void addProductDelete(){
        String query = "DELETE from products WHERE productID = '" + productIdTextBox.getText() + "'";
        connect = DatabaseConnection.getStatement();
        try {
            Alert alert;
            if(productIdTextBox.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill Product ID feild to delete Product");
                alert.showAndWait();
            }
            else{
                String check = "SELECT * FROM products WHERE productID = '" + productIdTextBox.getText() + "'";
                prepare = connect.prepareStatement(check);
                result = prepare.executeQuery();
                if(result.next()){
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to Delete Product: " + productIdTextBox.getText() + "?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)){
                        prepare = connect.prepareStatement(query);
                        prepare.executeUpdate();
                        
                        addProductsShowData();
                    }
                    else{
                        return;
                    }
                }
                clear();
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clear(){
        productIdTextBox.setText("");
        brandNameTextBox.setText("");
        productNameTextBox.setText("");
        priceTextBox.setText("");
    }

    private String[] statusList = {"Available", "Not Available"};
    public void addProductStatusList(){
        List<String> list = new ArrayList<>();
        for(String data: statusList){
            list.add(data);
        }
        ObservableList statusData = FXCollections.observableArrayList(list);
        statusChoiceBox.setItems(statusData);
    }
    private String[] productCategoryList = {"Electronics", "Beverages", };
    public void productCategoryList(){
        List<String> list = new ArrayList<>();
        for(String data: productCategoryList){
            list.add(data);
        }
        ObservableList categoryList = FXCollections.observableArrayList(list);
        productCategoryChoiceBox.setItems(categoryList);
    }

    public void addProductSearch(){
        FilteredList<ProductsData> filter = new FilteredList<>(addProductsList, e -> true);
        searchProduct.textProperty().addListener((Observable, oldValue, newValue) ->{
            filter.setPredicate(predicateProductData -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                //It can't read then, first we need to return true
                if(predicateProductData.getProductID().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getProductName().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getProductCategory().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getBrandName().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getProductStatus().toLowerCase().contains(searchKey)){
                    return true;
                }
//                else if(predicateProductData.getProductPrice().toString().contains(searchKey)){
//                    return true;
//                }
                else return false;
            });
        });
        
        SortedList<ProductsData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addProductListTableView.comparatorProperty());
        addProductListTableView.setItems(sortList);
    }
        
    public ObservableList<ProductsData> addProductsListData(){
        ObservableList<ProductsData> prodList = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM products WHERE productCategory = '" + getAdminData.seller + "'";
        
        connect = DatabaseConnection.getStatement();
        
        try {
            ProductsData prod;
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();
            while(result.next()){
                prod = new ProductsData(result.getString("productID")
                        , result.getString("productCategory")
                        , result.getString("brandName")
                        , result.getString("productName")
                        , result.getDouble("productPrice")
                        , result.getString("productStatus")
                );
                prodList.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prodList;
    }

    private ObservableList<ProductsData> addProductsList;
    public void addProductsShowData(){
        addProductsList = addProductsListData();
        
        addProductCategoryCol.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        addProductIdCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        addBrandNameCol.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addStatusCol.setCellValueFactory(new PropertyValueFactory<>("productStatus"));
        addPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        
        addProductListTableView.setItems(addProductsList);
    }
    
    public void addProducrsSelected(){
        ProductsData prod = addProductListTableView.getSelectionModel().getSelectedItem();
        int num = addProductListTableView.getSelectionModel().getSelectedIndex();
        
        if(num - 1 < -1){
            return;
        }
//        productCategoryChoiceBox.setItems(prod.getProductCategory());
        productIdTextBox.setText(prod.getProductID());
        brandNameTextBox.setText(prod.getBrandName());
        productNameTextBox.setText(prod.getProductName());
        priceTextBox.setText(String.valueOf(prod.getProductPrice()));
    }
    
    private double x =0;
    private double y =0;
    
    public void logout(){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confermation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            
            Optional<ButtonType> option = alert.showAndWait();
            
            if(option.get().equals(ButtonType.OK)){
                logoutNavBtn.getScene().getWindow().hide();
                
                Parent root = FXMLLoader.load(getClass().getResource("Chooser.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(0.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                
                stage.setScene(scene);
                stage.show();
            }
            else{
                return;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void switchNavOptions(ActionEvent event){
        if(event.getSource() == homeNavBtn){
            addProductDashboard.setVisible(false);
            ordersDashboard.setVisible(false);
            homeDashboard.setVisible(true);
            
            homeHbox.setStyle("-fx-background-color: #D3D3D33D");
            addProductHbox.setStyle("-fx-background-color: transparent");
            ordersHbox.setStyle("-fx-background-color: transparent");
            
            
            getCustomersCount();
            getTotalOrders();
            getTotalIncome();
        }
        else if(event.getSource() == addProductNavBtn){
            homeDashboard.setVisible(false);
            ordersDashboard.setVisible(false);
            addProductDashboard.setVisible(true);
            
            addProductHbox.setStyle("-fx-background-color: #D3D3D33D");
            homeHbox.setStyle("-fx-background-color: transparent");
            ordersHbox.setStyle("-fx-background-color: transparent");
            
            addProductsShowData();
            addProductSearch();
        }
        else if(event.getSource() == ordersNavBtn){
            homeDashboard.setVisible(false);
            addProductDashboard.setVisible(false);
            ordersDashboard.setVisible(true);
            
            ordersHbox.setStyle("-fx-background-color: #D3D3D33D");
            addProductHbox.setStyle("-fx-background-color: transparent");
            homeHbox.setStyle("-fx-background-color: transparent");
            
            addOrderReceivedShowData();
        }
    }
    
    
    public void close(MouseEvent event){
        System.exit(0);
    }
    
    @FXML
    public void minimize(MouseEvent event){
        Stage stage = (Stage)mainAnchorPane.getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        getTotalIncome();
        getTotalOrders();
        getCustomersCount();
        findEmployeeID();
        employeeLabel.setText("Welcome "+ getAdminData.employeeName + " sir");
        addProductsShowData();
        addProductStatusList();
        productCategoryList();
        
    }
    public void getTotalIncome(){
        String query = "select sum(totalAmount) from orders where productCategory = '" + getAdminData.seller + "'";
        connect = DatabaseConnection.getStatement();
        double totalIncome =0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(query);
            if(result.next()){
                totalIncome = result.getDouble("sum(totalAmount)");
            }
        } catch (Exception e) {
        }
        incomeHome.setText(String.valueOf(totalIncome));
    }
    
    public void getTotalOrders(){
        String query = "select count(orderID) from orders where productCategory = '" + getAdminData.seller + "'";
        connect = DatabaseConnection.getStatement();
        int ordTotal = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(query);
            if(result.next()){
                ordTotal = result.getInt("count(orderID)");
            }
        } catch (Exception e) {
        }
        ordersHome.setText(String.valueOf(ordTotal));
    }
    
    public void getCustomersCount(){
        String query = "select count(DISTINCT(custID)) from orders where productCategory = '" + getAdminData.seller + "'";
        connect = DatabaseConnection.getStatement();
        int custCount =0;
        try {
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();
            if(result.next()){
                custCount = result.getInt("count(DISTINCT(custID))");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerHome.setText(String.valueOf(custCount));
    }

    public ObservableList<OrdersData> addOrderReceivedListData(){
        ObservableList<OrdersData> ordList = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM orders WHERE productCategory = '" + getAdminData.seller + "'";
        
        connect = DatabaseConnection.getStatement();
        
        try {
            OrdersData ord;
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();
            while(result.next()){
                ord = new OrdersData(result.getInt("orderID")
                        , result.getString("productID")
                        , result.getInt("custID")
                        , result.getString("productCategory")
                        , result.getString("brandName")
                        , result.getInt("quantity")
                        , result.getDouble("totalAmount")
                );
                ordList.add(ord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordList;
    }

    private ObservableList<OrdersData> orderReceivedData;
    public void addOrderReceivedShowData(){
        orderReceivedData = addOrderReceivedListData();
        
        recOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        recProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        recCustomerID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        recProductCategory.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        revBrandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        recQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        recTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        
        recOrderTableView.setItems(orderReceivedData);
    }
}
