<template>
  <div class="chylee-container">
    <div class="page-content">
      <el-form :inline="true" :model="jobForm">
        <el-form-item label="任务名称">
          <el-input v-model="jobForm.name" placeholder="任务名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitJobForm">查询</el-button>
          <el-button @click="resetJobForm">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="action-bar">
        <el-button type="primary" @click="createJobHandler">新增</el-button>
      </div>
      <el-table header-cell-class-name="chylee-header"
        :data="jobData"
        style="width: 100%">
        <el-table-column prop="id" label="ID" header-align="center" align="right" width="40" />
        <el-table-column prop="jobName" label="名称" header-align="center" width="220" />
        <el-table-column prop="jobGroup" label="分组" header-align="center" width="80" />
        <!-- <el-table-column prop="jobDesc" label="描述" header-align="center" width="220" /> -->
        <el-table-column prop="bean" label="执行对象" header-align="center" width="140" />
        <el-table-column prop="param" label="参数" header-align="center" width="180" />
        <el-table-column prop="childIds" label="子任务" header-align="center" width="80" />
        <el-table-column label="状态" align="center" width="200">
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
            <el-button type="text" @click="trggerJobHandler(scope.$index, scope.row)">执行一次</el-button>
            <el-button type="text" @click="deleteJobHandler(scope.$index, scope.row)">删除</el-button>
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
    <edit-job v-if="editJobVisible" ref="editJob" @refreshDataList="refreshDataList"></edit-job>
  </div>
</template>
<script>
  import {getJobList,changeJobStatus,deleteJob,triggerJob} from "../../api/quartz";
  import EditJob from './job-edit'
  export default {
    name: "quartz_job",
    components: {
      EditJob
    },
    data() {
      return {
        editJobVisible: false,
        jobForm: {
          name: ''
        },
        total: 0,
        page: 1,
        jobData: []
      }
    },
    created() {
      this.loadPage();
    },
    methods: {
      loadPage(params) {
        getJobList(params).then(resp => {
          console.log(resp)
          this.total = resp.total;
          this.page = resp.page;
          this.jobData = resp.list;
        });
      },
      createJobHandler() {
        this.editJobVisible = true
        this.$nextTick(() => {
          this.$refs.editJob.init()
        })
      },
      handlePageChange(page) {
        this.loadPage({page:page})
      },
      submitJobForm() {
        console.log('submit!');
      },
      resetJobForm() {
      },
      refreshDataList(result) {
        if(this.jobData == null)
          this.jobData = [];
        this.jobData.push(result)
      },
      statusChangeHandler(scope) {
        changeJobStatus({id:scope.id, status:scope.status}).then(resp => {
            if(!resp.success) {
                scope.status = scope.status == 1 ? 0 : 1;
            }
        })
      },
      deleteJobHandler(index, scope) {
        this.$confirm('此操作将永久删除执行器, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            deleteJob({id:scope.id}).then(resp => {
                this.jobData.splice(index, 1);
            })
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消删除'
            });
        });
      },
      trggerJobHandler(index, scope) {
          this.$confirm('你确实要触发此执行器?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {
              triggerJob({id:scope.id});
          }).catch(() => {
              this.$message({
                  type: 'info',
                  message: '已取消执行'
              });
          });
      }
    }
  }
</script>
<style>
  .el-switch__label * {
    font-size: 12px;
  }
</style>
