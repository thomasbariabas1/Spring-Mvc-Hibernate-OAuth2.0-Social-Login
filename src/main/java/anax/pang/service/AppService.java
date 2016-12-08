package anax.pang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anax.pang.model.App;
import anax.pang.repository.AppRepository;

@Service
public class AppService {

	@Autowired
	private AppRepository appRepository;
	
	public App getAppByAppId(Integer appId, Boolean appInfo_MODE) {
		return appRepository.getAppByAppId(appId, appInfo_MODE);
	}

	public App getAppByAppName(String appName, Boolean appInfo_MODE) {
		return appRepository.getAppByAppName(appName, appInfo_MODE);
	}
	
	public List<App> getApps() {
		return appRepository.getApps();
	}
	
	public Integer createApp(App app) {
		return appRepository.createApp(app);
	}
	
	public App updateApp(App app) {
		return appRepository.updateApp(app);
	}
	
	public void deleteApp(App app) {
		appRepository.deleteApp(app);
	}
}
