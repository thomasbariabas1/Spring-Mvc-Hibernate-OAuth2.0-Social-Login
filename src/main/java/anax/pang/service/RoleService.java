package anax.pang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anax.pang.model.Role;
import anax.pang.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
    private RoleRepository roleRepository;
	
	public RoleRepository getRoleRepository() {
		return this.roleRepository;
	}
	
	public Role getRole(int roleId) {
		return getRoleRepository().getRole(roleId);
	}
	
	public Role getRole(String name) {
		return getRoleRepository().getRole(name);
	}
	
	public List<Role> getRoles() {
		return getRoleRepository().getRoles();
	}
}
