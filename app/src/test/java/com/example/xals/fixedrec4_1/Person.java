package com.example.xals.fixedrec4_1;

public class Person implements IPerson {
    private String name ;
	public  String getName()            {	return name;	}
	public  void   setName(String name) {	this.name = name;	}
 
	public void rename(String new_name) {
		if (!new_name.equals(name))		this.name = new_name;
	}
}