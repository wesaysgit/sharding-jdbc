package com.gigi.hi.db;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gigi.ShardingDemoApplication;
import com.gigi.entity.TenantOrderTb;
import com.gigi.mapper.TenantOrderTbMapper;
import com.gigi.service.TenantOrderService;
import com.gigi.utils.TradeNoUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest(classes = ShardingDemoApplication.class)
@RunWith(SpringRunner.class)
public class ShardingJdbcTest {

    @Autowired
    private TenantOrderService tenantOrderService;
    @Autowired
    private TenantOrderTbMapper tenantOrderTbMapper;

    @Test
    public void updById() {
        TenantOrderTb upd = new TenantOrderTb();
        upd.setOrderId("20240908190533434999");
        upd.setUtime(new Date());
        UpdateWrapper<TenantOrderTb> updWrapper = new UpdateWrapper<>();
        updWrapper.eq("tenant_id", 5L);
        int update = tenantOrderTbMapper.update(upd, updWrapper);
        log.info("updById update:{}", update);
    }

    @Test
    public void rangeList() {
        List<TenantOrderTb> tenantOrderTbs = tenantOrderService.listRangeTenantOrders(2L, 5L);
        log.info("rangeList size:{}", tenantOrderTbs.size());
    }

    @Test
    public void batchInsert() {
        for (int i = 0; i < 1000; i++) {
            insert();
        }
    }

    @Test
    public void insert() {
        long tenantId = RandomUtil.randomLong(100);
        Date now = new Date();
        TenantOrderTb orderTb = new TenantOrderTb();
        orderTb.setOrderId(TradeNoUtil.getTradeNo());
        orderTb.setTenantId(tenantId);
        orderTb.setCtime(now);
        orderTb.setUtime(now);
        int i = tenantOrderService.insertTenantOrder(orderTb);
        log.info("insertTenantOrder tenantId: {}, r: {}", tenantId, i);
    }
}
