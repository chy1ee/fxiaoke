(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-54b745c8"],{"163d":function(e,t,r){"use strict";var n=r("e7ad"),a=r("e042"),i=r("75c4"),l=r("1e5b"),s=r("94b3"),o=r("238a"),c=r("2ea2").f,u=r("dcb7").f,d=r("064e").f,p=r("777a").trim,f="Number",h=n[f],m=h,v=h.prototype,b=i(r("e005")(v))==f,g="trim"in String.prototype,y=function(e){var t=s(e,!1);if("string"==typeof t&&t.length>2){t=g?t.trim():p(t,3);var r,n,a,i=t.charCodeAt(0);if(43===i||45===i){if(r=t.charCodeAt(2),88===r||120===r)return NaN}else if(48===i){switch(t.charCodeAt(1)){case 66:case 98:n=2,a=49;break;case 79:case 111:n=8,a=55;break;default:return+t}for(var l,o=t.slice(2),c=0,u=o.length;c<u;c++)if(l=o.charCodeAt(c),l<48||l>a)return NaN;return parseInt(o,n)}}return+t};if(!h(" 0o1")||!h("0b1")||h("+0x1")){h=function(e){var t=arguments.length<1?0:e,r=this;return r instanceof h&&(b?o((function(){v.valueOf.call(r)})):i(r)!=f)?l(new m(y(t)),r,h):y(t)};for(var I,_=r("149f")?c(m):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),x=0;_.length>x;x++)a(m,I=_[x])&&!a(h,I)&&d(h,I,u(m,I));h.prototype=v,v.constructor=h,r("bf16")(n,f,h)}},"1e5b":function(e,t,r){var n=r("fb68"),a=r("859b").set;e.exports=function(e,t,r){var i,l=t.constructor;return l!==r&&"function"==typeof l&&(i=l.prototype)!==r.prototype&&n(i)&&a&&a(e,i),e}},"29ad":function(e,t,r){"use strict";var n=r("c242"),a=r.n(n);a.a},"37a8":function(e,t,r){"use strict";var n=r("45f4"),a=r.n(n);a.a},"45f4":function(e,t,r){},"777a":function(e,t,r){var n=r("e46b"),a=r("f6b4"),i=r("238a"),l=r("9769"),s="["+l+"]",o="​",c=RegExp("^"+s+s+"*"),u=RegExp(s+s+"*$"),d=function(e,t,r){var a={},s=i((function(){return!!l[e]()||o[e]()!=o})),c=a[e]=s?t(p):l[e];r&&(a[r]=c),n(n.P+n.F*s,"String",a)},p=d.trim=function(e,t){return e=String(a(e)),1&t&&(e=e.replace(c,"")),2&t&&(e=e.replace(u,"")),e};e.exports=d},"859b":function(e,t,r){var n=r("fb68"),a=r("69b3"),i=function(e,t){if(a(e),!n(t)&&null!==t)throw TypeError(t+": can't set as prototype!")};e.exports={set:Object.setPrototypeOf||("__proto__"in{}?function(e,t,n){try{n=r("4ce5")(Function.call,r("dcb7").f(Object.prototype,"__proto__").set,2),n(e,[]),t=!(e instanceof Array)}catch(a){t=!0}return function(e,r){return i(e,r),t?e.__proto__=r:n(e,r),e}}({},!1):void 0),check:i}},9769:function(e,t){e.exports="\t\n\v\f\r   ᠎             　\u2028\u2029\ufeff"},a56b:function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"chylee-container"},[r("div",{staticClass:"page-content"},[r("div",{staticClass:"filter-container"},[r("SelectTree",{staticClass:"filter-item",attrs:{props:e.props,options:e.optionData,value:e.valueId,clearable:e.isClearable,accordion:e.isAccordion},on:{getValue:function(t){return e.getValue(t)}}}),e._v(" "),r("el-button",{staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.handleSearch}},[e._v("\n        搜索\n      ")])],1),e._v(" "),r("el-table",{ref:"userListTable",attrs:{"header-cell-class-name":"chylee-header",data:e.userList}},[r("el-table-column",{attrs:{type:"index",width:"50"}}),e._v(" "),r("el-table-column",{attrs:{prop:"account","header-align":"center",label:"账号",width:"120"}}),e._v(" "),r("el-table-column",{attrs:{prop:"name","header-align":"center",label:"姓名",width:"180"}}),e._v(" "),r("el-table-column",{attrs:{prop:"mobile","header-align":"center",label:"手机号码",width:"180"}}),e._v(" "),r("el-table-column",{attrs:{prop:"openUserId","header-align":"center",label:"OpenId"}}),e._v(" "),r("el-table-column",{attrs:{"header-align":"center",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{attrs:{type:"text"},on:{click:function(r){return e.sendMsgHandle(t.row)}}},[e._v("发送信息")])]}}])})],1),e._v(" "),r("send-message",{ref:"sendMsg"})],1)])},a=[],i=(r("419a"),r("e980")),l=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("el-select",{attrs:{value:e.valueTitle,clearable:e.clearable},on:{clear:e.clearHandle}},[r("el-option",{staticClass:"options",attrs:{value:e.valueTitle,label:e.valueTitle}},[r("el-tree",{ref:"selectTree",attrs:{id:"tree-option",accordion:e.accordion,data:e.options,props:e.props,"node-key":e.props.value,"default-expanded-keys":e.defaultExpandedKey},on:{"node-click":e.handleNodeClick}})],1)],1)},s=[],o=(r("6d57"),r("163d"),{name:"el-tree-select",props:{props:{type:Object,default:{value:"id",label:"title",children:"children"}},options:{type:Array,default:[]},value:{type:Number,default:null},clearable:{type:Boolean,default:!0},accordion:{type:Boolean,default:!1}},data:function(){return{valueId:null,valueTitle:"",defaultExpandedKey:[]}},mounted:function(){this.valueId=this.value,this.initHandle()},methods:{initHandle:function(){this.valueId&&(this.valueTitle=this.$refs.selectTree.getNode(this.valueId).data[this.props.label],this.$refs.selectTree.setCurrentKey(this.valueId),this.defaultExpandedKey=[this.valueId]),this.initScroll()},initScroll:function(){this.$nextTick((function(){var e=document.querySelectorAll(".el-scrollbar .el-select-dropdown__wrap")[0],t=document.querySelectorAll(".el-scrollbar .el-scrollbar__bar");e.style.cssText="margin: 0px; max-height: none; overflow: hidden;",t.forEach((function(e){return e.style.width=0}))}))},handleNodeClick:function(e){this.valueTitle=e[this.props.label],this.valueId=e[this.props.value],this.$emit("getValue",this.valueId),this.defaultExpandedKey=[]},clearHandle:function(){this.valueTitle="",this.valueId=null,this.defaultExpandedKey=[],this.clearSelected(),this.$emit("getValue",null)},clearSelected:function(){var e=document.querySelectorAll("#tree-option .el-tree-node");e.forEach((function(e){return e.classList.remove("is-current")}))}},watch:{value:function(){this.valueId=this.value,this.initHandle()}}}),c=o,u=(r("29ad"),r("623f")),d=Object(u["a"])(c,l,s,!1,null,"17fc435a",null),p=d.exports,f=r("b775");function h(e){return Object(f["a"])({url:"/fxiaoke/dept",method:"get",params:e})}function m(e){return Object(f["a"])({url:"/fxiaoke/dept/"+e.deptId,method:"get",params:e})}var v=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("el-dialog",{attrs:{title:"发送消息",visible:e.dialogVisible},on:{"update:visible":function(t){e.dialogVisible=t}}},[r("div",{staticClass:"app-container"},[r("el-form",{ref:"msgForm",staticStyle:{width:"600px"},attrs:{model:e.msgForm,rules:e.msgRules,"label-width":"100px"}},[r("el-form-item",{attrs:{label:"接收人",prop:"openId"}},[r("el-input",{model:{value:e.msgForm.openId,callback:function(t){e.$set(e.msgForm,"openId",t)},expression:"msgForm.openId"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"消息类型"}},[r("el-radio-group",{model:{value:e.msgForm.type,callback:function(t){e.$set(e.msgForm,"type",t)},expression:"msgForm.type"}},[r("el-radio",{attrs:{label:"text"}},[e._v("文本")])],1)],1),e._v(" "),r("el-form-item",{attrs:{label:"内容",prop:"content"}},[r("el-input",{attrs:{type:"textarea",rows:7},model:{value:e.msgForm.content,callback:function(t){e.$set(e.msgForm,"content",t)},expression:"msgForm.content"}})],1),e._v(" "),r("el-form-item",[r("el-button",{attrs:{type:"primary"},on:{click:e.onSubmit}},[e._v("立即发送")])],1)],1)],1)])},b=[];function g(e){return Object(f["a"])({url:"/fxiaoke/msg",method:"post",params:e})}var y={data:function(){return{dialogVisible:!1,msgForm:{openId:"",type:"text",content:""},msgRules:{openId:[{required:!0,message:"请输入接收人",trigger:"blur"}],content:[{required:!0,message:"请输入内容",trigger:"blur"},{min:3,max:100,message:"长度在 3 到 100 个字符",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dialogVisible=!0,this.$nextTick((function(){t.$refs.msgForm.resetFields(),t.msgForm.openId=e}))},onSubmit:function(){var e=this;this.$refs["msgForm"].validate((function(t){t&&e.doSubmit()}))},doSubmit:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,g(this.msgForm);case 2:t=e.sent,t.success?(this.dialogVisible=!1,this.$message("发送成功")):this.$message.error(t.errorMessage||"发送失败");case 4:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}()}},I=y,_=Object(u["a"])(I,v,b,!1,null,null,null),x=_.exports,w={name:"dept",components:{SelectTree:p,SendMessage:x},data:function(){return{isClearable:!0,isAccordion:!0,valueId:0,props:{value:"id",label:"name",children:"children"},list:[],userList:[]}},created:function(){this.initDept()},computed:{optionData:function(){var e=JSON.parse(JSON.stringify(this.list));return e.filter((function(t){var r=e.filter((function(e){return t.id==e.parentId}));return r.length>0&&(t.children=r),0==t.parentId}))}},methods:{getValue:function(e){this.valueId=e},initDept:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,h();case 2:t=e.sent,t.success&&(this.list=t.departments);case 4:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),handleSearch:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(!this.valueId){e.next=7;break}return e.next=3,m({deptId:this.valueId});case 3:t=e.sent,t.success&&(this.userList=t.userList),e.next=8;break;case 7:this.$message.error("请选择部门");case 8:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),sendMsgHandle:function(e){this.$refs.sendMsg.init(e.openUserId)}}},k=w,S=(r("37a8"),Object(u["a"])(k,n,a,!1,null,null,null));t["default"]=S.exports},c242:function(e,t,r){}}]);