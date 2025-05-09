package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.EmailCode;
import fun.masttf.entity.query.EmailCodeQuery;

/**
 * @Description:邮箱验证码Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface EmailCodeService {

	/**
	 * 根据条件查询列表
	 */
	List<EmailCode> findListByQuery(EmailCodeQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(EmailCodeQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<EmailCode> findListByPage(EmailCodeQuery query);

	/**
	 * 新增
	 */
	Integer add(EmailCode bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<EmailCode> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<EmailCode> listBean);

	/**
	 * 根据EmailAndCode查询
	 */
	EmailCode getByEmailAndCode(String email, String code);

	/**
	 * 根据EmailAndCode更新
	 */
	Integer updateByEmailAndCode(EmailCode bean, String email, String code);

	/**
	 * 根据EmailAndCode删除
	 */
	Integer deleteByEmailAndCode(String email, String code);

	void sendEmailCode(String email, Integer type);

	void checkCode(String email, String eamilcode);
}