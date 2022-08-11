package com.heima.item.canal;

import com.github.benmanes.caffeine.cache.Cache;
import com.heima.item.config.RedisHandler;
import com.heima.item.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * @author Losca
 * @date 2022/8/10 14:51
 */
@Component
@CanalTable("tb_item")
public class ItemHandler implements EntryHandler<Item> {

    @Autowired
    private RedisHandler redisHandler;
    @Autowired
    private Cache<Long,Item> itemCache;
    @Override
    public void insert(Item item) {
        //写入数据库到jvm缓存
        //写入数据库到redis缓存
    }

    @Override
    public void update(Item before, Item after) {
        //写入数据库到jvm缓存
        itemCache.put(after.getId(),after);
        //写入数据库到redis缓存
        redisHandler.savaItem(after);
    }

    @Override
    public void delete(Item item) {
        //写入数据库到jvm缓存
        itemCache.invalidate(item);
        //写入数据库到redis缓存
        redisHandler.deleteItemById(item.getId());
    }
}
