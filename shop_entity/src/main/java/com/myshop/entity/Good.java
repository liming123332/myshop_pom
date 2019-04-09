package com.myshop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods")
public class Good implements Serializable {
    private int id;
    private String gname;
    private BigDecimal gprice;
    private int gsave;//库存
    private String ginfo;//商品详情
    private String gimage;//图片
    private int status;//商品状态
    private Date createtime;
    private int tid;//商品类别对应的id

}
