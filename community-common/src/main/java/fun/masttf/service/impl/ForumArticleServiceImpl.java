package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.service.ForumArticleService;
import fun.masttf.mapper.ForumArticleMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:文章信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("forumArticleService")
public class ForumArticleServiceImpl implements ForumArticleService {

	@Autowired
	private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ForumArticle> findListByQuery(ForumArticleQuery query) {
		return forumArticleMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(ForumArticleQuery query) {
		return forumArticleMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<ForumArticle> findListByPage(ForumArticleQuery query) {
		Integer count = forumArticleMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumArticle> list = forumArticleMapper.selectList(query);
		PaginationResultVo<ForumArticle> result = new PaginationResultVo<ForumArticle>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ForumArticle bean) {
		return forumArticleMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ForumArticle> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ForumArticle> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据ArticleId查询
	 */
	@Override
	public ForumArticle getByArticleId(String articleId) {
		return forumArticleMapper.selectByArticleId(articleId);
	}

	/**
	 * 根据ArticleId更新
	 */
	@Override
	public Integer updateByArticleId(ForumArticle bean, String articleId) {
		return forumArticleMapper.updateByArticleId(bean, articleId);
	}

	/**
	 * 根据ArticleId删除
	 */
	@Override
	public Integer deleteByArticleId(String articleId) {
		return forumArticleMapper.deleteByArticleId(articleId);
	}

}