package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_clearance_rules")
public class ClearanceRule extends AbstractPersistable<Integer> {

    private String name;//规则名称

    private String description;//规则描述

    private Double rightPercent;//正确百分比，大于0小于100

    private Integer rightCount;//正确数

    private Integer wrongCount;//错误数

    private Integer seriesRight;//连续正确数

    private Integer seriesWrong;//连续错误数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRightPercent() {
        return rightPercent;
    }

    public void setRightPercent(Double rightPercent) {
        this.rightPercent = rightPercent;
    }

    public Integer getRightCount() {
        return rightCount;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
    }

    public Integer getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    public Integer getSeriesRight() {
        return seriesRight;
    }

    public void setSeriesRight(Integer seriesRight) {
        this.seriesRight = seriesRight;
    }

    public Integer getSeriesWrong() {
        return seriesWrong;
    }

    public void setSeriesWrong(Integer seriesWrong) {
        this.seriesWrong = seriesWrong;
    }
}
