package com.mall.commodity_center.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.commodity_center.dao.commodity_center.BrandMapper;
import com.mall.commodity_center.dao.commodity_center.CategoryMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityInfoMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityMapper;
import com.mall.commodity_center.domain.dto.admin.SearchDto;
import com.mall.commodity_center.domain.dto.commodity.*;
import com.mall.commodity_center.domain.dto.transaction.SubmitCancelDto;
import com.mall.commodity_center.domain.dto.transaction.SubmitCancelRespDto;
import com.mall.commodity_center.domain.entity.commodity_center.Brand;
import com.mall.commodity_center.domain.entity.commodity_center.Category;
import com.mall.commodity_center.domain.entity.commodity_center.Commodity;
import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfo;
import com.mall.commodity_center.domain.enums.CommodityEnum;
import com.mall.commodity_center.domain.enums.EnumToDescription;
import com.mall.commodity_center.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CommodityService {
    private final CommodityMapper commodityMapper;
    private final CommodityInfoMapper commodityInfoMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;
    private final JwtOperator jwtOperator;
    private final BrandService brandService;
    private final EnumToDescription enumToDescription;

    public List<PageCommodityRespDTO> getMainoPageCommodities(){
        Example example = new Example(Commodity.class);
        Example.Criteria criteria = example.createCriteria();
        String token = this.getToken();
        String tokenId = this.getTokenId(token);
        criteria.andEqualTo("commodityStatus", CommodityEnum.ON_SALE.toString());
        criteria.andNotEqualTo("ownerId", tokenId);
        List<Commodity> commodities = this.commodityMapper.selectByExample(example);
        List<PageCommodityRespDTO> mainPageCommodityRespList = getPageRespDTOs(commodities);
        return mainPageCommodityRespList;
    }

    /**
     * 返回主页的商品块所需要的信息
     * @param pageCommodities
     * @return
     */
    private List<PageCommodityRespDTO> getPageRespDTOs(List<Commodity> pageCommodities) {
        List<PageCommodityRespDTO> mainPageCommodityRespList = new ArrayList<>();
        for (Commodity commodity :
                pageCommodities) {
            CommodityInfo commodityInfo = this.commodityInfoMapper.selectByPrimaryKey(commodity.getCommodityId());
            PageCommodityRespDTO mainPageCommodityResp = new PageCommodityRespDTO();
            BeanUtils.copyProperties(commodity,mainPageCommodityResp);
            BeanUtils.copyProperties(commodityInfo,mainPageCommodityResp,"commodityPictureUrl");
            JSONObject picUrl = (JSONObject) JSONObject.parse(commodityInfo.getCommodityPictureUrl());
            JSONArray urls = (JSONArray) picUrl.get("url");
//            如果不是存在云端
            if(!commodity.getCloud()){
                mainPageCommodityResp.setCommodityFirstPictureUrl("/static/commodities/"+urls.getString(0));
            }else{
                mainPageCommodityResp.setCommodityFirstPictureUrl(urls.getString(0));
            }

            log.info("mainPageCommodityResp:{}",mainPageCommodityResp);
            mainPageCommodityRespList.add(mainPageCommodityResp);
        }
        return mainPageCommodityRespList;
    }

    public List<PageCommodityRespDTO> getCommodityByCateGory(@RequestBody Integer categoryIndex){
        Commodity commodity = new Commodity();
        commodity.setBrandId(categoryIndex.toString());
        List<Commodity> commodities = this.commodityMapper.selectAll();
        ArrayList<Commodity> commodityArrayList = new ArrayList<Commodity>();
        for (Commodity backCommodities :
                commodities) {
            if (Integer.valueOf(backCommodities.getCommodityCategory()).equals(categoryIndex) && backCommodities.getCommodityStatus().equals(CommodityEnum.ON_SALE.toString())) {
                commodityArrayList.add(backCommodities);
            }
        }
        List<PageCommodityRespDTO> pageRespDTOs = getPageRespDTOs(commodityArrayList);
        return pageRespDTOs;
    }

    public void setCommodityTempPic(MultipartFile file) {
        log.info("in void");
    }
//  根据商品id找产品
    public CommodityRespDto getCommodityInfo(String id) {
        Commodity commodity = this.commodityMapper.selectByPrimaryKey(id);
        CommodityInfo commodityInfo = this.commodityInfoMapper.selectByPrimaryKey(id);
        Category category = this.categoryMapper.selectByPrimaryKey(commodity.getCommodityCategory());
        Brand brand = this.brandMapper.selectByPrimaryKey(commodity.getBrandId());
        JSONObject picUrls = (JSONObject) JSONObject.parse(commodityInfo.getCommodityPictureUrl());
        List<String> urls = (List<String>) picUrls.get("url");

        if(!commodity.getCloud()){
            List<String> newUrls = new ArrayList<String>();
            for (String url :
                    urls) {
                String newUrl = "/static/commodities/" + url;
                newUrls.add(newUrl);
            }
//            for (int i = 0; i < urls.size(); i++) {
//                String newUrl = "/static/commodities/" + urls.get(i);
//                urls.
//            }
            urls = newUrls;
        }
        log.info("urls:{}",urls);
        CommodityRespDto commodityInfoRespDto = new CommodityRespDto();
        BeanUtils.copyProperties(commodity,commodityInfoRespDto);
        BeanUtils.copyProperties(commodityInfo,commodityInfoRespDto,"commodityFirstPictureUrl");
        BeanUtils.copyProperties(category,commodityInfoRespDto);
        BeanUtils.copyProperties(brand,commodityInfoRespDto);
        commodityInfoRespDto.setCommodityPictureUrl(urls);
        return commodityInfoRespDto;


    }

    private String getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("X-Token");
    }

    /**
     * 从token中获取id
     * @param token
     * @return
     */
    private String getTokenId(String token) {
        Claims claimsFromToken = this.jwtOperator.getClaimsFromToken(token);
        String tokenId;
        tokenId = (String) claimsFromToken.get("id");
        return tokenId;
    }

    /**
     * 根据名字找品牌id，没有则添加品牌
     * @param name
     * @return
     */
    public String getBrandIdByName(String name){
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("brandName", name);
        List<Brand> brands = this.brandMapper.selectByExample(example);
        return null;
    }

    public List<Commodity> getCommodityByName(String name){
        Example example = new Example(Commodity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commodityName", name);
        List<Commodity> commodity = this.commodityMapper.selectByExample(example);
        return commodity;
    }

    public UploadCommodityRespDto handleSubmitCommodity(UploadCommodityDto uploadCommodityDto) {
        try {
//        1先从header 获取token,在获取userId
            String token = this.getToken();
            String tokenId = this.getTokenId(token);

//        TODO 对商品的校验(商品名不能相同)
            if (!this.getCommodityByName(uploadCommodityDto.getCommodityName()).isEmpty()){
                throw new IllegalArgumentException("已经有这种商品了");
            }
            Brand brand = this.brandService.getBrandIdByName(uploadCommodityDto.getBrandName());

//        2，创建随机id
            UUID uuid = UUID.randomUUID();
//        创建两个数据库的对象
            Commodity commodity = new Commodity();
            CommodityInfo commodityInfo = new CommodityInfo();

//        设置商品id及ownerid以及brandid
            commodity.setCommodityId(uuid.toString());
            commodity.setOwnerId(tokenId);
            commodity.setCloud(true);
            commodity.setCommodityStatus(CommodityEnum.WAIT_CHECK.toString());
            commodityInfo.setCommodityId(uuid.toString());
            commodity.setBrandId(brand.getBrandId());

//        更改一下url
            JSONArray urls = getCommodityPicUrls(uploadCommodityDto.getCommodityPictureUrl());
//        添加url
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", urls);
            commodityInfo.setCommodityPictureUrl(jsonObject.toJSONString());


            BeanUtils.copyProperties(uploadCommodityDto,commodity);
            BeanUtils.copyProperties(uploadCommodityDto,commodityInfo);

//         向数据库中注入数据
            this.commodityMapper.insert(commodity);
            this.commodityInfoMapper.insert(commodityInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return UploadCommodityRespDto.builder()
                    .message(e.getMessage())
                    .status("500")
                    .build();
        }
//        this.commodityMapper.selectByExample()
//        this.commodityInfoMapper.insert(commodityInfo);
        return UploadCommodityRespDto.builder()
                .status("200")
                .message("ok")
                .build();
    }

    private JSONArray getCommodityPicUrls(List<String> commodityPictureUrl) {
        JSONArray urls = JSONArray.parseArray(JSON.toJSONString(commodityPictureUrl));
        return urls;
    }

    public List<CommodityRespDto> getRequiredCheckCommodities() {
        Example example = new Example(Commodity.class);
        Example.Criteria criteria = example.createCriteria();
        String string = CommodityEnum.WAIT_CHECK.toString();
        log.info("CommodityEnum.WAIT_CHECK.toString:{}", string);
        criteria.andEqualTo("commodityStatus", string);
        List<Commodity> commodityInfos = this.commodityMapper.selectByExample(example);
        return getCommodityInfoByCommodityList(commodityInfos);
    }

    private void toCommmodityInfo(List<Commodity> commodityInfos, ArrayList<CommodityRespDto> pageCommodityRespDTOS) {
        for (Commodity commodity :
                commodityInfos) {
            CommodityRespDto commodityRespDto = this.getCommodityInfo(commodity.getCommodityId());
            commodityRespDto.setCommodityStatus(
                    this.enumToDescription.getCommodityEnumDescription(commodity.getCommodityStatus())
            );
            pageCommodityRespDTOS.add(commodityRespDto);
        }
    }

    public CheckCommodityRespDto setCommodityStatus(CheckCommodityDto checkCommodityDto) {
        Commodity commodity = this.commodityMapper.selectByPrimaryKey(checkCommodityDto.getCommodityId());
        switch (checkCommodityDto.getType()){
            case "REJECTED":
                commodity.setCommodityStatus(CommodityEnum.REJECTED.toString());
                break;
            case "ON_SALE":
                commodity = this.commodityMapper.selectByPrimaryKey(checkCommodityDto.getCommodityId());
                commodity.setCommodityStatus(CommodityEnum.ON_SALE.toString());
                break;
            default:
                return CheckCommodityRespDto.builder()
                        .status("500")
                        .message("未能更新成功")
                        .build();
        }
        this.commodityMapper.updateByPrimaryKeySelective(commodity);
        return CheckCommodityRespDto.builder()
                .status("200")
                .message("更新成功")
                .build();
    }

    public AlterCommodityStatusRespDto alterStatus(AlterCommodityStatusDto alterCommodityStatusDto) {
        try {
            String userId = this.getUserId();
            Commodity commodity = this.commodityMapper.selectByPrimaryKey(alterCommodityStatusDto.getCommodityId());
            if(commodity.getOwnerId().equals(userId)){
                switch (alterCommodityStatusDto.getType()){
                    case "WAIT_CHECK":
                        if(commodity.getCommodityStatus().equals(CommodityEnum.REJECTED.toString())){
                            commodity.setCommodityStatus(CommodityEnum.WAIT_CHECK.toString());
                            this.commodityMapper.updateByPrimaryKey(commodity);
                        }else {
                            throw new IllegalArgumentException("不能进行上架处理");
                        }
                        break;
                    case "UNDER_CARRIAGE":
                        if (CommodityEnum.ON_SALE.toString().equals(commodity.getCommodityStatus())) {
                            commodity.setCommodityStatus(CommodityEnum.UNDER_CARRIAGE.toString());
                            this.commodityMapper.updateByPrimaryKey(commodity);
                        }else {
                            throw new IllegalArgumentException("不能进行下架处理");
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + alterCommodityStatusDto.getType());
                }
                return AlterCommodityStatusRespDto.builder()
                        .message("update ok")
                        .status("200")
                        .build();
            }else {
                throw new IllegalStateException("商品不对劲");
            }
        } catch (Exception e) {
            return AlterCommodityStatusRespDto.builder()
                    .status("500")
                    .message(e.getMessage())
                    .build();
        }

    }

    private String getUserId() {
        String token = this.getToken();
        return this.getTokenId(token);
    }

    public SubmitCancelRespDto handleSubmitCancel(SubmitCancelDto scd) {
//        处理取消商品,状态改一下就差不多了吧
        try {
            Commodity commodity = this.commodityMapper.selectByPrimaryKey(scd.getCommodityId());
            commodity.setCommodityStatus(CommodityEnum.WAIT_CANCEL_CHECK.name());
            this.commodityMapper.updateByPrimaryKeySelective(commodity);
            return SubmitCancelRespDto.builder()
                    .status("200")
                    .message("等待用户审核")
                    .build();
        } catch (Exception e) {
            log.warn("handleSubmitCancel 出现了错误");
            return SubmitCancelRespDto.builder()
                    .status("500")
                    .message(e.getMessage())
                    .build();
        }
    }

    public UploadCommodityRespDto alterCommodity(AlterCommodityDto alterCommodityDto) {
        try {
            String userId = this.getUserId();
            Brand brandIdByName = this.brandService.getBrandIdByName(alterCommodityDto.getBrandName());

            String picUrls = getPicUrls(alterCommodityDto);

            this.commodityMapper.updateByPrimaryKey(Commodity.builder()
                    .commodityId(alterCommodityDto.getCommodityId())
                    .commodityName(alterCommodityDto.getCommodityName())
                    .brandId(brandIdByName.getBrandId())
                    .commodityCategory(alterCommodityDto.getCommodityCategory())
                    .commodityStatus(CommodityEnum.WAIT_CHECK.name())
                    .cloud(true)
                    .ownerId(userId)
                    .price(alterCommodityDto.getPrice())
                    .build()
            );
            this.commodityInfoMapper.updateByPrimaryKey(
                    CommodityInfo.builder()
                            .commodityId(alterCommodityDto.getCommodityId())
                            .commodityRemainder(alterCommodityDto.getCommodityRemainder())
                            .commodityInformation(alterCommodityDto.getCommodityInformation())
                            .commodityPictureUrl(picUrls)
                            .build()
            );
            return UploadCommodityRespDto.builder()
                    .message("已经更改商品")
                    .status("200")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return UploadCommodityRespDto.builder()
                    .message("更改商品失败")
                    .status("500")
                    .build();
        }
    }

    private String getPicUrls(AlterCommodityDto alterCommodityDto) {
        String picUrls = JSONArray.toJSONString(this.getCommodityPicUrls(alterCommodityDto.getCommodityPictureUrl()));
        return picUrls;
    }

    public List<CommodityRespDto> getAllCommodities() {
        List<Commodity> commodities = this.commodityMapper.selectAll();
        return getCommodityInfoByCommodityList(commodities);
    }

    @NotNull
    private List<CommodityRespDto> getCommodityInfoByCommodityList(List<Commodity> commodities) {
        ArrayList<CommodityRespDto> commodityRespDtos = new ArrayList<>();
        this.toCommmodityInfo(commodities, commodityRespDtos);
        return commodityRespDtos;
    }

    public List<CommodityRespDto> searchCommodities(SearchDto searchDto) {
        Example example = new Example(Commodity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orLike("commodityId", "%" + searchDto.getMessage() + "%");
        criteria.orLike("commodityName","%" + searchDto.getMessage() + "%");
        List<Commodity> commodities = this.commodityMapper.selectByExample(example);


        return this.getCommodityInfoByCommodityList(commodities);
    }
}
