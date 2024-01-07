package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;
import okhttp3.Response;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     * @return
     */
    public ResponseResult findAll();

    /**
     * 频道管理
     * @param dto
     * @return
     */
    ResponseResult channelManagement(ChannelDto dto);

    /**
     * 新增频道
     * @param wmChannel
     * @return
     */
    ResponseResult saveChannel(WmChannel wmChannel);

    /**
     * 更新频道
     * @param wmChannel
     * @return
     */
    ResponseResult updateChannel(WmChannel wmChannel);

    /**
     * 删除频道
     * @param channelId
     * @return
     */
    ResponseResult deleteChannel(int channelId);
}