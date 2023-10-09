package com.wxh.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxh.boot.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author wuxinhong
 * @date 2022/12/20 3:22 PM
 */
//@SpringBootApplication
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
//        extends BaseMapper<Stock> {
//    @Update("update db_stock set count = count - #{count} where product_code = #{productCode} and count >= #{count}")
//    int updateStock(@Param("productCode") String productCode, @Param("count") Integer count);

//    @Select("select * from db_stock where product_code = #{productCode} for update")
//    List<Stock> queryStockForUpdate(@Param("productCode") String productCode);

    @Select("select * from src_zndt where id = #{id}")
    List<Stock> queryStockForUpdate(@Param("id") Long id);
}
