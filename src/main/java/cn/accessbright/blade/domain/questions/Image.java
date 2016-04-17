package cn.accessbright.blade.domain.questions;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cn.accessbright.blade.domain.system.User;

@Entity
@Table(name = "t_images")
@EntityListeners(AuditingEntityListener.class)
public class Image extends AbstractAuditable<User, Integer> {
	private String name;
	private String url;
	private  Boolean available;

	@ManyToOne
	private User checkedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public User getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(User checkedBy) {
		this.checkedBy = checkedBy;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
}
