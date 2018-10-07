package com.study.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.study.mapper.WindowdataMapper;
import com.study.pojo.Windowdata;
import com.study.pojo.WindowdataExample;
import com.study.search.SearchOption;
import com.study.search.SearchPageResult;
import com.study.service.OverviewService;
import com.study.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 服务接口实现
 * 
 * @author 51988
 *
 */
@Service
public class OverviewServiceImpl implements OverviewService {

	@Autowired
	private WindowdataMapper windowdataMapper;

	// 条件查询+分页查询

	public SearchPageResult findPage(SearchOption searchOption, int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		WindowdataExample example = new WindowdataExample();
		WindowdataExample.Criteria criteria = example.createCriteria();

		if (searchOption != null) {
			if (searchOption.getMaterialType() != null && !"".equals(searchOption.getMaterialType().trim())) {
				criteria.andMaterialstypeEqualTo(searchOption.getMaterialType());
			}
			if (searchOption.getWindowType() != null && !"".equals(searchOption.getWindowType().trim())) {
				criteria.andTypeEqualTo(searchOption.getWindowType());
			}
			if (searchOption.getMinDate() != null) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(searchOption.getMinDate());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				Date start = calendar.getTime();
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.SECOND, -1);

				criteria.andMemorydateGreaterThanOrEqualTo(start);
			}
			if (searchOption.getMaxDate() != null) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(searchOption.getMaxDate());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.SECOND, -1);
				Date end = calendar.getTime();

				criteria.andMemorydateLessThanOrEqualTo(end);
			}
		}

		Page<Windowdata> page = (Page<Windowdata>) windowdataMapper.selectByExample(example);

		List<Windowdata> listAll = page.getResult();

		return new SearchPageResult(page.getTotal(), ServiceUtil.areas(listAll), listAll);
	}

}
