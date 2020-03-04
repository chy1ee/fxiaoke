<template>
  <div class="chylee-container">
    <div class="page-content">
      <el-form ref="cfgForm" :model="cfgForm" :rules="cfgRules" label-width="160px" style="width: 600px;">
        <el-form-item label="appId" prop="appId">
          <el-input v-model="cfgForm.appId"></el-input>
        </el-form-item>
        <el-form-item label="appSecret" prop="appSecret">
          <el-input v-model="cfgForm.appSecret"></el-input>
        </el-form-item>
        <el-form-item label="permanentCode" prop="permanentCode">
          <el-input v-model="cfgForm.permanentCode"></el-input>
        </el-form-item>
        <el-form-item label="token" prop="token">
          <el-input v-model="cfgForm.token"></el-input>
        </el-form-item>
        <el-form-item label="encodingAesKey" prop="encodingAesKey">
          <el-input v-model="cfgForm.encodingAesKey"></el-input>
        </el-form-item>
        <el-form-item label="syncOpenId" prop="syncOpenId">
          <el-input v-model="cfgForm.syncOpenId"></el-input>
        </el-form-item>
        <el-form-item label="authorizeUrl" prop="authorizeUrl">
          <el-input v-model="cfgForm.authorizeUrl"></el-input>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="onSubmit" style="float:right">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import { saveApi, fetchConfig } from "../../api/sys/config";

  export default {
    name: "index",
    data() {
      return {
        cfgForm: {
          id: 0,
          type: 1001,
          appId: '',
          appSecret: '',
          permanentCode: '',
          token: '',
          encodingAesKey: '',
          syncOpenId: '',
          authorizeUrl: 'https://open.fxiaoke.com/oauth2/authorize'
        },
        cfgRules: {
          appId: [
            { required: true, message: '请输入appId', trigger: 'blur' }
          ],
          appSecret: [
            { required: true, message: '请输入appSecret', trigger: 'blur' }
          ],
          permanentCode: [
            { required: true, message: '请输入permanentCode', trigger: 'blur' }
          ],
          token: [
            { required: true, message: '请输入token', trigger: 'blur' }
          ],
          encodingAesKey: [
            { required: true, message: '请输入encodingAesKey', trigger: 'blur' }
          ],
          syncOpenId: [
            { required: true, message: '请输入syncOpenId', trigger: 'blur' }
          ],
          authorizeUrl: [
            { required: true, message: '请输入authorizeUrl', trigger: 'blur' }
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
        const resp = await saveApi(this.cfgForm);
        if(resp.success) {
          this.$message("发送成功");
        }
        else {
          this.$message.error(resp.errorMessage||'发送失败');
        }
      },
      async config() {
        fetchConfig({type: 1001}).then(resp => {
          this.cfgForm = JSON.parse(resp.data.content)
          if(resp.data.id)
            this.cfgForm.id = resp.data.id
        })
      }
    }
  }
</script>
