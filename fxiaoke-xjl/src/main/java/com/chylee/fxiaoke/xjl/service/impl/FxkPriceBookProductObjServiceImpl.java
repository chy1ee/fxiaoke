package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.PriceBookProductObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkPriceBookProductObjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkPriceBookProductObjServiceImpl extends AbstractCrmServiceImpl implements FxkPriceBookProductObjService {

    public FxkPriceBookProductObjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public List<PriceBookProductObj> listByProductCode(String productCode) throws CrmApiException, CrmDataException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("pricebook_id", PRICEBOOK_ID));
        queryInfoFilters.add(new QueryInfoFilter("product_code", productCode));
        List<PriceBookProductObj> priceBookProductObjs = this.queryDataObj("PriceBookProductObj",
                queryInfoFilters, false, PriceBookProductObj.class);
        if (priceBookProductObjs == null || priceBookProductObjs.isEmpty())
            throw new CrmDataException("价目明细不存在["+productCode+"]");

        return priceBookProductObjs;
    }
}
