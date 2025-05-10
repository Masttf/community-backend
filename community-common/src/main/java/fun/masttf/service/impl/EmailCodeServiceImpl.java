package fun.masttf.service.impl;

import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.EmailCode;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.query.EmailCodeQuery;
import fun.masttf.service.EmailCodeService;
import fun.masttf.utils.StringTools;
import fun.masttf.mapper.EmailCodeMapper;
import fun.masttf.mapper.UserInfoMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.query.UserInfoQuery;
import fun.masttf.config.WebConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:邮箱验证码Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("emailCodeService")
public class EmailCodeServiceImpl implements EmailCodeService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EmailCodeServiceImpl.class);
	@Autowired
	private EmailCodeMapper<EmailCode, EmailCodeQuery> emailCodeMapper;

	@Autowired
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private WebConfig webConfig;

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
		PaginationResultVo<EmailCode> result = new PaginationResultVo<EmailCode>(count, page.getPageSize(),
				page.getPageNo(), page.getPageTotal(), list);
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
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return emailCodeMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<EmailCode> listBean) {
		if (listBean == null || listBean.isEmpty()) {
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

	/*
	 * 发送邮箱验证码
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void sendEmailCode(String email, Integer type) {
		if (type == 0) {
			UserInfo userInfo = userInfoMapper.selectByEmail(email);
			if (userInfo != null) {
				throw new BusinessException("邮箱已存在");
			}
			String code = StringTools.getRandomString(Constans.LENGTH_5);
			sendEmailCodeDo(email, code);
			emailCodeMapper.disableEmailCode(email);
			EmailCode emailCode = new EmailCode();
			emailCode.setEmail(email);
			emailCode.setCode(code);
			emailCode.setStatus(0);
			emailCode.setCreateTime(new Date());
			emailCodeMapper.insert(emailCode);
		}
	}

	private void sendEmailCodeDo(String email, String code) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(webConfig.getSendUserName());
			helper.setTo(email);
			helper.setSubject("注册邮箱验证码");
			helper.setText("您的验证码是：" + code);
			helper.setSentDate(new Date());
			javaMailSender.send(message);
		} catch (Exception e) {
			logger.error("发送邮箱验证码失败", e);
			throw new BusinessException("发送邮箱验证码失败");
		}
	}

	@Override
	public void checkCode(String email, String emailcode) {
		EmailCode emailCode = emailCodeMapper.selectByEmailAndCode(email, emailcode);
		if (emailCode != null) {
			if (emailCode.getStatus() == 1
					|| emailCode.getCreateTime().getTime() + Constans.LENGTH_10 * 60 * 1000 < System
							.currentTimeMillis()) {
				throw new BusinessException("验证码已失效");
			}
		} else {
			throw new BusinessException("邮箱验证码不匹配");
		}
		emailCodeMapper.disableEmailCode(email);
	}
}