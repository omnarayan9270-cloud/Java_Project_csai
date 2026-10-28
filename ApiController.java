package com.pinkstock.inventory.controller;
import com.pinkstock.inventory.model.*;
import com.pinkstock.inventory.repository.ProductRepository;
import com.pinkstock.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {
 private final InventoryService service; private final ProductRepository products;
 public ApiController(InventoryService service,ProductRepository products){this.service=service;this.products=products;}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(){return service.dashboard();}
 @GetMapping("/products") public List<Product> products(@RequestParam(required=false)String q){return q==null||q.isBlank()?service.allProducts():service.searchProducts(q);}
 @PostMapping("/products") @ResponseStatus(HttpStatus.CREATED) public Product create(@Valid @RequestBody Product p){return service.saveProduct(p);}
 @PutMapping("/products/{id}") public Product update(@PathVariable Long id,@Valid @RequestBody Product p){if(!products.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");p.setId(id);return service.saveProduct(p);}
 @DeleteMapping("/products/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){try{service.deleteProduct(id);}catch(Exception e){throw new ResponseStatusException(HttpStatus.CONFLICT,"Could not delete this product. It may have sales linked to it.");}}
 @GetMapping("/low-stock") public List<Product> lowStock(){return service.lowStock();}
 @GetMapping("/dead-stock") public List<Product> deadStock(){return service.deadStock();}
 @PostMapping("/sales") @ResponseStatus(HttpStatus.CREATED) public Sale sale(@RequestBody Map<String,Object> body){try{return service.recordSale(Long.valueOf(body.get("productId").toString()),Integer.parseInt(body.get("quantity").toString()));}catch(RuntimeException e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());}}
 @GetMapping("/sales") public List<Sale> sales(){return service.allSales();}
 @GetMapping("/suppliers") public List<Supplier> suppliers(){return service.allSuppliers();}
 @PostMapping("/suppliers") @ResponseStatus(HttpStatus.CREATED) public Supplier createSupplier(@RequestBody Supplier s){return service.saveSupplier(s);}
 @GetMapping("/recommendations") public List<Map<String,Object>> recommendations(@RequestParam(defaultValue="Jaipur")String area,@RequestParam(defaultValue="kirana")String niche){return service.seasonalSuggestions(area,niche);}
 @GetMapping("/trending") public List<Map<String,Object>> trending(){return service.trendingProducts();}
}
