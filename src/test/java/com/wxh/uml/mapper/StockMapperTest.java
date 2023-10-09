package com.wxh.uml.mapper;

import com.wxh.boot.MyApplication;
import com.wxh.boot.entity.Stock;
import com.wxh.boot.mapper.StockMapper;
import com.wxh.boot.mapper.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.TestOnly;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author wuxinhong
 * @date 2023/7/18 5:07 PM
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class StockMapperTest {
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void testQuery(){
//        List<Stock> stocks = stockMapper.queryStockForUpdate(1L);
//        log.info("list size:{}", stocks.size());
        Stock one = stockRepository.getOne(1L);
        log.info("one:{}", one);
    }
}
