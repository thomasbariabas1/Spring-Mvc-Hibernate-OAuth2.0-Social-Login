package anax.pang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="role")
public class Role {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int id;
	
	@Column(name = "name", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

	public Role () {}
	
	public Role (int id, String name) {
		this.id = id;
		this.name = name;
	}
	
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
}
