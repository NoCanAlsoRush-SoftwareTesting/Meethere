package lionel.meethere.order.controller;

import lionel.meethere.order.param.SiteBookingOrderAuditParam;
import lionel.meethere.order.param.SiteBookingOrderCreateParam;
import lionel.meethere.order.param.SiteBookingOrderUpdateParam;
import lionel.meethere.order.service.SiteBookingOrderService;
import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.entity.User;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/")
public class SiteBookingOrderController {

    @Autowired
    private SiteBookingOrderService orderService;

    @PostMapping("create")
    public Result<?> createOrder(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestBody SiteBookingOrderCreateParam createParam){
        orderService.crateSiteBookingOrder(userSessionInfo.getId(),createParam);
        return CommonResult.success();
    }

    @PostMapping("audit")
    public Result<?> auditOrder(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody SiteBookingOrderAuditParam auditParam) {
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();

        orderService.auditOrder(auditParam.getOrderId(),auditParam.getAuditStatus());
        return CommonResult.success();
    }

    @PostMapping("cancel")
    public Result<?> cancelOrderByAdmin(@SessionAttribute UserSessionInfo userSessionInfo,
                                   @RequestBody Integer orderId){
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();

        orderService.cancelOrderByAdmin(orderId);
        return CommonResult.success();
    }

    @PostMapping("user/cancel")
    public Result<?> cancelOrderByUser(@SessionAttribute UserSessionInfo userSessionInfo,
                                       @RequestBody Integer orderId){
        if(userSessionInfo.getAdmin() != 0)
            return CommonResult.failed();

        orderService.cancelOrderByUser(userSessionInfo.getId(),orderId);
        return CommonResult.success();
    }

    @PostMapping("user/update")
    public Result<?> updateOrderBookingTime(@SessionAttribute UserSessionInfo userSessionInfo,
                                            @RequestBody SiteBookingOrderUpdateParam updateParam){
        orderService.updateOrderBookTime(updateParam);
        return CommonResult.success();
    }
    @GetMapping("site/list")
    public Result<?> listOrder(@SessionAttribute UserSessionInfo userSessionInfo,
                               @RequestParam Integer siteId,
                               @RequestParam Integer status,
                               @ModelAttribute PageParam pageParam){
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();


        return CommonResult.success().data(orderService.getOrderBySite(siteId,status,pageParam));
    }

    @GetMapping("user/list")
    public Result<?> listUserOrder(@SessionAttribute UserSessionInfo userSessionInfo,
                                   @RequestParam Integer status,
                                   @ModelAttribute PageParam pageParam){
        return CommonResult.success().data(orderService.getOrderByUser(userSessionInfo.getId(),status,pageParam));
    }

    @GetMapping("get")
    public Result<?> getOrderById(@SessionAttribute UserSessionInfo userSessionInfo,
                                  @RequestParam Integer orderId){
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();

        return CommonResult.success().data(orderService.getOrderById(orderId));
    }

}
