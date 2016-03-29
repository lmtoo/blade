package cn.accessbright.blade.domain.system;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_users")
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "User.authority", attributeNodes = { @NamedAttributeNode("roles") })
public class User extends AbstractAuditable<User, Integer> {
	private String username;

	@JsonIgnore
	private String password;

	private String email;

	@ManyToMany(fetch=FetchType.EAGER)
	private List<Role> roles;

	private boolean isInternal;// 是否为内部人员，内部系统管理员不能被删除

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean isInternal() {
		return isInternal;
	}

	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}

	public Set<String> getRoleNames() {
		if (roles == null || roles.isEmpty()) {
			return Collections.emptySet();
		}
		Set<String> roleNames = new HashSet<>();
		for (Role role : roles) {
			roleNames.add(role.getName());
		}
		return roleNames;
	}
}