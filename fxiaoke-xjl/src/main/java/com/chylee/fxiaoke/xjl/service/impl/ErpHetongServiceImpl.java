package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.xjl.event.data.object.Object_47F7O__c;
import com.chylee.fxiaoke.xjl.event.data.object.Object_snPZx__c;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.event.AccountRespEvent;
import com.chylee.fxiaoke.xjl.event.HetongReqEvent;
import com.chylee.fxiaoke.xjl.mapper.CopaaMapper;
import com.chylee.fxiaoke.xjl.mapper.CopabMapper;
import com.chylee.fxiaoke.xjl.mapper.CopmaMapper;
import com.chylee.fxiaoke.xjl.model.Copaa;
import com.chylee.fxiaoke.xjl.model.Copab;
import com.chylee.fxiaoke.xjl.model.Copma;
import com.chylee.fxiaoke.xjl.service.ErpAccountService;
import com.chylee.fxiaoke.xjl.service.ErpHetongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ErpHetongServiceImpl implements ErpHetongService {
    private Logger logger = LoggerFactory.getLogger(ErpHetongServiceImpl.class);

    private final CopaaMapper copaaMapper;
    private final CopabMapper copabMapper;
    private final CopmaMapper copmaMapper;
    private final ErpAccountService accountService;

    public ErpHetongServiceImpl(CopaaMapper copaaMapper, CopabMapper copabMapper, CopmaMapper copmaMapper,
                                ErpAccountService accountService) {
        this.copaaMapper = copaaMapper;
        this.copabMapper = copabMapper;
        this.copmaMapper = copmaMapper;
        this.accountService = accountService;
    }

    @Override
    @Transactional("xjlTransactionManager")
    public AccountRespEvent save(HetongReqEvent reqEvent) {
        AccountObj accountObj = reqEvent.getAccountObj();
        if (accountObj == null)
            return new AccountRespEvent(-1, "客户不允许为空");

        // 写客户信息
        AccountRespEvent accountRespEvent = accountService.save(reqEvent);
        if (!accountRespEvent.isSuccess())
            return accountRespEvent;

        Object_snPZx__c ht = reqEvent.getHt();
        Object_47F7O__c htmx = reqEvent.getHtmx();

        Copma copma = accountRespEvent.getCopma();
        String khbh = copma.getMA001();
        if (StringUtils.isEmpty(ht.getAA010()))
            ht.setAA010(khbh);

        BigDecimal sl = copma.getMA101();
        if (sl == null)
            return new AccountRespEvent(-1, "获取税率失败 - " + ht.getAA010());

        try {
            copabMapper.insert(toCopab(htmx, ht, sl));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("保存合同明细失败", e);
            return new AccountRespEvent(-1, "保存合同明细失败 - " + e.getMessage());
        }

        try {
            copaaMapper.insert(toCopaa(htmx, ht, sl));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("保存合同主题失败", e);
            return new AccountRespEvent(-1, "保存合同主题失败 - " + e.getMessage());
        }

        return accountRespEvent;
    }

    private Copab toCopab(Object_47F7O__c htmx, Object_snPZx__c ht, BigDecimal sl) {
        Copab copab = new Copab();

        copab.setAB001(htmx.getAB001());	//合同单别
        copab.setAB002(htmx.getAB002());	//合同单号
        copab.setAB003("0001");	//序号
        copab.setAB004(htmx.getAB004());	//品号
        copab.setAB005(StringUtils.gbkLeft(htmx.getAB005(), 60));	//品名
        copab.setAB006(StringUtils.gbkLeft(htmx.getAB006(), 60));	//规格
        copab.setAB007(htmx.getAB007());	//单位
        copab.setAB008(null);	//小单位
        copab.setAB009(null);	//包装单位
        copab.setAB010(ht.getField_ed5Q2__c().divide(htmx.getField_lX0C3__c(), 2, BigDecimal.ROUND_HALF_UP));	//单价
        copab.setAB011(sl);	//税率
        copab.setAB012(htmx.getField_lX0C3__c());	//合同数量 htmx.getField_lX0C3__c()
        copab.setAB013(new BigDecimal(0));	//合同包装数量

        BigDecimal se = ht.getField_ed5Q2__c().multiply(sl).setScale(2, RoundingMode.HALF_UP);
        BigDecimal hj = ht.getField_ed5Q2__c().subtract(se);
        BigDecimal hl = new BigDecimal(1);

        copab.setAB014(hj);	//合同税前金额
        copab.setAB015(se);	//合同税额
        copab.setAB016(ht.getField_ed5Q2__c());	//合同金额
        copab.setAB017(new BigDecimal(0));	//订单数量
        copab.setAB018(new BigDecimal(0));	//订单包装数量
        copab.setAB019(new BigDecimal(0));	//订单税前金额
        copab.setAB020(new BigDecimal(0));	//订单税额
        copab.setAB021(new BigDecimal(0));	//订单金额
        copab.setAB022(new BigDecimal(0));	//数量/金额上限率
        copab.setAB023("N");	//结束
        copab.setAB024(StringUtils.gbkLeft(htmx.getField_mw1LQ__c(), 250));	//备注
        copab.setAB025("Y");	//审核码
        copab.setAB026(null);	//预留字段
        copab.setAB027(null);	//预留字段
        copab.setAB028(null);	//预留字段
        copab.setAB029(null);	//预留字段
        copab.setAB030(null);	//预留字段
        copab.setAB031(null);	//预留字段
        copab.setABI01(hj.multiply(hl).setScale(2, RoundingMode.HALF_UP));	//合同本币税前金额
        copab.setABI02(se.multiply(hl).setScale(2, RoundingMode.HALF_UP));	//合同本币税额
        copab.setABI03(ht.getField_ed5Q2__c().multiply(hl).setScale(2, RoundingMode.HALF_UP));	//合同本币金额
        copab.setABI04(null);	//项目编号
        copab.setUDF01(htmx.getField_BzJ2l__c());	//用户自定义字段1
        copab.setUDF02(null);	//用户自定义字段2
        copab.setUDF03(null);	//用户自定义字段3
        copab.setUDF04(null);	//用户自定义字段4
        copab.setUDF05(null);	//用户自定义字段5
        copab.setUDF06(null);	//用户自定义字段6
        copab.setUDF51(null);	//用户自定义字段7
        copab.setUDF52(null);	//用户自定义字段8
        copab.setUDF53(null);	//用户自定义字段9
        copab.setUDF54(null);	//用户自定义字段10
        copab.setUDF55(null);	//用户自定义字段11
        copab.setUDF56(null);	//用户自定义字段12
        copab.setUDF07(null);	//用户自定义字段13
        copab.setUDF08(null);	//用户自定义字段14
        copab.setUDF09(null);	//用户自定义字段15
        copab.setUDF10(null);	//用户自定义字段16
        copab.setUDF11(null);	//用户自定义字段17
        copab.setUDF12(null);	//用户自定义字段18
        copab.setUDF57(null);	//用户自定义字段19
        copab.setUDF58(null);	//用户自定义字段20
        copab.setUDF59(null);	//用户自定义字段21
        copab.setUDF60(null);	//用户自定义字段22
        copab.setUDF61(null);	//用户自定义字段23
        copab.setUDF62(null);	//用户自定义字段24

        return copab;
    }

    private Copaa toCopaa(Object_47F7O__c htmx, Object_snPZx__c ht, BigDecimal sl) {
        Copaa copaa= new Copaa();

        copaa.setAA001(ht.getField_d91gZ__c());	// 合同单别
        copaa.setAA002(ht.getField_WCjgL__c());	// 合同单号
        copaa.setAA003(ht.getField_d91gZ__c() + "-" + ht.getField_WCjgL__c());	// 合同编号
        copaa.setAA004(StringUtils.gbkLeft(ht.getName(), 59));	// 合同名称
        String sxrq = DateUtils.toString(ht.getField_jQWIc__c());
        copaa.setAA005(sxrq);	// 生效日期
        copaa.setAA006(null);	// 失效日期
        copaa.setAA007(sxrq);	// 单据日期
        copaa.setAA008(null);	// 主合同编号
        copaa.setAA009(StringUtils.gbkLeft(ht.getAA009(), 10));	// 对方负责人        *
        copaa.setAA010(StringUtils.gbkLeft(ht.getAA010(), 10));	// 对方单位          *
        copaa.setAA011(null);	// 品号类别
        copaa.setAA012(DateUtils.toString(ht.getField_jQWIc__c()));	// 签订日期
        copaa.setAA013(StringUtils.gbkLeft(ht.getAA013(), 10));	// 审核者
        copaa.setAA014("Y");	// 审核码
        copaa.setAA015(null);	// 部门编号
        copaa.setAA016(ht.getAA016());	// 业务人员
        copaa.setAA017("XJL00" + ht.getField_d91gZ__c().substring(ht.getField_d91gZ__c().length() - 1));	// 出货工厂
        copaa.setAA018(StringUtils.gbkLeft(ht.getField_Q977o__c(), 4));	    // 交易币种
        copaa.setAA019(StringUtils.gbkLeft(ht.getField_CrzUi__c(), 254));	// 合同描述
        copaa.setAA020(ht.getField_meIg7__c());	// 价格说明
        copaa.setAA021(null);	// 付款条件编号
        copaa.setAA022("1");	// 税种
        copaa.setAA023(null);	// 传送次数
        copaa.setAA024(null);	// 打印次数
        copaa.setAA025("N");	// 签核状态码
        copaa.setAA026(StringUtils.gbkLeft(ht.getField_Z40lu__c(), 71));	// 送货地址(一)
        copaa.setAA027(null);	// 送货地址(二)
        copaa.setAA028(ht.getField_F3087__c());	// 送货备注
        copaa.setAA029(ht.getField_6b385__c());	// 质量与检验一
        copaa.setAA030(ht.getField_16BLo__c());	// 质量与检验二
        copaa.setAA031(ht.getField_KnzZP__c());	// 质量与检验三
        copaa.setAA032(null);	// 质量与检验四
        copaa.setAA033(StringUtils.gbkLeft(ht.getField_P41zT__c(), 99));	// 其他备注一 交货期 长度100
        copaa.setAA034(StringUtils.gbkLeft(ht.getField_9Mc92__c(), 99));	// 其他备注二 运输方式 长度100
        copaa.setAA035(ht.getAA009() + " - " + ht.getField_SQZNE__c());	// 其他备注三
        copaa.setAA036(null);	// 其他备注四

        BigDecimal se = ht.getField_ed5Q2__c().multiply(sl).setScale(2, RoundingMode.HALF_UP);
        BigDecimal hj = ht.getField_ed5Q2__c().subtract(se);
        BigDecimal hl = new BigDecimal(1);

        copaa.setAA037(hj);	// 合同税前金额合计
        copaa.setAA038(se);	// 合同税额合计
        copaa.setAA039(ht.getField_ed5Q2__c());	// 合同金额合计
        copaa.setAA040(new BigDecimal(0));	// 订单税前金额合计
        copaa.setAA041(new BigDecimal(0));	// 订单税额合计
        copaa.setAA042(new BigDecimal(0));	// 订单金额合计
        copaa.setAA043(ht.getField_ed5Q2__c());	// 合同与订单金额合计余额
        copaa.setAA044(htmx.getField_lX0C3__c());	// 合同数量合计
        copaa.setAA045(new BigDecimal(0));	// 订单数量合计
        copaa.setAA046(htmx.getField_lX0C3__c());	// 合同与订单数量合计余额
        copaa.setAA047(null);	// 合同包装数量合计
        copaa.setAA048(null);	// 订单包装数量合计
        copaa.setAA049(null);	// 合同与订单包装数量合计余额
        copaa.setAA050(sxrq);	// 合同单据审核日期
        copaa.setAA051(null);	// 预留字段
        copaa.setAA052(null);	// 预留字段
        copaa.setAA053(null);	// 预留字段
        copaa.setAA054(null);	// 预留字段
        copaa.setAA055(null);	// 预留字段
        copaa.setAA056(null);	// 预留字段
        copaa.setAAI01(null);	// 交货日期
        copaa.setAAI02(null);	// 安装调试完成日期
        copaa.setAAI03(hl);	// 汇率
        copaa.setAAI04(hj.multiply(hl).setScale(2, RoundingMode.HALF_UP));	// 合同本币税前金额合计
        copaa.setAAI05(se.multiply(hl).setScale(2, RoundingMode.HALF_UP));	// 合同本币税额合计
        copaa.setAAI06(ht.getField_ed5Q2__c().multiply(hl).setScale(2, RoundingMode.HALF_UP));	// 合同本币金额合计
        copaa.setAAI07(null);	// 项目编号
        copaa.setUDF01(StringUtils.gbkLeft(ht.getUDF001(), 249));	// 用户自定义字段1     特殊需求
        copaa.setUDF02(StringUtils.gbkLeft(ht.getField_j5E4T__c(), 249));	// 用户自定义字段2     锁模力
        copaa.setUDF03(StringUtils.gbkLeft(ht.getField_4r4Q0__c(), 249));	// 用户自定义字段3     马力
        copaa.setUDF04(StringUtils.gbkLeft(ht.getField_6y9GX__c(), 249));	// 用户自定义字段4     轴式
        copaa.setUDF05(StringUtils.gbkLeft(ht.getField_Cv6Ac__c(), 249));	// 用户自定义字段5     电压
        copaa.setUDF06(StringUtils.gbkLeft(ht.getField_1tm9L__c(), 249));	// 用户自定义字段6     射出量
        copaa.setUDF51(null);	// 用户自定义字段7
        copaa.setUDF52(null);	// 用户自定义字段8
        copaa.setUDF53(null);	// 用户自定义字段9
        copaa.setUDF54(null);	// 用户自定义字段10
        copaa.setUDF55(null);	// 用户自定义字段11
        copaa.setUDF56(null);	// 用户自定义字段12
        copaa.setUDF07(StringUtils.gbkLeft(ht.getField_w61hH__c(), 249));	// 用户自定义字段13 主缸行程
        copaa.setUDF08(StringUtils.gbkLeft(ht.getField_5gCr5__c(), 249));	// 用户自定义字段14    //控制系统
        copaa.setUDF09(StringUtils.gbkLeft(ht.getField_u0N72__c(), 249));	// 用户自定义字段15  真空罩内腔有效高度
        copaa.setUDF10(StringUtils.gbkLeft(ht.getField_033ht__c(), 249));	// 用户自定义字段16    //油路系统
        copaa.setUDF11(StringUtils.gbkLeft(ht.getField_lK4ft__c(), 249));	// 用户自定义字段17  真空泵型号
        copaa.setUDF12(StringUtils.gbkLeft(ht.getField_dd5Y0__c(), 249));	// 用户自定义字段18  开模方式
        copaa.setUDF57(null);	// 用户自定义字段19
        copaa.setUDF58(null);	// 用户自定义字段20F
        copaa.setUDF59(null);	// 用户自定义字段21
        copaa.setUDF60(null);	// 用户自定义字段22
        copaa.setUDF61(null);	// 用户自定义字段23
        copaa.setUDF62(null);	// 用户自定义字段24

        return copaa;
    }
}
