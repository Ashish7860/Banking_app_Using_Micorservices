package com.nagarro.customer_management_service.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



@Entity
@Table(name = "customer")
public class Customer {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		
		private Long id;
		
		@NotNull
		@Pattern(regexp="[a-zA-Z]+", message="Kindly provide the first name in alphabets!!")
	    private String first_name;
		
		@NotNull
		@Pattern(regexp="[a-zA-Z]+", message="Kindly provide the last name in alphabets!!")
	    private String last_name;
		
		
		@NotNull
		@Pattern(regexp="^(Male|Female)$", message="Gender should be either Male or Female")
	    private String gender;
	    
	    @Email(message = "Email should be valid")
	    private String email;
	    
	    @NotNull
	    @Pattern(regexp="^\\d{10}$", message="Kindly provide the phone_number that must be of 10 digits")
	    private String phone_number;

	    
	    @NotBlank(message ="Address is compulsory")
	    private String address;

	 
	        
		//Default Constructor
	    public Customer() {
		
		}
		
		public Customer(Long id, String first_name, String last_name, String gender, String email, String phone_number,
				String address ) {
			super();
			this.id = id;
			this.first_name = first_name;
			this.last_name = last_name;
			this.gender = gender;
			this.email = email;
			this.phone_number = phone_number;
			this.address = address;
			
		}


	
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFirst_name() {
			return first_name;
		}

		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}

		public String getLast_name() {
			return last_name;
		}

		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone_number() {
			return phone_number;
		}

		public void setPhone_number(String phone_number) {
			this.phone_number = phone_number;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}



		@Override
		public String toString() {
			return "Customer [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", gender="
					+ gender + ", email=" + email + ", phone_number=" + phone_number + ", address=" +  "]";
		}

		

}