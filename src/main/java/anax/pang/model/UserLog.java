package anax.pang.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "userlog")
public class UserLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	@NotEmpty
	@OneToOne
	@JoinColumn(name = "user_id")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private User user;

	@NotEmpty
	@Column(name = "event_datetime", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "EET")
	@JsonProperty("event-datetime")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date eventDate;
	
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;
	
	@ManyToOne
	@JoinColumn(name = "app_id")
	private App app;
	
	@Column(name = "app_level")
	private Integer level;

	public UserLog() {
		super();
	}
	
	public UserLog(Long id, User user, Date eventDate) {
		super();
		this.id = id;
		this.user = user;
		this.eventDate = eventDate;
	}

	public UserLog(Date eventDate, Event event) {
		super();
		this.eventDate = eventDate;
		this.event = event;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
