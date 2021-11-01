package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.RoleRequest;
import vn.alpaca.common.exception.ResourceNotFoundException;
import vn.alpaca.userservice.entity.es.RoleES;
import vn.alpaca.userservice.entity.jpa.Role;
import vn.alpaca.userservice.mapper.RoleMapper;
import vn.alpaca.userservice.repository.es.RoleESRepository;
import vn.alpaca.userservice.repository.jpa.AuthorityJpaRepository;
import vn.alpaca.userservice.repository.jpa.RoleJpaRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log4j2
@RequiredArgsConstructor
public class RoleService {

  private final RoleJpaRepository roleJpaRepo;
  private final RoleESRepository roleEsRepo;
  private final AuthorityJpaRepository authorityJpaRepo;
  private final RoleMapper roleMapper;

  @PostConstruct
  protected void validateData() {
    long jpaCount = roleJpaRepo.count();
    long esCount = roleEsRepo.count();
    if (esCount != jpaCount) {
      log.info("ON LOAD ROLE DATA FROM JPA TO ES...");
      roleEsRepo.deleteAll();
      roleEsRepo.saveAll(
          roleJpaRepo.findAll().stream() 
              .map(roleMapper::roleToRoleES)
              .collect(Collectors.toList()));
    }
  }

  public List<Role> findAll() {
    List<Role> roles;

    if (roleEsRepo.count() > 0) {
      roles =
          StreamSupport.stream(roleEsRepo.findAll().spliterator(), false)
              .map(roleMapper::roleESToRole)
              .collect(Collectors.toList());
    } else {
      roles = roleJpaRepo.findAll();
    }

    return roles;
  }

  public Role findById(int id) {
    Role role;

    Optional<RoleES> roleES = roleEsRepo.findById(id);

    if (roleES.isPresent()) {
      role = roleMapper.roleESToRole(roleES.get());
    } else {
      role =
          roleJpaRepo
              .findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Not found role with id " + id));
    }

    return role;
  }

  public Role findByRoleName(String name) {
    Role role;

    Optional<RoleES> roleES = roleEsRepo.findByName(name);

    if (roleES.isPresent()) {
      role = roleMapper.roleESToRole(roleES.get());
    } else {
      role =
          roleJpaRepo
              .findByName(name)
              .orElseThrow(() -> new ResourceNotFoundException("Not found role with name " + name));
    }

    return role;
  }

  public Role create(RoleRequest requestData) {
    Role role = new Role();
    return getRole(requestData, role);
  }

  public Role update(int roleId, RoleRequest requestData) {
    Role role = findById(roleId);
    return getRole(requestData, role);
  }

  public void delete(int roleId) {
    Role role = findById(roleId);
    roleJpaRepo.delete(role);
  }

  private Role getRole(RoleRequest requestData, Role role) {
    role.setName(requestData.getName());
    if (!ObjectUtils.isEmpty(requestData.getAuthorityIds())) {
      role.getAuthorities().clear();

      try {
        authorityJpaRepo.findAllById(requestData.getAuthorityIds()).forEach(role::addAuthority);
      } catch (EntityNotFoundException ex) {
        throw new ResourceNotFoundException("Authority not found");
      } catch (Exception e) {
        log.error(e);
      }
    }

    return roleJpaRepo.save(role);
  }
}
