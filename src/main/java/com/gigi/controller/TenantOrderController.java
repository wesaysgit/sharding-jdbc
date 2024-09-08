package com.gigi.controller;

import com.gigi.entity.TenantOrderTb;
import com.gigi.service.TenantOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class TenantOrderController {

    @Autowired
    private TenantOrderService tenantOrderService;

    @RequestMapping("/list")
    public List<TenantOrderTb> tenantOrderTbList(@RequestParam("tenantId") Long tenantId) {
        return tenantOrderService.listTenantOrders(tenantId);
    }

}


