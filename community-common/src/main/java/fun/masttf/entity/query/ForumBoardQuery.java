package fun.masttf.entity.query;


/**
 * @Description:文章板块信息查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumBoardQuery extends BaseQuery {

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

	private String boardNameFuzzy;

	/**
	 * 封面
	 */
	private String cover;

	private String coverFuzzy;

	/**
	 * 描述
	 */
	private String boardDesc;

	private String boardDescFuzzy;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 0:只允许管理员发帖 1:任何人可以发帖
	 */
	private Integer postType;

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

	public String getBoardNameFuzzy() {
		return this.boardNameFuzzy;
	}

	public void setBoardNameFuzzy(String boardNameFuzzy) {
		this.boardNameFuzzy = boardNameFuzzy;
	}

	public String getCoverFuzzy() {
		return this.coverFuzzy;
	}

	public void setCoverFuzzy(String coverFuzzy) {
		this.coverFuzzy = coverFuzzy;
	}

	public String getBoardDescFuzzy() {
		return this.boardDescFuzzy;
	}

	public void setBoardDescFuzzy(String boardDescFuzzy) {
		this.boardDescFuzzy = boardDescFuzzy;
	}


}