(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-f25493d0"],{"0c2d":function(t,a,e){},"38f4":function(t,a,e){"use strict";var n=e("0c2d"),i=e.n(n);i.a},9406:function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"dashboard-container"},[e("adminDashboard")],1)},i=[],s=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"dashboard-editor-container"},[e("panel-group",{attrs:{jobTypeCount:t.jobTypeCount,executorCount:t.executorCount,qrtzLogCount:t.qrtzLogCount,errorCount:t.errorCount},on:{handleSetLineChartData:t.handleSetLineChartData}}),t._v(" "),e("el-row",{staticStyle:{background:"#fff",padding:"16px 16px 0","margin-bottom":"32px"}},[e("line-chart",{attrs:{"chart-data":t.lineChartData2}})],1)],1)},r=[],o=(e("f548"),e("b775"));function c(t){return Object(o["a"])({url:"/sys/dashboard",method:"get",params:t})}var l=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("el-row",{staticClass:"panel-group",attrs:{gutter:40}},[e("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[e("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("/config/job")}}},[e("div",{staticClass:"card-panel-icon-wrapper icon-people"},[e("svg-icon",{attrs:{"icon-class":"skill","class-name":"card-panel-icon"}})],1),t._v(" "),e("div",{staticClass:"card-panel-description"},[e("div",{staticClass:"card-panel-text"},[t._v("\n          对接接口数量\n        ")]),t._v(" "),e("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":t.jobTypeCount,duration:2600}})],1)])]),t._v(" "),e("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[e("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("/quartz/job")}}},[e("div",{staticClass:"card-panel-icon-wrapper icon-message"},[e("svg-icon",{attrs:{"icon-class":"chart","class-name":"card-panel-icon"}})],1),t._v(" "),e("div",{staticClass:"card-panel-description"},[e("div",{staticClass:"card-panel-text"},[t._v("\n          执行器数量\n        ")]),t._v(" "),e("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":t.executorCount,duration:3e3}})],1)])]),t._v(" "),e("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[e("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("/log/quartz")}}},[e("div",{staticClass:"card-panel-icon-wrapper icon-money"},[e("svg-icon",{attrs:{"icon-class":"tree","class-name":"card-panel-icon"}})],1),t._v(" "),e("div",{staticClass:"card-panel-description"},[e("div",{staticClass:"card-panel-text"},[t._v("\n          调度次数\n        ")]),t._v(" "),e("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":t.qrtzLogCount,duration:3200}})],1)])]),t._v(" "),e("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[e("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("/log/detail/-1")}}},[e("div",{staticClass:"card-panel-icon-wrapper icon-shopping"},[e("svg-icon",{attrs:{"icon-class":"404","class-name":"card-panel-icon"}})],1),t._v(" "),e("div",{staticClass:"card-panel-description"},[e("div",{staticClass:"card-panel-text"},[t._v("\n          错误记录\n        ")]),t._v(" "),e("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":t.errorCount,duration:3200}})],1)])])],1)},d=[],u=e("9e2e"),h=e.n(u),p={components:{CountTo:h.a},props:["jobTypeCount","executorCount","qrtzLogCount","errorCount"],methods:{handleSetLineChartData:function(t){this.$emit("handleSetLineChartData",t)}}},v=p,C=(e("cde4"),e("623f")),f=Object(C["a"])(v,l,d,!1,null,"3e2b76b1",null),m=f.exports,b=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{class:t.className,style:{height:t.height,width:t.width}})},_=[],g=e("24ce"),D=e.n(g),y=e("ed08"),$={data:function(){return{$_sidebarElm:null}},mounted:function(){this.$_initResizeEvent(),this.$_initSidebarResizeEvent()},beforeDestroy:function(){this.$_destroyResizeEvent(),this.$_destroySidebarResizeEvent()},activated:function(){this.$_initResizeEvent(),this.$_initSidebarResizeEvent()},deactivated:function(){this.$_destroyResizeEvent(),this.$_destroySidebarResizeEvent()},methods:{$_resizeHandler:function(){var t=this;return Object(y["debounce"])((function(){t.chart&&t.chart.resize()}),100)()},$_initResizeEvent:function(){window.addEventListener("resize",this.$_resizeHandler)},$_destroyResizeEvent:function(){window.removeEventListener("resize",this.$_resizeHandler)},$_sidebarResizeHandler:function(t){"width"===t.propertyName&&this.$_resizeHandler()},$_initSidebarResizeEvent:function(){this.$_sidebarElm=document.getElementsByClassName("sidebar-container")[0],this.$_sidebarElm&&this.$_sidebarElm.addEventListener("transitionend",this.$_sidebarResizeHandler)},$_destroySidebarResizeEvent:function(){this.$_sidebarElm&&this.$_sidebarElm.removeEventListener("transitionend",this.$_sidebarResizeHandler)}}};e("6cbb");var x={mixins:[$],props:{className:{type:String,default:"chart"},width:{type:String,default:"100%"},height:{type:String,default:"350px"},autoResize:{type:Boolean,default:!0},chartData:{type:Object,required:!0}},data:function(){return{chart:null}},watch:{chartData:{deep:!0,handler:function(t){this.setOptions(t)}}},mounted:function(){var t=this;this.$nextTick((function(){t.initChart()}))},beforeDestroy:function(){this.chart&&(this.chart.dispose(),this.chart=null)},methods:{initChart:function(){this.chart=D.a.init(this.$el,"macarons"),this.setOptions(this.chartData)},setOptions:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},a=t.headerData,e=t.successData,n=t.failData;this.chart.setOption({xAxis:{data:a,boundaryGap:!1,axisTick:{show:!1}},grid:{left:10,right:10,bottom:20,top:30,containLabel:!0},tooltip:{trigger:"axis",axisPointer:{type:"cross"},padding:[5,10]},yAxis:{axisTick:{show:!1}},legend:{data:["成功","失败"]},series:[{name:"失败",itemStyle:{normal:{color:"#FF005A",lineStyle:{color:"#FF005A",width:2}}},smooth:!0,type:"line",data:n,animationDuration:2800,animationEasing:"cubicInOut"},{name:"成功",smooth:!0,type:"line",itemStyle:{normal:{color:"#3888fa",lineStyle:{color:"#3888fa",width:2},areaStyle:{color:"#f3f8ff"}}},data:e,animationDuration:2800,animationEasing:"quadraticOut"}]})}}},z=x,E=Object(C["a"])(z,b,_,!1,null,null,null),w=E.exports,S={name:"DashboardAdmin",components:{PanelGroup:m,LineChart:w},computed:{lineChartData2:function(){for(var t={headerData:[],failData:[],successData:[]},a=this.lineChartData.length-1;a>-1;a--)t.headerData.push(this.lineChartData[a].endTime.replace(" ","\n")),t.failData.push(this.lineChartData[a].fail),t.successData.push(this.lineChartData[a].success);return t}},data:function(){return{jobTypeCount:0,executorCount:0,qrtzLogCount:0,errorCount:0,lineChartData:[]}},created:function(){var t=this;c().then((function(a){t.jobTypeCount=a.jobTypeCount,t.executorCount=a.executorCount,t.qrtzLogCount=a.qrtzLogCount,t.errorCount=a.errorCount,t.lineChartData=a.list}))},methods:{handleSetLineChartData:function(t){this.$router.push({path:t})}}},L=S,j=(e("38f4"),Object(C["a"])(L,s,r,!1,null,"37430976",null)),R=j.exports,O={name:"Dashboard",components:{adminDashboard:R}},k=O,T=Object(C["a"])(k,n,i,!1,null,null,null);a["default"]=T.exports},cde4:function(t,a,e){"use strict";var n=e("f80e"),i=e.n(n);i.a},f80e:function(t,a,e){}}]);