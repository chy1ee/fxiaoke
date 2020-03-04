<template>
  <div class="chylee-container">
    <div class="page-content">
      <el-form :inline="true" :model="jogForm">
        <el-form-item label="任务名称">
          <el-input v-model="jogForm.name" placeholder="任务名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="jogForm.valid">有效</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitLogForm">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table ref="userListTable"
                :data="logList" border fit highlight-current-row>
        <el-table-column prop="id" label="序号" header-align="center" align="right" width="60" />
        <el-table-column prop="jobGroup" header-align="center" label="接口" width="100" />
        <el-table-column prop="jobName" header-align="center" label="接口" width="140" />
        <el-table-column prop="startTime" align="center" label="开始时间" width="160" />
        <el-table-column prop="endTime" align="center" label="终止时间" width="160" />
        <el-table-column prop="count" header-align="center" align="right" label="数据量" width="80" />
        <el-table-column prop="success" header-align="center" align="right" label="成功" width="80" />
        <el-table-column header-align="center" align="right" label="失败" width="80">
          <template slot-scope="scope">
            <font v-if="scope.row.fail > 0" color="red">{{scope.row.fail}}</font>
            <font v-else>0</font>
          </template>
        </el-table-column>
        <el-table-column prop="waiting" header-align="center" align="right" label="待处理" width="80" />
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <router-link :to="'/log/detail/'+scope.row.id">
              <el-button type="text" size="small" icon="el-icon-edit">
                详细信息
              </el-button>
            </router-link>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="10"
        :total="total"
        :current-page="currentPage"
        @current-change="handlePageChange" />
    </div>
  </div>
</template>

<script>
  import { listLog } from '@/api/job/log'

  export default {
    name: "job",
    data() {
      return {
        jogForm: {
          name: "",
          valid: true
        },
        qrtzId: 0,
        total: 0,
        currentPage: 1,
        logList: []
      }
    },
    created() {
      let id = this.$route.params.id;
      this.qrtzId = id == ':id' ? 0 : id;
      this.listLogs();
    },
    methods: {
      listLogs(page) {
        let p = {
          qrtzId: this.qrtzId,
          valid: this.jogForm.valid ? 1: 0,
          page: page||1
        }
        listLog(p).then(resp => {
          this.logList = resp.list;
          this.total = resp.total;
          this.currentPage = resp.page;
        });
      },
      handlePageChange(page) {
        this.listLogs(page)
      },
      showDetailHandle(data) {
        this.$refs.logDetail.init(data.id);
      },
      submitLogForm() {
        this.listLogs();
      }
    }
  }
</script>
