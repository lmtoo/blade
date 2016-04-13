package cn.accessbright.blade.domain.questions;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_account_opus_schedules")
public class AccountOpusSchedule extends AbstractPersistable<Integer> {

    @ManyToOne
    private Account account;//玩家

    @ManyToOne
    private Opus opus;//作品

    private Date startTime; //开始时间

    private Date passTime;//通关时间

    private Integer rightCount;//正确答题数

    private Integer passStageCount;//过关数

    @CreatedDate
    private Date createTime;//创建时间

    @LastModifiedDate
    private Date updateTime;//最后更新时间

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Opus getOpus() {
        return opus;
    }

    public void setOpus(Opus opus) {
        this.opus = opus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Integer getRightCount() {
        return rightCount;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
    }

    public Integer getPassStageCount() {
        return passStageCount;
    }

    public void setPassStageCount(Integer passStageCount) {
        this.passStageCount = passStageCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
