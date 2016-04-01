package cn.accessbright.blade.domain.system;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_menu")
public class Menu extends Resource {
	private String url;

	@OneToMany(mappedBy = "parent")
	private List<Func> funcs;

	@OneToMany(mappedBy = "parent")
	private List<Menu> subMenus;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Func> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<Func> funcs) {
		this.funcs = funcs;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}
}