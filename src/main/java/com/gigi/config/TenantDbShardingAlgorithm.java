package com.gigi.config;

import com.google.common.collect.Range;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义分库算法
 */
@Slf4j
public class TenantDbShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long tenantId = shardingValue.getValue();
        // 获取分片字段的值
        for (String targetName : availableTargetNames) {
            // 自定义分库逻辑：根据 userId 选择目标数据库
            if (targetName.endsWith(String.valueOf(tenantId % availableTargetNames.size()))) {
                return targetName;
            }
        }
        throw new UnsupportedOperationException("No target database found for value: " + tenantId);
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        Range<Long> range = rangeShardingValue.getValueRange();  // 获取分片字段的值
        Long lower = range.lowerEndpoint(), upper = range.upperEndpoint();
        Set<String> targetDbs = new HashSet<>(2);
        // 获取分片字段的值
        while (lower < upper) {
            String subDbName = buildDbName(lower);
            // 自定义分库逻辑：根据 tenantId 选择目标数据库
            if (collection.contains(subDbName)) {
                targetDbs.add(subDbName);
            }
            lower++;
        }
        return targetDbs;
    }

    private String buildDbName(Long tenantId) {
        return "ds" + tenantId % 2;
    }

    public String formatDataBase() {
        return null;
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
