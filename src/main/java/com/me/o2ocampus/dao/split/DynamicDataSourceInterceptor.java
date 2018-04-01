package com.me.o2ocampus.dao.split;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Intercepts({@Signature(type=Executor.class, method="update", args= {MappedStatement.class, Object.class}),
	@Signature(type=Executor.class, method="query", args= {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	@Signature(type=Executor.class, method="query", args= {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class DynamicDataSourceInterceptor implements Interceptor{
	
	private String sqlToMasterRegex = ".*?insert.*|.*?delete.*|.*?update.*";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// is doing a transaction
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;		
		if(!synchronizationActive) {
			Object[] args = invocation.getArgs();
			MappedStatement mStatement = (MappedStatement)args[0];
			// if select LAST_INSERT_ID(), then use master...why???(because 'insert' stuff use master too...)
			if(mStatement.getSqlCommandType().equals(SqlCommandType.SELECT)
					&& mStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
				lookupKey = DynamicDataSourceHolder.DB_MASTER;
			}else {
				BoundSql boundSql = mStatement.getSqlSource().getBoundSql(args[1]);
				String sql = boundSql.getSql().toLowerCase(Locale.CHINA)
						.replaceAll("[\\t\\n\\r]", " "); // using regex....
				if(sql.matches(sqlToMasterRegex)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				}else {
					lookupKey = DynamicDataSourceHolder.DB_SLAVE;
				}
			}
		}
		DynamicDataSourceHolder.setDBType(lookupKey);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if(target instanceof Executor) {
			// wrap the executor with this interceptor
			return Plugin.wrap(target, this);
		}
		return null;
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

}
