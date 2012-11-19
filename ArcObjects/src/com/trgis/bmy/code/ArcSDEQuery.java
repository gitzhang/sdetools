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

	// 基本连接属性
	private static final String server = "192.168.1.3";
	private static final String instance = "5151";
	private static final String database = "sde";
	private static final String user = "sde";
	private static final String password = "sde";

	// 属性字段。Shape字段的字段名
	private static final String SHAPECOLUMN = "shape";

	static String[] cols = new String[] { "oid", "code", SHAPECOLUMN };
	static String[] updateColumn = { "code" };// 需要修改的列名

	// SDE连接
	private static SeConnection conn;

	/**
	 * @param 张谦
	 * @throws SeException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws SeException,
			UnknownHostException, IOException {
		confirmConnection();

		String layerName = "sde.sde.allnet";

		SeQuery queryResult = getQueryResult(layerName, cols, null,"CODE like 'A%'");

		// /////遍历结果集///////
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
					SeShape shape = row.getShape(i);// 得到SeShape进行范围查询
					SeQuery shapes = getContainsShape("sde.sde.wubaiarea",
							SHAPECOLUMN, shape); // 得到包含的shape
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
	 * 结果查询
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
		// 构造查询参数
		SeSqlConstruct construst = new SeSqlConstruct(layerName);
		if (!"".equals(where)) {
			construst.setWhere(where);
		}
		// 创建查询
		SeQuery query = new SeQuery(conn, cols, construst);
		// 准备查询
		query.prepareQuery();

		// 设置过滤规则
		if (filters != null && filters.length > 0) {
			query.setSpatialConstraints(SeQuery.SE_SPATIAL_FIRST, true, filters);
		}
		// 执行查询
		query.execute();
		return query;
	}

	/**
	 * 空间包含关系的查询
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
		// 构造过滤规则
		SeShapeFilter shapeFilter = new SeShapeFilter(layerName, spatialColumn,
				shape, SeFilter.METHOD_SC);
		SeFilter[] filters = new SeFilter[] { shapeFilter };
		// 构造返回列
		return getQueryResult(layerName, cols, filters, "");
	}

	private static void updateResult(String tableName, SeQuery query,
			String parentCode, int count) throws SeException {
		System.out.println("Update parentCode = " + parentCode);
		SeRow row = null;
		row = query.fetch();
		SeColumnDefinition[] colDefs = row.getColumns();

		int num = 1; // 应该有16个
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
					SeShape shape = row.getShape(i);// 得到SeShape进行范围查询
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
	 * 确认连接
	 * 
	 * @throws SeException
	 */
	private static void confirmConnection() throws SeException {
		if (conn == null || conn.isClosed()) {
			conn = new SeConnection(server, instance, database, user, password);
		}
	}

	/**
	 * 销毁连接
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
