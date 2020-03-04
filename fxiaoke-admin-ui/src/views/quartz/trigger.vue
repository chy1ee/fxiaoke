<template>
  <div class="chylee-container">
    <div class="page-content">
      <el-form :inline="true" :model="triggerForm">
        <el-form-item label="任务名称">
          <el-input v-model="triggerForm.name" placeholder="任务名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
          <el-button @click="onSubmit">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="action-bar">
        <el-button type="primary" @click="createTriggerHandler">新增</el-button>
      </div>
      <el-table header-cell-class-name="chylee-header"
                :data="triggerData"
                style="width: 100%">
        <el-table-column prop="triggerName" label="名称" header-align="center" width="140" />
        <el-table-column prop="triggerGroup" label="分组" header-align="center" width="100" />
        <el-table-column prop="cronExpression" label="Cron表达式" header-align="center" width="220" />
        <el-table-column prop="timeInterval" label="间隔（秒）" header-align="center" align="right" width="80" />
        <el-table-column prop="jobName" label="执行器" header-align="center" width="180" />
        <el-table-column label="状态" align="center" width="180">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-text="运行"
              :active-value="1"
              inactive-text="暂停"
              :inactive-value="0"
              @change="statusChangeHandler(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" header-align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="editTriggerHandler(scope.$index, scope.row)">编辑</el-button>
            <el-button type="text" @click="deleteTriggerHandler(scope.$index, scope.row)">删除</el-button>
            <el-button type="text">日志</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="10"
        :total="total"
        :current-page="page"
        @current-change="handlePageChange" />
    </div>
    <trigger-edit ref="editTrigger" v-if="editTriggerVisible" @refreshDataList="refreshDataList"></trigger-edit>
  </div>
</template>
<script>
    import {getTriggerList,changeTriggerStatus,deleteTrigger} from "../../api/quartz";
    import TriggerEdit from "./trigger-edit"

    export default {
        name: "trigger",
        components: {
            TriggerEdit
        },
        data() {
            return {
                editTriggerVisible: false,
                triggerForm: {
                    name: ''
                },
                total: 0,
                page: 1,
                triggerData: []
            }
        },
        created() {
            getTriggerList().then(response => {
                this.total = response.total;
                this.page = response.page;
                this.triggerData = response.list;
            });
        },
        methods: {
            handlePageChange() {

            },
            onSubmit() {
                console.log('submit!');
            },
            createTriggerHandler() {
                this.editTriggerVisible = true;
                this.$nextTick(() => {
                    this.$refs.editTrigger.init(-1);
                })
            },
            editTriggerHandler(index, scope) {
                this.editTriggerVisible = true;
                this.$nextTick(() => {
                    this.$refs.editTrigger.init(index, scope)
                })
            },
            deleteTriggerHandler(index, scope) {
                this.$confirm('此操作将永久删除触发器, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteTrigger({id:scope.id}).then(resp => {
                        this.triggerData.splice(index, 1);
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            statusChangeHandler(scope) {
                changeTriggerStatus({id:scope.id, status:scope.status}).then(resp => {
                    if(!resp.success) {
                        scope.status = scope.status == 1 ? 0 : 1;
                    }
                })
            },
            refreshDataList(result) {
                let trigger = {
                    id: result.data.id,
                    triggerName: result.data.triggerName,
                    cronExpression: result.data.cronExpression,
                    timeInterval: result.data.timeInterval,
                    jobId: result.data.jobId,
                    jobName: result.data.jobName,
                    triggerGroup: result.data.triggerGroup,
                    status: result.data.status,
                    index: result.index
                }
                if(result.index > -1) {
                    this.$set(this.triggerData, result.index, trigger)
                }
                else {
                  if(this.triggerData == null)
                    this.triggerData = []
                  this.triggerData.push(trigger)
                }
            }
        }
    }
</script>
<style>
  .el-switch__label * {
    font-size: 12px;
  }
</style>
