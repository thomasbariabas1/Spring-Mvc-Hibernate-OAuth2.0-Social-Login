package anax.pang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="user")

public class User {
	
	@Id
	@Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer id;
	
	@OneToOne
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private RegistryType registryType;
	
	@Email
	@Column(name = "email", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;

	@Column(name = "password", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;

	@NotEmpty
	@Column(name = "firstName", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String firstName;

	@NotEmpty	
	@Column(name = "lastName", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;

	@NotEmpty
	@Column(name = "address", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String address;

	@NotEmpty
	@Column(name = "telephone", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String telephone;
	
	@OneToOne
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Role role;

	public User() {
	}

	public User(Integer id) {
		super();
		this.id = id;
	}

	public User(String email) {
		super();
		this.email = email;
	}

	public User(String firstName, String lastName, String address, String telephone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.telephone = telephone;
	}
	
	public User(String email, String password, String firstName, String lastName, String address, String telephone) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.telephone = telephone;
	}

	public User(Integer id, String email, String password, String firstName, String lastName, String andress, String telephone) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = andress;
		this.telephone = telephone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegistryType getRegistryType() {
		return registryType;
	}

	public void setRegistryType(RegistryType registryType) {
		this.registryType = registryType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	public String toString() {
		return getFirstName() + "\t" + getLastName();
	}
}
