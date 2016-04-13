package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_opuses")
public class Opus extends AbstractPersistable<Integer> {
    private String name;//作品名称
    private Integer stageCount;//总关卡数

    @ManyToOne
    private Image image;

    @OneToMany(mappedBy = "opus")
    private List<Stage> stages;//所有关卡

    @OneToMany(mappedBy = "opus")
    private List<Card> cards;//所有卡牌

    private String description;//描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStageCount() {
        return stageCount;
    }

    public void setStageCount(Integer stageCount) {
        this.stageCount = stageCount;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
