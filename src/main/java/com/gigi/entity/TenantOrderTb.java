package com.gigi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tenant_order_tb")
public class TenantOrderTb {
    @TableId(value = "id")
    public Long id;

    public String orderId;

    public Long tenantId;
    public Date ctime;
    public Date utime;

}
