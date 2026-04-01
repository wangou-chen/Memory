package com.wangou.memory.controller;
import com.wangou.memory.entity.Memo;
import com.wangou.memory.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * RESTful接口层：提供备忘录的增删改查API
 * 接口前缀：/api/memos
 */
@RestController
@RequestMapping("/api/memos")
public class MemoController {

    @Autowired
    private MemoService memoService;

    // 新增备忘录：POST http://localhost:8080/api/memos
    @PostMapping
    public ResponseEntity<Memo> addMemo(@RequestBody Memo memo) {
        Memo savedMemo = memoService.addMemo(memo);
        return new ResponseEntity<>(savedMemo, HttpStatus.CREATED);
    }

    // 查询所有备忘录：GET http://localhost:8080/api/memos
    @GetMapping
    public ResponseEntity<List<Memo>> getAllMemos() {
        List<Memo> memos = memoService.getAllMemos();
        return new ResponseEntity<>(memos, HttpStatus.OK);
    }

    // 根据ID查询备忘录：GET http://localhost:8080/api/memos/1
    @GetMapping("/{id}")
    public ResponseEntity<Memo> getMemoById(@PathVariable Long id) {
        Optional<Memo> memo = memoService.getMemoById(id);
        return memo.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 修改备忘录：PUT http://localhost:8080/api/memos/1
    @PutMapping("/{id}")
    public ResponseEntity<Memo> updateMemo(@PathVariable Long id, @RequestBody Memo memo) {
        try {
            Memo updatedMemo = memoService.updateMemo(id, memo);
            return new ResponseEntity<>(updatedMemo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 删除备忘录：DELETE http://localhost:8080/api/memos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}