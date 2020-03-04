<template>
  <div class="dashboard-editor-container">
    <panel-group @handleSetLineChartData="handleSetLineChartData" 
      :jobTypeCount="jobTypeCount" :executorCount="executorCount" :qrtzLogCount="qrtzLogCount" :errorCount="errorCount" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData2" />
    </el-row>

  </div>
</template>

<script>
import { fetchInfo } from '@/api/sys/dashboard.js'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup,
    LineChart
  },
  computed: {
    lineChartData2:function() {
      let result = {
        headerData: [],
        failData: [],
        successData: []
      }

      for(let p=this.lineChartData.length-1;p>-1;p--) {
        result.headerData.push(this.lineChartData[p].endTime.replace(" ", "\n"));
        result.failData.push(this.lineChartData[p].fail);
        result.successData.push(this.lineChartData[p].success);
      }

      return result
    }
  },
  data() {
    return {
      jobTypeCount: 0,
      executorCount: 0, 
      qrtzLogCount: 0,
      errorCount: 0,
      lineChartData: []
    }
  },
  created() {
    fetchInfo().then(resp => {
      this.jobTypeCount = resp.jobTypeCount;
      this.executorCount = resp.executorCount;
      this.qrtzLogCount = resp.qrtzLogCount;
      this.errorCount = resp.errorCount;
      this.lineChartData = resp.list;
    })
  },
  methods: {
    handleSetLineChartData(url) {
      this.$router.push({path: url});
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
