<template>
  <el-dialog
    title="发送消息"
    :visible.sync="dialogVisible">
    <div class="app-container">
      <el-form ref="msgForm" :model="msgForm" :rules="msgRules" label-width="100px" style="width: 600px;">
        <el-form-item label="接收人" prop="openId">
          <el-input v-model="msgForm.openId"></el-input>
        </el-form-item>
        <el-form-item label="消息类型">
          <el-radio-group v-model="msgForm.type">
            <el-radio label="text">文本</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input type="textarea" :rows="7" v-model="msgForm.content"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即发送</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
  import { sendMsg } from "../../api/fxiaoke/msg";

  export default {
    data() {
      return {
        dialogVisible: false,
        msgForm: {
          openId: '',
          type: 'text',
          content: ''
        },
        msgRules: {
          openId: [
            { required: true, message: '请输入接收人', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '请输入内容', trigger: 'blur' },
            { min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init(openId) {
        this.dialogVisible = true;
        this.$nextTick(() => {
          this.$refs.msgForm.resetFields();
          this.msgForm.openId = openId;
        });
      },
      onSubmit() {
        this.$refs['msgForm'].validate((valid) => {
          if (valid) {
            this.doSubmit()
          }
        });
      },
      async doSubmit() {
        const resp = await sendMsg(this.msgForm);
        if(resp.success) {
          this.dialogVisible = false;
          this.$message("发送成功");
        }
        else {
          this.$message.error(resp.errorMessage||'发送失败');
        }
      }
    }
  }
</script>
