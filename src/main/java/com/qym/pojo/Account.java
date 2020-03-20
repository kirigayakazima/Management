package com.qym.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    //账户账单
    @OneToMany(mappedBy = "account")
    private List<RunningAccount> runningAccount;

    //账户的余额
    @Column(columnDefinition = "Integer default 0")
    private BigDecimal lastMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RunningAccount> getRunningAccount() {
        return runningAccount;
    }

    public void setRunningAccount(List<RunningAccount> runningAccount) {
        this.runningAccount = runningAccount;
    }

    public BigDecimal getLastMoney() {
        return lastMoney;
    }

    public void setLastMoney(BigDecimal lastMoney) {
        this.lastMoney = lastMoney;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", runningAccount=" + runningAccount +
                ", lastMoney=" + lastMoney +
                '}';
    }
}
