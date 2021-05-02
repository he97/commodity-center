package com.mall.commodity_center.service;

import com.alibaba.fastjson.JSONObject;
import com.mall.commodity_center.dao.commodity_center.BrandMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityInfoMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityMapper;
import com.mall.commodity_center.domain.entity.commodity_center.Brand;
import com.mall.commodity_center.domain.entity.commodity_center.Commodity;
import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrandService {
    private final BrandMapper brandMapper;
    private final CommodityInfoMapper commodityInfoMapper;
    private final CommodityMapper commodityMapper;

    public Brand findByBrandId(String BrandId) {
        return brandMapper.selectByPrimaryKey(BrandId);
    }

    public void methods(){
        List<Commodity> commodities = getCommodityList();
        log.info("commodities:{}",commodities);
        for (Commodity commodity :
                commodities) {
            CommodityInfo commodityInfo = CommodityInfo.builder()
                    .commodityId(commodity.getCommodityId())
                    .commodityInformation("这是" + commodity.getCommodityId() + "的info,名字是:" + commodity.getCommodityName() + "类别是:" + commodity.getCommodityCategory())
                    .commodityRemainder(99)
                    .build();
            log.info("commodity：{}",commodity);
            this.commodityInfoMapper.insert(commodityInfo);
            log.info("插入的内容：{}",commodity);
        }
    }
    private List<Commodity> getCommodityList() {
        List<Commodity> commodities = this.commodityMapper.selectAll();
        return commodities;
    }
    public void methodUpdatePicture(){
        getCommodityInfos();
        List<CommodityInfo> commodityInfos = this.commodityInfoMapper.selectAll();
        for (CommodityInfo commodityInfo:
                commodityInfos) {
            log.info("本次的info是:{}",commodityInfo);

            JSONObject jsonObject = new JSONObject();
            String brand;
            Integer index1,index2=1;
            Scanner sc=new Scanner(System.in);
            int max = 10;
            brand = sc.next();
            index1 = Integer.valueOf(sc.next());
            max = Integer.parseInt(sc.next());
            String[] array = new String[max];
            for (; index2<=max; index2++) {
                array[index2-1] = "D:\\springCloudAlibaba\\my_mall\\commodity_center\\src\\main\\resources\\commodities"+brand+"_"+index1+"_"+index2+".jpg";
            }
            log.info("array:{}",array);
            jsonObject.put("url",array);
            log.info("json:{}",jsonObject);
            CommodityInfo commodityInfoUpdate = new CommodityInfo();
            BeanUtils.copyProperties(commodityInfo,commodityInfoUpdate);
            commodityInfoUpdate.setCommodityPictureUrl(jsonObject.toJSONString());
            this.commodityInfoMapper.updateByPrimaryKeySelective(commodityInfoUpdate);

        }
    }

    private List<CommodityInfo> getCommodityInfos() {
        List<CommodityInfo> commodityInfos = this.commodityInfoMapper.selectAll();
        return commodityInfos;
    }

    /**
     * 根据名字找品牌id，没有则添加品牌
     * @param name
     * @return
     */
    public Brand getBrandIdByName(String name){
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("brandName", name);
        List<Brand> brands = this.brandMapper.selectByExample(example);
//        如果没有查找到该品牌
        if(brands.isEmpty()){
            UUID uuid = UUID.randomUUID();
            Brand newBrand = Brand.builder()
                    .brandId(uuid.toString())
                    .brandName(name)
                    .build();
            this.brandMapper.insert(newBrand);
            return newBrand;
        }
        return brands.get(0);
    }
}
