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
@Table(name = "t_account_cards")
public class AccountCard extends AbstractPersistable<Integer> {

    @ManyToOne
    private Account account;//玩家账号

    @ManyToOne
    private Card card;//卡牌

    private Integer total;//卡牌数

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


}
