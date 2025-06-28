package com.pan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@TableName("record")
public class RecordPojo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "book_id")
    private Integer bookId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "time")
    private String time;


    public RecordPojo(Integer bookId, Integer userId, String time) {
        this.bookId = bookId;
        this.userId = userId;
        this.time = time;
    }

}
