package cn.accessbright.blade.domain.questions;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/11.
 */
@Entity
@Table(name = "t_account_product_histories")
public class AccountProductHistory extends AbstractPersistable<Integer> {

    @ManyToOne
    private Account account;

    @Temporal(TemporalType.TIMESTAMP)
    private Date occurTime;//购买时间

    @ManyToOne
    private Product product;//商品

    private Integer quantity;//数量

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
