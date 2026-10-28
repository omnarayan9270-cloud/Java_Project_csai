package com.pinkstock.inventory.service;

import com.pinkstock.inventory.model.*;
import com.pinkstock.inventory.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
public class InventoryService {
 private final ProductRepository products;
 private final SaleRepository sales;
 private final SupplierRepository suppliers;
 public InventoryService(ProductRepository products, SaleRepository sales, SupplierRepository suppliers) {
  this.products=products; this.sales=sales; this.suppliers=suppliers;
 }
 public List<Product> allProducts(){return products.findAll();}
 public List<Product> searchProducts(String q){return products.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(q,q);}
 public Product saveProduct(Product p){
  if(p.getSupplier()!=null && p.getSupplier().getId()!=null)
   p.setSupplier(suppliers.findById(p.getSupplier().getId()).orElse(null));
  if(p.getCreatedDate()==null)p.setCreatedDate(LocalDate.now());
  return products.save(p);
 }
 public void deleteProduct(Long id){products.deleteById(id);}
 public List<Product> lowStock(){return products.findAll().stream().filter(p->p.getQuantity()<=p.getReorderLevel()).toList();}
 public List<Product> deadStock(){
  LocalDate cutoff=LocalDate.now().minusDays(45);
  return products.findAll().stream().filter(p->p.getQuantity()>0)
   .filter(p->p.getLastSoldDate()!=null?p.getLastSoldDate().isBefore(cutoff):(p.getCreatedDate()!=null&&p.getCreatedDate().isBefore(cutoff))).toList();
 }
 @Transactional
 public Sale recordSale(Long productId,int quantity){
  if(quantity<1)throw new IllegalArgumentException("Quantity must be at least 1.");
  Product p=products.findById(productId).orElseThrow(()->new IllegalArgumentException("Product not found."));
  if(p.getQuantity()<quantity)throw new IllegalArgumentException("Not enough stock available.");
  p.setQuantity(p.getQuantity()-quantity);p.setLastSoldDate(LocalDate.now());products.save(p);
  return sales.save(new Sale(p,quantity,p.getSellingPrice()));
 }
 public List<Sale> allSales(){return sales.findAllByOrderBySoldAtDesc();}
 public List<Supplier> allSuppliers(){return suppliers.findAll();}
 public Supplier saveSupplier(Supplier s){return suppliers.save(s);}
 public Map<String,Object> dashboard(){
  List<Product> list=products.findAll();Map<String,Object> d=new LinkedHashMap<>();
  d.put("productCount",list.size());d.put("unitsInStock",list.stream().mapToInt(Product::getQuantity).sum());
  d.put("lowStockCount",lowStock().size());d.put("deadStockCount",deadStock().size());
  d.put("inventoryValue",list.stream().mapToDouble(p->p.getCostPrice()*p.getQuantity()).sum());
  d.put("salesToday",sales.findBySoldAtAfter(LocalDate.now().atStartOfDay()).stream().mapToDouble(Sale::getTotal).sum());
  return d;
 }
 public List<Map<String,Object>> seasonalSuggestions(String area,String niche){
  int month=LocalDate.now().getMonthValue();
  String season=month>=3&&month<=6?"Summer":month>=7&&month<=9?"Monsoon":(month==10||month==11)?"Festive season":"Winter";
  String n=niche==null?"":niche.toLowerCase(Locale.ROOT);List<String> items;
  if(n.contains("kirana")||n.contains("grocery"))items=season.equals("Festive season")?List.of("Dry-fruit gift packs","Snacks and namkeen","Cooking oil packs","Tea and coffee","Festival sweets"):List.of("Tea and coffee","Snack multipacks","Cooking oil packs","Instant foods","Packaged beverages");
  else if(n.contains("cloth")||n.contains("boutique"))items=season.equals("Summer")?List.of("Cotton kurtis","Lightweight dupattas","Cotton shirts","Summer dresses","Breathable nightwear"):List.of("Seasonal ethnic wear","Dupattas and scarves","Everyday basics","Festive outfits","Giftable accessories");
  else if(n.contains("hardware"))items=season.equals("Monsoon")?List.of("Waterproofing supplies","Rain protection covers","LED bulbs","Repair kits","Extension boards"):List.of("LED bulbs","Extension boards","Basic tool kits","Electrical fittings","Repair supplies");
  else if(season.equals("Summer"))items=List.of("Water bottles","Electrolyte drinks","Cotton clothing","Sunscreen","Fans and coolers");
  else if(season.equals("Monsoon"))items=List.of("Umbrellas","Raincoats","Mosquito repellent","Waterproof bags","Quick-dry footwear");
  else if(season.equals("Festive season"))items=List.of("Gift packs","Decorative lights","Ethnic accessories","Sweets and dry-fruit boxes","Party supplies");
  else items=List.of("Warm clothing","Moisturizers","Tea and coffee","Blankets","Room heaters");
  List<Map<String,Object>> result=new ArrayList<>();
  for(int i=0;i<items.size();i++){Map<String,Object> r=new LinkedHashMap<>();r.put("product",items.get(i));r.put("season",season);r.put("area",(area==null||area.isBlank())?"Your area":area);r.put("priority",i<2?"Test first":"Consider testing");r.put("basis","Rule-based idea only; verify local demand and your own sales.");result.add(r);}
  return result;
 }
 public List<Map<String,Object>> trendingProducts(){
  Map<String,Integer> totals=new HashMap<>();
  for(Sale s:sales.findBySoldAtAfterOrderBySoldAtDesc(LocalDateTime.now().minusDays(30)))totals.merge(s.getProduct().getName(),s.getQuantity(),Integer::sum);
  List<Map<String,Object>> out=new ArrayList<>();
  totals.entrySet().stream().sorted((a,b)->b.getValue().compareTo(a.getValue())).limit(8).forEach(e->{Map<String,Object> r=new LinkedHashMap<>();r.put("product",e.getKey());r.put("unitsSold30Days",e.getValue());r.put("basis","Recorded sales in this shop over the last 30 days.");out.add(r);});
  return out;
 }
}
