package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_account_questions")
public class AccountQuestion extends AbstractPersistable<Integer> {

    @ManyToOne
    private Account account;//玩家账号

    @ManyToOne
    private Title title;//问题

    private Integer level; //当前等级数
    private Integer point;//当前收集到的点数
    private Double triggerPercent;//触发概率（大于0小于100）

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Double getTriggerPercent() {
        return triggerPercent;
    }

    public void setTriggerPercent(Double triggerPercent) {
        this.triggerPercent = triggerPercent;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
