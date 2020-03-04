<template>
 <div class="chylee-container">
    <div class="page-content">
      <el-table ref="userListTable"
                :data="detailList" border fit highlight-current-row>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="name" label="接口" header-align="center" width="180" />
        <el-table-column prop="dataId" header-align="center" label="ID" width="220" />
        <el-table-column align="center" label="状态" width="80">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status == 1">成功</el-tag>
            <el-tag type="danger" v-else-if="scope.row.status == -1">失败</el-tag>
            <el-tag type="info" v-else-if="scope.row.status == 0">等待</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="发生时间" prop="lastTime" width="180"></el-table-column>
        <el-table-column prop="error" header-align="center" label="错误" />
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button type="text" v-if="scope.row.status == -1" @click="handleError(scope.row)">重新处理</el-button>
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
  import { listLogDetail, resetXXX } from '@/api/job/log'

  export default {
    name: "detail",
    data() {
      return {
        logId: 0,
        currentPage: 1,
        total: 0,
        detailList: []
      }
    },
    created() {
      let id = this.$route.params.id;
      this.logId = id == ':id' ? 0 : id;
      this.listDetail();
    },
    methods: {
      listDetail(page) {
        let p = {
          logId: this.logId,
          page: page||1
        }

        listLogDetail(p).then(resp => {
          this.detailList = resp.list;
          this.currentPage = resp.page;
          this.total = resp.total;
        })
      },
      handlePageChange(page) {
        this.listDetail(page)
      },
      handleError(data) {
        this.$confirm("你确定重新处理吗？", "提示").then(() => {
          resetXXX(data).then(resp => {
            data.status = 0;
            this.$message("操作成功");
          });
        }).catch(() => {
        });
      }
    }
  }
</script>
