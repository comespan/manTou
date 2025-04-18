package co.yixiang.yshop.module.order.handle;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.constant.ShopConstants;
import co.yixiang.yshop.framework.tenant.core.util.TenantUtils;
import co.yixiang.yshop.module.message.redismq.DelayedQueueListener;
import co.yixiang.yshop.module.message.redismq.msg.OrderMsg;
import co.yixiang.yshop.module.order.service.storeorder.AppStoreOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自动收货订单监听
 */
@Component
@Slf4j
public class OrderAutoConfirmListener implements DelayedQueueListener<OrderMsg> {
    @Resource
    private AppStoreOrderService appStoreOrderService;
    @Override
    public String delayedQueueKey() {
        return ShopConstants.REDIS_ORDER_OUTTIME_UNCONFIRM;
    }

    @Override
    public void consume(OrderMsg message) throws Exception {
        if(ObjectUtil.isNotNull(message) && StrUtil.isNotEmpty(message.getOrderId())) {
            TenantUtils.executeIgnore(() -> {
                appStoreOrderService.takeOrder(message.getOrderId(),null);
                log.info("订单编号:{}自动确认收货成功",message.getOrderId());
            });
        }
    }
}
