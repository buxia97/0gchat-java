package com.zerogchat.service.impl;

import com.zerogchat.entity.*;
import com.zerogchat.common.PageList;
import com.zerogchat.dao.*;
import com.zerogchat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层实现类
 * ChatChatServiceImpl
 * @author buxia97
 * @date 2023/09/09
 */
@Service
public class ChatChatServiceImpl implements ChatChatService {

    @Autowired
	ChatChatDao dao;

    @Override
    public int insert(ChatChat chatChat) {
        return dao.insert(chatChat);
    }

    @Override
    public int batchInsert(List<ChatChat> list) {
    	return dao.batchInsert(list);
    }

    @Override
    public int update(ChatChat chatChat) {
    	return dao.update(chatChat);
    }

    @Override
    public int delete(Object key) {
    	return dao.delete(key);
    }

    @Override
    public int batchDelete(List<Object> keys) {
        return dao.batchDelete(keys);
    }

	@Override
	public ChatChat selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<ChatChat> selectList(ChatChat chatChat) {
		return dao.selectList(chatChat);
	}

	@Override
	public PageList<ChatChat> selectPage(ChatChat chatChat, Integer offset, Integer pageSize) {
		PageList<ChatChat> pageList = new PageList<>();

		int total = this.total(chatChat);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<ChatChat> list = dao.selectPage(chatChat, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(ChatChat chatChat) {
		return dao.total(chatChat);
	}
}