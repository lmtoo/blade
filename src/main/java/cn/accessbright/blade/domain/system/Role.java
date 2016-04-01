package cn.accessbright.blade.domain.system;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_sys_roles")
@EntityListeners(AuditingEntityListener.class)
public class Role extends AbstractAuditable<User, Integer> {
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	@ElementCollection
	@CollectionTable(name = "t_sys_roles_permissions")
	private Set<String> permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
}