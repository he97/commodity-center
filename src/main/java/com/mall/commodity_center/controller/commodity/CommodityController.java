package com.mall.commodity_center.controller.commodity;

import com.mall.commodity_center.auth.CheckAuthorization;
import com.mall.commodity_center.auth.CheckLogin;
import com.mall.commodity_center.domain.dto.admin.SearchDto;
import com.mall.commodity_center.domain.dto.commodity.*;
import com.mall.commodity_center.domain.dto.transaction.SubmitCancelDto;
import com.mall.commodity_center.domain.dto.transaction.SubmitCancelRespDto;
import com.mall.commodity_center.domain.entity.transaction.CartCommoditiesDTO;
import com.mall.commodity_center.service.BrandService;
import com.mall.commodity_center.service.CommodityService;
import com.mall.commodity_center.service.UserAndCommodityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/commodity")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CommodityController {
    private final BrandService brandService;
    private final CommodityService commodityService;
    private final UserAndCommodityService userAndCommodityService;

    @CheckLogin
    @GetMapping("/getmainopagecommodities")
    public List<PageCommodityRespDTO> getMainPageCommodity(){
        return this.commodityService.getMainoPageCommodities();
    }
    @GetMapping("/findAllCommoByCategpry/{categoryIndex}")
    public List<PageCommodityRespDTO> getCommodityByCategory(@PathVariable Integer categoryIndex){
        return  this.commodityService.getCommodityByCateGory(categoryIndex);
    }
    @CheckAuthorization("admin")
    @GetMapping("/getRequiredCheckCommodities")
    public List<CommodityRespDto> getRequiredCheckCommodities(){
        return this.commodityService.getRequiredCheckCommodities();
    }

    @CheckAuthorization("admin")
    @GetMapping("/getAll")
    public List<CommodityRespDto> getAllCommodities(){
        return this.commodityService.getAllCommodities();
    }


    @PostMapping("/searchCommoditiesById")
    public List<CommodityRespDto> searchCommoditiesById(@RequestBody SearchDto searchDto){
        return this.commodityService.searchCommodities(searchDto);
    }

    @PostMapping("/handleCommodity")
    public CheckCommodityRespDto checkCommodity(@RequestBody CheckCommodityDto checkCommodityDto){
        return this.commodityService.setCommodityStatus(checkCommodityDto);
    }

    @PostMapping("/getPic")
    public void getPic(@RequestBody MultipartFile file){
        log.info("name");
//        FormDa
        this.commodityService.setCommodityTempPic(file);
    }

    @GetMapping("/getInfo/{id}")
    public CommodityRespDto getCommodityInfo(@PathVariable String id){
        return this.commodityService.getCommodityInfo(id);
    }
//    提交商品
    @PostMapping("/submitCommodity")
    public UploadCommodityRespDto handleSubmitCommodity(@RequestBody UploadCommodityDto uploadCommodityDto){
        return this.commodityService.handleSubmitCommodity(uploadCommodityDto);

    }
//    更改商品
    @PostMapping("/alterCommodity")
    public UploadCommodityRespDto alterCommodity(@RequestBody AlterCommodityDto alterCommodityDto){
        return this.commodityService.alterCommodity(alterCommodityDto);
    }

    @GetMapping("/userCommit")
    public List<CartCommoditiesDTO> userCommit(){
        return this.userAndCommodityService.getUserCommit();
    }

    @CheckLogin
    @GetMapping("/getSell")
    public List<CommodityInfoLogDto> getSell(){
        return this.userAndCommodityService.getSell();
    }

    @CheckLogin
    @PostMapping("/alterStatus")
    public AlterCommodityStatusRespDto alterStatus(@RequestBody  AlterCommodityStatusDto alterCommodityStatusDto){
        return this.commodityService.alterStatus(alterCommodityStatusDto);

    }
//    提交取消商品
    @CheckLogin
    @PostMapping("/submitCancel")
    public SubmitCancelRespDto handleSubmitCancel(@RequestBody SubmitCancelDto scd){
        return this.commodityService.handleSubmitCancel(scd);
    }
}
