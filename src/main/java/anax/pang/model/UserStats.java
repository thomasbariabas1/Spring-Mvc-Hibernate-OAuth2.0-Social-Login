package anax.pang.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.ParameterMode;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "userstats")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NamedStoredProcedureQuery(
		name = "TopUsers", 
		procedureName = "TopUsers", 
		resultClasses = { UserStats.class }, 
		parameters = {
						@StoredProcedureParameter(name = "IN_UserId", type = Integer.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "IN_HowMany", type = Integer.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "IN_AppId", type = Integer.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "IN_AppLevel", type = Integer.class, mode = ParameterMode.IN)
				}
		)
@NamedQueries({
	@NamedQuery(name = "UserStats.findByUserIdAppIdLevel", query = "SELECT new UserStats(level, latestScore, highScore, rewardPoints) FROM UserStats us WHERE us.user = :user AND us.app.appId = :appId AND us.level = :level"),
	@NamedQuery(name = "UserStats.findByUserIdAppId", query = "SELECT new UserStats(level, latestScore, highScore, rewardPoints) FROM UserStats us WHERE us.user = :user AND us.app.appId = :appId"),
	@NamedQuery(name = "UserStats.findByUserId", query = "SELECT new UserStats(level, latestScore, highScore, rewardPoints) FROM UserStats us WHERE us.user = :user"),
	@NamedQuery(name = "UserStats.findAll.Top", query = "SELECT new UserStats(us.user, us.level, us.latestScore, us.highScore, us.rewardPoints) FROM UserStats us WHERE us.app.appId = :appId AND us.level = :level ORDER BY us.highScore DESC")
	})
public class UserStats extends ResourceSupport {

	@Id
	@Column(name = "userstats_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userStatsId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	private App app;
	
	@Column(name = "app_level", nullable = false)
	private Integer level;

	@Column(name = "latest_score", nullable = false)
	private Double latestScore = 0d;

	@Column(name = "high_score", nullable = false)
	private Double highScore = 0d;

	@Column(name = "reward_points", nullable = false)
	private Integer rewardPoints = 0;
	
	@Column(name = "date_last_score", nullable = false)
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "EET")
	private Date dateLastScore = new Date();
	
	@Column(name = "date_high_score", nullable = false)
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "EET")
	private Date dateHighScore = new Date();

	public UserStats() {
		super();
	}

	public UserStats(Long userStatsId) {
		super();
		this.userStatsId = userStatsId;
	}

	public UserStats(App app, Integer level) {
		super();
		this.app = app;
		this.level = level;
	}

	public UserStats(User user, App app, Integer level) {
		super();
		this.user = user;
		this.app = app;
		this.level = level;
	}

	public UserStats(Integer level, Double latestScore, Double highScore, Integer rewardPoints) {
		super();
		this.level = level;
		this.latestScore = latestScore;
		this.highScore = highScore;
		this.rewardPoints = rewardPoints;
	}

	public UserStats(User user, Integer level, Double latestScore, Double highScore, Integer rewardPoints) {
		super();
		this.user = user;
		this.level = level;
		this.latestScore = latestScore;
		this.highScore = highScore;
		this.rewardPoints = rewardPoints;
	}

	public Long getUserStatsId() {
		return userStatsId;
	}

	public void setUserStatsId(Long userStatsId) {
		this.userStatsId = userStatsId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Double getLatestScore() {
		return latestScore;
	}

	public void setLatestScore(Double latestScore) {
		this.latestScore = latestScore;
	}

	public Double getHighScore() {
		return highScore;
	}

	public void setHighScore(Double highScore) {
		this.highScore = highScore;
	}

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
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

	public Date getDateLastScore() {
		return dateLastScore;
	}

	public void setDateLastScore(Date dateLastScore) {
		this.dateLastScore = dateLastScore;
	}

	public Date getDateHighScore() {
		return dateHighScore;
	}

	public void setDateHighScore(Date dateHighScore) {
		this.dateHighScore = dateHighScore;
	}
}
