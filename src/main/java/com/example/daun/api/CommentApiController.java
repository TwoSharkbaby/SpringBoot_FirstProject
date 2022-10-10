package com.example.daun.api;

import com.example.daun.annotation.RunningTime;
import com.example.daun.dto.CommentDto;
import com.example.daun.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        List<CommentDto> dtos = commentService.comments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/api/articles/{articleId}/comments") // articleId는 FK값, dto는 제이슨정보
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto){
        CommentDto commentDto = commentService.create(articleId,dto);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto){
        CommentDto commentDto = commentService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @RunningTime
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        CommentDto commentDto = commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }
}
