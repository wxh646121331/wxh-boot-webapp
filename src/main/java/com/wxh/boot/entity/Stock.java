package com.wxh.boot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wuxinhong
 * @date 2022/12/20 3:20 PM
 */

//@TableName("src_zndt")
@Data
@Entity
@Table(name = "src_zndt")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String productCode;
//
//    private String warehouse;
//
//    private Integer count;
//
//    private Integer version;
}
