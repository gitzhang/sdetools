/**
 * 
 */
package com.trgis.bmy.feature.service;

import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.geom.SeGeometryException;

/**
 * @author 张谦
 * 
 *         考古信息系统 要素服务接口
 * 
 */
public interface BmyFeatureService {

	/**
	 * 新增出土物
	 * @param name 出土物名称
	 * @param id 出土物关联id
	 * @param x 出土物x坐标
	 * @param y 出土物y坐标
	 * @return 新增状态 0 成功 -1 失败
	 * @throws SeException 
	 * @throws SeGeometryException 
	 */
	int addRelice(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * 修改增出土物
	 * @param name 出土物名称
	 * @param id 出土物关联id
	 * @param x 出土物x坐标
	 * @param y 出土物y坐标
	 * @return 新增状态 0 成功 -1 失败
	 * @throws SeException 
	 * @throws SeGeometryException 
	 */
	int updateRelice(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * 新增器物
	 * @param name 器物名称
	 * @param id 系统关联ID
	 * @param x 器物x坐标
	 * @param y 器物y坐标
	 * @return 新增状态 0 成功 -1 失败
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int addUtensils(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * 修改器物
	 * @param name 器物名称
	 * @param id 系统关联ID
	 * @param x 器物x坐标
	 * @param y 器物y坐标
	 * @return 新增状态 0 成功 -1 失败
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int updateUtensils(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * 添加迹象
	 * @param name 迹象名称
	 * @param id 迹象关联ID
	 * @param x 迹象x坐标
	 * @param y 迹象y坐标
	 * @return 新增状态 0 成功 -1 失败
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int addSign(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * 修改迹象
	 * @param name 迹象名称
	 * @param id 迹象关联ID
	 * @param x 迹象x坐标
	 * @param y 迹象y坐标
	 * @return 新增状态 0 成功 -1 失败
	 * @throws SeException
	 * @throws SeGeometryException
	 */
	int updateSign(String name, String id, double x, double y) throws SeException, SeGeometryException;
	
	/**
	 * 添加动物骨骼
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
	 * 修改动物骨骼
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
	 * 添加陶俑
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
	 * 修改陶俑
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
	 * 新增钻孔
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
	 * 修改钻孔
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
	 * 删除方法
	 * @param tableName
	 * @param id
	 * @return
	 */
	int deleteFeature(String tableName,String id);
	
}
