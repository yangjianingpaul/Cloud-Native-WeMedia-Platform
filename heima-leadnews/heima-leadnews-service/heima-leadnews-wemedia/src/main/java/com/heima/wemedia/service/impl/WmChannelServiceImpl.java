package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.WemediaConstants;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {


    /**
     * 查询所有频道
     *
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    /**
     * 频道管理
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult channelManagement(ChannelDto dto) {
//        1。检查参数
        dto.checkParam();
//        2。分页查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmChannel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (dto.getName().equals("")) {
            lambdaQueryWrapper.ge(WmChannel::getId, 1);
        } else {
//        按照用户查询
            lambdaQueryWrapper.eq(WmChannel::getName, dto.getName());
        }
//        按照时间倒序
        lambdaQueryWrapper.orderByDesc(WmChannel::getCreatedTime);
        page = page(page, lambdaQueryWrapper);
//        3。结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),
                dto.getSize(),
                (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    /**
     * 新增频道
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult saveChannel(WmChannel wmChannel) {
        if (wmChannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        wmChannel.setCreatedTime(new Date());
        wmChannel.setIsDefault(WemediaConstants.WM_CHANNEL_IS_DEFAULT);
        save(wmChannel);
        return ResponseResult.okResult(wmChannel);
    }

    /**
     * 更新频道
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult updateChannel(WmChannel wmChannel) {
        if (wmChannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        removeById(wmChannel.getId());
        wmChannel.setCreatedTime(new Date());
        wmChannel.setIsDefault(WemediaConstants.WM_CHANNEL_IS_DEFAULT);
        save(wmChannel);
        return ResponseResult.okResult(wmChannel);
    }

    /**
     * 删除频道
     * @param channelId
     * @return
     */
    @Override
    public ResponseResult deleteChannel(int channelId) {
        WmChannel wmChannel = getById(channelId);
        if (wmChannel.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CHANNEL_NOT_FORBID);
        }

        boolean result = removeById(channelId);
        return ResponseResult.okResult(result);
    }
}