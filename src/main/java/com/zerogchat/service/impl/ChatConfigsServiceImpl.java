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
 * ChatConfigsServiceImpl
 * @author buxia97
 * @date 2023/09/09
 */
@Service
public class ChatConfigsServiceImpl implements ChatConfigsService {

    @Autowired
	ChatConfigsDao dao;

    @Override
    public int insert(ChatConfigs chatConfigs) {
        return dao.insert(chatConfigs);
    }

    @Override
    public int batchInsert(List<ChatConfigs> list) {
    	return dao.batchInsert(list);
    }

    @Override
    public int update(ChatConfigs chatConfigs) {
    	return dao.update(chatConfigs);
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
	public ChatConfigs selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<ChatConfigs> selectList(ChatConfigs chatConfigs) {
		return dao.selectList(chatConfigs);
	}

	@Override
	public PageList<ChatConfigs> selectPage(ChatConfigs chatConfigs, Integer offset, Integer pageSize) {
		PageList<ChatConfigs> pageList = new PageList<>();

		int total = this.total(chatConfigs);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<ChatConfigs> list = dao.selectPage(chatConfigs, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(ChatConfigs chatConfigs) {
		return dao.total(chatConfigs);
	}
}