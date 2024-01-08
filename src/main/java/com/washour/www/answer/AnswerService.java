package com.washour.www.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.washour.www.DataNotFoundException;
import com.washour.www.member.Member;
import com.washour.www.qeustion.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;


    public Answer create(Question question, String content, Member author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);
        answer.setQuestion(question);
        this.answerRepository.save(answer);
        
        return answer;
    }
    
    
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }


    // 답변 조회
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
	public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

	
	 public void vote(Answer answer, Member member) {
	        answer.getVoter().add(member);
	        this.answerRepository.save(answer);
	    }
}
