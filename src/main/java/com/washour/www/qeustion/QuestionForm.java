package com.washour.www.qeustion;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	// build.gradle에서 라이브러리 추가 후 
	// subject 폼의 값과 content 폼의 값의 유효성 체크
	
	@NotEmpty(message="제목은 필수 사항입니다.") // subject 값이 비어있을 때 작동
	@Size(max=200)					// subject 값을 최대 200자 까지 
	private String subject;
	
	@NotEmpty(message="내용은 필수 항목입니다.")
	private String content;
	
}
