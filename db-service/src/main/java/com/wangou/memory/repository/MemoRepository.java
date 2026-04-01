package com.wangou.memory.repository;
import com.wangou.memory.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据访问层：继承JpaRepository即可获得所有基础CRUD方法
 * JpaRepository<实体类, 主键类型>
 */
@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // 无需写任何代码，Spring Data JPA自动实现：
    // findAll()、findById()、save()、deleteById() 等核心方法
}