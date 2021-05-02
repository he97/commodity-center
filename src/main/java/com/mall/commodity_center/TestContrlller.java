package com.mall.commodity_center;


import com.mall.commodity_center.dao.commodity_center.BrandMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityMapper;
import com.mall.commodity_center.domain.entity.commodity_center.Brand;
import com.mall.commodity_center.domain.entity.commodity_center.Commodity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestContrlller {
    private final CommodityMapper commodityMapper;
    private final BrandMapper brandMapper;
    int i = 1;

    @GetMapping("/tset")

    public Brand insert() {
        Brand brand = new Brand();
        Commodity commodity = new Commodity();
//        commodity.setCommodityName("ning ren");
//        commodity.setBrandId(1);
//        commodityMapper.insertSelective(commodity);
//        List<Commodity> commodities = commodityMapper.selectAll();
        Integer integer = 8;
        brand = this.brandMapper.selectByPrimaryKey(1);
        return brand;
    }
    /**
     * 热点规则
     *
     * @param a
     * @param b
     * @return
     */
    @GetMapping("/hot")
    public String hot(
            @RequestParam(required = false) String a,
            @RequestParam(required = false) String b
    ) {
        return a + " " + b+"in commodity-center";
    }
}
