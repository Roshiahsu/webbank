package cn.tedu.webbank.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName Transaction
 * @Version 1.0
 * @Description TODO 交易資料表
 * @Date 2022/10/26、下午10:26
 */
@Data
public class Transaction implements Serializable {
    /**
     * 交易ID
     */
    private Long id;
    /**
     * 用戶ID
     */
    private Long userId;
    /**
     * 交易類型
     */
    private String transTypeId;
    /**
     * 交易額
     */
    private Long trade;
    /**
     * 交易日期
     */
    private LocalDateTime transDate;

    public Transaction(Long userId, String transTypeId, Long trade, LocalDateTime transDate) {
        this.userId = userId;
        this.transTypeId = transTypeId;
        this.trade = trade;
        this.transDate = transDate;
    }
}
