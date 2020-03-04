<template>
  <el-dialog
    title="新增执行器"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="30%">
    <el-form ref="groupForm" :model="groupForm" :rules="groupRule" label-width="80px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="groupForm.name" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="submitEdit()">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
  import { addGroup } from '../../api/quartz'

  export default {
      data() {
          return {
              visible: false,
              groupForm: {
                  name: ''
              },
              groupRule: {
                name: [
                    {required: true, message: "不能为空", trigger: "blue"}
                ]
              }
          }
      },
      methods: {
          init() {
            this.visible = true
            this.$nextTick(() => {
                this.$refs['groupForm'].resetFields();
            })
          },
          submitEdit() {
              this.$refs['groupForm'].validate((valid) => {
                  if (valid) {
                      addGroup(this.groupForm).then(resp => {
                          this.$emit("refreshDataList", resp.data);
                          this.visible = false;
                      })
                  }
              })
          }
      }
  }
</script>
