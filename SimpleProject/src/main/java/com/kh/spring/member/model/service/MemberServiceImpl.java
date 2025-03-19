package com.kh.spring.member.model.service;

import java.security.InvalidParameterException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordMissMatchException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberDAO memberDao;
	private final SqlSessionTemplate sqlSession;
	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberValidator validator;
	
	/*
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/
	
	@Override
	public MemberDTO signIn(MemberDTO member) {
		
		validator.validatedSignInMember(member);
		
		MemberDTO signInMember = memberDao.signIn(sqlSession, member);
		
		if(signInMember == null) throw new MemberNotFoundException("존재하지 않는 아이디 입니다.");
		
		if(!passwordEncoder.matches(member.getMemberPw(), signInMember.getMemberPw())) throw new PasswordMissMatchException("비밀번호가 일치하지 않습니다.");
		
		return signInMember;
	}

	@Override
	public void signUp(MemberDTO member) {
		if(member.getMemberId().length() > 10) {
			throw new TooLargeValueException("아이디가 너무 깁니다. 10자 이내로 작성해주세요.");
		}
		
		if(member == null ||
		   member.getMemberId() == null ||
		   member.getMemberId().trim().isEmpty() ||
		   member.getMemberPw() == null ||
		   member.getMemberPw().trim().isEmpty()) {
			throw new InvalidParameterException("유효하지 않은 입력값입니다.");
		}
		
		if(memberDao.checkId(sqlSession, member.getMemberId()) != null) return;
		
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		
		if(memberDao.signUp(sqlSession, member) != 1) return;
		
		sqlSession.commit();
	}

	@Override
	public MemberDTO updateMember(MemberDTO member) {
		return null;
	}

	@Override
	public int deleteMember(MemberDTO member) {
		return 0;
	}
}
