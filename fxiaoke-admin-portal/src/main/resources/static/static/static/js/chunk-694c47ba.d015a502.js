(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-694c47ba"],{"4a24":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"chylee-container"},[a("div",{staticClass:"page-content"},[a("el-table",{ref:"userListTable",attrs:{data:t.detailList,border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{type:"index"}}),t._v(" "),a("el-table-column",{attrs:{prop:"name",label:"接口","header-align":"center",width:"180"}}),t._v(" "),a("el-table-column",{attrs:{prop:"dataId","header-align":"center",label:"ID",width:"220"}}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"状态",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[1==e.row.status?a("el-tag",{attrs:{type:"success"}},[t._v("成功")]):-1==e.row.status?a("el-tag",{attrs:{type:"danger"}},[t._v("失败")]):0==e.row.status?a("el-tag",{attrs:{type:"info"}},[t._v("等待")]):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"发生时间",prop:"lastTime",width:"180"}}),t._v(" "),a("el-table-column",{attrs:{prop:"error","header-align":"center",label:"错误"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",width:"120",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[-1==e.row.status?a("el-button",{attrs:{type:"text"},on:{click:function(a){return t.handleError(e.row)}}},[t._v("重新处理")]):t._e()]}}])})],1),t._v(" "),a("el-pagination",{attrs:{background:"",layout:"prev, pager, next","page-size":10,total:t.total,"current-page":t.currentPage},on:{"current-change":t.handlePageChange}})],1)])},r=[],l=a("7c63"),o={name:"detail",data:function(){return{logId:0,currentPage:1,total:0,detailList:[]}},created:function(){var t=this.$route.params.id;this.logId=":id"==t?0:t,this.listDetail()},methods:{listDetail:function(t){var e=this,a={logId:this.logId,page:t||1};Object(l["b"])(a).then((function(t){e.detailList=t.list,e.currentPage=t.page,e.total=t.total}))},handlePageChange:function(t){this.listDetail(t)},handleError:function(t){var e=this;this.$confirm("你确定重新处理吗？","提示").then((function(){Object(l["c"])(t).then((function(a){t.status=0,e.$message("操作成功")}))})).catch((function(){}))}}},i=o,c=a("623f"),s=Object(c["a"])(i,n,r,!1,null,null,null);e["default"]=s.exports},"7c63":function(t,e,a){"use strict";a.d(e,"a",(function(){return r})),a.d(e,"b",(function(){return l})),a.d(e,"c",(function(){return o}));var n=a("b775");function r(t){return Object(n["a"])({url:"/job/log",method:"get",params:t})}function l(t){return Object(n["a"])({url:"/job/log/detail/"+t.logId,method:"get",params:t})}function o(t){return Object(n["a"])({url:"/job/log/reset/"+t.id,method:"post"})}}}]);