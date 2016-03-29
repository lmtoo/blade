package cn.accessbright.blade.domain;

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
@Table(name = "t_image")
@EntityListeners(AuditingEntityListener.class)
public class Image extends AbstractAuditable<User, Integer> {
	private String name;
	private String url;
	private Switch status;

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

	public Switch getStatus() {
		return status;
	}

	public void setStatus(Switch status) {
		this.status = status;
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
