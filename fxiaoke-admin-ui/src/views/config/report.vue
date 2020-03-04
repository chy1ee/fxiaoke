<template>
  <div class="chylee-container">
    <div class="page-content">
      <el-form ref="cfgForm" :model="cfgForm" :rules="cfgRules" label-width="100px" style="width: 600px;">
        <el-form-item label="状态">
          <el-radio-group v-model="cfgForm.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="管理员" prop="content">
          <el-input type="textarea" :rows="7" v-model="cfgForm.openid"></el-input>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="onSubmit" style="float:right">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import { saveReport, fetchConfig } from "@/api/sys/config";

  export default {
    name: "touser",
    data() {
      return {
        cfgForm: {
          status: 1,
          openid: ''
        },
        cfgRules: {
          openid: [
            { required: true, message: '请输入appId', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      this.config();
    },
    methods: {
      onSubmit() {
        this.$refs['cfgForm'].validate((valid) => {
          if (valid) {
            this.doSubmit();
          }
        });
      },
      async doSubmit() {
        saveReport(this.cfgForm).then(resp => {
          this.$message("发送成功");
        })
      },
      async config() {
        fetchConfig({type: 1002}).then(resp => {
          this.cfgForm = JSON.parse(resp.data.content);
          if(resp.data.id)
            this.cfgForm.id = resp.data.id
        })
      }
    }
  }
</script>
