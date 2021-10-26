package cn.sfturing.service.impl;

import cn.sfturing.dao.OfficeDao;
import cn.sfturing.entity.Hospital;
import cn.sfturing.entity.Office;
import cn.sfturing.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OfficeServiceImpl implements OfficeService {
	@Autowired
	private OfficeDao officeDao;

	@Override
	public List<Office> findOfficeByHosId(Integer hosId) {

		return officeDao.findOfficeByHosName(hosId);
	}

	@Override
	public List<Office> findOfficeByRe(Map<String, Object> officeMap) {
		// TODO Auto-generated method stub
		return officeDao.findOfficeByRe(officeMap);
	}

	@Override
	public int findOfficeByReNum(List<Hospital> hospital) {
		// TODO Auto-generated method stub
		return officeDao.findOfficeByReNum(hospital);
	}

	@Override
	public Office findOfficeById(int id) {
		// TODO Auto-generated method stub
		return officeDao.findOfficeById(id);
	}

	@Override
	public List<Office> findOfficeByConditon(Office office, int start, int size) {
		// TODO Auto-generated method stub
		return officeDao.findOfficeByCondition(office.getHospitalName(), office.getOfficesName(), start, size);
	}

	@Override
	public int findOrderOfficeNum(Office office) {
		// TODO Auto-generated method stub
		return officeDao.findOrderOfficeNum(office.getHospitalName(), office.getOfficesName());
	}

	
}
