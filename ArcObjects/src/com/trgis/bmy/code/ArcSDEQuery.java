package com.trgis.bmy.code;

import java.io.IOException;
import java.net.UnknownHostException;

import com.esri.sde.sdk.client.SeColumnDefinition;
import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeFilter;
import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeQuery;
import com.esri.sde.sdk.client.SeRow;
import com.esri.sde.sdk.client.SeShape;
import com.esri.sde.sdk.client.SeShapeFilter;
import com.esri.sde.sdk.client.SeSqlConstruct;
import com.esri.sde.sdk.client.SeUpdate;

public class ArcSDEQuery {

	// ������������
	private static final String server = "192.168.1.3";
	private static final String instance = "5151";
	private static final String database = "sde";
	private static final String user = "sde";
	private static final String password = "sde";

	// �����ֶΡ�Shape�ֶε��ֶ���
	private static final String SHAPECOLUMN = "shape";

	static String[] cols = new String[] { "oid", "code", SHAPECOLUMN };
	static String[] updateColumn = { "code" };// ��Ҫ�޸ĵ�����

	// SDE����
	private static SeConnection conn;

	/**
	 * @param ��ǫ
	 * @throws SeException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws SeException,
			UnknownHostException, IOException {
		confirmConnection();

		String layerName = "sde.sde.allnet";

		SeQuery queryResult = getQueryResult(layerName, cols, null,"CODE like 'A%'");

		// /////���������///////
		SeRow row = null;
		row = queryResult.fetch();
		String code = "";
		SeColumnDefinition[] colDefs = row.getColumns();
		while (row != null) {
			for (int i = 0; i < colDefs.length; i++) {
				int dataType = colDefs[i].getType();
				switch (dataType) {
				case SeColumnDefinition.TYPE_STRING:
					code = row.getString(i);
					break;
				case SeColumnDefinition.TYPE_SHAPE:
					SeShape shape = row.getShape(i);// �õ�SeShape���з�Χ��ѯ
					SeQuery shapes = getContainsShape("sde.sde.wubaiarea",
							SHAPECOLUMN, shape); // �õ�������shape
					System.out.println("updateResult : *******************");
					updateResult("sde.sde.wubaiarea", shapes, code,
							shapes.getNumColumns());
					break;
				default:
					break;
				}
			}
			row = queryResult.fetch();
		}
		distoryConnection();
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
	private static SeQuery getQueryResult(String layerName, String[] cols,
			SeFilter[] filters, String where) throws SeException {

		confirmConnection();
		// �����ѯ����
		SeSqlConstruct construst = new SeSqlConstruct(layerName);
		if (!"".equals(where)) {
			construst.setWhere(where);
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
	 * @return
	 * @throws SeException
	 */
	private static SeQuery getContainsShape(String layerName,
			String spatialColumn, SeShape shape) throws SeException {
		confirmConnection();
		// ������˹���
		SeShapeFilter shapeFilter = new SeShapeFilter(layerName, spatialColumn,
				shape, SeFilter.METHOD_SC);
		SeFilter[] filters = new SeFilter[] { shapeFilter };
		// ���췵����
		return getQueryResult(layerName, cols, filters, "");
	}

	private static void updateResult(String tableName, SeQuery query,
			String parentCode, int count) throws SeException {
		System.out.println("Update parentCode = " + parentCode);
		SeRow row = null;
		row = query.fetch();
		SeColumnDefinition[] colDefs = row.getColumns();

		int num = 1; // Ӧ����16��
		conn.startTransaction();
		while (row != null) {
			int oid = 0; // OID
			for (int i = 0; i < colDefs.length; i++) {
				int dataType = colDefs[i].getType();
				switch (dataType) {
				case SeColumnDefinition.TYPE_INT32:
					oid = row.getInteger(i);
					System.out.println("OID:" + oid);
					break;
				case SeColumnDefinition.TYPE_STRING:
					SeUpdate update = new SeUpdate(conn);
					update.toTable(tableName, updateColumn, "oid=" + oid);
					SeRow rowSet = update.getRowToSet();
					rowSet.setString(0, parentCode + FormatString.DString(num));
					update.execute();
					num++;
					break;
				case SeColumnDefinition.TYPE_SHAPE:
					SeShape shape = row.getShape(i);// �õ�SeShape���з�Χ��ѯ
					break;
				default:
					break;
				}
			}
			row = query.fetch();
		}
		conn.commitTransaction();
	}

	/**
	 * ȷ������
	 * 
	 * @throws SeException
	 */
	private static void confirmConnection() throws SeException {
		if (conn == null || conn.isClosed()) {
			conn = new SeConnection(server, instance, database, user, password);
		}
	}

	/**
	 * ��������
	 * 
	 * @throws SeException
	 */
	private static void distoryConnection() throws SeException {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}

}
