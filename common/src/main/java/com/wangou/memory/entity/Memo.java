package com.wangou.memory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 备忘录实体类：映射H2数据库的memo表
 */
@Entity
@Table(name = "memo")
@Data  // Lombok自动生成getter/setter/toString/equals等
@NoArgsConstructor  // 无参构造
@AllArgsConstructor // 全参构造
public class Memo {
    @Id  // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自增
    private Long id;

    @Column(nullable = false, length = 100)  // 非空，标题最长100字
    private String title;

    @Column(length = 1000)  // 内容最长1000字
    private String content;

    @Column(updatable = false)  // 创建时间不允许更新
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // 保存前自动填充创建/更新时间
    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    // 更新前自动更新时间
    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}