package com.jw.demo.domain;

import com.jw.common.mybatis.annotation.CustColumn;
import com.jw.common.mybatis.annotation.CustId;
import com.jw.common.mybatis.annotation.CustTable;

import java.io.Serializable;

/**
 * 微信二维码详情
 *
 * @author wangwangxian
 * @date 2018/10/12 17:51
 */

@CustTable(value = "wx_qrcode")
public class WxQrcodeEntity implements Serializable{
    /**
     * 主键id
     */
    @CustId
    private int id;
    /**
     * 微信appid
     */
    @CustColumn("wx_app_id")
    private String wxAppId;
    /**
     * 二维码名称
     */
    @CustColumn("name")
    private String name;
    /**
     * 二维码类型:0-临时二维码,1-永久二维码
     */
    @CustColumn("code_type")
    private Integer codeType;
    /**
     * 有效期,单位:天
     */
    @CustColumn("expire_day")
    private Integer expireDay;
    /**
     * 二维码ticket，凭借此ticket可以在有效时间内换取二维码
     */
    @CustColumn("ticket")
    private String ticket;
    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    @CustColumn("url")
    private String url;
    /**
     * 生效时间
     */
    @CustColumn("effective_time")
    private Long effectiveTime;
    /**
     * 创建时间
     */
    @CustColumn("create_time")
    private Long createTime;
    /**
     * 创建人
     */
    @CustColumn("create_by")
    private String createBy;
    /**
     * 修改时间
     */
    @CustColumn("update_time")
    private Long updateTime;
    /**
     * 修改人
     */
    @CustColumn("update_by")
    private String updateBy;
    /**
     * 拉粉数
     */
    @CustColumn("fans_count")
    private Integer fansCount;
    /**
     * 取消关注数
     */
    @CustColumn("unsubscribe_count")
    private Integer unsubscribeCount;
    /**
     * 扫描次数
     */
    @CustColumn("scan_times")
    private Integer scanTimes;
    /**
     *
     */
    private Integer version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(Integer expireDay) {
        this.expireDay = expireDay;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getUnsubscribeCount() {
        return unsubscribeCount;
    }

    public void setUnsubscribeCount(Integer unsubscribeCount) {
        this.unsubscribeCount = unsubscribeCount;
    }

    public Integer getScanTimes() {
        return scanTimes;
    }

    public void setScanTimes(Integer scanTimes) {
        this.scanTimes = scanTimes;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "WxQrcodeEntity{" +
                "id=" + id +
                ", wxAppId='" + wxAppId + '\'' +
                ", name='" + name + '\'' +
                ", codeType=" + codeType +
                ", expireDay=" + expireDay +
                ", ticket='" + ticket + '\'' +
                ", url='" + url + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", fansCount=" + fansCount +
                ", unsubscribeCount=" + unsubscribeCount +
                ", scanTimes=" + scanTimes +
                '}';
    }
}
