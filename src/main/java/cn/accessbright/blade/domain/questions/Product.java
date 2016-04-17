package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_products")
public class Product extends AbstractPersistable<Integer> {
    private String name;//商品名称
    private Integer total;//购买所需金额
    private CurrencyType currency;//金额类别(1人民币，2游戏币)

    private EffectType effectType;//商品增益类型(1增加金币，2增加体力)

    private Integer effectValue;//增益值

    private String remark;//备注

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public Integer getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(Integer effectValue) {
        this.effectValue = effectValue;
    }
}
