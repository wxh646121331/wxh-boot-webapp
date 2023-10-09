package com.wxh.boot.mapper;

import com.wxh.boot.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * @author wuxinhong
 * @date 2023/7/18 5:25 PM
 */
@Repository
@EnableJpaRepositories
public interface StockRepository extends JpaRepository<Stock, Long> {
}
