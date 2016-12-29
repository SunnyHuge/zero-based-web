package org.smart4j.chapter02.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter02.helper.DatabaseHelper;
import org.smart4j.chapter02.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务.
 */
public class CustomerService {

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    // 客户表
    private static final String TABLE_CUSTOMER = "customer";

    /**
     * 根据关键字获取客户列表
     * @return          客户信息列表
     */
    public List<Customer> getCustomerList() {
        String sql = "select * from " + TABLE_CUSTOMER;
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    /**
     * 根据id获取客户
     * @param id    客户Id
     * @return      客户信息
     */
    public Customer getCustomerById(Long id) {
        String sql = "select * from " + TABLE_CUSTOMER + " where id=?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * 创建客户
     * @param fieldMap  客户信息
     * @return          是否成功
     */
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    /**
     * 更新客户
     * @param id        客户Id
     * @param fieldMap  客户信息
     * @return          是否成功
     */
    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    /**
     * 根据id删除用户
     * @param id    用户Id
     * @return      是否成功
     */
    public boolean deleteCustomer(Long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
