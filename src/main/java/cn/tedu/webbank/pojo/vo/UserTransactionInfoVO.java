package cn.tedu.webbank.pojo.vo;

import lombok.Data;

import java.io.LineNumberReader;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName UserTransactionInfoVO
 * @Version 1.0
 * @Description 資料庫回傳交易訊息詳情
 * @Date 2022/11/1、下午12:26
 */
@Data
public class UserTransactionInfoVO implements Serializable {

    /**
     * 交易ID
     */
    private Long id;
    /**
     * 用戶ID
     */
    private Long userId;
    /**
     * 用戶名
     */
    private String username;
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
    /**
     * 交易類型名稱
     */
    private String typeName;
}
