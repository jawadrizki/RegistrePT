package com.poissontata.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.User;

@Transactional
public interface UserRepository extends JpaRepository<User,String>{
	
	@Modifying
	@Query(nativeQuery = true, value = "insert into users (username, password, active, type, partenaire) values (:username,MD5(:password),:active,:t,:p)")
	public void addUser(@Param("username") String username,@Param("password")String password,@Param("active")boolean active,@Param("t")String type,@Param("p")Long partenaire);

	@Modifying
	@Query(nativeQuery = true, value = "insert into users_roles values (:username,:role)")
	public void associateRoleTo(@Param("username") String username,@Param("role")String role);
	
	@Query("select u from users u where u.username like :username")
	public Page<User> findByUsername(@Param("username") String username,Pageable pageable);
	
	@Query("select p from partenaires p where p.id not in (select partenaire from users where partenaire != 'NULL')")
	public List<Collaborateur> findPartenaireWithoutCompte();
	
	@Query("select p from partenaires p where p.id in (select partenaire from users where username = :username)")
	public Collaborateur getAccountOwner(@Param("username") String username);
	
	@Modifying
	@Query(nativeQuery = true, value = "update users set active = true where username like :username")
	public void activateCompte(@Param("username") String username);
	
	@Modifying
	@Query(nativeQuery = true, value = "update users set active = false where username like :username")
	public void deactivateCompte(@Param("username") String username);
	
	@Modifying
	@Query(nativeQuery = true, value = "update users set password = MD5(:pwd) where username like :username")
	public void changePwd(@Param("username") String username,@Param("pwd") String pwd);
	
}
