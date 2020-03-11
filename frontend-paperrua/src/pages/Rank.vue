<template>
 
  <div class="content" style="background-image: linear-gradient(15deg, #13547a 0%, #80d0c7 100%);"> 
    <div class="container-fluid">
      <div class="row">
        <div class="col-6">
          <card class="strpied-tabled-with-hover"
                body-classes="table-full-width table-responsive"
          >
            <template slot="header">
              <h4 class="card-title">Top Author</h4>
              <p class="card-category">5 authors with the most papers</p>
            </template>
            <table class="table">
                <thead>
                  <slot name="columns">
                    <tr>
                      <th v-for="column in columns" :key="column">{{column}}</th>
                    </tr>
                  </slot>
                </thead>
                <tbody>
                <tr v-for="n in 5">
                  <slot :row="n">
                    <td>{{n}}</td>
                    <td>{{topAuthors[n-1].authorName}}</td>
                    <td>{{topAuthors[n-1].articleNum}}</td>
                  </slot>
                </tr>
                </tbody>
              </table>
          </card>

        </div>
        <div class="col-6">
          <card class="strpied-tabled-with-hover"
                body-classes="table-full-width table-responsive"
          >
            <template slot="header">
              <h4 class="card-title">Top Keywords</h4>
              <p class="card-category">5 most searched keywords</p>
            </template>
            <table class="table">
                <thead>
                  <slot name="columns">
                    <tr>
                      <th v-for="column in columns1" :key="column">{{column}}</th>
                    </tr>
                  </slot>
                </thead>
                <tbody>
                <tr v-for="n in 5">
                  <slot :row="n">
                    <td>{{n}}</td>
                    <td>{{topKeywords[n-1].keyWords}}</td>
                    <td>{{topKeywords[n-1].keyWordsSearchTime}}</td>
                  </slot>
                </tr>
                </tbody>
              </table>
          </card>

        </div>

      </div>

        <div class="row">
        <div class="col-6">
          <card class="strpied-tabled-with-hover"
                body-classes="table-full-width table-responsive"
          >
            <template slot="header">
              <h4 class="card-title">Top Aff in ASE</h4>
              <p class="card-category">5 affiliations with the most papers in ASE</p>
            </template>
           <table class="table">
                <thead>
                  <slot name="columns">
                    <tr>
                      <th v-for="column in columns2" :key="column">{{column}}</th>
                    </tr>
                  </slot>
                </thead>
                <tbody>
                <tr v-for="n in 5">
                  <slot :row="n">
                    <td>{{n}}</td>
                    <td>{{topAseAffis[n-1].affiliationName}}</td>
                    <td>{{topAseAffis[n-1].articleNum}}</td>
                  </slot>
                </tr>
                </tbody>
              </table>
          </card>

        </div>
        <div class="col-6">
          <card class="strpied-tabled-with-hover"
                body-classes="table-full-width table-responsive"
          >
            <template slot="header">
              <h4 class="card-title">Top Aff in ICSE</h4>
              <p class="card-category">5 affiliations with the most papers in ICSE</p>
            </template>
            <table class="table">
                <thead>
                  <slot name="columns">
                    <tr>
                      <th v-for="column in columns2" :key="column">{{column}}</th>
                    </tr>
                  </slot>
                </thead>
                <tbody>
                <tr v-for="n in 5">
                  <slot :row="n">
                    <td>{{n}}</td>
                    <td>{{topIcseAffis[n-1].affiliationName}}</td>
                    <td>{{topIcseAffis[n-1].articleNum}}</td>
                  </slot>
                </tr>
                </tbody>
              </table>
          </card>

        </div>

      </div>
       <div class="col-6">
          <card class="strpied-tabled-with-hover"
                body-classes="table-full-width table-responsive"
          >
            <template slot="header">
              <h4 class="card-title">Top refs</h4>
              <p class="card-category">5 most referenced papers</p>
            </template>
            <table class="table">
                <thead>
                  <slot name="columns">
                    <tr>
                      <th v-for="column in columns" :key="column">{{column}}</th>
                    </tr>
                  </slot>
                </thead>
                <tbody>
                <tr v-for="n in 5">
                  <slot :row="n">
                    <td>{{n}}</td>
                    <td>{{topRefs[n-1].docTitle}}</td>
                    <td>{{topRefs[n-1].reference}}</td>
                  </slot>
                </tr>
                </tbody>
              </table>
          </card>

        </div>
    </div>
  </div>
</template>
<script>
  import LTable from 'src/components/Table.vue'
  import Card from 'src/components/Cards/Card.vue'
  var topAuthors=[]
  var topRefs=[]
  var topAseAffis=[]
  var topIcseAffis=[]
  var topKeywords=[]
  var tableColumns = ['Id', 'Name', 'salary', 'country',"City"]
  var columns = ['Rank', 'Name', 'Num']
  var columns1 = ['Rank', 'Name', 'SearchNum']
  var columns2=['Rank', 'AFF', 'PaperNum']
  var data=topAuthors
  var tableData=[]
  export default {
    components: {
      LTable,
      Card
    },
    data () {
      return {
        topAuthors,
        tableColumns,
        tableData,
        columns,
        data,
        columns2,
        topKeywords,
        columns1,
        topRefs,
        topIcseAffis,
        topAseAffis,
        table1: {
          columns: [...tableColumns],
          data: [...tableData]
        }
      }
    },
    methods: {
      hasValue (item, column) {
        return item[column.toLowerCase()] !== 'undefined'
      },
      itemValue (item, column) {
        return item[column.toLowerCase()]
      }
    },
    mounted() {
      var _this = this;
      this.$axios.get('http://47.100.223.54:3180/rank/topAuthor').then(function (response) {
       // console.log(response.data.data);
        _this.topAuthors=response.data.data;
        console.log("topauthores: "+_this.topAuthors);
        console.log("TopAuthor获取成功！！！");
      }).catch(function (error) {
        console.log("TopAuthor获取失败!!");
        console.log(error);
      });
      this.$axios.get('http://47.100.223.54:3180/rank/aseTopAff').then(function (response) {
        //console.log(response.data.data);
        _this.topAseAffis=response.data.data;
        console.log("aseTopAff获取成功！！！");
      }).catch(function (error) {
        console.log("aseTopAff获取失败!!");
        console.log(error);
      });
      this.$axios.get('http://47.100.223.54:3180/rank/icseTopAff').then(function (response) {
        //console.log(response.data.data);
        _this.topIcseAffis=response.data.data;
        console.log("icseTopAff获取成功！！！");
      }).catch(function (error) {
        console.log("icseTopAff获取失败!!");
        console.log(error);
      });
      this.$axios.get('http://47.100.223.54:3180/rank/topKeyWords').then(function (response) {
        console.log(response.data.data);
        _this.topKeywords=response.data.data;
        console.log(topKeywords);
        console.log("topKeyWords获取成功！！！");
      }).catch(function (error) {
        console.log("topKeyWords获取失败!!");
        console.log(error);
      });
      this.$axios.get('http://47.100.223.54:3180/rank/topReference').then(function (response) {
        //console.log(response.data.data);
        _this.topRefs=response.data.data;
        console.log("topReference获取成功！！！");
      }).catch(function (error) {
        console.log("gettopReference获取失败!!");
        console.log(error);
      });
    
    }
  }
</script>
<style>
</style>
