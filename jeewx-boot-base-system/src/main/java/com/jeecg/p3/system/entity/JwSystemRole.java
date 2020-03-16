package com.jeecg.p3.system.entity;

import java.util.Date;

import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>JwSystemRole:运营角色表; InnoDB free: 9216 kB<br>
 * @author junfeng.zhou
 * @since：2015年12月21日 10时28分28秒 星期一 
 * @version:1.0
 */
public class JwSystemRole implements Entity<Long> {
	private static final long serialVersionUID = 1L;
	
	
	private Boolean isChecked;
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	@Override
	public String toString() {
		return "JwSystemRole [id=" + id + ", roleId=" + roleId + ", roleName="
				+ roleName + ", crtOperator=" + crtOperator + ", crtDt="
				+ crtDt + ", roleTyp=" + roleTyp + ", delStat=" + delStat
				+ ", creator=" + creator + ", editor=" + editor + ", createDt="
				+ createDt + ", editDt=" + editDt + ", lastEditDt="
				+ lastEditDt + ", recordVersion=" + recordVersion + "]";
	}
	
}
