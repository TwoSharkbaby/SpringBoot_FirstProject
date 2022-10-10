package com.example.daun.api;

import com.example.daun.dto.ArticleFrom;
import com.example.daun.entity.Article;
import com.example.daun.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j // 시스템프린트는 서버에 부담이기에 로그로 대체
public class ArticleApiController { // RestAPI용 컨트롤러 JSON

    @Autowired // DI, 생성 객체를 가져와 연결함
    private ArticleService articleService;


    @GetMapping("/api/articles") // 전체 가져오기
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}") // 단일 가져오기
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    @PostMapping("/api/articles") // 생성하기
    public ResponseEntity<Article> create(@RequestBody ArticleFrom from) { // 포스트형식으로 받은걸 바로 변환하기 못해서 RequestBody를 통해서 articlefrom 형식으로 변환시키고
        Article created = articleService.create(from);  // article에 저장하고
        return (created != null) ? // created가 있으면 생성하고 아니면 오류메세지 전달
            ResponseEntity.status(HttpStatus.OK).body(created) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleFrom from) { // http상태를 표기하기 위해 ResponseEntity를 사용
        Article updated = articleService.update(id, from);
        return (updated != null) ?
            ResponseEntity.status(HttpStatus.OK).body(updated) : // 알맞은 신호라고 전달하면서 body에 업데이터를 같이 보냄
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
            ResponseEntity.status(HttpStatus.NO_CONTENT).build() :  // 삭제되면 노컨텐트
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 이상이 있으면 배드리퀘스트
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleFrom> froms) { // 묶음으로 오는걸 리스트로 변환해서 받고
        List<Article> createdList = articleService.createArticles(froms); // 받은걸 서비스에서 구현하고 결과를 받고
        return (createdList != null) ?  // 상태 전달
            ResponseEntity.status(HttpStatus.OK).body(createdList) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}


