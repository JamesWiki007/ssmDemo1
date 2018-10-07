package com.study.controller;

import com.study.pojo.BasePageResult;
import com.study.pojo.ReturnResult;
import com.study.pojo.Windowdata;
import com.study.service.impl.Zhongcai88ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 窗户数据控制层
 * 
 * @author 51988
 *
 */
@RestController
@RequestMapping("/zhongcai88")
public class Zhongcai88Controller {
	
	@Autowired
	private Zhongcai88ServiceImpl service;
	
	@RequestMapping("/search")
	public BasePageResult search(@RequestBody Windowdata windowdata, int pageNum, int pageSize) {
		
		return service.findPage(windowdata, pageNum, pageSize);
	}
	
	
	@RequestMapping("/add")
	public ReturnResult add(@RequestBody Windowdata windowdata) {
		try {
			service.add(windowdata);
			return new ReturnResult(true, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnResult(false, "添加失败");
		}
	}
	
}
