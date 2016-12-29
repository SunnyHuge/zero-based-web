package org.smart4j.chapter02.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter02.util.CollectionUtil;
import org.smart4j.chapter02.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 */
public final class DatabaseHelper {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    // 查询工具
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    // ThreadLocal 存放connection
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    /** jdbc连接属性 */
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties props = PropsUtil.loadProps("config.properties");
        DRIVER  = props.getProperty("jdbc.driver");
        URL  = props.getProperty("jdbc.url");
        USERNAME  = props.getProperty("jdbc.userName");
        PASSWORD  = props.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch(Exception e) {
            logger.error("Can not load jdbc driver", e);
        }
    }

    /**
     * 查询实体列表
     * @param entityClass   实体Class
     * @param sql           执行sql
     * @param params        参数列表
     * @param <T>           泛型
     * @return              查询结果
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<>(entityClass), params);
        } catch (SQLException e) {
            logger.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * 查询实体
     * @param entityClass   实体Class
     * @param sql           执行sql
     * @param params        参数列表
     * @param <T>           泛型
     * @return              查询结果
     */
    public static<T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<>(entityClass), params);
        } catch (SQLException e) {
            logger.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 执行查询sql返回list对象，其中map表示列名与值得映射
     * @param sql       执行sql
     * @param parms     参数列表
     * @return          查询结果
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... parms) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), parms);
        } catch (SQLException e) {
            logger.error("execute query failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return result;
    }

    /**
     * 执行更新sql，返回更新记录条数
     * @param sql       执行sql
     * @param params    参数列表
     * @return          更新结果
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            logger.error("execute update failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 插入实体
     * @param entityClass       插入对象类型
     * @param fieldMap          插入数据
     * @param <T>               泛型
     * @return                  插入结果
     */
    public static<T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            logger.warn("Can not insert entity : fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除实体
     * @param entityClass       实体类型
     * @param id                实体id
     * @param <T>               泛型
     * @return                  删除结果
     */
    public static<T> boolean deleteEntity(Class<T> entityClass, Long id) {
        if (id == null) {
            logger.warn("delete entity failure : id is null");
            return false;
        }

        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 更新实体
     * @param entityClass       实体类型
     * @param fieldMap          更新数据
     * @param id                实体id
     * @param <T>               泛型
     * @return                  更新结果
     */
    public static<T> boolean updateEntity(Class<T> entityClass, Long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            logger.warn("update entity failure : fieldMap is empty");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName + "=?, ");
        }
        sql += columns.replace(columns.lastIndexOf(", "), columns.length(), "") + " WHERE id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        return executeUpdate(sql, paramList.toArray()) == 1;
    }

    /**
     * 根据实体类型获取对应数据库表名
     * @param entityClass       实体类型
     * @param <T>               泛型
     * @return                  对应数据库表名
     */
    private static<T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * 获取数据库连接
     * @return      connection
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                logger.error("get DB connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("close DB connection failure", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_HOLDER.remove();
        }
    }
}
