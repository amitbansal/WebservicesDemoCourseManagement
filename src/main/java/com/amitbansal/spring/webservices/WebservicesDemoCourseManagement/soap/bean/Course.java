package com.amitbansal.spring.webservices.WebservicesDemoCourseManagement.soap.bean;

public class Course {
private int id;
private String name;
private String description;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

public Course(int id2, String name2, String description2) {
	super();
	this.id = id2;
	this.name = name2;
	this.description = description2;
}
@Override
public String toString() {
	return "Course [id=" + id + ", name=" + name + ", description=" + description + "]";
}

}
