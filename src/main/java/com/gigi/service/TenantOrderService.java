package com.gigi.service;

import com.gigi.entity.TenantOrderTb;

import java.util.List;

public interface TenantOrderService {

    List<TenantOrderTb> listTenantOrders(Long tenantId);

    List<TenantOrderTb> listRangeTenantOrders(Long lowerTenantId, Long upperTenantId);

    int insertTenantOrder(TenantOrderTb tenantOrderTb);

    int updTenantOrder(TenantOrderTb tenantOrderTb);

}
