package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.core.mapper.ErpCopmaMapper;
import com.chylee.fxiaoke.xjl.event.data.object.AccountAddrObj;
import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.xjl.event.data.object.ContactObj;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.event.AccountReqEvent;
import com.chylee.fxiaoke.xjl.event.AccountRespEvent;
import com.chylee.fxiaoke.xjl.mapper.CopmaMapper;
import com.chylee.fxiaoke.xjl.model.Copma;
import com.chylee.fxiaoke.xjl.service.ErpAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ErpAccountServiceImpl implements ErpAccountService {
    private Logger logger = LoggerFactory.getLogger(ErpAccountServiceImpl.class);

    private final CopmaMapper copmaMapper;
    private final ErpCopmaMapper erpCopmaMapper;

    public ErpAccountServiceImpl(CopmaMapper copmaMapper, ErpCopmaMapper erpCopmaMapper) {
        this.copmaMapper = copmaMapper;
        this.erpCopmaMapper = erpCopmaMapper;
    }

    @Override
    @Transactional("xjlTransactionManager")
    public AccountRespEvent save(AccountReqEvent reqEvent) {
        AccountObj accountObj = reqEvent.getAccountObj();

        if (accountObj == null)
            return new AccountRespEvent(-1, "保存客户信息失败：客户不允许为空");

        //客户编码为空
        if (StringUtils.isEmpty(accountObj.getField_AlGoN__c()))
            return addKehu(reqEvent);
        else
            return updateKehu(reqEvent);
    }

    private AccountRespEvent updateKehu(AccountReqEvent reqEvent) {
        AccountObj accountObj = reqEvent.getAccountObj();
        String khbh = accountObj.getField_AlGoN__c();
        Copma copmaYf = copmaMapper.loadByKhbh(khbh);
        if (copmaYf == null)
            return new AccountRespEvent(-1, "在易飞找不到客户编码为["+khbh+"]的客户");
        /*
        Copma copmaCrm = toCopma(accountObj, reqEvent.getContactObj(), reqEvent.getAccountAddrObjs());
        if (!copmaYf.equals(copmaCrm)) {
            if (logger.isDebugEnabled()) {
                logger.debug("[hasCode-{}-{}]", copmaYf.hashCode(), copmaCrm.hashCode());
                logger.debug("**********" + copmaYf);
                logger.debug("**********" + copmaCrm);
            }
            //备份易飞的客户信息
            erpCopmaMapper.insert(copmaYf);

            Copma copmaToUpdate = new Copma();
            copmaToUpdate.setMA001(khbh);

            String[] fields = new String[]{
                    "MA002", "MA003", "MA005", "MA006", "MA007", "MA008", "MA016", "MA023", "MA027", "MA085"};

            Class clazz = Copma.class;
            try {
                for (String field : fields) {
                    Method set = clazz.getDeclaredMethod("set" + field, new Class[]{String.class});
                    Method get = clazz.getDeclaredMethod("get" + field);
                    Object valueCrm = get.invoke(copmaCrm);
                    Object valueYf = get.invoke(copmaYf);
                    if (!Objects.equals(valueYf, valueCrm)) {
                        if (logger.isDebugEnabled())
                            logger.debug("{}-原值[{}]-新值[{}]", field, valueYf, valueCrm);
                        Object[] objects = new Object[] { valueCrm == null ? "NULL" : valueCrm };
                        set.invoke(copmaToUpdate, objects);
                        set.invoke(copmaYf, objects);
                    }
                }

                if (logger.isDebugEnabled())
                    logger.debug("更新客户资料：" + copmaToUpdate.toString());

                copmaMapper.updateByKhbh(copmaToUpdate);
            } catch(Exception e) {
                logger.error("更新易飞客户资料时发生错误", e);
                return new AccountRespEvent(-1, "更新易飞客户资料时发生错误");
            }
        }
        else if (logger.isDebugEnabled())
            logger.debug("易飞与CRM的客户资料一致，无需更新");
        */

        return new AccountRespEvent(copmaYf, false);
    }

    private AccountRespEvent addKehu(AccountReqEvent reqEvent) {
        AccountObj accountObj = reqEvent.getAccountObj();

        if(accountObj.getField_PlekP__c() == null || accountObj.getField_PlekP__c().length() != 2)
            return new AccountRespEvent(-1, "客户区域码格式错误[" + accountObj.getField_PlekP__c() + "]");

        if(accountObj.getField_p3Bv1__c() == null || accountObj.getField_p3Bv1__c().length() != 1)
            return new AccountRespEvent(-1, "客户名称首字母格式错误[" + accountObj.getField_p3Bv1__c() + "]");

        String khbh = getKhbh(accountObj.getField_PlekP__c(), accountObj.getField_p3Bv1__c());
        accountObj.setField_AlGoN__c(khbh);
        Copma copma = toCopma(accountObj, reqEvent.getContactObj(), reqEvent.getAccountAddrObjs());
        try {
            copmaMapper.insert(copma);
            return new AccountRespEvent(copma, true);
        }
        catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("保存客户信息失败：{}", e.getMessage());
            return new AccountRespEvent(-1, e.getMessage());
        }
    }

    private String getKhbh(String dq, String zm) {
        String khbm = copmaMapper.khbm(zm);
        String xh;
        if(khbm == null)
            xh = "001";
        else {
            xh = String.format("%0"+3+"d", Integer.parseInt(khbm.trim()) + 1);
        }
        return dq + zm + xh;
    }

    /**
     * 转换为易飞对象
     *
     * @param obj CRM客户对象
     * @param contactObj 联系人
     * @param accountAddrObjs 联系地址
     * @return 易飞客户对象
     */
    public Copma toCopma(AccountObj obj, ContactObj contactObj, List<AccountAddrObj> accountAddrObjs) {
        Copma copma = new Copma();

        copma.setUSR_GROUP("XJL02");

        copma.setMA001(obj.getField_AlGoN__c());	//客户编号
        copma.setMA002(StringUtils.gbkLeft(obj.getField_e4t2b__c(), 20));	//客户简称
        copma.setMA003(StringUtils.gbkLeft(obj.getName(), 72));	//客户全称
        copma.setMA004(null);	//负责人
        if (contactObj != null) {
            copma.setMA005(StringUtils.gbkLeft(contactObj.getName(), 30));    //联系人
            copma.setMA006(StringUtils.gbkLeft(contactObj.getMobile1(), 20));    //TEL_NO(一)
            copma.setMA007(StringUtils.gbkLeft(contactObj.getTel1(), 20));    //TEL_NO(二)
            copma.setMA008(StringUtils.gbkLeft(contactObj.getTel2(), 20));    //FaX_NO
        }
        copma.setMA009(null);	//E-Mail
        copma.setMA010(StringUtils.gbkLeft(obj.getField_20jT0__c(), 20));	//税号
        copma.setMA011(null);	//注册资金 obj.getField_1Juaa__c() 字符串转换错误
        copma.setMA012(null);	//年营业额
        copma.setMA013(null);	//员工人数
        copma.setMA014("CNY");	//交易币种
        copma.setMA015(null);	//部门
        copma.setMA016(StringUtils.gbkLeft(obj.getYwry(),10));	//业务人员
        copma.setMA017(null);	//渠道
        copma.setMA018(null);	//地区
        copma.setMA019(null);	//国家
        copma.setMA020(DateUtils.toString(obj.getField_2740P__c()));	//开业日期
        copma.setMA021(null);	//初次交易日
        copma.setMA022(null);	//最近交易日

        if (accountAddrObjs != null && !accountAddrObjs.isEmpty()) {
            for (AccountAddrObj accountAddrObj : accountAddrObjs) {
                if (accountAddrObj.isIs_default_add())
                    copma.setMA023(StringUtils.gbkLeft(accountAddrObj.getAddress(), 72));	//注册地址(一)

                if (accountAddrObj.isIs_ship_to_add())
                    copma.setMA027(StringUtils.gbkLeft(accountAddrObj.getAddress(), 72));	//送货地址(一)
            }

            if (StringUtils.trim(copma.getMA023(), false) == null)
                copma.setMA023(StringUtils.gbkLeft(accountAddrObjs.get(0).getAddress(), 72));

            if (StringUtils.trim(copma.getMA027(), false) == null)
                copma.setMA027(StringUtils.gbkLeft(accountAddrObjs.get(0).getAddress(), 72));
        }

        copma.setMA024(null);	//注册地址(二)
        copma.setMA025(null);	//发票地址(一)
        copma.setMA026(null);	//发票地址(二)
        copma.setMA028(null);	//销售评级
        copma.setMA029(null);	//信用评级
        copma.setMA030(null);	//价格说明
        copma.setMA031(null);	//付款条件
        copma.setMA032("N");	//信用额度控制
        copma.setMA033(null);	//信用额度
        copma.setMA034(null);	//可超出率%
        copma.setMA035("2");	//计价方式
        copma.setMA036(null);	//折扣率
        copma.setMA037("A");	//发票种类
        copma.setMA038("1");	//税种
        copma.setMA039("1");	//单据发送方式
        copma.setMA040(null);	//邮编
        copma.setMA041("1");	//结算方式
        copma.setMA042("1");	//票据寄领
        copma.setMA043(null);	//开票日期
        copma.setMA044(null);	//预计收款日
        copma.setMA045(null);	//资金实现日
        copma.setMA046(null);	//付款银行(一)
        copma.setMA047(null);	//账款科目
        copma.setMA048("1");	//运输方式
        copma.setMA049(null);	//备注
        copma.setMA050(null);	//品牌
        copma.setMA051(null);	//目的地
        copma.setMA052(null);	//海运港口
        copma.setMA053(null);	//空运机场
        copma.setMA054(null);	//海运公司
        copma.setMA055(null);	//空运公司
        copma.setMA056(null);	//代理商
        copma.setMA057(null);	//报关行
        copma.setMA058(null);	//验货公司
        copma.setMA059(null);	//佣金比率
        copma.setMA060(null);	//其它比率
        copma.setMA061(null);	//保险费率
        copma.setMA062(null);	//文件地址(一)
        copma.setMA063(null);	//文件地址(二)
        copma.setMA064(null);	//送货地址(二)
        copma.setMA065(null);	//总公司
        copma.setMA066("N");	//总公司付款
        copma.setMA067(null);	//分店数
        copma.setMA068(null);	//停业日期
        copma.setMA069(null);	//付款银行(二)
        copma.setMA070(null);	//付款银行(三)
        copma.setMA071(null);	//银行账号(一)
        copma.setMA072(null);	//银行账号(二)
        copma.setMA073(null);	//银行账号(三)
        copma.setMA074(null);	//票据科目
        copma.setMA075("142");	//定价顺序
        copma.setMA076(null);	//类型
        copma.setMA077(null);	//路线
        copma.setMA078(null);	//其他
        copma.setMA079(null);	//邮编
        copma.setMA080(null);	//邮编
        copma.setMA081(null);	//邮编
        copma.setMA082(null);	//信用额度由总公司控制
        copma.setMA083(null);	//付款条件编号
        copma.setMA084("N");	//发票号码由总公司控制
        copma.setMA085(StringUtils.gbkLeft(obj.getYwry(),10));	//收款业务员
        copma.setMA086(null);	//预留字段
        copma.setMA087("2");	//税额
        copma.setMA088(null);	//订单信用检查方式
        copma.setMA089(null);	//销货信用检查方式
        copma.setMA090(new BigDecimal(1));	//LC收状金额比率
        copma.setMA091(new BigDecimal(1));	//未兑现应收票据比率
        copma.setMA092(new BigDecimal(1));	//应收账款比率
        copma.setMA093(new BigDecimal(1));	//未开票销货金额比率
        copma.setMA094(new BigDecimal(1));	//未出货订单金额比率
        copma.setMA095(null);	//订金比率
        copma.setMA096(null);	//开票方法
        copma.setMA097(null);	//核准状况
        copma.setMA098(null);	//营业执照
        copma.setMA099(null);	//生产/经营许可证
        copma.setMA100(null);	//GMP/GSP认证
        copma.setMA101(new BigDecimal("0.13"));	//税率
        copma.setMA102(null);	//快捷码
        copma.setMA103(null);	//出货通知信用检查方式
        copma.setMA104(null);	//未销货出货通知金额比率
        copma.setMA105(null);	//预收账款科目
        copma.setMA106(null);	//汇至EBC
        copma.setMA107(null);	//预留字段
        copma.setMA108(null);	//预留字段
        copma.setMA109(null);	//预留字段
        copma.setMA110(null);	//预留字段
        copma.setMA111(null);	//预留字段
        copma.setMA112(null);	//EBC申请编号
        copma.setMA113(null);	//EBC汇出码
        copma.setMA114(null);	//手机
        copma.setMAB01(null);	//寄售客户
        copma.setMA115(null);	//分期收款条件编号
        copma.setUDF01(null);	//用户自定义字段1
        copma.setUDF02(null);	//用户自定义字段2
        copma.setUDF03(null);	//用户自定义字段3
        copma.setUDF04(null);	//用户自定义字段4
        copma.setUDF05(null);	//用户自定义字段5
        copma.setUDF06(null);	//用户自定义字段6
        copma.setUDF51(null);	//用户自定义字段7
        copma.setUDF52(null);	//用户自定义字段8
        copma.setUDF53(null);	//用户自定义字段9
        copma.setUDF54(null);	//用户自定义字段10
        copma.setUDF55(null);	//用户自定义字段11
        copma.setUDF56(null);	//用户自定义字段12
        copma.setUDF07(null);	//用户自定义字段13
        copma.setUDF08(null);	//用户自定义字段14
        copma.setUDF09(null);	//用户自定义字段15
        copma.setUDF10(null);	//用户自定义字段16
        copma.setUDF11(null);	//用户自定义字段17
        copma.setUDF12(null);	//用户自定义字段18
        copma.setUDF57(null);	//用户自定义字段19
        copma.setUDF58(null);	//用户自定义字段20
        copma.setUDF59(null);	//用户自定义字段21
        copma.setUDF60(null);	//用户自定义字段22
        copma.setUDF61(null);	//用户自定义字段23
        copma.setUDF62(null);	//用户自定义字段24

        return copma;
    }

}
