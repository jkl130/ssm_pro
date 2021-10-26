package cn.sfturing.service;

import cn.sfturing.entity.Hospital;
import cn.sfturing.entity.Office;

import java.util.List;
import java.util.Map;

public interface OfficeService {


	// 通过id查询科室信息
	public Office findOfficeById(int id);

    List<Office> findOfficeByHosId(Integer hosId);

    // 推荐9个开通预约挂号的医院的科室
	public List<Office> findOfficeByRe(Map<String, Object> officeMap);

	// 推荐9个开通预约挂号的医院的科室数量
	public int findOfficeByReNum(List<Hospital> hospital);

	// 通过条件查询科室
	public List<Office> findOfficeByConditon(Office office, int start, int size);
	
	// 查询查询科室数量
	public int findOrderOfficeNum(Office office);

}
