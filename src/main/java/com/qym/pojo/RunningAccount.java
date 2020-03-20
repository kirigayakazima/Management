package com.qym.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RunningAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //盈利和亏损,默认0为亏损,1为盈利
    private Boolean kind;

    //种类描述,亏损还是盈利
    private String description;
    //流水的钱
    private BigDecimal activeMoney;

    //余额
    @NotEmpty(message = "余额不能为空")
    private BigDecimal money;

    //流水产生时间
    private Date createTime;

    @ManyToOne
    private Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getKind() {
        return kind;
    }

    public void setKind(Boolean kind) {
        this.kind = kind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getActiveMoney() {
        return activeMoney;
    }

    public void setActiveMoney(BigDecimal activeMoney) {
        this.activeMoney = activeMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "RunningAccount{" +
                "id=" + id +
                ", kind=" + kind +
                ", description='" + description + '\'' +
                ", activeMoney=" + activeMoney +
                ", money=" + money +
                ", createTime=" + createTime +
                ", account=" + account +
                '}';
    }
}
