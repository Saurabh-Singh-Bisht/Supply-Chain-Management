/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package scm;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
public class CustomerDashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableColumn<ProductsData, String> brandNameCol;

    @FXML
    private Button buyAddToCartBtn;

    @FXML
    private Button buyPayBtn;

    @FXML
    private TextField buyProductCategoryTextFeild;

    @FXML
    private AnchorPane buyProductDashboard;

    @FXML
    private Spinner<Integer> buyQuantitySpinner;

    @FXML
    private Button buyReciptBtn;

    @FXML
    private TableView<ProductsData> buyTableView;

    @FXML
    private Label buyTotalAmountLabel;

    @FXML
    private TextField buybrandNameTextFeild;

    @FXML
    private AnchorPane homeDashboard;

    @FXML
    private Label homeWelcomeLabel;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane myOrdersDashboard;

    @FXML
    private Button navBuyProductBtn;

    @FXML
    private Button navHomeBtn;

    @FXML
    private Button navLogoutBtn;

    @FXML
    private Button navOrderBtn;

    @FXML
    private TableColumn<ProductsData, String> priceCol;

    @FXML
    private TableColumn<ProductsData, String> productCategoryCol;

    @FXML
    private TableColumn<ProductsData, String> productIDCol;

    @FXML
    private TableColumn<ProductsData, String> productNameCol;

    @FXML
    private TableColumn<ProductsData, String> statusCol;
    
    @FXML
    private HBox byProductHbox;

    @FXML
    private HBox homeHbox;
    
    @FXML
    private HBox ordersHbox;
    
    @FXML
    private TextField buyProductIDTextFeild;
    
    @FXML
    private TableView<OrdersData> productCartTableView;
    
    @FXML
    private TableColumn<OrdersData, String> cartBrandNameCol;

    @FXML
    private TableColumn<OrdersData, String> cartProductCategoryCol;

    @FXML
    private TableColumn<OrdersData, String> cartProductIDCol;

    @FXML
    private TableColumn<OrdersData, String> cartQuantityCol;

    @FXML
    private TableColumn<OrdersData, String> cartTotalAmountCol;
    
    @FXML
    private ImageView electronicImg;
    
    @FXML
    private ImageView breveragesImg;
    
    
    @FXML
    private TableView<OrdersData> myOrdersTableView;
    
    @FXML
    private TableColumn<OrdersData, String> myProductCategory;

    @FXML
    private TableColumn<OrdersData, String> myProductId;

    @FXML
    private TableColumn<OrdersData, String> myQuantity;
    
    @FXML
    private TableColumn<OrdersData, String> myAmount;

    @FXML
    private TableColumn<OrdersData, String> myBrandName;

    @FXML
    private TableColumn<OrdersData, String> myOrderId;

    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

   private SpinnerValueFactory spinner;
   public void purchaseQuantitySpinner(){
       spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 50,10);
       buyQuantitySpinner.setValueFactory(spinner);
   }

   private List<String> list = new ArrayList<>();
    String orderQuery = "INSERT INTO orders(productID, custID, productCategory, brandName, quantity, totalAmount) values";
    private ObservableList<OrdersData> addOrderDataList = FXCollections.observableArrayList();;
    private OrdersData od;
    private double amountFinal;

   public void addToCart(){
       if(buyProductIDTextFeild.getText().isEmpty() || buyProductCategoryTextFeild.getText().isEmpty() || buybrandNameTextFeild.getText().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error Message");;
           alert.setHeaderText(null);
           alert.setContentText("Please fill all the blank feilds");
           alert.showAndWait();
           return;
       }
       
       String productID = buyProductIDTextFeild.getText();
       String productCategory = buyProductCategoryTextFeild.getText();
       String brandName = buybrandNameTextFeild.getText();
       int quantity = buyQuantitySpinner.getValue();
       
       double amount = Integer.valueOf(buyQuantitySpinner.getValue()) * getPrice();
       
       amountFinal += amount;
       buyTotalAmountLabel.setText(String.valueOf(amountFinal));
       
       String temp = orderQuery + "('" + productID + "'," + "'" + getCustomerData.customerID + "'," +
               "'" + productCategory + "'," + "'" + brandName + "'," + "'" + quantity + "'," + "'" + amount + "');";
       
       list.add(temp);
       
       
       od = new OrdersData(productID, productCategory, brandName, quantity, amount);
       
       addOrderDataList.add(od);
       
       cartProductIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
       cartProductCategoryCol.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
       cartBrandNameCol.setCellValueFactory(new PropertyValueFactory<>("brandName"));
       cartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
       cartTotalAmountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
       
       productCartTableView.setItems(addOrderDataList);
       
   }
   
   public void buyProduct(){
       connect = DatabaseConnection.getStatement();
       try {
           Alert alert;
           for(String data : list){
               prepare = connect.prepareStatement(data);
               prepare.executeUpdate();
               
           }
           
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();
           
       } catch (Exception e) {
           e.printStackTrace();
       }
       clearPurchased();
    }
   
   public void clearPurchased(){
       addOrderDataList.clear();
       buyProductIDTextFeild.setText("");
       buyProductCategoryTextFeild.setText("");
       buybrandNameTextFeild.setText("");
   }

   public double getPrice(){
       String query = "SELECT * FROM PRODUCTS where productID = '" + buyProductIDTextFeild.getText() + "'";
       connect = DatabaseConnection.getStatement();
        
        try {
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getDouble(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return 1;
   }

    public ObservableList<ProductsData> addProductsListData(){
        ObservableList<ProductsData> prodList = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM products";
        
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
        
        productCategoryCol.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        brandNameCol.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("productStatus"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        
        buyTableView.setItems(addProductsList);
    }

    public void addProducrsSelected(){
        ProductsData prod = buyTableView.getSelectionModel().getSelectedItem();
        int num = buyTableView.getSelectionModel().getSelectedIndex();
        
        if(num - 1 < -1){
            return;
        }
//        productCategoryChoiceBox.setItems(prod.getProductCategory());
        buyProductIDTextFeild.setText(prod.getProductID());
        buybrandNameTextFeild.setText(prod.getBrandName());
        buyProductCategoryTextFeild.setText(prod.getProductCategory());
        
    }

    public void switchNavOptions(ActionEvent event){
        if(event.getSource() == navHomeBtn){
            buyProductDashboard.setVisible(false);
            myOrdersDashboard.setVisible(false);
            homeDashboard.setVisible(true);
            
            homeHbox.setStyle("-fx-background-color: #D3D3D33D");
            byProductHbox.setStyle("-fx-background-color: transparent");
            ordersHbox.setStyle("-fx-background-color: transparent");
        }
        else if(event.getSource() == navBuyProductBtn){
            homeDashboard.setVisible(false);
            myOrdersDashboard.setVisible(false);
            buyProductDashboard.setVisible(true);
            
            byProductHbox.setStyle("-fx-background-color: #D3D3D33D");
            homeHbox.setStyle("-fx-background-color: transparent");
            ordersHbox.setStyle("-fx-background-color: transparent");
            
            addProductsShowData();
//            addProductSearch();
        }
        else if(event.getSource() == navOrderBtn){
            homeDashboard.setVisible(false);
            buyProductDashboard.setVisible(false);
            myOrdersDashboard.setVisible(true);
            
            ordersHbox.setStyle("-fx-background-color: #D3D3D33D");
            byProductHbox.setStyle("-fx-background-color: transparent");
            homeHbox.setStyle("-fx-background-color: transparent");
            
            customerOrdersShowData();
        }
    }

    public void findCustomerID(){
        String query = "SELECT * FROM customer WHERE custEmail = '" + getCustomerData.customerEmail + "'";
        connect = DatabaseConnection.getStatement();
        try {
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();
            if(result.next()){
                getCustomerData.customerID = result.getInt("custID");
                getCustomerData.customerName = result.getString("custNAME");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(getCustomerData.customerName);
    }
    
    public void displayCustomerName(){
        homeWelcomeLabel.setText("Welcome " + getCustomerData.customerName);
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
                navLogoutBtn.getScene().getWindow().hide();
                
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
    
    public ObservableList<OrdersData> customerOrdersListData(){
        ObservableList<OrdersData> orderList = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM orders where custID = '" + getCustomerData.customerID + "';";
        
        connect = DatabaseConnection.getStatement();
        
        try {
            OrdersData ord;
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();
            while(result.next()){
                ord = new OrdersData(result.getInt("orderID")
                        , result.getString("productId")
                        , result.getString("productCategory")
                        , result.getString("brandName")
                        , result.getInt("quantity")
                        , result.getDouble("totalAmount")
                );
                orderList.add(ord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }
    
    private ObservableList<OrdersData> customerOrdersData;
    public void customerOrdersShowData(){
        customerOrdersData = customerOrdersListData();
        
        myOrderId.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        myProductId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        myProductCategory.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        myBrandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        myQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        myAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        
        myOrdersTableView.setItems(customerOrdersData);
    }

    public void close(MouseEvent event){
        System.exit(0);
    }
    
    @FXML
    public void minimize(MouseEvent event){
        Stage stage = (Stage)mainAnchorPane.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        addProductsShowData();
        buyReciptBtn.setVisible(false);
        findCustomerID();
        displayCustomerName();
        purchaseQuantitySpinner();
        
    }    
    
    public void electronics(MouseEvent event){
        if(event.getSource() == electronicImg){
            homeDashboard.setVisible(false);
            myOrdersDashboard.setVisible(false);
            buyProductDashboard.setVisible(true);
            
            byProductHbox.setStyle("-fx-background-color: #D3D3D33D");
            homeHbox.setStyle("-fx-background-color: transparent");
            ordersHbox.setStyle("-fx-background-color: transparent");
            
            showElectronicsProducts("Electronics");
        }
        else if(event.getSource() == breveragesImg){
            homeDashboard.setVisible(false);
            myOrdersDashboard.setVisible(false);
            buyProductDashboard.setVisible(true);
            
            byProductHbox.setStyle("-fx-background-color: #D3D3D33D");
            homeHbox.setStyle("-fx-background-color: transparent");
            ordersHbox.setStyle("-fx-background-color: transparent");
            
            showElectronicsProducts("Beverages");
        }
    }
    
    public ObservableList<ProductsData> addElectronicsListData(String str){
        ObservableList<ProductsData> prodList = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM products WHERE productCategory = '" + str + "'";
        
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
  
    private ObservableList<ProductsData> addElectronicsList;
    public void showElectronicsProducts(String str){
        addProductsList = addElectronicsListData(str);
        
        productCategoryCol.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        brandNameCol.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("productStatus"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        
        buyTableView.setItems(addProductsList);
    }
    
}
