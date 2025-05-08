package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.SysSetting;
import fun.masttf.entity.query.SysSettingQuery;
import fun.masttf.service.SysSettingService;
import fun.masttf.mapper.SysSettingMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:系统设置信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("sysSettingService")
public class SysSettingServiceImpl implements SysSettingService {

	@Autowired
	private SysSettingMapper<SysSetting, SysSettingQuery> sysSettingMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<SysSetting> findListByQuery(SysSettingQuery query) {
		return sysSettingMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(SysSettingQuery query) {
		return sysSettingMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<SysSetting> findListByPage(SysSettingQuery query) {
		Integer count = sysSettingMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<SysSetting> list = sysSettingMapper.selectList(query);
		PaginationResultVo<SysSetting> result = new PaginationResultVo<SysSetting>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(SysSetting bean) {
		return sysSettingMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<SysSetting> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return sysSettingMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<SysSetting> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return sysSettingMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Code查询
	 */
	@Override
	public SysSetting getByCode(String code) {
		return sysSettingMapper.selectByCode(code);
	}

	/**
	 * 根据Code更新
	 */
	@Override
	public Integer updateByCode(SysSetting bean, String code) {
		return sysSettingMapper.updateByCode(bean, code);
	}

	/**
	 * 根据Code删除
	 */
	@Override
	public Integer deleteByCode(String code) {
		return sysSettingMapper.deleteByCode(code);
	}

}