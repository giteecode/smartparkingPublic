package com.smart.common.dynamicquery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 查询的实现类

 */
@Repository
public class DynamicQueryImpl implements DynamicQuery {

	/**
	 * @PersistenceContext(type = PersistenceContextType.EXTENDED)
	 * 默认是 PersistenceContextType.TRANSACTION 开启只读事物
	 */
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void save(Object entity) {
		em.persist(entity);
	}

	@Override
	public void update(Object entity) {
		em.merge(entity);
	}

	private Query createNativeQuery(String sql, Object... params) {
		Query q = em.createNativeQuery(sql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i + 1, params[i]);
			}
		}
		return q;
	}

	@Override
	public int nativeExecuteUpdate(String nativeSql, Object... params) {
		return createNativeQuery(nativeSql, params).executeUpdate();
	}

	@Override
	public <T> T nativeQuerySingleResult(Class<T> resultClass, String nativeSql, Object... params) {
		Query q = createNativeQuery(resultClass, nativeSql, params);
		List<T> list = q.getResultList();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}


	private <T> Query createNativeQuery(Class<T> resultClass, String sql, Object... params) {
		Query q;
		if (resultClass == null) {
			q = em.createNativeQuery(sql);
		} else {
			q = em.createNativeQuery(sql, resultClass);
		}
		if(params!=null){
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i + 1, params[i]);
			}
		}
		return q;
	}

	@Override
	public <T> List<T> query(String nativeSql, Object... params) {
		Query q = createNativeQuery(null, nativeSql, params);
		return q.getResultList();
	}

    @Override
    public <T> List<T> query(Class<T> resultClass, String nativeSql, Object... params) {
        Query q = createNativeQuery(resultClass, nativeSql, params);
        return q.getResultList();
    }
	@Override
	public Long nativeQueryCount(String nativeSql, Object... params) {
		nativeSql = StringUtils.substringBefore(nativeSql, "order by");
		Object count = createNativeQuery(nativeSql, params).getSingleResult();
		return ((Number) count).longValue();
	}
	@Override
	public <T> Page<T> nativeQuery(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params) {
		List<T> rows = nativeQueryPagingList(resultClass, pageable, nativeSql, params);
		Long total = nativeQueryCount(nativeSql, params);
		return new PageImpl<>(rows, pageable, total);
	}
	@Override
	public <T> List<T> nativeQueryPagingList(Class<T> resultClass, Pageable pageable, String nativeSql,
											 Object... params) {
		Integer pageNumber = pageable.getPageNumber();
		Integer pageSize = pageable.getPageSize();
		Integer startPosition = pageNumber * pageSize;
		return createNativeQuery(resultClass, nativeSql, params)
                .setFirstResult(startPosition).setMaxResults(pageSize)
				.getResultList();
	}

    @Override
    public <T> T nativeQueryMap(String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<T> list = q.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public <T> T nativeQueryModel(Class<T> resultClass, String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(resultClass));
        List<T> list = q.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public <T> List<T> nativeQueryListModel(Class<T> resultClass,
                                            String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(resultClass));
        return q.getResultList();
    }

    @Override
    public <T> List<T> nativeQueryPagingListModel(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params) {
        Integer pageNumber = pageable.getPageNumber();
        Integer pageSize = pageable.getPageSize();
        Integer startPosition = pageNumber * pageSize;
	    Query q = createNativeQuery(nativeSql, params)
                .setFirstResult(startPosition).setMaxResults(pageSize);
        q.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.aliasToBean(resultClass));
        return q.getResultList();
    }

    @Override
    public <T> List<T> nativeQueryListMap(String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return q.getResultList();
    }

    @Override
    public Object querySingleResult(String nativeSql, Object... params) {
        Query q = createNativeQuery(null, nativeSql, params);
        List list = q.getResultList();
        if(list.size()!=0){
            return list.get(0);
        }else{
            return null;
        }
    }
}

