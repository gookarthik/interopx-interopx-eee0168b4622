package com.interopx.platform.repository.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.interopx.platform.repository.model.DataQuality;
import com.interopx.platform.repository.model.DataSourceQuality;

@Repository
public class DataQualityDaoImpl extends AbstractDao implements DataQualityDao {

	@Override
	public DataQuality saveOrUpdate(DataQuality dataQuality) {
		getSession().saveOrUpdate(dataQuality);
		return dataQuality;
	}

	@Override
	public List<DataQuality> getDataQualityByEtIdOrDsId(Integer etId, Integer dsId) {
		Criteria criteria = getSession().createCriteria(DataQuality.class);
		if(etId!=null)
			criteria.add(Restrictions.eq("etId", etId));
		if(dsId!=null)
			criteria.add(Restrictions.eq("dsId", dsId));
		return criteria.list();
	}

	@Override
	public Double getAllIssuesCountByEtIdOrDsId(Integer etId, Integer dsId) {
		Double sumOfIssues = 0.0;
		String nativeQuery = "select SUM(no_of_issues) from data_quality";
		if (etId != null && dsId != null) {
			nativeQuery = nativeQuery + " where et_id ='" + etId + "' and ds_id='" + dsId + "'";
		} else if (etId != null) {
			nativeQuery = nativeQuery + " where et_id ='" + etId + "'";
		} else {
			nativeQuery = nativeQuery + " where ds_id ='" + dsId + "'";
		}
		SQLQuery query = getSession().createSQLQuery(nativeQuery);
		if (query.uniqueResult() != null) {
			if ((BigInteger) query.uniqueResult() != BigInteger.ZERO) {
				BigInteger result = (BigInteger) query.uniqueResult();
				sumOfIssues = result.doubleValue();
			}
		}
		return sumOfIssues;
	}

	@Override
	public Double getAverageScoreByEtIdOrDsId(Integer etId, Integer dsId) {
		String nativeQuery = "select AVG(overall_score) from data_quality";
		if (etId != null && dsId != null) {
			nativeQuery = nativeQuery + " where et_id ='" + etId + "' and ds_id='" + dsId + "'";
		} else if (etId != null) {
			nativeQuery = nativeQuery + " where et_id ='" + etId + "'";
		} else {
			nativeQuery = nativeQuery + " where ds_id ='" + dsId + "'";
		}
		SQLQuery query = getSession().createSQLQuery(nativeQuery);
		Double result = (Double) query.uniqueResult();
		return result;
	}


	@Override
	public List<DataQuality> getDataQualityByExtractionId() {
		List<DataQuality> listObj = new ArrayList<>();
		String hql = "from DataQuality WHERE id IN (SELECT MiN(id) FROM DataQuality GROUP BY etId)";
		Query queryObj = getSession().createQuery(hql);
		List<DataQuality> dataQualityList = queryObj.list();
		for (DataQuality dataQuality : dataQualityList) {
			String nativeQuery = "select AVG(overall_score) from data_quality where et_id ='" + dataQuality.getEtId() + "'";
			SQLQuery query = getSession().createSQLQuery(nativeQuery);
			Double score = (Double) query.uniqueResult();
			DataQuality data = new DataQuality();
			data.setId(dataQuality.getId());
			data.setEtId(dataQuality.getEtId());
			data.setDsId(dataQuality.getDsId());
			data.setLastUpdatedTime(dataQuality.getLastUpdatedTime());
			data.setOverallScore(score);
			Double noOfIsssues= getAllIssuesCountByEtIdOrDsId(dataQuality.getEtId(), null);
			data.setNoOfIssues(Integer.valueOf(noOfIsssues.intValue()));
			data.setResourceList(dataQuality.getResourceList());
			listObj.add(data);
		}
		return listObj;
	}

	@Override
	public DataSourceQuality getDistinctDataSourceQualityByDataSourceId() {
		String hql = "from DataSourceQuality WHERE id IN (SELECT MiN(id) FROM DataSourceQuality GROUP BY dsId)";
		Query queryObj = getSession().createQuery(hql);
		List<DataSourceQuality> dataSourceQualities = queryObj.list();
		DataSourceQuality data = new DataSourceQuality();
		for (DataSourceQuality dataSourceQuality : dataSourceQualities) {
			data.setId(dataSourceQuality.getId());
			data.setDsId(dataSourceQuality.getDsId());
			data.setScore(dataSourceQuality.getScore());
			data.setNoOfIssues(dataSourceQuality.getNoOfIssues());
			data.setDataSourceName(dataSourceQuality.getDataSourceName());
			data.setDataQualityList(dataSourceQuality.getDataQualityList());
		}
		return data;
	}

	@Override
	public DataSourceQuality saveOrUpdate(DataSourceQuality dataSourceQuality) {
		getSession().saveOrUpdate(dataSourceQuality);
		return dataSourceQuality;
	}

	@Override
	public DataSourceQuality getDataSourceQualityByDsId(Integer dsId) {
		Criteria criteria = getSession().createCriteria(DataSourceQuality.class);
		if (dsId != null)
			criteria.add(Restrictions.eq("dsId", dsId));
		return (DataSourceQuality) criteria.uniqueResult();
	}

	@Override
	public String getDataSourceNameByDsIdOrEtId(Integer extractionId, Integer datasourceId) {
		String dataSourceName = null;
		if (extractionId != null) {
			String sql = "SELECT data_source_name FROM data_quality INNER JOIN data_source_quality on data_source_quality.id = data_quality.data_source_quality_id WHERE data_quality.et_id ="+extractionId;
			SQLQuery query = getSession().createSQLQuery(sql);
			dataSourceName = (String) query.uniqueResult();
		}
		if (datasourceId != null) {
			Criteria criteria = getSession().createCriteria(DataSourceQuality.class);
			criteria.add(Restrictions.eq("dsId", datasourceId));
			DataSourceQuality dataSourceQuality = (DataSourceQuality) criteria.uniqueResult();
			dataSourceName = dataSourceQuality.getDataSourceName();
		}
		return dataSourceName;
	}
}

