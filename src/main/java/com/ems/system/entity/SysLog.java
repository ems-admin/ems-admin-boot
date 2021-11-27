package com.ems.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:25
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_log")
public class SysLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String logType;

    private String method;

    private String params;

    private long time;

    private String ip;

    private String username;

    private String exceptionDetail;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public SysLog(String logType, long time){
        this.logType = logType;
        this.time = time;
    }
}
