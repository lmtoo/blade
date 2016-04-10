package cn.accessbright.blade.domain.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_sys_users")
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractAuditable<User, Integer> {

    @NotNull(message = "用户名不能为空!")
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    private boolean locked;

    private boolean internal;// 是否为内部人员，内部系统管理员不能被删除

    private String activeCode;// 用户激活码

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void assginRole(Role role) {
        if (!roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void assginRoles(Collection<Role> roles) {
        Iterator<Role> roleIter = roles.iterator();
        while (roleIter.hasNext()) {
            assginRole(roleIter.next());
        }
    }

    public void copyRoles(User user) {
        assginRoles(user.getRoles());
    }

    public boolean isInternal() {
        return internal;
    }

    public void internal() {
        this.internal = true;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
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