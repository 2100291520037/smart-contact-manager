package com.exampleSmartStudent.demo8.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {
//
// @Override
//	public String toString() {
//		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
//				+ ", enable=" + enable + ", imageUrl=" + imageUrl + ", about=" + about +  "]";
//	}
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private int id;
 @NotBlank(message ="User Name can not be empty!!")
@Size(min= 3 ,max= 20, message="min 2 and max 20 charaters are allowed!!")
 private String name;
 @Column(unique = true)
 private String email;
 private String password;
 private String role;
 private boolean enable;
 private String imageUrl;
 @Column(length= 500)
 private String about;
 @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY) // cased user add all releated
 private List<Contact> contacts = new ArrayList<Contact>();
public List<Contact> getContacts() {
	return contacts;
}
public void setContacts(List<Contact> contacts) {
	this.contacts = contacts;
}
public User() {
	super();
}
public int getId() {
	return id;
}
public String getName() {
	return name;
}
public String getEmail() {
	return email;
}
public String getPassword() {
	return password;
}
public String getRole() {
	return role;
}
public boolean isEnable() {
	return enable;
}
public String getImageUrl() {
	return imageUrl;
}
public String getAbout() {
	return about;
}
public void setId(int id) {
	this.id = id;
}
public void setName(String name) {
	this.name = name;
}
public void setEmail(String email) {
	this.email = email;
}
public void setPassword(String password) {
	this.password = password;
}
public void setRole(String role) {
	this.role = role;
}
public void setEnable(boolean enable) {
	this.enable = enable;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public void setAbout(String about) {
	this.about = about;
}
 
 
 
}
