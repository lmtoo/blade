package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_skill_levels")
public class SkillLevel extends AbstractPersistable<Integer> {

    private Integer level;//等级
    private Integer cost;//升级所需金币
    private Integer questionCount;//升级所需问题数量
    private Double triggerPercent;//触发概率

    @ManyToOne
    private Skill skill;//所属技能

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Double getTriggerPercent() {
        return triggerPercent;
    }

    public void setTriggerPercent(Double triggerPercent) {
        this.triggerPercent = triggerPercent;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
