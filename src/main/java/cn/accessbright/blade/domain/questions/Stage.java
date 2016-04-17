package cn.accessbright.blade.domain.questions;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

/**
 * 关卡
 */
@Entity
@Table(name = "t_stages")
public class Stage extends AbstractPersistable<Integer> {
    @ManyToOne
    private Opus opus;//所属作品

    private String name;//关卡名称

    @ManyToOne
    private Image image;

    private Integer questionCount;//问题数量

    //通关时间(s)
    private Integer passTime;//通关时间

    //通关类别
    @Enumerated(EnumType.ORDINAL)
    private PassType passType;//通关时间类型：1关卡总时间，2每道题的时间

    @ManyToOne
    private ClearanceRule clearanceRule;//通关规则

    public Opus getOpus() {
        return opus;
    }

    public void setOpus(Opus opus) {
        this.opus = opus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getPassTime() {
        return passTime;
    }

    public void setPassTime(Integer passTime) {
        this.passTime = passTime;
    }

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    public ClearanceRule getClearanceRule() {
        return clearanceRule;
    }

    public void setClearanceRule(ClearanceRule clearanceRule) {
        this.clearanceRule = clearanceRule;
    }
 
}
