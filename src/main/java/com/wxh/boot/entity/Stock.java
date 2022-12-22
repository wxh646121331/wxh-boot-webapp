package com.wxh.boot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wuxinhong
 * @date 2022/12/20 3:20 PM
 */

@TableName("db_stock")
@Data
public class Stock {
    
    private Long id;

    private String productCode;

    private String warehouse;

    private Integer count;

    private Integer version;
}
