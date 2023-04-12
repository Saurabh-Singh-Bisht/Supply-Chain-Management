/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scm;

/**
 *
 * @author SSB
 */
public class OrdersData {
    private int orderID;
    private String productID;
    private int custID;
    private String productCategory;
    private String brandName;
    private int quantity;
    private Double totalAmount;
    
    public OrdersData(String cartProductIDCol, String cartProductCategoryCol
            , String cartBrandNameCol, int cartQuantityCol
            , double cartTotalAmountCol) {
        this.productID =cartProductIDCol;
        this.productCategory =cartProductCategoryCol;
        this.brandName =cartBrandNameCol;
        this.quantity =cartQuantityCol;
        this.totalAmount =cartTotalAmountCol;
    }

    OrdersData(int orderID, String productID, String productCategory
            , String brandName, int quantity, double totalAmount) {
        this.orderID = orderID;
        this.productID =productID;
        this.productCategory =productCategory;
        this.brandName =brandName;
        this.quantity =quantity;
        this.totalAmount =totalAmount;
    }

    OrdersData(int orderID, String productID, int custID, String productCategory
            , String brandName, int quantity, double totalAmount) {
        this.orderID = orderID;
        this.productID =productID;
        this.custID = custID;
        this.productCategory =productCategory;
        this.brandName =brandName;
        this.quantity =quantity;
        this.totalAmount =totalAmount;
        
    }
    
    public int getOrderID() {
        return orderID;
    }

    public String getProductID() {
        return productID;
    }

    public int getCustID() {
        return custID;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getBrandName() {
        return brandName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
