<template>
  <div class="chylee-container">
    <div class="page-content">
      <json-view :data="describe" deep="4" />
    </div>
  </div>
</template>

<script>
  import { describe } from '@/api/fxiaoke/object'
  import jsonView from 'vue-json-views';

  export default {
    name: "describe",
    components: {
      jsonView
    },
    data() {
      return {
        describe: {},
        apiName: null,
        fieldValue: '',
        fieldKeys: [],
        fields: {},
        options: []
      }
    },
    created() {
      this.fetchDescribe(this.$route.params.name)
    },
    methods: {
      async fetchDescribe(api) {
        describe({
          apiName: api
        }).then(resp => {
            this.describe = JSON.parse(resp.data);
        });
      },
      jsonFormat(json, options) {
          let reg = null,
              formatted = '',
              pad = 0,
              PADDING = '    '; // one can also use '\t' or a different number of spaces
          // optional settings
          options = options || {};
          // remove newline where '{' or '[' follows ':'
          options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
          // use a space after a colon
          options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

          // begin formatting...
          if (typeof json !== 'string') {
              // make sure we start with the JSON as a string
              json = JSON.stringify(json);
          } else {
              // is already a string, so parse and re-stringify in order to remove extra whitespace
              json = JSON.parse(json);
              json = JSON.stringify(json);
          }

          // add newline before and after curly braces
          reg = /([\{\}])/g;
          json = json.replace(reg, '\r\n$1\r\n');

          // add newline before and after square brackets
          reg = /([\[\]])/g;
          json = json.replace(reg, '\r\n$1\r\n');

          // add newline after comma
          reg = /(\,)/g;
          json = json.replace(reg, '$1\r\n');

          // remove multiple newlines
          reg = /(\r\n\r\n)/g;
          json = json.replace(reg, '\r\n');

          // remove newlines before commas
          reg = /\r\n\,/g;
          json = json.replace(reg, ',');

          // optional formatting...
          if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
              reg = /\:\r\n\{/g;
              json = json.replace(reg, ':{');
              reg = /\:\r\n\[/g;
              json = json.replace(reg, ':[');
          }
          if (options.spaceAfterColon) {
              reg = /\:/g;
              json = json.replace(reg, ': ');
          }

          this.each(json.split('\r\n'), function(index, node) {
              let i = 0,
                  indent = 0,
                  padding = '';

              if (node.match(/\{$/) || node.match(/\[$/)) {
                  indent = 1;
              } else if (node.match(/\}/) || node.match(/\]/)) {
                  if (pad !== 0) {
                      pad -= 1;
                  }
              } else {
                  indent = 0;
              }

              for (i = 0; i < pad; i++) {
                  padding += PADDING;
              }

              formatted += padding + node + '\r\n';
              pad += indent;
          });
          return formatted;
      },
      each(obj, callback, args) {
          // obj是需要遍历的数组或者对象
          // callback是处理数组/对象的每个元素的回调函数，它的返回值会中断循环的过程
          let value, i = 0, length = obj.length, isArray = this.isArraylike(obj);//判断是不是数组
          if (args) {
              if (isArray) { // 数组
                  for (; i < length; i++ ) {
                      value = callback.apply(obj[i], args); // 若args = [arg1, arg2, arg3]，则相当于:callback(args1, args2, args3)，callback里边的this指向了obj[i]
                      if ( value === false ) // 当callback函数返回值会false的时候，注意是全等！循环结束
                          break;
                  }
              }
              else { // 非数组
                  for (i in obj) { // 遍历对象
                      value = callback.apply(obj[i], args);
                      if ( value === false )
                          break;
                  }
              }
          }
          else {
              if (isArray) {
                  for (; i < length; i++) {
                      value = callback.call(obj[i], i, obj[i]); // 相当于callback(i, obj[i])。然后callback里边的this指向了obj[i]
                      if (value === false)
                          break;
                  }
              }
              else {
                  for (i in obj) {
                      value = callback.call(obj[i], i, obj[i]);
                      if (value === false)
                          break;
                  }
              }
          }
          return obj;
      },
      isArraylike( obj ) {
          return Object.prototype.toString.call(obj)== '[object Array]';
      }
    }
  }
</script>
<style>
  .filter-container .filter-item {
    margin-bottom: 4px !important;
  }
</style>
