package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 玩家等级
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_account_levels")
public class AccountLevel extends AbstractPersistable<Integer> {
    private Integer level;//等级
    private Integer requiredExperience;//升级所需经验
    private Integer topPhysical;//体力上限
    private Integer givePhysical;//赠与体力
    private Integer physicalCost;//消耗体力
    private Long timeCost;//升级消耗时间(s)

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getRequiredExperience() {
        return requiredExperience;
    }

    public void setRequiredExperience(Integer requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public Integer getTopPhysical() {
        return topPhysical;
    }

    public void setTopPhysical(Integer topPhysical) {
        this.topPhysical = topPhysical;
    }

    public Integer getGivePhysical() {
        return givePhysical;
    }

    public void setGivePhysical(Integer givePhysical) {
        this.givePhysical = givePhysical;
    }

    public Integer getPhysicalCost() {
        return physicalCost;
    }

    public void setPhysicalCost(Integer physicalCost) {
        this.physicalCost = physicalCost;
    }

    public Long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Long timeCost) {
        this.timeCost = timeCost;
    }
}
