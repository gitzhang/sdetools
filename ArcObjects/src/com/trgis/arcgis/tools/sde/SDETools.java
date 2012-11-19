package com.trgis.arcgis.tools.sde;

import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeDelete;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeFilter;
import com.esri.sde.sdk.client.SeInsert;
import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeQuery;
import com.esri.sde.sdk.client.SeShape;
import com.esri.sde.sdk.client.SeShapeFilter;
import com.esri.sde.sdk.client.SeSqlConstruct;
import com.esri.sde.sdk.client.SeUpdate;

public class SDETools {

	// SDE����
	private SeConnection conn;

	// ������������
	private String server = "localhost";
	private String instance = "5151";
	private String database = "sde";
	private String user = "sde";
	private String password = "sde";

	public SDETools(String server, String instance, String database,
			String user, String password) {
		this.server = server;
		this.instance = instance;
		this.database = database;
		this.user = user;
		this.password = password;
	}

	/**
	 * ȷ������
	 * 
	 * @throws SeException
	 */
	public SeConnection getConnection() throws SeException {
		if (conn == null || conn.isClosed()) {
			conn = new SeConnection(server, instance, database, user, password);
		}
		return conn;
	}

	/**
	 * �����ѯ
	 * 
	 * @param layerName
	 * @param cols
	 * @param filters
	 * @return
	 * @throws SeException
	 */
	public SeQuery getQueryResult(String layerName, String[] cols,
			SeFilter[] filters, String whereCase) throws SeException {

		getConnection();
		// �����ѯ����
		SeSqlConstruct construst = new SeSqlConstruct(layerName);
		if (whereCase != null) {
			construst.setWhere(whereCase);
		}
		// ������ѯ
		SeQuery query = new SeQuery(conn, cols, construst);
		// ׼����ѯ
		query.prepareQuery();

		// ���ù��˹���
		if (filters != null && filters.length > 0) {
			query.setSpatialConstraints(SeQuery.SE_SPATIAL_FIRST, true, filters);
		}
		// ִ�в�ѯ
		query.execute();
		return query;
	}

	/**
	 * �ռ������ϵ�Ĳ�ѯ
	 * 
	 * @param layerName
	 * @param shape
	 * @param spatialColumn
	 * @param cols
	 * @param whereCase
	 * @return
	 * @throws SeException
	 */
	public SeQuery getContainsShape(String layerName, String spatialColumn,
			SeShape shape, String[] cols, String whereCase) throws SeException {
		getConnection();
		// ������˹���
		SeShapeFilter shapeFilter = new SeShapeFilter(layerName, spatialColumn,
				shape, SeFilter.METHOD_SC);
		SeFilter[] filters = new SeFilter[] { shapeFilter };
		// ���췵����
		return getQueryResult(layerName, cols, filters, whereCase);
	}

	public SeUpdate getSeUpdate() {
		try {
			return new SeUpdate(getConnection());
		} catch (SeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SeInsert getSeInsert() {
		try {
			return new SeInsert(getConnection());
		} catch (SeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SeDelete getSeDelete() {
		try {
			return new SeDelete(getConnection());
		} catch (SeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SeLayer getSeLayer(String tableName, String shapeColumn) {
		try {
			return new SeLayer(getConnection(),tableName,shapeColumn);
		} catch (SeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void startTransaction() {
		try {
			getConnection().startTransaction();
		} catch (SeException e) {
			e.printStackTrace();
		}
	}

	public void commitTransaction() {
		try {
			getConnection().commitTransaction();
		} catch (SeException e) {
			e.printStackTrace();
		}
	}

	public void distory() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SeException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}


}
