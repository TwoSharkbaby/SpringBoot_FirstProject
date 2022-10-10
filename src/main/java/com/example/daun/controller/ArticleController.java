package com.example.daun.controller;

import com.example.daun.dto.ArticleFrom;
import com.example.daun.dto.CommentDto;
import com.example.daun.entity.Article;
import com.example.daun.repository.ArticleRepository;
import com.example.daun.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j // 시스템프린트는 서버에 부담이기에 로그로 대체
public class ArticleController {
    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 자동연결
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleFrom() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleFrom from) { // post로 받은 데이터를 ArticleFrom형식의 tdo로 데이터를 받음
        Article article = from.toEntity(); // dto로 바꾼 형식을 db가 이해할수 있는 entity형식으로 변환
        Article saved = articleRepository.save(article); // repository에게 entity를 db에 저장하게함
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}") // 여기 있는 id와 pathvariable에 있는 id가 같아야지 동작함
    public String show(@PathVariable Long id, Model model) {  // getmapping한곳에서 데이터가 오는걸 받을 때 pathvariable 사용
        Article articleEntity = articleRepository.findById(id).orElse(null); // 찾은 아이디의 값이 없으면 null
        List<CommentDto> commentDtos = commentService.comments(id); // 해당아이디의 덧글들을 가져와 담고
        model.addAttribute("article", articleEntity); // 위에 가져온 데이터를 모델에 등록 및 전달
        model.addAttribute("commentDtos",commentDtos);  // 모델에 등록 및 전달
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleEntityList = articleRepository.findAll(); // 모든 article을 리스트형식으로 가져옴
        model.addAttribute("articleList", articleEntityList); // 위에 가져온 데이터를 모델에 등록 및 전달
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleFrom from) {
        Article articleEntity = from.toEntity(); // post로 받은 데이터를 ArticleFrom형식의 tdo로 데이터를 받음
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null); // 받은 데이터의 아이디의 값이 있으면 target에 저장
        if (target != null) {
            articleRepository.save(articleEntity); // target에 데이터가 있으면 articleRepository에 tdo로 받아둔 데이터를 저장
        }
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        Article target = articleRepository.findById(id).orElse(null);
        if(target != null){
            articleRepository.delete(target); // 인터페이스의 delete 사용
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다!"); // 리다이렉트할때 한번 사용되는 메세지기능
        }
        return "redirect:/articles";
    }
}


