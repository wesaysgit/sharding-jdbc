package com.gigi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gigi.entity.TenantOrderTb;
import com.gigi.mapper.TenantOrderTbMapper;
import com.gigi.service.TenantOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantOrderServiceImpl implements TenantOrderService {

    @Autowired
    private TenantOrderTbMapper tenantOrderTbMapper;

    @Override
    public List<TenantOrderTb> listTenantOrders(Long tenantId) {
        QueryWrapper<TenantOrderTb> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tenant_id", tenantId);

        return tenantOrderTbMapper.selectList(queryWrapper);
    }

    @Override
    public List<TenantOrderTb> listRangeTenantOrders(Long lowerTenantId, Long upperTenantId) {
        QueryWrapper<TenantOrderTb> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("tenant_id", lowerTenantId, upperTenantId);
        return tenantOrderTbMapper.selectList(queryWrapper);
    }

    @Override
    public int insertTenantOrder(TenantOrderTb tenantOrderTb) {
        return tenantOrderTbMapper.insert(tenantOrderTb);
    }

    @Override
    public int updTenantOrder(TenantOrderTb tenantOrderTb) {
        return tenantOrderTbMapper.updateById(tenantOrderTb);
    }
}
