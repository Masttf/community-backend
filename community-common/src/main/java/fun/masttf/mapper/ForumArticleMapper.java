package fun.masttf.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:文章信息Mapper
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumArticleMapper<T, P> extends BaseMapper<T, P> {

	/**
	 * 根据ArticleId查询
	 */
	T selectByArticleId(@Param("articleId") String articleId);

	/**
	 * 根据ArticleId更新
	 */
	Integer updateByArticleId(@Param("bean") T t, @Param("articleId") String articleId);

	/**
	 * 根据ArticleId删除
	 */
	Integer deleteByArticleId(@Param("articleId") String articleId);

	void updateArticleCount(@Param("updateType") Integer updateType,@Param("changeCount") Integer changeCount, @Param("articleId") String articleId);

	void updateBoardNameBatch(@Param("boardType") Integer boardType,@Param("boardName") String boardName, @Param("boardId") Integer boardId);

	void updateStatusBatchByUserId(@Param("status") Integer status, @Param("userId") String userId);
}