package com.smart.common.dynamicquery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 动态查询
 * @author 小柒2012
 */
public interface DynamicQuery {
	/**
	 * 保存
	 * @param entity
	 */
	void save(Object entity);

	/**
	 * 更新
	 * @param entity
	 */
	void update(Object entity);

	/**
	 * 执行nativeSql的update,delete操作
	 * @param nativeSql
	 * @param params
	 * @return
	 */
	int nativeExecuteUpdate(String nativeSql, Object... params);

	/**
	 * 执行nativeSql统计查询
	 * @param nativeSql
	 * @param params 占位符参数(例如?1)绑定的参数值
	 * @return 统计条数
	 */
	Long nativeQueryCount(String nativeSql, Object... params);

	/**
	 * 执行nativeSql查询一行
	 * @param resultClass 查询结果类型
	 * @param nativeSql
	 * @param params 占位符参数(例如?1)绑定的参数值
	 * @return
	 */
	<T> T nativeQuerySingleResult(Class<T> resultClass, String nativeSql, Object... params);

	/**
	 * 执行nativeSql查询 List<Object[]>
	 * @param nativeSql
	 * @param params 占位符参数(例如?1)绑定的参数值
	 * @return
	 */
	<T> List<T> query(String nativeSql, Object... params);

	/**
	 * 执行nativeSql查询
	 * @param resultClass
	 * @param nativeSql
	 * @param params 占位符参数(例如?1)绑定的参数值
	 * @return
	 */
	<T> List<T> query(Class<T> resultClass, String nativeSql, Object... params);

	/**
	 * 执行nativeSql分页查询
	 * @param resultClass 查询结果类型
	 * @param pageable 分页数据
	 * @param nativeSql
	 * @param params 占位符参数(例如?1)绑定的参数值
	 * @return 分页对象
	 */
	<T> Page<T> nativeQuery(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params);

	/**
	 * 执行nativeSql分页查询
	 * @param resultClass
	 * @param pageable
	 * @param nativeSql
	 * @param params
	 * @param List<T>
	 * @return
	 */
	<T> List<T> nativeQueryPagingList(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params);

    /**
     * 查询对象列表，返回List<Map<key,value>>
     * @param nativeSql
     * @param params
     * @return  T
     */
    <T> T nativeQueryMap(String nativeSql, Object... params);

    /**
     * 查询对象列表，返回List<组合对象>
     * @param resultClass
     * @param nativeSql
     * @param params
     * @return
     */
    <T> T nativeQueryModel(Class<T> resultClass, String nativeSql, Object... params);
    /**
     * 查询对象列表，返回List<Map<key,value>>
     * @param nativeSql
     * @param params
     * @return  List<T>
     */
    <T> List<T> nativeQueryListMap(String nativeSql, Object... params);

    /**
     * 查询对象列表，返回List<组合对象>
     * @param resultClass
     * @param nativeSql
     * @param params
     * @return  List<T>
     */
    <T> List<T> nativeQueryListModel(Class<T> resultClass, String nativeSql, Object... params);

    /**
     * 查询对象列表，返回List<组合对象>
     * @param resultClass
     * @param nativeSql
     * @param params
     * @return  List<T>
     */
    <T> List<T> nativeQueryPagingListModel(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params);

    /**
     * 执行nativeSql查询 List<Object[]>
     * @param nativeSql
     * @param params 占位符参数(例如?1)绑定的参数值
     * @return
     */
    Object querySingleResult(String nativeSql, Object... params);
}
