package kr.co.caloriebus.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.caloriebus.board.model.dao.BoardDao;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public List selectBoardList(String category, int reqPage) {
		List list = boardDao.selectBoardList(category);
		
		return list;
	}
}
