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
 * ChatBanServiceImpl
 * @author buxia97
 * @date 2023/09/09
 */
@Service
public class ChatBanServiceImpl implements ChatBanService {

    @Autowired
	ChatBanDao dao;

    @Override
    public int insert(ChatBan chatBan) {
        return dao.insert(chatBan);
    }

    @Override
    public int batchInsert(List<ChatBan> list) {
    	return dao.batchInsert(list);
    }

    @Override
    public int update(ChatBan chatBan) {
    	return dao.update(chatBan);
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
	public ChatBan selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<ChatBan> selectList(ChatBan chatBan) {
		return dao.selectList(chatBan);
	}

	@Override
	public PageList<ChatBan> selectPage(ChatBan chatBan, Integer offset, Integer pageSize) {
		PageList<ChatBan> pageList = new PageList<>();

		int total = this.total(chatBan);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<ChatBan> list = dao.selectPage(chatBan, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(ChatBan chatBan) {
		return dao.total(chatBan);
	}
}