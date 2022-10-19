package com.pojo;


public class StudentPojo implements Pojo {
	private String name;
	private String mobile;
	private String email;
	private String dob;
	private int id;
	
	public StudentPojo(){
		
	}
	
	public StudentPojo(String name,String mobile,String email,String dob){
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.dob = dob;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDob() {
		return this.dob;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public int getId() {
		return this.id;
	}

//	@Override
//	public void setDetails(ArrayList<Object> array) {
//		this.setName((String)array.get(0));
//		this.setDob((String)array.get(1));
//		this.setEmail((String)array.get(2));
//		this.setMobile((String)array.get(3));
//	}
	
}