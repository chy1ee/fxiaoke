<template>
  <el-dialog
    title="发送消息"
    :visible.sync="dialogVisible">
    <div class="filter-container">
      <el-select v-model="fieldValue" filterable placeholder="请选择" @change="describeChangeHandle">
        <el-option
          v-for="item in fieldKeys"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="cacheHandle">
        缓存
      </el-button>
    </div>
    <div class="table-container">
      <el-table ref="userListTable"
                height="300"
                :data="options" border fit highlight-current-row>
        <el-table-column type="index" width="50" />
        <el-table-column prop="value" header-align="center" label="Key" width="180" />
        <el-table-column prop="label" header-align="center" label="Value" width="260" />
        <el-table-column></el-table-column>
      </el-table>
    </div>
  </el-dialog>
</template>

<script>
  import { describe, cache } from '@/api/fxiaoke/object'

  export default {
    data() {
      return {
        apiName: null,
        fieldValue: '',
        dialogVisible: false,
        fieldKeys: [],
        fields: {},
        options: []
      }
    },
    methods: {
      init(api) {
        this.apiName = api;
        this.dialogVisible = true;
        this.$nextTick(() => {
          this.fetchDescribe(api);
        })
      },
      async fetchDescribe(api) {
        const resp = await describe({
          apiName: api
        });
        if(resp.success) {
          this.fields = resp.data.describe.fields;
          let fieldKeys = [];
          for(let key in this.fields) {
            if(this.fields[key].type == 'select_one')
              fieldKeys.push({ label: this.fields[key].description, value: key })
          }
          this.fieldKeys = fieldKeys;
        }
      },
      describeChangeHandle() {
        this.options = this.fields[this.fieldValue].options;
      },
      cacheHandle() {
        this.doCache(this.apiName);
      },
      async doCache(api) {
        const resp = await cache({
          apiName: api
        });
        if(resp.success)
          this.$message("缓存成功");
        else
          this.$message.error(resp.errorMessage);
      }
    }
  }
</script>
<style>
  .filter-container .filter-item {
    margin-bottom: 4px !important;
  }
</style>
