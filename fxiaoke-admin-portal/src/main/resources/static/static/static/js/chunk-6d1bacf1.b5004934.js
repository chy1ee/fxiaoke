(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6d1bacf1"],{"7fee":function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"chylee-container"},[r("div",{staticClass:"page-content"},[r("el-form",{attrs:{inline:!0,model:t.groupForm}},[r("el-form-item",{attrs:{label:"分组名称"}},[r("el-input",{attrs:{placeholder:"分组名称"},model:{value:t.groupForm.name,callback:function(e){t.$set(t.groupForm,"name",e)},expression:"groupForm.name"}})],1),t._v(" "),r("el-form-item",[r("el-button",{attrs:{type:"primary"}},[t._v("查询")]),t._v(" "),r("el-button",[t._v("重置")])],1)],1),t._v(" "),r("div",{staticClass:"action-bar"},[r("el-button",{attrs:{type:"primary"},on:{click:t.createJobHandler}},[t._v("新增")])],1),t._v(" "),r("div",{staticStyle:{width:"500px"}},[r("el-table",{attrs:{"header-cell-class-name":"chylee-header",data:t.groupData}},[r("el-table-column",{attrs:{prop:"name",label:"名称","header-align":"center",width:"220"}}),t._v(" "),r("el-table-column",{attrs:{label:"状态",align:"center",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[(t.test=1==e.row.status)?r("el-tag",[t._v("正常")]):r("el-tag",{attrs:{type:"warning"}},[t._v("停用")])]}}])}),t._v(" "),r("el-table-column",{attrs:{label:"操作",align:"center",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[1==e.row.status?r("el-button",{attrs:{type:"text"},on:{click:function(r){return t.deleteJobHandler(e.$index,e.row)}}},[t._v("删除")]):t._e()]}}])})],1)],1)],1),t._v(" "),t.editGroupVisible?r("group-edit",{ref:"editGroup",on:{refreshDataList:t.refreshDataList}}):t._e()],1)},a=[],o=(r("cc57"),r("c879")),u=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("el-dialog",{attrs:{title:"新增执行器","close-on-click-modal":!1,visible:t.visible,width:"30%"},on:{"update:visible":function(e){t.visible=e}}},[r("el-form",{ref:"groupForm",attrs:{model:t.groupForm,rules:t.groupRule,"label-width":"80px"}},[r("el-form-item",{attrs:{label:"名称",prop:"name"}},[r("el-input",{model:{value:t.groupForm.name,callback:function(e){t.$set(t.groupForm,"name",e)},expression:"groupForm.name"}})],1)],1),t._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(e){t.visible=!1}}},[t._v("取 消")]),t._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.submitEdit()}}},[t._v("确 定")])],1)],1)},i=[],s={data:function(){return{visible:!1,groupForm:{name:""},groupRule:{name:[{required:!0,message:"不能为空",trigger:"blue"}]}}},methods:{init:function(){var t=this;this.visible=!0,this.$nextTick((function(){t.$refs["groupForm"].resetFields()}))},submitEdit:function(){var t=this;this.$refs["groupForm"].validate((function(e){e&&Object(o["a"])(t.groupForm).then((function(e){t.$emit("refreshDataList",e.data),t.visible=!1}))}))}}},l=s,c=r("623f"),d=Object(c["a"])(l,u,i,!1,null,null,null),m=d.exports,p={name:"group",components:{GroupEdit:m},data:function(){return{editGroupVisible:!1,groupForm:{name:""},groupData:[]}},created:function(){var t=this;Object(o["g"])().then((function(e){t.groupData=e.list}))},methods:{createJobHandler:function(){var t=this;this.editGroupVisible=!0,this.$nextTick((function(){t.$refs.editGroup.init()}))},deleteJobHandler:function(t,e){var r=this;this.$confirm("此操作将永久删除该文件, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){Object(o["d"])({id:e.id});var n=r.groupData[t];n.status=-1,r.$set(r.groupData,t,n),r.$message({type:"success",message:"删除成功!"})})).catch((function(){r.$message({type:"info",message:"已取消删除"})}))},refreshDataList:function(t){this.groupData.push({id:t.id,name:t.name,status:t.status})}}},f=p,b=Object(c["a"])(f,n,a,!1,null,null,null);e["default"]=b.exports},c879:function(t,e,r){"use strict";r.d(e,"h",(function(){return a})),r.d(e,"k",(function(){return o})),r.d(e,"b",(function(){return u})),r.d(e,"e",(function(){return i})),r.d(e,"m",(function(){return s})),r.d(e,"i",(function(){return l})),r.d(e,"c",(function(){return c})),r.d(e,"l",(function(){return d})),r.d(e,"f",(function(){return m})),r.d(e,"g",(function(){return p})),r.d(e,"d",(function(){return f})),r.d(e,"a",(function(){return b})),r.d(e,"j",(function(){return g}));var n=r("b775");function a(t){return Object(n["a"])({url:"/quartz/job",method:"get",params:t})}function o(t){return Object(n["a"])({url:"/quartz/job",method:"post",params:t})}function u(t){return Object(n["a"])({url:"/quartz/job/status",method:"post",params:t})}function i(t){return Object(n["a"])({url:"/quartz/job/delete",method:"post",params:t})}function s(t){return Object(n["a"])({url:"/quartz/job/trigger",method:"post",params:t})}function l(t){return Object(n["a"])({url:"/quartz/trigger",method:"get",params:t})}function c(t){return Object(n["a"])({url:"/quartz/trigger/status",method:"post",params:t})}function d(t){return Object(n["a"])({url:"/quartz/trigger",method:"post",params:t})}function m(t){return Object(n["a"])({url:"/quartz/trigger/delete",method:"post",params:t})}function p(t){return Object(n["a"])({url:"/quartz/group",method:"get",params:t})}function f(t){return Object(n["a"])({url:"/quartz/group",method:"delete",params:t})}function b(t){return Object(n["a"])({url:"/quartz/group",method:"post",params:t})}function g(t){return Object(n["a"])({url:"/quartz/log",method:"get",params:t})}}}]);