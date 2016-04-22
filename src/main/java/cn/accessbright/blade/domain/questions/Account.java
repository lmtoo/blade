package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_accounts")
public class Account extends AbstractPersistable<Integer> {
    private Integer coin;//金币数

    private Integer level;//等级数

    private Integer experience;//经验值

    private Integer physical;//体力值

    private Switch status;

    public Switch getStatus() {
        return status;
    }

    public void setStatus(Switch status) {
        this.status = status;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getPhysical() {
        return physical;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }
}
