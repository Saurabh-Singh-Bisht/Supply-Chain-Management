/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scm;

/**
 *
 * @author SSB
 */
public class ProductsData {

    

    private String productID;
    private String productCategory;
    private String brandName;
    private String productName;
    private double productPrice;
    private String productStatus;
    
    public ProductsData(String productID, String productCategory
            , String brandName, String productName, double productPrice
            , String productStatus) {
        this.productID = productID;
        this.productCategory = productCategory;
        this.brandName = brandName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
    }
    
    
    public String getProductID(){
        return productID;
    }
    public String getProductCategory(){
        return productCategory;
    }
    public String getBrandName(){
        return brandName;
    }
    public String getProductName(){
        return productName;
    }
    public double getProductPrice(){
        return productPrice;
    }
    public String getProductStatus(){
        return productStatus;
    }
}
