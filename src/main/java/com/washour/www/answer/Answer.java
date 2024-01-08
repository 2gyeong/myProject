package com.washour.www.answer;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.washour.www.member.Member;
import com.washour.www.qeustion.Question;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "answer")
public class Answer {
		@Id
		@Column(name="answer_id")
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;

//		@Column(columnDefinition = "TEXT")
		@Column(length = 4000)
	    private String content;

	    private LocalDateTime createDate;

	    @ManyToOne
	    private Question question;
	    
	    @ManyToOne
	    private Member author;
	    
	    private LocalDateTime modifyDate;
	    
	    @ManyToMany
	    Set<Member> voter;
}
