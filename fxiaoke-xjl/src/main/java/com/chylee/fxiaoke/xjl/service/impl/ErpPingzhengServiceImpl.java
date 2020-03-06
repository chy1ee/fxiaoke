package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.event.data.object.Object_79pYP__c;
import com.chylee.fxiaoke.xjl.event.data.object.Object_okom1__c;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.xjl.event.PingzhengReqEvent;
import com.chylee.fxiaoke.xjl.mapper.ActtaMapper;
import com.chylee.fxiaoke.xjl.mapper.ActtbMapper;
import com.chylee.fxiaoke.xjl.model.Actta;
import com.chylee.fxiaoke.xjl.model.Acttb;
import com.chylee.fxiaoke.xjl.service.ErpPingzhengService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ErpPingzhengServiceImpl implements ErpPingzhengService {

    private final ActtaMapper acttaMapper;

    private final ActtbMapper acttbMapper;

    public ErpPingzhengServiceImpl(ActtaMapper acttaMapper, ActtbMapper acttbMapper) {
        this.acttaMapper = acttaMapper;
        this.acttbMapper = acttbMapper;
    }

    @Override
    public ResponseEvent save(PingzhengReqEvent reqEvent) {
        Object_okom1__c pz = reqEvent.getPz();
        List<Object_79pYP__c> pzmxs = reqEvent.getPzmx();

        acttaMapper.insert(toActta(pz));
        for(int i=0,j=pzmxs.size();i<j;i++) {
            Object_79pYP__c pzmx = pzmxs.get(i);
            acttbMapper.insert(toActtb(pzmx, pz, String.format("%04d", (i+1)*10)));
        }

        return new ResponseEvent();
    }

    private Acttb toActtb(Object_79pYP__c pzmx, Object_okom1__c pz, String xh) {
        Acttb acttb = new Acttb();

        acttb.setTB001(pz.getField_EHyt1__c());	//凭证单别
        acttb.setTB002(pz.getField_8GijD__c());	//凭证单号
        acttb.setTB003(xh);	//序号
        acttb.setTB004(new BigDecimal(1));	//借贷类型
        acttb.setTB005(pzmx.getField_h5b4L__c());	//科目编号
        acttb.setTB006(null);	//部门编号
        acttb.setTB007(pzmx.getField_dmn2M__c());	//本币金额
        acttb.setTB008(null);	//核算项目(一)
        acttb.setTB009(null);	//核算项目(二)
        acttb.setTB010(pzmx.getField_Hi19I__c());	//摘要
        acttb.setTB011(null);	//项目编号
        acttb.setTB012(null);	//备注
        acttb.setTB013("CNY");	//币种
        acttb.setTB014(new BigDecimal(1));	//汇率
        acttb.setTB015(pzmx.getField_dmn2M__c());	//原币金额
        acttb.setTB016("Y");	//审核码
        acttb.setTB017(null);	//核算项目一名称
        acttb.setTB018(null);	//核算项目二名称
        acttb.setTB019(null);	//数量
        acttb.setTB020(null);	//单价
        acttb.setTB021(null);	//现金流量表项目
        acttb.setTB022(null);	//结算方式
        acttb.setTB023(null);	//结算号
        acttb.setTB024(null);	//结算日期
        acttb.setTB025(null);	//保留字段
        acttb.setTB026(null);	//保留字段
        acttb.setTB027(null);	//保留字段
        acttb.setTB028(null);	//保留字段
        acttb.setTB029(null);	//保留字段
        acttb.setTB030(null);	//保留字段
        acttb.setTB031(null);	//客户
        acttb.setTB032(null);	//供应商
        acttb.setTB033(pz.getField_N7e3j__c());	//人员
        acttb.setUDF01(null);	//用户自定义字段1
        acttb.setUDF02(null);	//用户自定义字段2
        acttb.setUDF03(null);	//用户自定义字段3
        acttb.setUDF04(null);	//用户自定义字段4
        acttb.setUDF05(null);	//用户自定义字段5
        acttb.setUDF06(null);	//用户自定义字段6
        acttb.setUDF51(null);	//用户自定义字段7
        acttb.setUDF52(null);	//用户自定义字段8
        acttb.setUDF53(null);	//用户自定义字段9
        acttb.setUDF54(null);	//用户自定义字段10
        acttb.setUDF55(null);	//用户自定义字段11
        acttb.setUDF56(null);	//用户自定义字段12
        acttb.setUDF07(null);	//用户自定义字段13
        acttb.setUDF08(null);	//用户自定义字段14
        acttb.setUDF09(null);	//用户自定义字段15
        acttb.setUDF10(null);	//用户自定义字段16
        acttb.setUDF11(null);	//用户自定义字段17
        acttb.setUDF12(null);	//用户自定义字段18
        acttb.setUDF57(null);	//用户自定义字段19
        acttb.setUDF58(null);	//用户自定义字段20
        acttb.setUDF59(null);	//用户自定义字段21
        acttb.setUDF60(null);	//用户自定义字段22
        acttb.setUDF61(null);	//用户自定义字段23
        acttb.setUDF62(null);	//用户自定义字段24


        return acttb;
    }

    private Actta toActta(Object_okom1__c pz) {
        String toDay = DateUtils.toString(new Date());
        Actta actta = new Actta();

        actta.setTA001(pz.getField_EHyt1__c());	//凭证单别
        actta.setTA002(pz.getField_8GijD__c());	//凭证单号
        actta.setTA003(toDay);	//凭证日期
        actta.setTA004(null);	//收支科目
        actta.setTA005(null);	//总号
        actta.setTA006("1");	//来源码
        actta.setTA007(pz.getField_fpB2k__c());	//本币借方总金额
        actta.setTA008(pz.getField_fpB2k__c());	//本币贷方总金额
        actta.setTA009(null);	//备注
        actta.setTA010("N");	//审核码
        actta.setTA011(null);	//过账码
        actta.setTA012(null);	//打印次数
        actta.setTA013(null);	//复制分类
        actta.setTA014(toDay);	//审核日
        actta.setTA015(null);	//审核者
        actta.setTA016(null);	//签核状态码
        actta.setTA017(null);	//附件数
        actta.setTA018(null);	//现金流量对象
        actta.setTA019(null);	//过账日
        actta.setTA020(null);	//过账
        actta.setTA021(null);	//出纳码
        actta.setTA022(null);	//出纳日
        actta.setTA023(null);	//出纳
        actta.setTA024(null);	//传送次数
        actta.setTA025(null);	//币种
        actta.setTA026(null);	//保留字段
        actta.setTA027(null);	//保留字段
        actta.setTA028(null);	//保留字段
        actta.setTA029(null);	//保留字段
        actta.setTA030(null);	//保留字段
        actta.setTA031(null);	//保留字段
        actta.setTA032(null);	//结转码
        actta.setUDF01(null);	//用户自定义字段1
        actta.setUDF02(null);	//用户自定义字段2
        actta.setUDF03(null);	//用户自定义字段3
        actta.setUDF04(null);	//用户自定义字段4
        actta.setUDF05(null);	//用户自定义字段5
        actta.setUDF06(null);	//用户自定义字段6
        actta.setUDF51(null);	//用户自定义字段7
        actta.setUDF52(null);	//用户自定义字段8
        actta.setUDF53(null);	//用户自定义字段9
        actta.setUDF54(null);	//用户自定义字段10
        actta.setUDF55(null);	//用户自定义字段11
        actta.setUDF56(null);	//用户自定义字段12
        actta.setUDF07(null);	//用户自定义字段13
        actta.setUDF08(null);	//用户自定义字段14
        actta.setUDF09(null);	//用户自定义字段15
        actta.setUDF10(null);	//用户自定义字段16
        actta.setUDF11(null);	//用户自定义字段17
        actta.setUDF12(null);	//用户自定义字段18
        actta.setUDF57(null);	//用户自定义字段19
        actta.setUDF58(null);	//用户自定义字段20
        actta.setUDF59(null);	//用户自定义字段21
        actta.setUDF60(null);	//用户自定义字段22
        actta.setUDF61(null);	//用户自定义字段23
        actta.setUDF62(null);	//用户自定义字段24


        return actta;
    }

}
