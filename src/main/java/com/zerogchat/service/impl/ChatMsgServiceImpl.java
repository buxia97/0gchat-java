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
 * ChatMsgServiceImpl
 * @author buxia97
 * @date 2023/09/09
 */
@Service
public class ChatMsgServiceImpl implements ChatMsgService {

    @Autowired
	ChatMsgDao dao;

    @Override
    public int insert(ChatMsg chatMsg) {
        return dao.insert(chatMsg);
    }

    @Override
    public int batchInsert(List<ChatMsg> list) {
    	return dao.batchInsert(list);
    }

    @Override
    public int update(ChatMsg chatMsg) {
    	return dao.update(chatMsg);
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
	public ChatMsg selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<ChatMsg> selectList(ChatMsg chatMsg) {
		return dao.selectList(chatMsg);
	}

	@Override
	public List<ChatMsg> selectTime(Integer chatid,Integer time) {
		return dao.selectTime(chatid,time);
	}

	@Override
	public PageList<ChatMsg> selectPage(ChatMsg chatMsg, Integer offset, Integer pageSize) {
		PageList<ChatMsg> pageList = new PageList<>();

		int total = this.total(chatMsg);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<ChatMsg> list = dao.selectPage(chatMsg, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(ChatMsg chatMsg) {
		return dao.total(chatMsg);
	}
}