package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.service.WmSensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {

    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;

    /**
     * 获取敏感词列表
     * @param dto
     * @return
     */
    @Override
    public ResponseResult sensitiveList(SensitiveDto dto) {
        dto.checkParam();
        LambdaQueryWrapper<WmSensitive> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (dto.getName().equals("")) {
            lambdaQueryWrapper.ge(WmSensitive::getId, 1);
        } else {
            lambdaQueryWrapper.eq(WmSensitive::getSensitives, dto.getName());
            if (wmSensitiveMapper.selectCount(lambdaQueryWrapper) == 0) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            }
        }
        lambdaQueryWrapper.orderByDesc(WmSensitive::getCreatedTime);
        IPage page = new Page(dto.getPage(), dto.getSize());
        page = page(page, lambdaQueryWrapper);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),
                dto.getSize(),
                (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    /**
     * 新增敏感词
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult sensitiveSave(WmSensitive wmSensitive) {
        if (wmSensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<WmSensitive> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WmSensitive::getSensitives, wmSensitive.getSensitives());
        if (wmSensitiveMapper.selectCount(lambdaQueryWrapper) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
        wmSensitive.setCreatedTime(new Date());
        boolean result = save(wmSensitive);
        return ResponseResult.okResult(result);
    }

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @Override
    public ResponseResult sensitiveDelete(Integer id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        boolean result = removeById(id);
        return ResponseResult.okResult(result);
    }

    /**
     * 修改敏感词
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult sensitiveUpdate(WmSensitive wmSensitive) {
        if (wmSensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        LambdaQueryWrapper<WmSensitive> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WmSensitive::getSensitives, wmSensitive.getSensitives());
        if (wmSensitiveMapper.selectCount(lambdaQueryWrapper) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        Integer id = wmSensitive.getId();
        boolean deleteResult = removeById(id);
        if (deleteResult) {
            wmSensitive.setCreatedTime(new Date());
            boolean saveResult = save(wmSensitive);
            return ResponseResult.okResult(saveResult);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.REMOVE_FAIL);
        }
    }
}
