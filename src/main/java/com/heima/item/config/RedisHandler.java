package com.heima.item.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.item.pojo.Item;
import com.heima.item.pojo.ItemStock;
import com.heima.item.service.IItemService;
import com.heima.item.service.IItemStockService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Losca
 * @date 2022/8/9 17:25
 */
@Component
public class RedisHandler implements InitializingBean {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    IItemService itemService;
    @Autowired
    IItemStockService iItemStockService;

    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * InitializingBean接口来实现，因为InitializingBean可以在对象被Spring创建并且成员变量全部注入后执行。
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Item> itemList = itemService.list();
        for (Item item : itemList) {
            redisTemplate.opsForValue().set("item:id:"+item.getId(),mapper.writeValueAsString(item));
        }
        List<ItemStock> itemStockList = iItemStockService.list();
        for (ItemStock itemStock : itemStockList) {
            redisTemplate.opsForValue().set("item:id:stock:"+itemStock.getId(),mapper.writeValueAsString(itemStock));
        }
    }
}
