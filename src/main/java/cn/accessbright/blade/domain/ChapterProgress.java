package cn.accessbright.blade.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cn.accessbright.blade.domain.system.User;

@Entity
@Table(name = "t_chapter_progress")
@EntityListeners(AuditingEntityListener.class)
public class ChapterProgress extends AbstractPersistable<Integer> {

	@ManyToOne
	private User player;

	@ManyToOne
	private Chapter chapter;

	private String name;

	@Enumerated(EnumType.STRING)
	private ChapterProgressStatus status;

	@CreatedDate
	private Date createdDate;

	@CreatedBy
	@ManyToOne
	private User createdBy;

	public User getPlayer() {
		return player;
	}

	public void setPlayer(User player) {
		this.player = player;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public ChapterProgressStatus getStatus() {
		return status;
	}

	public void setStatus(ChapterProgressStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}
