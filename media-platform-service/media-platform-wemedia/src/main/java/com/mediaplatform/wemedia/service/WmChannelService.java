package com.mediaplatform.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.wemedia.dtos.ChannelDto;
import com.mediaplatform.model.wemedia.pojos.WmChannel;
import okhttp3.Response;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * Query all channels
     *
     * @return
     */
    public ResponseResult findAll();

    /**
     * channel management
     *
     * @param dto
     * @return
     */
    ResponseResult channelList(ChannelDto dto);

    /**
     * New channel
     *
     * @param wmChannel
     * @return
     */
    ResponseResult saveChannel(WmChannel wmChannel);

    /**
     * Update channel
     *
     * @param wmChannel
     * @return
     */
    ResponseResult updateChannel(WmChannel wmChannel);

    /**
     * Delete channel
     *
     * @param channelId
     * @return
     */
    ResponseResult deleteChannel(int channelId);
}