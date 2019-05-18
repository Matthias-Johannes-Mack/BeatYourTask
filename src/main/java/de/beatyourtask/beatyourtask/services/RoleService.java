package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.Role;
import de.beatyourtask.beatyourtask.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  /**
   * Speichert ein Role-Objekt.
   * @param role die zu speichernde Role.
   * @return gespeichertes Role-Objekt inkl. generierter ID.
   */
  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

}
