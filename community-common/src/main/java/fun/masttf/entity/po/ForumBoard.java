package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:文章板块信息
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumBoard implements Serializable {

	/**
	 * 板块ID
	 */
	private Integer boardId;

	/**
	 * 父级板块ID
	 */
	private Integer pBoardId;

	/**
	 * 板块名
	 */
	private String boardName;

	/**
	 * 封面
	 */
	private String cover;

	/**
	 * 描述
	 */
	private String boardDesc;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 0:只允许管理员发帖 1:任何人可以发帖
	 */
	private Integer postType;

	public Integer getpBoardId() {
		return pBoardId;
	}

	public void setpBoardId(Integer pBoardId) {
		this.pBoardId = pBoardId;
	}

	public List<ForumBoard> getChildren() {
		return children;
	}

	public void setChildren(List<ForumBoard> children) {
		this.children = children;
	}

	private List<ForumBoard> children;

	public Integer getBoardId() {
		return this.boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public Integer getPBoardId() {
		return this.pBoardId;
	}

	public void setPBoardId(Integer pBoardId) {
		this.pBoardId = pBoardId;
	}

	public String getBoardName() {
		return this.boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getBoardDesc() {
		return this.boardDesc;
	}

	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getPostType() {
		return this.postType;
	}

	public void setPostType(Integer postType) {
		this.postType = postType;
	}

	@Override
	public String toString() {
		return "ForumBoard [" +
			"板块ID boardId=" + (boardId == null ? "空" : boardId) + ", " +
			"父级板块ID pBoardId=" + (pBoardId == null ? "空" : pBoardId) + ", " +
			"板块名 boardName=" + (boardName == null ? "空" : boardName) + ", " +
			"封面 cover=" + (cover == null ? "空" : cover) + ", " +
			"描述 boardDesc=" + (boardDesc == null ? "空" : boardDesc) + ", " +
			"排序 sort=" + (sort == null ? "空" : sort) + ", " +
			"0:只允许管理员发帖 1:任何人可以发帖 postType=" + (postType == null ? "空" : postType) + ", " +
			"]";
	}

}