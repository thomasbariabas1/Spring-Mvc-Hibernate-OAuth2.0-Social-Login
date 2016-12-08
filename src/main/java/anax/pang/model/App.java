package anax.pang.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "app", uniqueConstraints = { @UniqueConstraint(columnNames = "app_name") })
@NamedQueries({
		@NamedQuery(name = "App.findByAppId_L", query = "SELECT new App(app.appId, app.appName, app.appDescription) FROM App app WHERE app.appId = :appId"),
		@NamedQuery(name = "App.findByAppName_L", query = "SELECT new App(app.appId, app.appName, app.appDescription) FROM App app WHERE app.appName = :appName"),
		@NamedQuery(name = "App.findByAppId_E", query = "SELECT app FROM App app WHERE app.appId = :appId"),
		@NamedQuery(name = "App.findByAppName_E", query = "SELECT app FROM App app WHERE app.appName = :appName"),
		@NamedQuery(name = "App.findAll", query = "SELECT new App(app.appId, app.appName, app.appDescription) FROM App app")
		})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class App {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_id", nullable = false)
	private Integer appId;

	@Column(name = "app_name", nullable = false)
	@NotEmpty
	private String appName;

	@Lob
	@Column(name = "app_description", nullable = false)
	@NotEmpty
	private String appDescription;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "appinfo", joinColumns = @JoinColumn(name = "app_id"))
	private List<AppInfo> listOfAppInfo = new ArrayList<AppInfo>();

	public App() {
		super();
	}

	public App(String appName) {
		super();
		this.appName = appName;
	}

	public App(Integer appId) {
		super();
		this.appId = appId;
	}

	public App(Integer appId, String appName) {
		super();
		this.appId = appId;
		this.appName = appName;
	}

	public App(Integer appId, String appName, String appDescription) {
		super();
		this.appId = appId;
		this.appName = appName;
		this.appDescription = appDescription;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDescription() {
		return appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	public List<AppInfo> getListOfAppInfo() {
		return listOfAppInfo;
	}

	public void setListOfAppInfo(List<AppInfo> listOfAppInfo) {
		this.listOfAppInfo = listOfAppInfo;
	}

}
