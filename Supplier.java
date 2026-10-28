package com.pinkstock.inventory.model;
import jakarta.persistence.*;
@Entity
public class Supplier {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String name, phone, email, area, niche;
 @Column(length=1000) private String notes;
 public Supplier(){}
 public Supplier(String name,String phone,String email,String area,String niche,String notes){this.name=name;this.phone=phone;this.email=email;this.area=area;this.niche=niche;this.notes=notes;}
 public Long getId(){return id;} public void setId(Long id){this.id=id;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getArea(){return area;} public void setArea(String v){area=v;}
 public String getNiche(){return niche;} public void setNiche(String v){niche=v;}
 public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
}
