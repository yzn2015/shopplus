package com.yzn.sport.cart;

import com.yzn.sport.mapper.SkuMapper;
import com.yzn.sport.pojo.BuyerCart;
import com.yzn.sport.pojo.BuyerItem;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: YangZaining
 * @Date: Created in 12:38$ 2018/8/9$
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    @Resource
    private JedisPool jedisPool;

    @Override
    public void addBuyerCartToRedis(Long sid, Integer count,String username) {
        Jedis jedis = jedisPool.getResource();
        Map<String,String> map =jedis.hgetAll(username);
        boolean flag = false;
        for(Map.Entry<String,String> m : map.entrySet()){
            if(sid.equals(m.getKey())){
                flag = true;
            }
        }
        if(flag){
            jedis.hincrBy(username, String.valueOf(sid),count);
        }
        else {
            jedis.hset(username,String.valueOf(sid),String.valueOf(count));
        }
        jedis.close();
    }

    @Resource
    private SkuMapper skuMapper;

    @Override
    public BuyerCart getBuyerCartFromRedis(String username) {
        Jedis jedis = jedisPool.getResource();
        Map<String,String> map = jedis.hgetAll(username);
        BuyerCart bc = new BuyerCart();
        List<BuyerItem> blist = new ArrayList<>();
        for (Map.Entry<String,String> m : map.entrySet()){
            Long sid = Long.parseLong(m.getValue());
            BuyerItem bi = new BuyerItem();
            bi.setAmount(Integer.parseInt(map.get(String.valueOf(sid))));
            bi.setIsHave(true);
            bi.setSku(skuMapper.selectByPrimaryKey(sid));
            blist.add(bi);
        }
        bc.setItems(blist);
        return null;
    }
}
