package anax.pang.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class AppInfo {

	@Column(name = "level", nullable = false)
	@NotEmpty
	private Integer level;

	@Column(name = "score", nullable = false)
	@NotEmpty
	private Double score;
	
	public AppInfo() {
		super();
	}

	public AppInfo(Integer level, Double score) {
		super();
		this.level = level;
		this.score = score;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
