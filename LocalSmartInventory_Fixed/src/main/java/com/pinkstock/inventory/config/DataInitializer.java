package com.pinkstock.inventory.config;
import com.pinkstock.inventory.model.*;
import com.pinkstock.inventory.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DataInitializer {
 @Bean CommandLineRunner seed(ProductRepository products,SupplierRepository suppliers,SaleRepository sales){
  return args->{
   if(suppliers.count()==0){
    suppliers.save(new Supplier("Demo Jaipur Grocery Supplier","+91-90000-00001","grocery@example.test","Jaipur","Kirana / Grocery","Demo placeholder contact; verify before ordering."));
    suppliers.save(new Supplier("Demo Jaipur Boutique Supply","+91-90000-00002","boutique@example.test","Jaipur","Clothing / Boutique","Demo placeholder contact; verify before ordering."));
    suppliers.save(new Supplier("Demo Jaipur Hardware Supply","+91-90000-00003","hardware@example.test","Jaipur","Hardware","Demo placeholder contact; verify before ordering."));
   }
   if(products.count()==0){
    var s=suppliers.findAll();
    Product a=product("Masala Snack Pack","Grocery","Kirana","KRN-101",35,12,20,10,s.get(0),LocalDate.now().minusDays(3));
    Product b=product("Cotton Kurti","Clothing","Boutique","CLT-201",8,220,399,5,s.get(1),LocalDate.now().minusDays(60));
    Product c=product("LED Bulb 9W","Hardware","Electrical","HWR-301",4,55,89,8,s.get(2),LocalDate.now().minusDays(10));
    Product d=product("Gift Box","Gifts","Festive","GFT-401",18,90,149,5,s.get(0),LocalDate.now().minusDays(70));
    products.save(a);products.save(b);products.save(c);products.save(d);
    sales.save(new Sale(a,7,a.getSellingPrice()));sales.save(new Sale(a,4,a.getSellingPrice()));sales.save(new Sale(c,3,c.getSellingPrice()));
   }
  };
 }
 private Product product(String name,String category,String niche,String sku,int qty,double cost,double price,int reorder,Supplier supplier,LocalDate lastSold){
  Product p=new Product();p.setName(name);p.setCategory(category);p.setNiche(niche);p.setSku(sku);p.setQuantity(qty);p.setCostPrice(cost);p.setSellingPrice(price);p.setReorderLevel(reorder);p.setArea("Jaipur");p.setSupplier(supplier);p.setCreatedDate(LocalDate.now().minusDays(80));p.setLastSoldDate(lastSold);return p;
 }
}
