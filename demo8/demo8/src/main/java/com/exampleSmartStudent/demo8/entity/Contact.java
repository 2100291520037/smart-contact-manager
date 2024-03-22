package com.exampleSmartStudent.demo8.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CONTACT")
public class Contact {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String Name;
	private String secondName;
	private String work;
	private String email;
	private String phone;
	private String image;
	 @Column(length= 500)
	private String description;
	@ManyToOne
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getCid() {
		return cid;
	}
	public String getName() {
		return Name;
	}
	public String getSecondName() {
		return secondName;
	}
	public String getWork() {
		return work;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getImage() {
		return image;
	}
	public String getDescription() {
		return description;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public void setName(String name) {
		Name = name;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Contact{" +
				"cid=" + cid +
				", Name='" + Name + '\'' +
				", secondName='" + secondName + '\'' +
				", work='" + work + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", image='" + image + '\'' +
				", description='" + description + '\'' +
				", user=" + user +
				'}';
	}
}
