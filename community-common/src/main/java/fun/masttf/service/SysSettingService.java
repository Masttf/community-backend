package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.SysSetting;
import fun.masttf.entity.query.SysSettingQuery;

/**
 * @Description:系统设置信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface SysSettingService {

	/**
	 * 根据条件查询列表
	 */
	List<SysSetting> findListByQuery(SysSettingQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(SysSettingQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<SysSetting> findListByPage(SysSettingQuery query);

	/**
	 * 新增
	 */
	Integer add(SysSetting bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<SysSetting> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<SysSetting> listBean);

	/**
	 * 根据Code查询
	 */
	SysSetting getByCode(String code);

	/**
	 * 根据Code更新
	 */
	Integer updateByCode(SysSetting bean, String code);

	/**
	 * 根据Code删除
	 */
	Integer deleteByCode(String code);

}