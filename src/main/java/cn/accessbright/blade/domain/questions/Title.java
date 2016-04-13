package cn.accessbright.blade.domain.questions;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "t_titles")
public class Title extends AbstractPersistable<Integer> {
    @ManyToOne
    private Image image;

    private String description;//问题描述

    private String question;//问题内容

    private String answerCorrect;//正确选项

    private String answerWrong;//错误选项

    @ManyToOne
    private Opus opus;//所属关卡

    @ManyToOne
    private Card card;//所属卡牌

    @ManyToOne
    private Skill skill;//技能效果

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public String getAnswerWrong() {
        return answerWrong;
    }

    public void setAnswerWrong(String answerWrong) {
        this.answerWrong = answerWrong;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Opus getOpus() {
        return opus;
    }

    public void setOpus(Opus opus) {
        this.opus = opus;
    }
}
