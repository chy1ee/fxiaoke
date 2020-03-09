<template>
    <div class="chylee-container">
    <div class="page-content">
        <el-form :inline="true" :model="objForm">
        <el-form-item label="查询条件">
            <el-input v-model="objForm.api" placeholder="dataObjectApiName" @blur="apiBlurHandle"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="objForm.id" placeholder="objectDataId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="objForm.name" filterable placeholder="请选择">
            <el-option
              v-for="item in fields"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
            <el-input v-model="objForm.value" placeholder="field_value"></el-input>
        </el-form-item>
        <el-form-item>
            <el-checkbox v-model="objForm.custom">自定义</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitObjForm">查询</el-button>
          <el-button @click="resetJobForm">重置</el-button>
        </el-form-item>
      </el-form>
      <json-view :data="describe" :deep="deep" />
    </div>
  </div>
</template>

<script>
import { get, query } from "@/api/fxiaoke/data";
import { describe } from '@/api/fxiaoke/object'
import jsonView from 'vue-json-views';

export default {
  name: "data1",
  components: {
    jsonView
  },
  data() {
    return {
      deep: 0,
      describe: {},
      fields: [{label:'12',value:123}, {label:'34',value:1234}],
      objForm: {
        api: "",
        id: "",
        name: "",
        value: "",
        custom: false
      }
    }
  },
  created() {
    this.objForm.api = this.$route.query.name
    if (this.$route.query.custom == 'custom')
      this.objForm.custom = true
    if (this.objForm.api != '')
      this.apiBlurHandle()
  },
  methods: {
    async apiBlurHandle() {
      if (this.objForm.api == '')
        return;
      describe({
        apiName: this.objForm.api
      }).then(resp => {
        if (resp.success) {
          let result = JSON.parse(resp.data);
          let fields = result.data.describe.fields;
          this.fields = [];
          for (let field in fields)
            this.fields.push({label:fields[field].label, value:field})
        }
      });
    },
    submitObjForm() {
      if (this.objForm.id != "") {
        get(this.objForm).then(resp => {
          if (resp.success) {
            this.describe = JSON.parse(resp.data);
          }
          else {
            this.describe ="{}"
            this.$message(resp.message);
          }
        })
      }
      else {
        query(this.objForm).then(resp => {
          if (resp.success) {
            this.describe = JSON.parse(resp.data)
          }
          else {
            this.describe ="{}"
            this.$message(resp.message);
          }
        })
      }

    },
    resetJobForm() {

    },
  }
}
</script>
