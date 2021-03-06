package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.jobs.JobContext;
import com.chylee.fxiaoke.xjl.event.data.object.DeviceObj;
import com.chylee.fxiaoke.xjl.event.data.object.Object_47F7O__c;
import com.chylee.fxiaoke.xjl.event.data.object.Object_snPZx__c;
import com.chylee.fxiaoke.xjl.event.data.object.ProductObj;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.ShebeiRespEvent;
import com.chylee.fxiaoke.common.jobs.JobContextHolder;
import com.chylee.fxiaoke.xjl.service.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.ws.Response;
import java.util.List;

@Component
public class ShebeiJobDetailExecutor extends AbstractXjlJobDetailExecutor {
    private final ErpShebeiService shebeiService;
    private final FxkProductObjService productObjService;
    private final FxkPersonnelObjService personnelObjService;
    private final FxkHetongService hetongService;
    private final FxkHetongMxService hetongMxService;
    private final FxkDeviceObjService deviceObjService;

    protected ShebeiJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                      FXKSequenceService sequenceService, ErpShebeiService shebeiService,
                                      FxkProductObjService productObjService, FxkPersonnelObjService personnelObjService,
                                      FxkHetongService hetongService, FxkHetongMxService hetongMxService,
                                      FxkDeviceObjService deviceObjService) {
        super(jobDetailService, reportService, sequenceService);
        this.shebeiService = shebeiService;
        this.productObjService = productObjService;
        this.personnelObjService = personnelObjService;
        this.hetongService = hetongService;
        this.hetongMxService = hetongMxService;
        this.deviceObjService = deviceObjService;
    }

    @Override
    protected void writeErrorTo(String dataId, String error) {
    }

    @Override
    protected void writeResultTo(Event event, ResponseEvent resp) throws CrmApiException {
        ShebeiRespEvent respEvent = (ShebeiRespEvent)event;
        for (DeviceObj obj : respEvent.getDeviceObjs())
            deviceObjService.save(obj);
    }

    @Override
    protected ResponseEvent saveEvent(Event event) throws CrmApiException {
        return null;
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException, ErpDataException {
        String[] ddh = jobDetail.getDataId().split("-");
        if (ddh.length != 2)
            throw new ErpDataException("dataId无效[格式错误]");

        ShebeiRespEvent respEvent = shebeiService.loadShebei(ddh[0], ddh[1]);
        if (!respEvent.isSuccess())
            throw new ErpDataException(respEvent.getMessage());

        if (respEvent.getDeviceObjs() == null)
            throw new ErpDataException("获取销货单数据失败：" + jobDetail.getId());

        return fillRespEvent(respEvent, jobDetail.getDataId());
    }

    private Event fillRespEvent(ShebeiRespEvent respEvent, String xhd) throws CrmApiException, CrmDataException {
        List<String> owner = null;
        boolean addedToMessage = false;
        for (DeviceObj obj : respEvent.getDeviceObjs()) {
            obj.setDataObjectApiName("DeviceObj");

            //合同
            List<Object_snPZx__c> hts = hetongService.listByDbAndDh(obj.getDb().trim(), obj.getDh().trim());
            if (hts == null || hts.isEmpty())
                throw new CrmDataException(String.format("找不到对应的合同：%s-%s", obj.getDb().trim(), obj.getDh().trim()));

            Object_snPZx__c ht = hts.get(0);
            obj.setDevice_customer_id(ht.getField_67u8b__c());  //客户
            obj.setContact_id(ht.getField_Vvn5I__c());          //联系人
            obj.setBuy_time(Long.toString(ht.getField_jQWIc__c().getTime()));   //购买日期 合同日期

            for (Object_47F7O__c htmx : hetongMxService.listByHtId(ht.get_id())) {
                if (ht.getField_P9um1__c() != null && ht.getField_P9um1__c().equals(htmx.getField_Oocl6__c())) {
                    obj.setDevice_product_id(htmx.getField_6210o__c());     //产品
                    ProductObj productObj = productObjService.loadById(htmx.getField_6210o__c());
                    obj.setDevice_name(productObj.getName());           //设备名称
                    break;
                }
            }

            if (obj.getDevice_product_id() == null)
                throw new CrmDataException(String.format("合同明细找不到设备：[%s][%s-%s]",
                        ht.getField_P9um1__c(), obj.getDb().trim(), obj.getDh().trim()));

            obj.setField_kwf17__c(ht.getField_P9um1__c());      //设备机型
            if (StringUtils.hasText(obj.getField_nA2gP__c()))
                obj.setField_nA2gP__c(DateUtils.toTimestamp(obj.getField_nA2gP__c().trim()));

            if (!addedToMessage) {
                //业务员
                owner = personnelObjService.getOwner(obj.getTG006());

                StringBuilder builder = new StringBuilder();
                builder.append("合同[").append(obj.getDb()).append("-").append(obj.getDh())
                        .append("]的设备已出库");

                JobContextHolder.getContext().setOwner(owner);
                JobContextHolder.getContext().setMessage(builder.toString());

                addedToMessage = true;
            }

            obj.setOwner(owner);
            obj.setTG006(null);

            obj.setDb(null);
            obj.setDh(null);
        }

        JobContext context = JobContextHolder.getContext();
        context.setSerialNo(xhd);
        context.setType("销货单");

        return respEvent;
    }

    @Override
    public boolean isSupported(int typeId) {
        return typeId == 6;
    }
}
