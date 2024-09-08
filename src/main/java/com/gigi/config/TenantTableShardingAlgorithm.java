package com.gigi.config;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义分表算法
 */
@Slf4j
public class TenantTableShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    @Value("${sharding-tables.logic-table-name}")
    private String logicTableName;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long tenantId = shardingValue.getValue();  // 获取分片字段的值
        String subTable = buildTableName(tenantId);
        for (String targetName : availableTargetNames) {
            // 自定义分库逻辑：根据 userId 选择目标数据库
            if (targetName.endsWith(subTable)) {
                return targetName;
            }
        }
        throw new UnsupportedOperationException("No target database found for value: " + tenantId);
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        Range<Long> range = rangeShardingValue.getValueRange();  // 获取分片字段的值
        Long lower = range.lowerEndpoint(), upper = range.upperEndpoint();
        Set<String> targetTables = new HashSet<>(3);
        while (lower <= upper) {
            String subTable = buildTableName(lower);
            targetTables.add(subTable);
            lower++;
        }
        return targetTables;
    }

    public String buildTableName(Long tenantId) {
        logicTableName = StrUtil.isEmpty(logicTableName) ? "tenant_order_tb_" : logicTableName;
        long l = tenantId % 3;
        return logicTableName + "0" + (l + 1);
    }

    @Override
    public void init() {
        // 如果有需要，可以在这里做初始化工作
    }

    @Override
    public String getType() {
        return "CUSTOM_DB_SHARDING";
    }
}
