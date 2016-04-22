package cn.accessbright.blade.domain.questions;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_skills")
public class Skill extends AbstractPersistable<Integer> {
    private String name;//技能名称

    @Enumerated(EnumType.ORDINAL)
    private SkillType type;//技能类型（1去除错误选项，2延长答题时间）

    private Integer value;//技能值

    private String description;

    @OrderBy("level ASC")
    @OneToMany(mappedBy = "skill")
    private List<SkillLevel> levels;//技能等级

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SkillLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<SkillLevel> levels) {
        this.levels = levels;
    }
}
