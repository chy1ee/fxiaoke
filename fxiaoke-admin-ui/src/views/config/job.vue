<template>
    <div class="chylee-container">
        <div class="page-content">
            <div class="action-bar">
                <el-button type="primary" @click="refreshHandle">新增</el-button>
            </div>
            <el-table header-cell-class-name="chylee-header"
                :data="typeData"
                style="width: 100%">
                <el-table-column prop="id" label="ID" header-align="center" align="right" width="40" />
                <el-table-column prop="name" label="名称" header-align="center" width="120" />
                <el-table-column prop="executor" label="执行器" header-align="center" width="220" />
                <el-table-column prop="apiName" label="接口" header-align="center" width="400" />
                <el-table-column align="center" label="状态" width="80">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.status == 1">正常</el-tag>
                        <el-tag type="danger" v-else>停用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" header-align="center">
                <template slot-scope="scope">
                    <el-button type="text">编辑</el-button>
                    <el-button type="text">删除</el-button>
                </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import { listType, refresh } from "@/api/job/type"

export default {
    name: "config_job",
    data() {
        return {
            typeData: []
        }
    },
    created() {
        this.listTypes()
    },
    methods: {
        refreshHandle: function() {
            refresh().then(resp => {
                if (resp.success)
                    this.listTypes()
                else
                    this.$message.error(resp.message)
            })
        },
        listTypes: function() {
            listType().then(resp => {
                this.typeData = resp.list;
            });
        }
    }
}
</script>
