package com.wangou.memory.controller;

import com.wangou.memory.entity.Memo;
import com.wangou.memory.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 备忘录模块 API 控制层
 * 统一前缀：/api/memos
 * 提供备忘录的 增、删、改、查、分页 功能
 */
@RestController
@RequestMapping("/api/memos")
public class MemoController {

    @Autowired
    private MemoService memoService;

    /**
     * 新增备忘录
     * @param memo 备忘录实体
     * @return 保存后的备忘录信息
     */
    @PostMapping
    public ResponseEntity<Memo> addMemo(@RequestBody Memo memo) {
        Memo savedMemo = memoService.addMemo(memo);
        return new ResponseEntity<>(savedMemo, HttpStatus.CREATED);
    }

    /**
     * 分页查询所有备忘录（JPA 原生分页，真正可用）
     * @param pageNum  当前页码，默认第 1 页
     * @param pageSize 每页条数，默认 10 条
     * @return 分页结果
     */
    @GetMapping
    public ResponseEntity<Page<Memo>> getAllMemos(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Memo> memoPage = memoService.getAllMemos(pageable);
        return new ResponseEntity<>(memoPage, HttpStatus.OK);
    }

    /**
     * 根据 ID 查询单条备忘录
     * @param id 备忘录ID
     * @return 对应的备忘录信息 / 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Memo> getMemoById(@PathVariable Long id) {
        Optional<Memo> memo = memoService.getMemoById(id);
        return memo.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 更新备忘录
     * @param id 要更新的备忘录ID
     * @param memo 更新后的内容
     * @return 更新后的备忘录 / 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<Memo> updateMemo(@PathVariable Long id, @RequestBody Memo memo) {
        try {
            Memo updatedMemo = memoService.updateMemo(id, memo);
            return new ResponseEntity<>(updatedMemo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 删除备忘录
     * @param id 要删除的备忘录ID
     * @return 204 无内容
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}