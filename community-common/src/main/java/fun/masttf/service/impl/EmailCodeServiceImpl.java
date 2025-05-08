package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.EmailCode;
import fun.masttf.entity.query.EmailCodeQuery;
import fun.masttf.service.EmailCodeService;
import fun.masttf.mapper.EmailCodeMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:邮箱验证码Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("emailCodeService")
public class EmailCodeServiceImpl implements EmailCodeService {

	@Autowired
	private EmailCodeMapper<EmailCode, EmailCodeQuery> emailCodeMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<EmailCode> findListByQuery(EmailCodeQuery query) {
		return emailCodeMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(EmailCodeQuery query) {
		return emailCodeMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<EmailCode> findListByPage(EmailCodeQuery query) {
		Integer count = emailCodeMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<EmailCode> list = emailCodeMapper.selectList(query);
		PaginationResultVo<EmailCode> result = new PaginationResultVo<EmailCode>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(EmailCode bean) {
		return emailCodeMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<EmailCode> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return emailCodeMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<EmailCode> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return emailCodeMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据EmailAndCode查询
	 */
	@Override
	public EmailCode getByEmailAndCode(String email, String code) {
		return emailCodeMapper.selectByEmailAndCode(email, code);
	}

	/**
	 * 根据EmailAndCode更新
	 */
	@Override
	public Integer updateByEmailAndCode(EmailCode bean, String email, String code) {
		return emailCodeMapper.updateByEmailAndCode(bean, email, code);
	}

	/**
	 * 根据EmailAndCode删除
	 */
	@Override
	public Integer deleteByEmailAndCode(String email, String code) {
		return emailCodeMapper.deleteByEmailAndCode(email, code);
	}

}