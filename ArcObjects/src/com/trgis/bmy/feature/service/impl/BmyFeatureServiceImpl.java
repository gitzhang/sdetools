/**
 * 
 */
package com.trgis.bmy.feature.service.impl;

import java.util.ResourceBundle;

import com.esri.sde.sdk.client.SeDelete;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeInsert;
import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeRow;
import com.esri.sde.sdk.client.SeShape;
import com.esri.sde.sdk.client.SeUpdate;
import com.esri.sde.sdk.geom.SeCoordRef;
import com.esri.sde.sdk.geom.SeGeometryException;
import com.trgis.arcgis.tools.sde.SDETools;
import com.trgis.bmy.feature.service.BmyFeatureService;

/**
 * 
 * ������ϢϵͳҪ�ط��� ʵ��
 * 
 * @author ��ǫ
 * 
 */
@SuppressWarnings("unused")
public class BmyFeatureServiceImpl implements BmyFeatureService {

	/**
	 * �ɹ���
	 */
	private static final int SUCCESS_CODE = 0;

	/**
	 * ʧ����
	 */
	private static final int ERROR_CODE = -1;

	/**
	 * Ĭ�Ϸ���˿�
	 */
	private static final String DEFAULT_INSTANCE = "5151";

	private ResourceBundle properties; // ��Դ�ļ�

	private String server;
	private String instance = DEFAULT_INSTANCE;
	private String database = "sde";
	private String user = "sde";
	private String password = "sde";

	static String[] updateColumn = { "name","shape" };// ��Ҫ�޸ĵ�����

	private SDETools sdeTools;

	public BmyFeatureServiceImpl() {
		properties = ResourceBundle.getBundle("bmyfeatureservice");// Լ��Ĭ�ϵ�������Դ
		server = properties.getString("server");
		instance = properties.getString("instance");
		database = properties.getString("database");
		user = properties.getString("user");
		password = properties.getString("password");
	}

	protected int addFeature(String tableName, String name, String id,
			double x, double y) throws SeException, SeGeometryException {
		try {
			sdeTools = new SDETools(server, instance, database, user, password);
			if (sdeTools == null) {
				throw new RuntimeException("��ʼ��SDE����");
			}
			String shapeColumn = "shape";
			SeLayer layer = sdeTools.getSeLayer(tableName, shapeColumn);
			SeInsert insert = sdeTools.getSeInsert();
			String[] cols = new String[3];
			cols[0] = "NAME";
			cols[1] = "SID";
			cols[2] = "SHAPE";
			insert.intoTable(tableName, cols);
			insert.setWriteMode(true);
			SeRow row = insert.getRowToSet();
			row.setString(0, name);
			row.setString(1, id);
			SeCoordRef ref = new SeCoordRef();
			SeShape shape = new SeShape(layer.getCoordRef());
			shape.generateFromText("POINT(" + x + " " + y + ")");
			row.setShape(2, shape);
			insert.execute();
			insert.flushBufferedWrites();
			insert.close();
			sdeTools.commitTransaction();
			sdeTools.distory();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ERROR_CODE;
		}
		return SUCCESS_CODE;
	}

	protected int updateFeature(String tableName, String name, String id,
			double x, double y) throws SeException, SeGeometryException {
		try {
			sdeTools = new SDETools(server, instance, database, user, password);
			if (sdeTools == null) {
				throw new RuntimeException("��ʼ��SDE����");
			}
			String shapeColumn = "shape";
			SeLayer layer = sdeTools.getSeLayer(tableName, shapeColumn);
			SeUpdate update = sdeTools.getSeUpdate();

			update.toTable(tableName, updateColumn, "sid='"+id+"'");
			SeRow rowSet = update.getRowToSet();
			rowSet.setString(0, name);
			SeShape shape = new SeShape(layer.getCoordRef());
			shape.generateFromText("POINT(" + x + " " + y + ")");
			rowSet.setShape(1, shape);
			update.execute();
			update.close();

			sdeTools.commitTransaction();
			sdeTools.distory();
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR_CODE;
		}
		return SUCCESS_CODE;
	}
	
	@Override
	public int deleteFeature(String tableName, String id) {
		try {
			sdeTools = new SDETools(server, instance, database, user, password);
			if (sdeTools == null) {
				throw new RuntimeException("��ʼ��SDE����");
			}
			String shapeColumn = "shape";
			SeLayer layer = sdeTools.getSeLayer(tableName, shapeColumn);
			SeDelete sd= sdeTools.getSeDelete();
			String whereClause="sid='"+id+"'";
			sd.fromTable(layer.getName(), whereClause);
			sd.close();
			sdeTools.commitTransaction();
			sdeTools.distory();
		} catch (SeException e) {
			e.printStackTrace();
			return ERROR_CODE;
		}
		return SUCCESS_CODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.trgis.bmy.feature.service.BmyFeatureService#addRelice(java.lang.String
	 * , java.lang.String, java.lang.Double, java.lang.Double)
	 */
	@Override
	public int addRelice(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.relic";
		return addFeature(tableName, name, id, x, y);
	}

	@Override
	public int addUtensils(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.utensils";
		return addFeature(tableName, name, id, x, y);
	}

	@Override
	public int addSign(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.sign";
		return addFeature(tableName, name, id, x, y);
	}

	@Override
	public int addSkeleton(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.skeleton";
		return addFeature(tableName, name, id, x, y);
	}

	@Override
	public int addFigurine(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.figurine";
		return addFeature(tableName, name, id, x, y);
	}

	@Override
	public int addDrill(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.drill";
		return addFeature(tableName, name, id, x, y);
	}

	@Override
	public int updateRelice(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.relic";
		return updateFeature(tableName, name, id, x, y);
	}
	
	@Override
	public int updateUtensils(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.utensils";
		return updateFeature(tableName, name, id, x, y);
	}

	@Override
	public int updateSign(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.sign";
		return updateFeature(tableName, name, id, x, y);
	}

	@Override
	public int updateSkeleton(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.skeleton";
		return updateFeature(tableName, name, id, x, y);
	}

	@Override
	public int updateFigurine(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.figurine";
		return updateFeature(tableName, name, id, x, y);
	}

	@Override
	public int updateDrill(String name, String id, double x, double y)
			throws SeException, SeGeometryException {
		String tableName = "sde.sde.drill";
		return updateFeature(tableName, name, id, x, y);
	}

	/**
	 * For Test
	 * 
	 * @param args
	 * @throws SeGeometryException
	 * @throws SeException
	 */
	public static void main(String[] args) throws SeGeometryException,
			SeException {
		BmyFeatureService service = new BmyFeatureServiceImpl();
		//int flag = service.updateRelice("�����޸�", "333", 5233741, 3806309);
		int flag = service.deleteFeature("sde.sde.relic", "333");
		System.out.println(flag);
	}
}
