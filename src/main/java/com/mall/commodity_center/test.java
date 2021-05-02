package com.mall.commodity_center;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.mall.commodity_center.dao.commodity_center.CommodityInfoMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityMapper;
import com.mall.commodity_center.domain.entity.commodity_center.Commodity;
import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfo;
import com.mall.commodity_center.service.BrandService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class test {
    private final CommodityInfoMapper commodityInfoMapper;
    private final CommodityMapper commodityMapper;
    private final BrandService brandService;
    public static void main(String[] args) {
//        JSONObject jsonObject = new JSONObject();
//        String brand;
//        Integer index1,index2=1;
//        Scanner sc=new Scanner(System.in);
//        int max = 10;
//        brand = sc.next();
//        index1 = Integer.valueOf(sc.next());
//        max = Integer.valueOf(sc.next());
//        String array[] = new String[max];
//        for (; index2<=max; index2++) {
//            array[index2-1] = "D:\\springCloudAlibaba\\my_mall\\commodity_center\\src\\main\\resources\\commodities"+brand+"_"+index1+"_"+index2+".jpg";
//        }
//        log.info("array:{}",array);
//        jsonObject.put("url",array);
//        log.info("json:{}",jsonObject);
        String string = "{\"url\": [\"D:\\\\springCloudAlibaba\\\\my_mall\\\\commodity_center\\\\src\\\\main\\\\resources\\\\commodities橙花_1_1.jpg\", \"D:\\\\springCloudAlibaba\\\\my_mall\\\\commodity_center\\\\src\\\\main\\\\resources\\\\commodities橙花_1_2.jpg\", \"D:\\\\springCloudAlibaba\\\\my_mall\\\\commodity_center\\\\src\\\\main\\\\resources\\\\commodities橙花_1_3.jpg\", \"D:\\\\springCloudAlibaba\\\\my_mall\\\\commodity_center\\\\src\\\\main\\\\resources\\\\commodities橙花_1_4.jpg\"]}";

        JSONObject json = new JSONObject();
        String[] data = new String[3];
        data[0]="452";
        data[1]="555";
        data[2]="dsa";
        json.put("nm",data);
        String data2 = json.toJSONString();
        log.info("data2:{}",data2);
        JSONObject parse = (JSONObject) JSONObject.parse(string);
        log.info("json转译后:{}",parse.get("url "));


    }

    private List<Commodity> getCommodityList() {
        List<Commodity> commodities = this.commodityMapper.selectAll();
        return commodities;
    }
}