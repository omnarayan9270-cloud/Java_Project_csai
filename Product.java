package com.pinkstock.inventory.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity
@Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String name;
 @NotBlank private String category;
 private String niche;
 @Column(unique=true) private String sku;
 @Min(0) private int quantity;
 @Min(0) private int reorderLevel=5;
 @DecimalMin("0.0") private double costPrice;
 @DecimalMin("0.0") private double sellingPrice;
 private String area="Jaipur";
 private LocalDate createdDate=LocalDate.now();
 private LocalDate lastSoldDate;
 @ManyToOne(fetch=FetchType.EAGER) private Supplier supplier;
 public Product(){}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public String getNiche(){return niche;} public void setNiche(String v){niche=v;}
 public String getSku(){return sku;} public void setSku(String v){sku=v;}
 public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
 public int getReorderLevel(){return reorderLevel;} public void setReorderLevel(int v){reorderLevel=v;}
 public double getCostPrice(){return costPrice;} public void setCostPrice(double v){costPrice=v;}
 public double getSellingPrice(){return sellingPrice;} public void setSellingPrice(double v){sellingPrice=v;}
 public String getArea(){return area;} public void setArea(String v){area=v;}
 public LocalDate getCreatedDate(){return createdDate;} public void setCreatedDate(LocalDate v){createdDate=v;}
 public LocalDate getLastSoldDate(){return lastSoldDate;} public void setLastSoldDate(LocalDate v){lastSoldDate=v;}
 public Supplier getSupplier(){return supplier;} public void setSupplier(Supplier v){supplier=v;}
}
