package com.qym.Query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicQuery {
    //下面三个都是总收入
    private BigDecimal costMoney;

    private BigDecimal incomeMoney;

    private BigDecimal todayIncomeMoney;

    private Integer count;

    private Integer accountCount;

    //进度
    private List<Integer> progresses;

}
