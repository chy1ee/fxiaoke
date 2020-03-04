<template>
  <div class="chylee-container">
    <div class="page-content">
      <el-form :inline="true" :model="groupForm">
        <el-form-item label="分组名称">
          <el-input v-model="groupForm.name" placeholder="分组名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">查询</el-button>
          <el-button>重置</el-button>
        </el-form-item>
      </el-form>
      <div class="action-bar">
        <el-button type="primary" @click="createJobHandler">新增</el-button>
      </div>
      <div style="width:500px;">
        <el-table header-cell-class-name="chylee-header"
                  :data="groupData">
          <el-table-column prop="name" label="名称" header-align="center" width="220" />
          <el-table-column label="状态" align="center" width="80">
            <template slot-scope="scope">
              <el-tag v-if="test=scope.row.status == 1">正常</el-tag>
              <el-tag type="warning" v-else>停用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="100">
            <template slot-scope="scope">
              <el-button type="text" v-if="scope.row.status == 1" @click="deleteJobHandler(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <group-edit ref="editGroup" v-if="editGroupVisible" @refreshDataList="refreshDataList"></group-edit>
  </div>
</template>
<script>
  import { getGroupList, deleteGroup } from '../../api/quartz'
  import GroupEdit from './group-edit'

  export default {
      name: "group",
      components: {
          GroupEdit
      },
      data() {
          return {
              editGroupVisible: false,
              groupForm: {
                  name: ''
              },
              groupData: []
          }
      },
      created() {
          getGroupList().then(response => {
              this.groupData = response.list;
          })
      },
      methods: {
          createJobHandler() {
            this.editGroupVisible = true;
              this.$nextTick(() => {
                  this.$refs.editGroup.init();
              });
          },
          deleteJobHandler(index, group) {
              this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning'
              }).then(() => {
                  deleteGroup({id: group.id})
                  let groupToDel = this.groupData[index];
                  groupToDel.status = -1;
                  this.$set(this.groupData, index, groupToDel);
                  this.$message({
                      type: 'success',
                      message: '删除成功!'
                  });
              }).catch(() => {
                  this.$message({
                      type: 'info',
                      message: '已取消删除'
                  });
              });
          },
          refreshDataList(result) {
              this.groupData.push({
                  id: result.id,
                  name: result.name,
                  status: result.status
              })
          }
      }
  }
</script>
