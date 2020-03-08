package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.xjl.event.data.object.ProductObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.common.service.FxkObjectService;
import com.chylee.fxiaoke.xjl.service.FxkProductObjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkProductObjServiceImpl extends AbstractCrmServiceImpl implements FxkProductObjService {
    private final FxkObjectService objectService;

    public FxkProductObjServiceImpl(AccessTokenManager accessTokenManager, FxkObjectService objectService) {
        super(accessTokenManager);
        this.objectService = objectService;
    }

    @Override
    public ProductObj loadById(String id) throws CrmApiException {
        ProductObj productObj = loadDataObj("ProductObj", id, false, ProductObj.class);

        if(productObj.getUnit() != null) {
            productObj.setUnit(
                    objectService.selectOneMap("ProductObj", "unit").loadByLabel(productObj.getUnit()));
        }

        return productObj;
    }

    @Override
    public List<ProductObj> listByProductCode(String productCode) throws CrmDataException, CrmApiException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("product_code", productCode));
        List<ProductObj> productObjs = this.queryDataObj("ProductObj", queryInfoFilters, false, ProductObj.class);
        if (productObjs == null || productObjs.isEmpty())
            throw new CrmDataException("产品不存在["+productCode+"]");
        return productObjs;
    }
}
