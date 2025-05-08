package fun.masttf.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:用户附件下载Mapper
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumArticleAttachmentDownloadMapper<T, P> extends BaseMapper<T, P> {

	/**
	 * 根据FileIdAndUserId查询
	 */
	T selectByFileIdAndUserId(@Param("fileId") String fileId, @Param("userId") String userId);

	/**
	 * 根据FileIdAndUserId更新
	 */
	Integer updateByFileIdAndUserId(@Param("bean") T t, @Param("fileId") String fileId, @Param("userId") String userId);

	/**
	 * 根据FileIdAndUserId删除
	 */
	Integer deleteByFileIdAndUserId(@Param("fileId") String fileId, @Param("userId") String userId);


}