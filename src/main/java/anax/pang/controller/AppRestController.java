package anax.pang.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import anax.pang.model.App;
import anax.pang.service.AppService;

@RestController
@RequestMapping("/api/user0/apps")
public class AppRestController {
	
	private final String const_appInfo_MODE = "true"; 

	@Autowired
	private AppService appService;
	
	@GetMapping("/{appId}")
	public App getApp(	@PathVariable Integer appId, 
						@RequestParam(required = false, value = "appInfo_MODE", defaultValue = const_appInfo_MODE) Boolean appInfo_MODE) {
		return appService.getAppByAppId(appId, appInfo_MODE);
	}
	
	@GetMapping("/name={appName}")
	public App getAppByAppName(	@PathVariable String appName,
								@RequestParam(required = false, value = "appInfo_MODE", defaultValue = const_appInfo_MODE) Boolean appInfo_MODE) {
		return appService.getAppByAppName(appName, appInfo_MODE);
	}
	
	@GetMapping("/{appId}/appinfo")
	public List<?> getAppInfoByApp(@PathVariable Integer appId) {
		return (List<?>) appService.getAppByAppId(appId, Boolean.valueOf(const_appInfo_MODE)).getListOfAppInfo();
	}
	
	@GetMapping("")
	public List<?> getApps() {
		return appService.getApps();
	}
	
	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public App createApp(@Valid @RequestBody App app) {
		app.setAppId(appService.createApp(app));
		return app;
	}
	
	@PutMapping("/{appId}")
	public App updateApp(@PathVariable Integer appId, @Valid @RequestBody App app) {
		app.setAppId(appId);
		return appService.updateApp(app);
	}
	
	@DeleteMapping("/{appId}")
	public void deleteApp(@PathVariable Integer appId) {
		appService.deleteApp(appService.getAppByAppId(appId, false));
	}
	
}
