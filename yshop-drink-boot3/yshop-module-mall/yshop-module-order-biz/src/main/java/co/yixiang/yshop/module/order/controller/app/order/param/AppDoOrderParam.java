package co.yixiang.yshop.module.order.controller.app.order.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * @ClassName HandleOrderParam
 * @Author hupeng <610796224@qq.com>
 * @Date 2023/6/21
 **/
@Getter
@Setter
public class AppDoOrderParam {
    @NotBlank(message = "参数有误")
    @Schema(description = "订单ID", required = true)
    private String uni;
}
