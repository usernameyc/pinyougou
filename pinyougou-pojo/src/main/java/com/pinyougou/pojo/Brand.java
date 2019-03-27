package com.pinyougou.pojo;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 品牌实体类
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-02-25<p>
 */
@Table(name = "tb_brand")
public class Brand implements Serializable{
    /** 品牌id(主键) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /** 品牌名称 */
    @Column(name = "name")
    private String name;
    /** 品牌首字母 */
    @Column(name = "first_char")
    private String firstChar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }
}
