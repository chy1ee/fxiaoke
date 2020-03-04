<template>
    <div class="chylee-container">
    <div class="page-content">
      <el-table ref="userListTable"
                :data="logList" border fit highlight-current-row>
        <el-table-column prop="jobGroup" header-align="center" label="任务组" width="120" />
        <el-table-column prop="jobName" header-align="center" label="处理器名称" width="220" />
        <el-table-column prop="startTime" align="center" label="开始时间" width="180" />
        <el-table-column prop="endTime" align="center" label="终止时间" width="180" />
        <el-table-column prop="error" header-align="center" align="right" label="错误信息" width="320" />
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <router-link :to="'/log/job/'+scope.row.id">
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
        :current-page="page"
        @current-change="handlePageChange" />
    </div>
  </div>
</template>
<script>
import { listLog } from "@/api/quartz"

export default {
    name: "quartz",
    data() {
        return {
            total: 0,
            page: 1,
            logList: []
        }
    },
    created() {
        this.list();
    },
    methods: {
        list(param) {
            listLog(param).then(resp => {
                this.total = resp.total;
                this.page = resp.page;
                this.logList = resp.list;
            })
        },
        handlePageChange(page) {
            this.list({page:page})
        }
    }
}
</script>
