package com.lixin.litemall.db.dao;

import com.lixin.litemall.db.domain.LitemallOrder;
import com.lixin.litemall.db.domain.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") LitemallOrder order);

    List<Map> getOrderIds(@Param("query") String query, @Param("orderByClause") String orderByClause);

    List<OrderVo> getOrderList(@Param("query") String query, @Param("orderByClause") String orderByClause);
}
