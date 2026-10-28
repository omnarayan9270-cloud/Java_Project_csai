package com.pinkstock.inventory.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="sales")
public class Sale {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Product product;
 private int quantity;
 private double unitPrice;
 private double total;
 private LocalDateTime soldAt=LocalDateTime.now();
 public Sale(){}
 public Sale(Product product,int quantity,double unitPrice){this.product=product;this.quantity=quantity;this.unitPrice=unitPrice;this.total=quantity*unitPrice;this.soldAt=LocalDateTime.now();}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Product getProduct(){return product;} public void setProduct(Product v){product=v;}
 public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
 public double getUnitPrice(){return unitPrice;} public void setUnitPrice(double v){unitPrice=v;}
 public double getTotal(){return total;} public void setTotal(double v){total=v;}
 public LocalDateTime getSoldAt(){return soldAt;} public void setSoldAt(LocalDateTime v){soldAt=v;}
}
