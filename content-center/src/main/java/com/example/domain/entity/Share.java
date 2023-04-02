package com.example.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表名：share
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "share")
public class Share {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private String title;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_original")
    private Boolean isOriginal;

    private String author;

    private String cover;

    private String summary;

    private Integer price;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "show_flag")
    private Boolean showFlag;

    @Column(name = "audit_status")
    private String auditStatus;

    private String reason;
}