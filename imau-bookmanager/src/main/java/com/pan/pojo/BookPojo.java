package com.pan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.ToString;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString
@TableName("imau_learn.book")
public class BookPojo {

    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;

    @TableField(value = "book_name")
    private String bookNme;

    @TableField(value = "book_isbn")
    private String bookIsbn;

    @TableField(value = "book_price")
    private BigDecimal bookPrice;

    @TableField(value = "book_writer")
    private String bookWriter;


    public BookPojo(String bookNme, String bookIsbn,
                    BigDecimal bookPrice, String bookWriter) {
        this.bookNme = bookNme;
        this.bookIsbn = bookIsbn;
        this.bookPrice = bookPrice;
        this.bookWriter = bookWriter;
    }


}
