/**
 * 
 */
package com.trgis.bmy.code;

import java.io.IOException;
import java.net.UnknownHostException;

import com.esri.sde.sdk.client.SeColumnDefinition;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeQuery;
import com.esri.sde.sdk.client.SeRow;
import com.esri.sde.sdk.client.SeShape;
import com.esri.sde.sdk.client.SeUpdate;
import com.trgis.arcgis.tools.sde.SDETools;

/**
 * 更新500分区的代码
 * 
 * @author bmy
 * 
 */
public class UpdateSMArea {

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

	static String layerName = "sde.sde.wushiarea"; // 五百分区图层名称
	static String updateTable = "sde.sde.shimiarea";

	private static SDETools sdeTools;

	/**
	 * @param 张谦
	 * @throws SeException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws SeException,
			UnknownHostException, IOException {
		sdeTools = new SDETools(server, instance, database, user, password);

		SeQuery queryResult = sdeTools.getQueryResult(layerName, cols, null,
				"CODE like 'B0011%'");

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
					SeQuery shapes = sdeTools.getContainsShape(updateTable,
							SHAPECOLUMN, shape, cols, null); // 得到包含的shape
					updateResult(updateTable, shapes, code,
							shapes.getNumColumns());
					break;
				default:
					break;
				}
			}
			row = queryResult.fetch();
		}
		sdeTools.distory();
	}

	private static void updateResult(String tableName, SeQuery query,
			String parentCode, int count) throws SeException {
		System.out.println("Update parentCode = " + parentCode);
		SeRow row = null;
		row = query.fetch();
		SeColumnDefinition[] colDefs = row.getColumns();
		String[] codes = { "U", "V", "W", "X", "Y", "P", "Q", "R", "S", "T",
				"K", "L", "M", "N", "O", "F", "G", "H", "I", "J", "A", "B",
				"C", "D", "E" };
		int num = 0; // 应该有16个
		sdeTools.startTransaction();
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
					String code = row.getString(i);
					System.out.println("CODE:" + code);
					if (code != null) {
						continue;
					}
					SeUpdate update = sdeTools.getSeUpdate();
					if (update == null) {
						continue;
					}
					update.toTable(tableName, updateColumn, "oid=" + oid);
					SeRow rowSet = update.getRowToSet();
					rowSet.setString(0, parentCode + codes[num]);
					update.execute();
					num++;
					break;
				case SeColumnDefinition.TYPE_SHAPE:
					// SeShape shape = row.getShape(i);// 得到SeShape进行范围查询
					break;
				default:
					break;
				}
			}
			row = query.fetch();
		}
		sdeTools.commitTransaction();
	}

}
