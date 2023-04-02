package com.example.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表名：notice
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String content;

    @Column(name = "show_flag")
    private Byte showFlag;

    @Column(name = "create_time")
    private Date createTime;
}