package com.washour.www.qeustion;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.washour.www.answer.AnswerForm;
import com.washour.www.member.Member;
import com.washour.www.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	private final MemberService memberService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult
    		, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Member member = this.memberService.getMember(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), member);
        return "redirect:/question/list";
    }
	
	@GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/question/list";
    }
	
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        Member member = this.memberService.getMember(principal.getName());
        this.questionService.vote(question, member);
        return String.format("redirect:/question/detail/%s", id);
    }
	

}
