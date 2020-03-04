<template>
  <el-dialog
    :title="index == -1 ? '新增触发器' : '编辑触发器'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="30%">
    <el-form ref="triggerForm" :model="triggerForm" :rules="triggerRule" label-width="80px">
      <input v-model="triggerForm.id" name="id" type="hidden" />
      <el-form-item label="名称" prop="triggerName">
        <el-input v-model="triggerForm.triggerName" />
      </el-form-item>
      <el-form-item label="Cron" prop="cronExpression">
        <el-input v-model="triggerForm.cronExpression" />
      </el-form-item>
      <el-form-item label="间隔" prop="timeInterval">
        <el-input v-model="triggerForm.timeInterval" />
      </el-form-item>
      <el-form-item label="执行器" prop="jobId">
        <el-select v-model="triggerForm.jobId" filterable remote reserve-keyword
                   placeholder="请输入关键词" :remote-method="listJobByName" @change="jobChange">
          <el-option v-for="item in jobs" :key="item.id" :label="item.jobName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="分组" prop="triggerGroup">
        <el-input v-model="triggerForm.triggerGroup" :readonly="true"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="triggerForm.status">
          <el-radio :label="1">正常</el-radio>
          <el-radio :label="0">暂停</el-radio>
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
    import { getJobList, saveTrigger } from '../../api/quartz'

    export default {
        data() {
            let myCheck = (rule, value, callback) => {
                if((this.triggerForm.cronExpression == null || this.triggerForm.cronExpression == "") &&
                    (this.triggerForm.timeInterval == null || this.triggerForm.timeInterval == "")) {
                    callback(new Error('Cron和间隔不能同时为空'))
                }else{
                    callback()
                }
            };
            return {
                index: -1,
                visible: false,
                jobs: [],
                triggerForm: {
                    id: 0,
                    triggerName: '',
                    cronExpression: '',
                    timeInterval: '',
                    jobId: '',
                    jobName: '',
                    triggerGroup: '',
                    status: 1
                },
                triggerRule: {
                    triggerName: [
                        {required: true, message: '不能为空', trigger: 'blur'},
                        { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
                    ],
                    jobId: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    timeInterval: [
                        {validator:myCheck, trigger: 'blur'}
                    ],
                    cronExpression: [
                        {validator:myCheck, trigger: 'blur'}
                    ]
                }
            }
        },
        created() {

        },
        methods: {
            init(index, trigger) {
                this.index = index;
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['triggerForm'].resetFields()
                    if (index > -1) {
                        this.triggerForm.id = trigger.id;
                        this.triggerForm.triggerName = trigger.triggerName;
                        this.triggerForm.cronExpression = trigger.cronExpression,
                        this.triggerForm.timeInterval = trigger.timeInterval;
                        this.triggerForm.jobId = trigger.jobId,
                        this.triggerForm.jobName= trigger.jobName,
                        this.triggerForm.triggerGroup = trigger.triggerGroup,
                        this.triggerForm.status =trigger.status;

                        this.jobs = [
                            {id:trigger.jobId, jobName: trigger.jobName, jobGroup: trigger.jobGroup}
                        ]
                    }
                })
            },
            submitEdit() {
                this.$refs['triggerForm'].validate((valid) => {
                    if (valid) {
                        saveTrigger(this.triggerForm).then(resp => {
                            this.visible = false;
                            this.$emit("refreshDataList", {
                                index: this.index,
                                data: this.triggerForm
                            });
                        })
                    }
                })
            },
            listJobByName(name) {
                let nameToUse = name.trim();
                if(nameToUse.length >= 1) {
                    getJobList({name: nameToUse}).then(resp => {
                        this.jobs = resp.list;
                    })
                }
            },
            jobChange(jobId) {
                const job = this.jobs.find((item) => {
                    return item.id === jobId
                })
                this.triggerForm.triggerGroup = job.jobGroup;
                this.triggerForm.jobName = job.jobName;
            }
        }
    }
</script>
