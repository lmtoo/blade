package cn.accessbright.blade.domain.questions;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_account_stage_schedules")
public class AccountStageSchedule extends AbstractPersistable<Integer> {

    @ManyToOne
    private Stage stage;//关卡

    @ManyToOne
    private Account account;

    private Date startTime;//起始时间

    private Integer answerTime;//耗时

    private Integer answerCorrect;//答对题数

    private Integer questionTotal;//答题数

    private Integer wrongCount;//答错题数

    private Integer overTimeCount;//超时题数

    private Integer gotCoin;//获取金币

    private Integer gotExperience;//获取经验

    private Integer physicalCost;//消耗体力

    private Double rightPercent;//答对百分比,大于0小于100

    private Boolean isPass;//是否通关

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Integer answerTime) {
        this.answerTime = answerTime;
    }

    public Integer getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(Integer answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public Integer getQuestionTotal() {
        return questionTotal;
    }

    public void setQuestionTotal(Integer questionTotal) {
        this.questionTotal = questionTotal;
    }

    public Integer getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    public Integer getOverTimeCount() {
        return overTimeCount;
    }

    public void setOverTimeCount(Integer overTimeCount) {
        this.overTimeCount = overTimeCount;
    }

    public Integer getGotCoin() {
        return gotCoin;
    }

    public void setGotCoin(Integer gotCoin) {
        this.gotCoin = gotCoin;
    }

    public Integer getGotExperience() {
        return gotExperience;
    }

    public void setGotExperience(Integer gotExperience) {
        this.gotExperience = gotExperience;
    }

    public Integer getPhysicalCost() {
        return physicalCost;
    }

    public void setPhysicalCost(Integer physicalCost) {
        this.physicalCost = physicalCost;
    }

    public Double getRightPercent() {
        return rightPercent;
    }

    public void setRightPercent(Double rightPercent) {
        this.rightPercent = rightPercent;
    }

    public Boolean getPass() {
        return isPass;
    }

    public void setPass(Boolean pass) {
        isPass = pass;
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
