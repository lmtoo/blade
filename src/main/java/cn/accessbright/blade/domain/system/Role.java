package cn.accessbright.blade.domain.system;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "t_roles")
@EntityListeners(AuditingEntityListener.class)
public class Role extends AbstractAuditable<User, Integer> {
	private String name;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	@OneToMany(mappedBy = "role")
	private Set<Permission> permissions;

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

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Transient
	public Set<String> getPermissionNames() {
		Set<String> list = new java.util.HashSet<>();
		Set<Permission> perlist = getPermissions();
		for (Permission per : perlist) {
			list.add(per.getName());
		}
		return list;
	}
}