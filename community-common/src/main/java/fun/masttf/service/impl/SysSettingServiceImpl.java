package fun.masttf.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.SysSetting;
import fun.masttf.entity.query.SysSettingQuery;
import fun.masttf.service.SysSettingService;
import fun.masttf.utils.JsonUtils;
import fun.masttf.utils.StringTools;
import fun.masttf.utils.SysCacheUtils;
import fun.masttf.mapper.SysSettingMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.dto.SysSettingDto;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.SysSettingCodeEnum;

/**
 * @Description:系统设置信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("sysSettingService")
public class SysSettingServiceImpl implements SysSettingService {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SysSettingServiceImpl.class);
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
		PaginationResultVo<SysSetting> result = new PaginationResultVo<SysSetting>(count, page.getPageSize(),
				page.getPageNo(), page.getPageTotal(), list);
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
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return sysSettingMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<SysSetting> listBean) {
		if (listBean == null || listBean.isEmpty()) {
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

	@Override
	public void refreshCache() {
		try {
			SysSettingDto sysSettingDto = new SysSettingDto();
			List<SysSetting> list = sysSettingMapper.selectList(new SysSettingQuery());
			for (SysSetting sysSetting : list) {
				String JsonContent = sysSetting.getJsonContent();
				if (StringTools.isEmpty(JsonContent))
					continue;
				String code = sysSetting.getCode();
				SysSettingCodeEnum setting = SysSettingCodeEnum.getByCode(code);
				PropertyDescriptor pd = new PropertyDescriptor(setting.getPropName(), SysSettingDto.class);
				Method method = pd.getWriteMethod();
				Class<?> subClass = Class.forName(setting.getClassz());
				method.invoke(sysSettingDto, JsonUtils.convertJson2Obj(JsonContent, subClass));

			}
			SysCacheUtils.refresh(sysSettingDto);

		} catch (Exception e) {
			logger.error("刷新系统设置信息缓存失败", e);
		}
	}
}