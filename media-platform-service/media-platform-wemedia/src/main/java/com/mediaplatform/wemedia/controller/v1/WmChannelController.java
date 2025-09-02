package com.mediaplatform.wemedia.controller.v1;

import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.wemedia.dtos.ChannelDto;
import com.mediaplatform.model.wemedia.pojos.WmChannel;
import com.mediaplatform.wemedia.service.WmChannelService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    @Autowired
    private WmChannelService wmChannelService;

    @GetMapping("/channels")
    public ResponseResult findAll() {
        return wmChannelService.findAll();
    }

    @PostMapping("/list")
    public ResponseResult channelList(@RequestBody ChannelDto dto) {
        return wmChannelService.channelList(dto);
    }

    @PostMapping("/save")
    public ResponseResult saveChannel(@RequestBody WmChannel wmChannel) {
        return wmChannelService.saveChannel(wmChannel);
    }

    @PostMapping("/update")
    public ResponseResult updateChannel(@RequestBody WmChannel wmChannel) {
        return wmChannelService.updateChannel(wmChannel);
    }

    @GetMapping("/del/{id}")
    public ResponseResult deleteChannel(@PathVariable String id) {
        int channelId = Integer.parseInt(id);
        return wmChannelService.deleteChannel(channelId);
    }
}
