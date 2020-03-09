<template>
  <div class="chylee-container">
    <div class="page-content">
      <el-table ref="objectList" :data="objectList" header-cell-class-name="chylee-header">
        <el-table-column prop="describeApiName" header-align="center" label="ApiName" width="300" />
        <el-table-column prop="describeDisplayName" header-align="center" label="说明" width="400" />
        <el-table-column prop="defineType" header-align="center" label="类型" width="150" />
        <el-table-column prop="active" header-align="center" label="Active" width="120" />
        <el-table-column header-align="center" label="操作">
          <template slot-scope="scope">
            <router-link :to="'/fxiaoke/describe/'+scope.row.describeApiName">
              <el-button type="text" icon="el-icon-edit" class="btn_link">对象描述</el-button>
            </router-link>
            <router-link :to="{path:'/fxiaoke/data', query:{name:scope.row.describeApiName,custom:scope.row.defineType}}">
              <el-button type="text" icon="el-icon-document">查询数据</el-button>
            </router-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  import { list } from '@/api/fxiaoke/object'

  export default {
    name: "object2",
    data() {
      return {
        objectList: []
      }
    },
    created() {
      this.init();
    },
    methods: {
      async init() {
        const resp = await list();
        if(resp.success) {
          this.objectList = resp.data.objects;
          console.log(resp)
        }
      }
    }
  }
</script>
<style>
  .btn_link {
    margin: 0 15px 0 15px;
  }
</style>
