package com.washour.www.qeustion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.washour.www.DataNotFoundException;
import com.washour.www.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepository;
    
    // 리스트 - 페이징 처리
	public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
    
    public Question getQuestion(Integer id) {  
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    // test
    public void testcreate(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
    
    public void create(String subject, String content, Member member) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(member);
        this.questionRepository.save(q);
    }
    
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
    
    public void vote(Question question, Member member) {
        question.getVoter().add(member);
        this.questionRepository.save(question);
    }
    
    
}
