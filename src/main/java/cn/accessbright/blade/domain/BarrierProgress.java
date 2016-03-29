package cn.accessbright.blade.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cn.accessbright.blade.domain.system.User;

@Entity
@Table(name = "t_barrier_progress")
@EntityListeners(AuditingEntityListener.class)
public class BarrierProgress extends AbstractPersistable<Integer> {

	@ManyToOne
	private User player;

	@ManyToOne
	private Barrier barrier;

	@LastModifiedDate
	private Date lastModifiedDate;

	@CreatedDate
	private Date createdDate;

	private Integer rightCount;

	private Integer answerCount;

	public User getPlayer() {
		return player;
	}

	public void setPlayer(User player) {
		this.player = player;
	}

	public Barrier getBarrier() {
		return barrier;
	}

	public void setBarrier(Barrier barrier) {
		this.barrier = barrier;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getRightCount() {
		return rightCount;
	}

	public void setRightCount(Integer rightCount) {
		this.rightCount = rightCount;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
}