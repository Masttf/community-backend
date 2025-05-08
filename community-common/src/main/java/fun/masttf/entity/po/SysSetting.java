package fun.masttf.entity.po;

import java.io.Serializable;

/**
 * @Description:系统设置信息
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class SysSetting implements Serializable {

	/**
	 * 编号
	 */
	private String code;

	/**
	 * 设置信息
	 */
	private String jsonContent;

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

	@Override
	public String toString() {
		return "SysSetting [" +
			"编号 code=" + (code == null ? "空" : code) + ", " +
			"设置信息 jsonContent=" + (jsonContent == null ? "空" : jsonContent) + ", " +
			"]";
	}

}