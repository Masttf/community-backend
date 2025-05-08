package fun.masttf.entity.query;


/**
 * @Description:系统设置信息查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class SysSettingQuery extends BaseQuery {

	/**
	 * 编号
	 */
	private String code;

	private String codeFuzzy;

	/**
	 * 设置信息
	 */
	private String jsonContent;

	private String jsonContentFuzzy;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getJsonContent() {
		return this.jsonContent;
	}

	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}

	public String getCodeFuzzy() {
		return this.codeFuzzy;
	}

	public void setCodeFuzzy(String codeFuzzy) {
		this.codeFuzzy = codeFuzzy;
	}

	public String getJsonContentFuzzy() {
		return this.jsonContentFuzzy;
	}

	public void setJsonContentFuzzy(String jsonContentFuzzy) {
		this.jsonContentFuzzy = jsonContentFuzzy;
	}


}