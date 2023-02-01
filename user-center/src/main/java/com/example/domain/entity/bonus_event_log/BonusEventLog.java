package com.example.domain.entity.bonus_event_log;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表名：bonus_event_log
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bonus_event_log")
public class BonusEventLog {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 积分数
     */
    private Integer value;

    /**
     * 事件
     */
    private String event;

    @Column(name = "create_time")
    private Date createTime;

    private String description;
}