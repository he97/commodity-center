package com.mall.commodity_center.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.mall.commodity_center.dao.commodity_center.CommodityInfoLogMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityMapper;
import com.mall.commodity_center.domain.dto.commodity.CommodityInfoLogDto;
import com.mall.commodity_center.domain.dto.commodity.CommodityRespDto;
import com.mall.commodity_center.domain.dto.commodity.TransactionCommodityDto;
import com.mall.commodity_center.domain.entity.commodity_center.Commodity;
import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfoLog;
import com.mall.commodity_center.domain.entity.transaction.CartCommoditiesDTO;
import com.mall.commodity_center.domain.enums.CommodityEnum;
import com.mall.commodity_center.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserAndCommodityService {
    private final JwtOperator jwtOperator;
    private final CommodityService commodityService;
    private final CommodityMapper commodityMapper;
    private final CommodityInfoLogMapper commodityInfoLogMapper;

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

    private String getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("X-Token");
    }

    public List<CartCommoditiesDTO> getUserCommit() {
        String token = this.getToken();
        String tokenId = this.getTokenId(token);
        Example example = new Example(Commodity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ownerId",tokenId);
//        找到了所有的
        List<Commodity> commodities = this.commodityMapper.selectByExample(example);
        List<CartCommoditiesDTO> commodityRespDtos = getCommoditiesDTOS(commodities);
        return commodityRespDtos;
    }

    private List<CartCommoditiesDTO> getCommoditiesDTOS(List<Commodity> commodities) {
        List<CartCommoditiesDTO> commodityRespDtos = new ArrayList<>();
        for (Commodity commodity:
                commodities) {
            CommodityRespDto commodityInfo = this.commodityService.getCommodityInfo(commodity.getCommodityId());
            CartCommoditiesDTO cartCommoditiesDTO = this.toCartCommodityDto(commodityInfo);
            commodityRespDtos.add(cartCommoditiesDTO);

        }
        return commodityRespDtos;
    }

    @NotNull
    private CartCommoditiesDTO  toCartCommodityDto(CommodityRespDto commodityInfo) {
        CartCommoditiesDTO cartCommoditiesDTO = new CartCommoditiesDTO();
        BeanUtils.copyProperties(commodityInfo,cartCommoditiesDTO);
        cartCommoditiesDTO.setCommodityFirstPictureUrl(commodityInfo.getCommodityPictureUrl().get(0));
        return cartCommoditiesDTO;
    }

    /**
     * 获取用户已经卖出的商品
     * @return
     */
    public List<CommodityInfoLogDto> getSell() {
        try {
            String token = this.getToken();
            String tokenId = this.getTokenId(token);
            Example example = new Example(Commodity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ownerId", tokenId);
            List<Commodity> commodities = this.commodityMapper.selectByExample(example);
            ArrayList<CommodityInfoLogDto> commodityInfoLogDtos = new ArrayList<>();
            for (Commodity commodity :
                    commodities) {
                Example logExample = new Example(CommodityInfoLog.class);
                Example.Criteria logExampleCriteria = logExample.createCriteria();
                logExampleCriteria.andEqualTo("commodityId", commodity.getCommodityId());
                List<CommodityInfoLog> commodityInfoLogs = this.commodityInfoLogMapper.selectByExample(logExample);
                commodityInfoLogs.forEach(dto -> {
                    CommodityInfoLogDto commodityInfoLogDto = new CommodityInfoLogDto();
                    BeanUtils.copyProperties(dto, commodityInfoLogDto);
                    commodityInfoLogDtos.removeIf( delete -> {
                        if(
                            delete.getCommodityId().equals(commodityInfoLogDto.getCommodityId()) &&
                            delete.getTransactionId().equals(commodityInfoLogDto.getTransactionId())
                        ){
                            return true;
                        }

                        return false;
                    });
                    commodityInfoLogDtos.add(commodityInfoLogDto);
                });
            }
            return commodityInfoLogDtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("未有订单");
        }
    }
}
