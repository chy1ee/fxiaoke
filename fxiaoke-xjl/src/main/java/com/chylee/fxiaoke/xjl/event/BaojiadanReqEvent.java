package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.xjl.event.data.object.*;


public class BaojiadanReqEvent extends AccountReqEvent {
    private QuoteObj quoteObj;
    private QuoteLinesObj quoteLinesObj;

    public QuoteObj getQuoteObj() {
        return quoteObj;
    }

    public void setQuoteObj(QuoteObj quoteObj) {
        this.quoteObj = quoteObj;
    }

    public QuoteLinesObj getQuoteLinesObj() {
        return quoteLinesObj;
    }

    public void setQuoteLinesObj(QuoteLinesObj quoteLinesObj) {
        this.quoteLinesObj = quoteLinesObj;
    }

}
