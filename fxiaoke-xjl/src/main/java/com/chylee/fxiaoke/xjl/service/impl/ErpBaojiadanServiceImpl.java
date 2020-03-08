package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.xjl.event.AccountRespEvent;
import com.chylee.fxiaoke.xjl.event.BaojiadanReqEvent;
import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.xjl.event.data.object.QuoteLinesObj;
import com.chylee.fxiaoke.xjl.event.data.object.QuoteObj;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.mapper.CoptaMapper;
import com.chylee.fxiaoke.xjl.mapper.CoptbMapper;
import com.chylee.fxiaoke.xjl.model.Copma;
import com.chylee.fxiaoke.xjl.model.Copta;
import com.chylee.fxiaoke.xjl.model.Coptb;
import com.chylee.fxiaoke.xjl.service.ErpAccountService;
import com.chylee.fxiaoke.xjl.service.ErpBaojiadanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ErpBaojiadanServiceImpl implements ErpBaojiadanService {

    private Logger logger = LoggerFactory.getLogger(ErpBaojiadanServiceImpl.class);

    private final CoptaMapper coptaMapper;
    private final CoptbMapper coptbMapper;

    private final ErpAccountService accountService;

    public ErpBaojiadanServiceImpl(CoptaMapper coptaMapper, CoptbMapper coptbMapper, ErpAccountService accountService) {
        this.coptaMapper = coptaMapper;
        this.coptbMapper = coptbMapper;
        this.accountService = accountService;
    }

    @Override
    @Transactional("xjlTransactionManager")
    public AccountRespEvent save(BaojiadanReqEvent reqEvent) {
        AccountObj accountObj = reqEvent.getAccountObj();
        if (accountObj == null)
            return new AccountRespEvent(-1, "客户不允许为空");

        // 写客户信息
        AccountRespEvent accountRespEvent = accountService.save(reqEvent);
        if (!accountRespEvent.isSuccess())
            return accountRespEvent;

        QuoteObj quoteObj = reqEvent.getQuoteObj();
        QuoteLinesObj quoteLinesObj = reqEvent.getQuoteLinesObj();

        Copma copma = accountRespEvent.getCopma();
        String khbh = copma.getMA001().trim();
        if (StringUtils.isEmpty(quoteObj.getAccount_bh()))
            quoteObj.setAccount_bh(khbh);

        //写明细表
        try {
            coptbMapper.insert(toCoptb(quoteObj, quoteLinesObj, copma));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("保存报价单明细失败", e);
            return new AccountRespEvent(-1, "保存报价单明细失败 - " + e.getMessage());
        }

        //写主表
        try {
            coptaMapper.insert(toCopta(quoteObj, quoteLinesObj, copma));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("保存报价单主题失败", e);
            return new AccountRespEvent(-1, "保存报价单主题失败 - " + e.getMessage());
        }

        return accountRespEvent;
    }

    /**
     * 报价单明细
     * @param quoteLinesObj
     * @return
     */
    public Coptb toCoptb(QuoteObj quoteObj, QuoteLinesObj quoteLinesObj, Copma copma) {
        Coptb coptb = new Coptb();

        coptb.setTB001(quoteLinesObj.getBjddb());	//单别
        coptb.setTB002(quoteLinesObj.getBjddh());	//单号
        coptb.setTB003("0001");	//序号
        coptb.setTB004(quoteLinesObj.getField_A5P2v__c());	//品号
        coptb.setTB005(StringUtils.gbkLeft(quoteLinesObj.getField_6XaI6__c(), 60));	//品名
        coptb.setTB006(StringUtils.gbkLeft(quoteLinesObj.getQuote_lines_specs(), 60));	//规格
        coptb.setTB007(quoteLinesObj.getQuantity());	//数量
        coptb.setTB008(quoteLinesObj.getQuote_lines_unit());	//单位
        coptb.setTB009(quoteObj.getQuote_amount().divide(quoteLinesObj.getQuantity()).setScale(2, RoundingMode.HALF_UP));	//单价
        coptb.setTB010(quoteObj.getQuote_amount());	//金额
        coptb.setTB011("Y");	//审核码
        coptb.setTB012(StringUtils.gbkLeft(quoteLinesObj.getField_kUrd0__c(), 255));	//备注
        coptb.setTB013("N");	//分量计价
        coptb.setTB014(null);	//小单位
        coptb.setTB015(null);	//赠品量
        coptb.setTB016(DateUtils.toString(quoteObj.getQuote_time()));	//生效日期
        coptb.setTB017(null);	//失效日期
        coptb.setTB018(null);	//客户品号
        coptb.setTB019(null);	//包装方式
        coptb.setTB020(null);	//毛重(Kg)
        coptb.setTB021(null);	//材积(CUFT)
        coptb.setTB022(null);	//包装数量
        coptb.setTB023(null);	//赠品包装量
        coptb.setTB024(null);	//包装单位
        coptb.setTB025(StringUtils.discountToBigDecimal(quoteLinesObj.getDiscount()));	//折扣率
        
        BigDecimal se = quoteObj.getQuote_amount().multiply(copma.getMA101()).setScale(2, RoundingMode.HALF_UP);
        
        coptb.setTB026(copma.getMA101());	//税率
        coptb.setTB027(quoteObj.getQuote_amount().subtract(se));	//税前金额
        coptb.setTB028(se);	//税额
        coptb.setTB029(null);	//预留字段
        coptb.setTB030(null);	//预留字段
        coptb.setTB031(null);	//预留字段
        coptb.setTB032(null);	//预留字段
        coptb.setTB033(null);	//预留字段
        coptb.setTB034(null);	//预留字段
        coptb.setTB035(null);	//配置方案
        coptb.setTB036(null);	//模拟单位成本
        coptb.setTB037(null);	//模拟成本
        coptb.setTB038(null);	//预估毛利率
        coptb.setTB039(null);	//预估毛利
        coptb.setUDF01(null);	//用户自定义字段1
        coptb.setUDF02(null);	//用户自定义字段2
        coptb.setUDF03(null);	//用户自定义字段3
        coptb.setUDF04(null);	//用户自定义字段4
        coptb.setUDF05(null);	//用户自定义字段5
        coptb.setUDF06(null);	//用户自定义字段6
        coptb.setUDF51(null);	//用户自定义字段7
        coptb.setUDF52(null);	//用户自定义字段8
        coptb.setUDF53(null);	//用户自定义字段9
        coptb.setUDF54(null);	//用户自定义字段10
        coptb.setUDF55(null);	//用户自定义字段11
        coptb.setUDF56(null);	//用户自定义字段12
        coptb.setUDF07(null);	//用户自定义字段13
        coptb.setUDF08(null);	//用户自定义字段14
        coptb.setUDF09(null);	//用户自定义字段15
        coptb.setUDF10(null);	//用户自定义字段16
        coptb.setUDF11(null);	//用户自定义字段17
        coptb.setUDF12(null);	//用户自定义字段18
        coptb.setUDF57(null);	//用户自定义字段19
        coptb.setUDF58(null);	//用户自定义字段20
        coptb.setUDF59(null);	//用户自定义字段21
        coptb.setUDF60(null);	//用户自定义字段22
        coptb.setUDF61(null);	//用户自定义字段23
        coptb.setUDF62(null);	//用户自定义字段24

        return coptb;
    }

    /**
     * 报价单主表
     *
     * @param quoteObj
     * @return
     */
    public Copta toCopta(QuoteObj quoteObj, QuoteLinesObj quoteLinesObj, Copma copma) {
        Copta copta = new Copta();

        copta.setTA001(quoteObj.getField_kq20e__c());   //报价单别
        copta.setTA002(quoteObj.getField_SS32r__c());   //报价单号
        copta.setTA003(DateUtils.toString(quoteObj.getQuote_time()));   //报价日期
        copta.setTA004(quoteObj.getAccount_bh()); //客户
        copta.setTA005(quoteObj.getField_UW798__c());   //业务人员 quoteObj.getField_2bInU__c()
        copta.setTA006(StringUtils.gbkLeft(quoteObj.getAccount_name(),72));   //客户全称
        copta.setTA007(StringUtils.gbkLeft(quoteObj.getField_FrJQF__c(), 4));   //币种
        copta.setTA008(new BigDecimal("1"));   //汇率
        copta.setTA009(quoteObj.getQuote_amount());   //报价金额
        copta.setTA010(null);   //价格说明
        copta.setTA011(null);   //付款条件名称
        copta.setTA012("2");   //计价方式 1.利润率、2.折扣率
        copta.setTA013(DateUtils.toString(quoteObj.getQuote_time()));   //单据日期
        copta.setTA014(null);   //交货日 订货起999日内
        copta.setTA015(null);   //审核者
        copta.setTA016("N");   //客户审核
        copta.setTA017("1");   //打印格式 1.中式、2.英式
        copta.setTA018(new BigDecimal(0));   //打印次数
        copta.setTA019("Y");   //审核码 Y/N/V
        copta.setTA020(StringUtils.gbkLeft(quoteObj.getField_vlgrq__c(), 250));   //备注一 付款方式
        copta.setTA021(quoteObj.getField_w8AF1__c());   //备注二   机台颜色
        copta.setTA022(quoteObj.getField_f81BW__c());   //税种 1.内含、2.外加、3.零税率、4.免税、9.不计税

        BigDecimal se = quoteObj.getQuote_amount().multiply(copma.getMA101()).setScale(2, RoundingMode.HALF_UP);

        copta.setTA023(se);   //税额
        copta.setTA024(quoteLinesObj.getQuantity());   //总数量
        copta.setTA025(copma.getMA101());   //税率
        copta.setTA026(null);   //付款条件编号
        copta.setTA027(null);   //总毛重(Kg)
        copta.setTA028(null);   //总材积(CUFT)
        copta.setTA029("N");   //签核状态码 0.待处理、S.传送中、1.签核中、2.退件、3.已核准、4.撤销审核中、5.作废中、6.取消作废中、N.不运行电子签核[DEF:'N']
        copta.setTA030(null);   //总包装数量
        copta.setTA031(null);   //传送次数 [DEF:0]
        copta.setTA032("1");   //客户性质 1.正式、2.潜在[DEF:"1"]
        copta.setTA033(null);   //潜在客户编号
        copta.setTA034(null);   //EBC汇出码
        copta.setTA035(null);   //预留字段
        copta.setTA036(null);   //预留字段
        copta.setTA037(null);   //预留字段
        copta.setTA038(null);   //预留字段
        copta.setTA039(null);   //预留字段
        copta.setTA040(null);   //EBC报价单号
        copta.setTA041(null);   //EBC报价版本
        copta.setTA042(null);   //来源码 0.ERP 1.EBC [DEF:"0"]
        copta.setTA043(null);   //预留字段
        copta.setUDF01(quoteObj.getField_mIx4B__c());   //油路系统
        copta.setUDF02(quoteObj.getField_j86il__c() + "/" + quoteObj.getField_ambw6__c());   //锁模力及轴式
        copta.setUDF03(quoteObj.getField_X4422__c() + "/" + quoteObj.getField_Np59i__c());   //马力及电压
        copta.setUDF04(quoteObj.getField_4u1m4__c());   //工作台面
        copta.setUDF05(quoteObj.getField_tevVi__c());   //控制系统
        copta.setUDF06(quoteObj.getField_t21Ku__c());   //开模方式
        copta.setUDF07(quoteObj.getField_ttSo3__c() + "/" + quoteObj.getField_04619__c());   //真空泵型号及真空罩框内有效高度
        copta.setUDF08(quoteObj.getField_4M62h__c());   //锁模尺寸
        copta.setUDF09(quoteObj.getField_Jbtgr__c());   //射出量
        copta.setUDF10(StringUtils.isEmpty(quoteObj.getField_ns4nt__c()) ? quoteObj.getXq2() : quoteObj.getField_ns4nt__c());   //机台工具箱/特殊要求二
        copta.setUDF11(StringUtils.gbkLeft(quoteObj.getXq1(), 255));   //特殊要求
        copta.setUDF12(quoteObj.getField_x6Dj4__c());   //主缸行程
        copta.setUDF51(null);
        copta.setUDF52(null);
        copta.setUDF53(null);
        copta.setUDF54(null);
        copta.setUDF55(null);
        copta.setUDF56(null);
        copta.setUDF57(null);
        copta.setUDF58(null);
        copta.setUDF59(null);
        copta.setUDF60(null);
        copta.setUDF61(null);
        copta.setUDF62(null);

        return copta;
    }
}
