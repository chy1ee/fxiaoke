<template>
    <div class="chylee-container">
    <div class="page-content">
        <el-form :inline="true" :model="objForm">
        <el-form-item label="查询条件">
            <el-input v-model="objForm.api" placeholder="dataObjectApiName"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="objForm.id" placeholder="objectDataId"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="objForm.name" placeholder="field_name"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="objForm.value" placeholder="field_value"></el-input>
        </el-form-item>
        <el-form-item>
            <el-checkbox v-model="objForm.custom">自定义</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitObjForm">查询</el-button>
          <el-button @click="resetJobForm">重置</el-button>
        </el-form-item>
      </el-form>
      <json-view :data="describe" deep="2" />
    </div>
  </div>
</template>

<script>
import { get, query } from "@/api/fxiaoke/data";
import jsonView from 'vue-json-views';

export default {
    name: "data1",
    components: {
        jsonView
    },
    data() {
        return {
            describe: {},
            objForm: {
                api: "",
                id: "",
                name: "",
                value: "",
                custom: false
            }
        }
    },
    created() {
        this.objForm.api = this.$route.query.name
        if (this.$route.query.custom == 'custom')
            this.objForm.custom = true
    },
    methods: {
        submitObjForm() {
            if (this.objForm.id != "") {
                get(this.objForm).then(resp => {
                    if (resp.success) {
                        //this.describe = this.jsonFormat(resp.data)
                        this.describe = JSON.parse(resp.data);
                    }
                    else {
                        this.describe =""
                        this.$message(resp.message);
                    }
                })
            }
            else {
                query(this.objForm).then(resp => {
                    if (resp.success) {
                        this.describe = this.jsonFormat(resp.data)
                    }
                    else {
                        this.describe =""
                        this.$message(resp.message);
                    }
                })
            }

        },
        resetJobForm() {

        },
    }
}
</script>
