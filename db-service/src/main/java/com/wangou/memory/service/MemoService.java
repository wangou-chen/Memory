package com.wangou.memory.service;

import com.wangou.memory.entity.Memo;
import com.wangou.memory.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 业务逻辑层：封装备忘录的增删改查操作
 */
@Service
public class MemoService {

    // 注入数据访问层
    @Autowired
    private MemoRepository memoRepository;

    /**
     * 新增备忘录
     */
    public Memo addMemo(Memo memo) {
        return memoRepository.save(memo);
    }

    /**
     * 查询所有备忘录（不分页）
     */
    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    /**
     * 分页查询备忘录（JPA 原生分页）
     */
    public Page<Memo> getAllMemos(Pageable pageable) {
        return memoRepository.findAll(pageable);
    }

    /**
     * 根据ID查询备忘录
     */
    public Optional<Memo> getMemoById(Long id) {
        return memoRepository.findById(id);
    }

    /**
     * 修改备忘录（先查后更，确保ID存在）
     */
    public Memo updateMemo(Long id, Memo updatedMemo) {
        return memoRepository.findById(id).map(memo -> {
            memo.setTitle(updatedMemo.getTitle());
            memo.setContent(updatedMemo.getContent());
            return memoRepository.save(memo);
        }).orElseThrow(() -> new RuntimeException("备忘录不存在，ID：" + id));
    }

    /**
     * 删除备忘录
     */
    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
}