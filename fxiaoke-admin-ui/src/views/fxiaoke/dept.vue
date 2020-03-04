<template>
  <div class="chylee-container">
    <div class="page-content">
      <div class="filter-container">
        <SelectTree
          :props="props"
          :options="optionData"
          :value="valueId"
          :clearable="isClearable"
          :accordion="isAccordion"
          @getValue="getValue($event)"
          class="filter-item"/>
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleSearch">
          搜索
        </el-button>
      </div>

      <el-table ref="userListTable" header-cell-class-name="chylee-header" :data="userList">
        <el-table-column type="index" width="50" />
        <el-table-column prop="account" header-align="center" label="账号" width="120" />
        <el-table-column prop="name" header-align="center" label="姓名" width="180" />
        <el-table-column prop="mobile" header-align="center" label="手机号码" width="180" />
        <el-table-column prop="openUserId" header-align="center" label="OpenId" />
        <el-table-column header-align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="sendMsgHandle(scope.row)">发送信息</el-button>
          </template>
        </el-table-column>
      </el-table>

      <send-message ref="sendMsg"></send-message>
    </div>
  </div>
</template>

<script>
  import SelectTree from '@/components/TreeSelect/index';
  import { fetchDept, fetchDeptUser } from '@/api/fxiaoke/dept'
  import SendMessage from './send-msg'

  export default {
    name: "dept",
    components: {
      SelectTree, SendMessage
    },
    data() {
      return {
        isClearable:true,      // 可清空（可选）
        isAccordion:true,      // 可收起（可选）
        valueId:0,            // 初始ID（可选）
        props:{                // 配置项（必选）
          value: 'id',
          label: 'name',
          children: 'children',
          // disabled:true
        },
        // 选项列表（必选）
        list:[],
        userList: []
      };
    },
    created() {
      this.initDept();
    },
    computed:{
      /* 转树形数据 */
      optionData(){
        let cloneData = JSON.parse(JSON.stringify(this.list))      // 对源数据深度克隆
        return  cloneData.filter(father=>{                      // 循环所有项，并添加children属性
          let branchArr = cloneData.filter(child=>father.id == child.parentId);       // 返回每一项的子级数组
          branchArr.length>0 ? father.children=branchArr : ''   //给父级添加一个children属性，并赋值
          return father.parentId==0;      //返回第一层
        });
      }
    },
    methods:{
      // 取值
      getValue(value){
        this.valueId = value
      },
      async initDept() {
        const resp = await fetchDept();
        if(resp.success) {
          this.list = resp.departments;
        }
      },
      async handleSearch() {
        if(this.valueId) {
          const resp = await fetchDeptUser({deptId: this.valueId});
          if(resp.success)
            this.userList = resp.userList;
        }
        else {
          this.$message.error('请选择部门');
          //this.$alert('请选择部门', '提示');
        }
      },
      sendMsgHandle(data) {
        this.$refs.sendMsg.init(data.openUserId);
      }
    }
  };
</script>

<style>
  .el-select {
    margin-bottom: 10px;
  }
</style>
