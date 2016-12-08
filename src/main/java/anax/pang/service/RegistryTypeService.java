package anax.pang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anax.pang.model.RegistryType;
import anax.pang.repository.RegistryTypeRepository;

@Service
public class RegistryTypeService {

	@Autowired
    private RegistryTypeRepository registryTypeRepository;
	
	public RegistryTypeRepository getRegistryTypeRepository() {
		return this.registryTypeRepository;
	}
	
	public RegistryType getRegistryType(int registryTypeId) {
		return getRegistryTypeRepository().getRegistryType(registryTypeId);
	}
	
	public RegistryType getRegistryType(String shortName) {
		return getRegistryTypeRepository().getRegistryType(shortName);
	}
	
	public List<RegistryType> getRegistryTypes() {
		return getRegistryTypeRepository().getRegistryTypes();
	}
	
}
