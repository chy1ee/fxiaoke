<template>
  <el-dialog
    :title="index == -1 ? '新增执行器' : '编辑执行器'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="30%">
    <el-form ref="jobForm" :model="jobForm" :rules="jobRule" label-width="80px">
      <input v-model="jobForm.id" name="id" type="hidden" />
      <el-form-item label="分组" prop="jobGroup">
        <el-select v-model="jobForm.jobGroup" placeholder="请选择分组">
          <el-option v-for="item in groups" :key="item.id" :label="item.name" :value="item.name" />
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="jobName">
        <el-input v-model="jobForm.jobName" placeholder="名称" />
      </el-form-item>
      <el-form-item label="执行器" prop="bean">
        <el-input v-model="jobForm.bean" placeholder="执行器" />
      </el-form-item>
      <el-form-item label="参数" prop="params">
        <el-input v-model="jobForm.params" placeholder="参数" />
      </el-form-item>
      <el-form-item label="子任务" prop="childIds">
        <el-input v-model="jobForm.params" placeholder="多个子任务ID用逗号分隔" />
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="jobForm.status">
          <el-radio :label="1">正常</el-radio>
          <el-radio :label="2">暂停</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="submitEdit()">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import { getGroupList, saveJob } from '../../api/quartz'

    export default {
        data() {
            return {
                index: -1,
                visible: false,
                groups: [],
                jobForm: {
                    id: 0,
                    jobGroup: '',
                    jobName: '',
                    bean: '',
                    params: '',
                    childIds: '',
                    status: 1
                },
                jobRule: {
                    jobGroup: [
                        {required: true, message: '请选择分组', trigger: 'blur'}
                    ],
                    jobName: [
                        {required: true, message: '不能为空', trigger: 'blur'},
                        { min: 3, max: 50, message: '长度在 3 到 100 个字符', trigger: 'blur' }
                    ],
                    bean: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ]
                }
            }
        },
        created() {
            getGroupList({status: 1}).then(resp => {
                this.groups = resp.list
            })
        },
        methods: {
            init() {
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['jobForm'].resetFields()
                })
            },
            submitEdit() {
                this.$refs['jobForm'].validate((valid) => {
                    if (valid) {
                        saveJob(this.jobForm).then(resp => {
                            console.log(resp)
                            this.visible = false;
                            this.$emit("refreshDataList", resp.data);
                        })
                    }
                })
            }
        }
    }
</script>
