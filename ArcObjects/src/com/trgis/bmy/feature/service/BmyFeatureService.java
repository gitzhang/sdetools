/**
 * 
 */
package com.trgis.bmy.feature.service;

import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.geom.SeGeometryException;

/**
 * @author ��ǫ
 * 
 *         ������Ϣϵͳ Ҫ�ط���ӿ�
 * 
 */
public interface BmyFeatureService {

	/**
	 * ����������
	 * @param name ����������
	 * @param id ���������id
	 * @param x ������x����
	 * @param y ������y����
	 * @return ����״̬ 0 �ɹ� -1 ʧ��
	 * @throws SeException 
	 * @throws SeGeometryException 
	 */
	int addRelice(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * �޸���������
	 * @param name ����������
	 * @param id ���������id
	 * @param x ������x����
	 * @param y ������y����
	 * @return ����״̬ 0 �ɹ� -1 ʧ��
	 * @throws SeException 
	 * @throws SeGeometryException 
	 */
	int updateRelice(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * ��������
	 * @param name ��������
	 * @param id ϵͳ����ID
	 * @param x ����x����
	 * @param y ����y����
	 * @return ����״̬ 0 �ɹ� -1 ʧ��
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int addUtensils(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * �޸�����
	 * @param name ��������
	 * @param id ϵͳ����ID
	 * @param x ����x����
	 * @param y ����y����
	 * @return ����״̬ 0 �ɹ� -1 ʧ��
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int updateUtensils(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * ��Ӽ���
	 * @param name ��������
	 * @param id �������ID
	 * @param x ����x����
	 * @param y ����y����
	 * @return ����״̬ 0 �ɹ� -1 ʧ��
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int addSign(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * �޸ļ���
	 * @param name ��������
	 * @param id �������ID
	 * @param x ����x����
	 * @param y ����y����
	 * @return ����״̬ 0 �ɹ� -1 ʧ��
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int updateSign(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * ��Ӷ������
	 * @param name
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int addSkeleton(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * �޸Ķ������
	 * @param name
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int updateSkeleton(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * �����ٸ
	 * @param name
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int addFigurine(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * �޸���ٸ
	 * @param name
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int updateFigurine(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * �������
	 * @param name
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int addDrill(String name, String id, double x , double y) throws SeException, SeGeometryException;
	
	/**
	 * �޸����
	 * @param name
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int updateDrill(String name, String id, double x , double y) throws SeException, SeGeometryException;
	
	/**
	 * ɾ������
	 * @param tableName
	 * @param id
	 * @return
	 */
	int deleteFeature(String tableName,String id);
	
}
