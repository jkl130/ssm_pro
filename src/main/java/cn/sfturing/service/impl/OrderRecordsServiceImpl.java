package cn.sfturing.service.impl;

import cn.sfturing.dao.OrderRecordsDao;
import cn.sfturing.entity.OrderDateAndTime;
import cn.sfturing.entity.OrderRecords;
import cn.sfturing.service.OrderRecordsService;
import cn.sfturing.utils.AlipayBean;
import cn.sfturing.utils.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderRecordsServiceImpl implements OrderRecordsService {
    @Autowired
    private OrderRecordsDao orderRecordsDao;

    @Autowired
    private AlipayUtil alipayUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRecordsServiceImpl.class);

    @Override
    @Transactional
    public int insertOrderRecords(OrderRecords orderRecords) {
        // TODO Auto-generated method stub
        return orderRecordsDao.insertOrderRecords(orderRecords);
    }

    @Override
    public List<OrderRecords> findOrderRecordsByUserID(int UserID) {
        // TODO Auto-generated method stub
        return orderRecordsDao.findOrderRecordsByUserID(UserID);
    }

    @Override
    public List<OrderDateAndTime> findOrderRecordsDateByDoctorID(int doctorId) {
        return orderRecordsDao.findOrderRecordsDateByDoctorID(doctorId);
    }

    @Override
    public List<OrderRecords> findOrderRecordsByDoctorID(int doctorId) {
        return orderRecordsDao.findOrderRecordsByDoctorID(doctorId);
    }

    @Override
    public int updateOrderSta1(int id) {
        // TODO Auto-generated method stub
        return orderRecordsDao.updateOrderSta1(id);
    }

    @Override
    public int findLastId() {
        // TODO Auto-generated method stub
        return orderRecordsDao.findLastId();
    }

    @Override
    public int updateOrderdiseaseInfo(String diseaseInfo, int id) {
        // TODO Auto-generated method stub
        return orderRecordsDao.updateOrderdiseaseInfo(diseaseInfo, id);
    }

    @Override
    public void cancelOrder(int id) {
        OrderRecords orderRecords = orderRecordsDao.findOrderById(id);
        if (orderRecords.getIsSuccess() == 1) {
            // 需要退款
            AlipayBean alipayBean = new AlipayBean();
            alipayBean.setOut_trade_no(String.valueOf(id));
            try {
                alipayUtil.refund(alipayBean);
            } catch (Exception e) {
                // 退款失败
                LOGGER.error(e.getMessage());
                return;
            }
        }
        orderRecordsDao.cancelOrder1(id);
    }

}
