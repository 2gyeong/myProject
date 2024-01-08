package com.washour.www.qeustion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.washour.www.answer.Answer;
import com.washour.www.member.Member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "question")
public class Question {

	@Id
	@Column(name="question_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	@Column(length = 200)	// 200자 까지 
	private String subject;
	
	
	//@Column(columnDefinition = "TEXT")
	@Column(length = 4000)
	private String content;

    @CreatedDate
    private LocalDateTime createDate;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
    
    @ManyToOne
    private Member author;
    
    // 수정일시 저장
    private LocalDateTime modifyDate;
    
    // 추천 버튼
    // set 컬렉션 : 중복 저장 X , List와 달리 순서보장 X
    @ManyToMany
    Set<Member> voter;
}
