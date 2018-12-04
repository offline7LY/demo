package com.lx.pay.biz.abs;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PaymentContext extends Context {

    private String outTradeNo; //商户订单号（必填）

    private Integer totalFee; //总金额，单位分（必填）
    /**
     * 返回参数
     */
    private String htmlStr; //构建html表单


    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public PaymentContext() {
        super();
    }

}
