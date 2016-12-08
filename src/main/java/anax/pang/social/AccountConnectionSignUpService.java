package anax.pang.social;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;

import anax.pang.service.RegistryTypeService;
import anax.pang.service.RoleService;
import anax.pang.service.UserService;
import anax.pang.utility.HashUtilityBean;

@Component
public class AccountConnectionSignUpService implements ConnectionSignUp {

	@Autowired
	private UserService userService;

	@Autowired
	private RegistryTypeService registryTypeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private HashUtilityBean hashUtilityBean;

	public String execute(Connection<?> connection) {

		User profile;
		Facebook facebook = (Facebook) connection.getApi();
		String[] fields = { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices",
				"education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown",
				"inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link",
				"locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes",
				"payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other",
				"sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits",
				"viewer_can_send_gift", "website", "work" };
		profile = facebook.fetchObject("me", User.class, fields);

		anax.pang.model.User user = new anax.pang.model.User(profile.getEmail());
		// profile.getFirstName(),profile.getLastName(),
		user.setFirstName(profile.getFirstName());
		user.setLastName(profile.getLastName());
		user.setTelephone("69");
		user.setAddress("K");
		user.setRegistryType(registryTypeService.getRegistryType("regcust"));
		user.setRole(roleService.getRole("ROLE_USER"));
		user.setPassword(hashUtilityBean.encryptHashMD5("123"));

		// AccessGrant access = new AccessGrant(profile.getName());
		// System.out.println("Access Token"+access.getAccessToken());
		String userId = UUID.randomUUID().toString();

		userService.createUser(user);

		return userId;
	}
}