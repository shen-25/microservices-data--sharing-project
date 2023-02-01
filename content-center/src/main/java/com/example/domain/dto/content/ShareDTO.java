package com.example.domain.dto.content;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShareDTO {

    private Integer id;

    private Integer userId;


    private String title;

    private Date createTime;

    private Date updateTime;


    private Boolean isOriginal;

    private String author;

    private String cover;

    private String summary;

    private Integer price;

    private String downloadUrl;

    private Integer buyCount;


    private Boolean showFlag;

    private String auditStatus;

    private String reason;

    private String wxNickname;
}
