package fun.masttf.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:文件信息Mapper
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumArticleAttachmentMapper<T, P> extends BaseMapper<T, P> {

	/**
	 * 根据FileId查询
	 */
	T selectByFileId(@Param("fileId") String fileId);

	/**
	 * 根据FileId更新
	 */
	Integer updateByFileId(@Param("bean") T t, @Param("fileId") String fileId);

	/**
	 * 根据FileId删除
	 */
	Integer deleteByFileId(@Param("fileId") String fileId);


}