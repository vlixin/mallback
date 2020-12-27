package com.lixin.litemall.admin.web;

import com.lixin.litemall.admin.dto.GoodsAllinone;
import com.lixin.litemall.admin.service.AdminGoodsService;
import com.lixin.litemall.admin.vo.good.GoodsDetialVo;
import com.lixin.litemall.common.api.CommonPage;
import com.lixin.litemall.common.api.CommonResult;
import com.lixin.litemall.core.validator.Order;
import com.lixin.litemall.core.validator.Sort;
import com.lixin.litemall.db.domain.LitemallGoods;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/goods")
@Validated
public class AdminGoodsController {
    private final Log logger = LogFactory.getLog(AdminGoodsController.class);

    @Autowired
    private AdminGoodsService adminGoodsService;

    /**
     * 查询商品
     *
     * @param goodsId 商品ID
     * @param goodsSn 商品SN
     * @param name    商品名称
     * @param page    当前页面
     * @param limit   页面大小
     * @param sort    排序 根据时间
     * @param order
     * @return
     */
    //("admin:goods:list")
    //Desc(menu = {"商品管理", "商品管理"}, button = "查询")
    @GetMapping("/list")
    public CommonResult<CommonPage<LitemallGoods>> list(Integer goodsId, String goodsSn, String name,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer limit,
                                                        @Sort @RequestParam(defaultValue = "add_time") String sort,
                                                        @Order @RequestParam(defaultValue = "desc") String order) {
        return CommonResult.success(CommonPage.restPage(
                adminGoodsService.list(goodsId, goodsSn, name, page, limit, sort, order)
        ));
    }

    @GetMapping("/catAndBrand")
    public Object list2() {
        return adminGoodsService.list2();
    }

    /**
     * 编辑商品
     *
     * @param goodsAllinone
     * @return
     */
    //("admin:goods:update")
    //Desc(menu = {"商品管理", "商品管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody GoodsAllinone goodsAllinone) {
        return adminGoodsService.update(goodsAllinone);
    }

    /**
     * 删除商品
     *
     * @param goods
     * @return
     */
    //("admin:goods:delete")
    //Desc(menu = {"商品管理", "商品管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallGoods goods) {
        return adminGoodsService.delete(goods);
    }

    /**
     * 添加商品
     *
     * @param goodsAllinone
     * @return
     */
    //("admin:goods:create")
    //Desc(menu = {"商品管理", "商品管理"}, button = "上架")
    @PostMapping("/create")
    public Object create(@RequestBody GoodsAllinone goodsAllinone) {
        return adminGoodsService.create(goodsAllinone);
    }

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    //("admin:goods:read")
    //Desc(menu = {"商品管理", "商品管理"}, button = "详情")
    @GetMapping("/detail")
    public CommonResult<GoodsDetialVo> detail(@NotNull Integer id) {
        return CommonResult.success(adminGoodsService.detail(id));
    }

}
