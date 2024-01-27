package com.heima.model.comment.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ap_reply")
public class ApReply {

    @TableId(value = "id", type = IdType.AUTO)
    Integer id;

    @TableField("comment_id")
    Integer commentId;

    @TableField("content")
    String content;

    @TableField("created_time")
    Data createdTime;
}
