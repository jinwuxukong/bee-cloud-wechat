package com.jeecg.p3.goldeneggs.entity;

import java.util.Date;

import org.jeecgframework.p3.core.utils.persistence.Entity;
import org.jeewx.api.core.util.DateUtils;

import com.jeecg.p3.goldeneggs.annotation.Excel;

/**
 * 描述：</b>WxActGoldeneggsRecord:砍价帮砍记录表<br>
 * @author junfeng.zhou
 * @since：2016年06月13日 10时55分39秒 星期一 
 * @version:1.0
 */
public class WxActGoldeneggsRecord implements Entity<String> {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String actId;
	/**
	 * 活动名称
	 */
	@Excel(exportName="活动名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private String title;
	@Excel(exportName="openid", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	@Excel(exportName="昵称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	@Excel(exportName="中奖时间", exportConvertSign = 1, exportFieldWidth = 30, importConvertSign = 0)
	private String awardsId;
	@Excel(exportName="收货人姓名", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	@Excel(exportName="手机号", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	@Excel(exportName="地址", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	@Excel(exportName="奖品名字", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	/**
	 * 核销状态
	 */
	 * 头像
	 */
	 * 奖品图片
	 */
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	/**
	private String code;
	/**
	 *奖品编码
	 */
	
	 *核销员id
	 */
	private String verifyId;
	 */
	private Date recieveTime;
	/**
	 *奖项配置id
	 */
	private String relationId;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTimeConvert() {
		return DateUtils.formatDate(this.createTime, "yyyy-MM-dd HH:mm:ss");

	}
	public String getRecieveStatus() {
		return recieveStatus;
	}
	public void setRecieveStatus(String recieveStatus) {
		this.recieveStatus = recieveStatus;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getVerifyId() {
		return verifyId;
	}
	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}
	public Date getRecieveTime() {
		return recieveTime;
	}
	public void setRecieveTime(Date recieveTime) {
		this.recieveTime = recieveTime;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
}
