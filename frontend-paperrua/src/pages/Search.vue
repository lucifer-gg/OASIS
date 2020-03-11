<template>
  <div class="content" :style="note" style="height:100%">
   
    <div v-if="ok" class="container-fluid" style="margin-left:50px;margin-top:100px;">
      <div class="row" style="height:150px;font-size:80px;margin-left:24%;color:white">OASIS SEARCH</div>
      <div class="row" style="height: 50px;margin-left: 80px">
        <div class="col-md-2">
          <p style="font-size:20px;margin-left:0;color:white">SEARCH INFO：      </p>
        </div>

        <div class="col-md-6">
          <el-input placeholder="Please use * to connect different target" v-model="input1" class="input-with-select placeholder">
            <template slot="prepend">Authors </template>
          </el-input>
        </div>
        <div class="col-md-1">
        </div>
        <div class="col-md-3">
          <el-button type="primary" icon="el-icon-search" style="height:50px;width:180px;font-size:20px; background-color: black" @click="search">SEARCH</el-button>
        </div>

      </div>

      <div class="row" style="height: 50px;margin-left: 80px">
        <div class="col-md-2">
        </div>
        <div class="col-md-6">
          <el-input placeholder="Please use * to connect different target" v-model="input3" class="input-with-select">
            <template slot="prepend" >Affliction</template>
          </el-input>
        </div>
        <div class="col-md-2">
        </div>
      </div>

      <div class="row" style="height: 50px;margin-left: 80px">
        <div class="col-md-2">
        </div>
        <div class="col-md-6">
          <el-input placeholder="ASE/ICSE" v-model="input2" class="input-with-select">
            <template slot="prepend" style="width: 50px" >Publication</template>
          </el-input>
        </div>
        <div class="col-md-2">
        </div>
      </div>

      <div class="row" style="height: 50px;margin-left: 80px">
        <div class="col-md-2">
        </div>
        <div class="col-md-6">
          <el-input placeholder="Please use * to connect different target" v-model="input4" class="input-with-select">
            <template slot="prepend" style="width: 50px">Key Words</template>
          </el-input>
        </div>
        <div class="col-md-2">
        </div>
      </div>
      <div style="height:40px;"></div>
      <div class="row" style="height:50px;margin-left:80px">
        <div class="col-md-2">
             <p style="font-size:22px;margin-left:0;color:white">DURATION:      </p>
        </div>
        <div class="col-md-3">
            <el-input placeholder="Please input the year begins" slot="prepend" v-model="yearFrom" style="width: 240px;">

            </el-input>
        </div>
        <div  style="font-size: 20px;color:white">To</div>
        <div   style="margin-left:40px">
          <el-input placeholder="Please input the year ends" slot="prepend" v-model="yearTo" style="width: 240px;">

          </el-input>
        </div>
      </div>
    </div>
    <div v-if="no" v-loading="loa"     element-loading-text="LOADING..."
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">
      <h3 v-if="notfind" style="margin-left:270px;">Sorry your search does not return any result...
      </h3>
      <card>
      <li v-if="find" v-for="paper in papers" v-bind:key="paper.articleId" class="news-item">
      <span class="score">{{paper.refs}} refs</span>
      <span class="title">
        <a style="color:navy;" :href=paper.pdfLink>{{paper.title}}</a>
        <span>({{paper.time}})</span>
      </span>
      <br>
      <br>
      <span class="author" v-for="author in paper.authors">
        <a style="color: lightskyblue">--{{author}} </a>
      </span>
      <br>
      <br>
      <span style="color: #E6A23C">abstract:</span>
      <span class="abstract text-wrapper" style="font-size: 16px;color: #828282">
          {{paper.docAbstract}}
      </span>
      <br>
      <br>
      <span style="color: #943bea">keywords:</span>
      <span class="time" v-for="k in paper.keyWords" style="color: gold">
        --{{k}}
      </span>
      <br>
      <br>
      <span style="color: #409EFF">publication:</span>
      <span class="text-wrapper"  style="color: #6dc030">
        {{paper.publicationTitle}}
      </span>
    </li>
      </card>
    </div>
    
    
  </div>
</template>
<script>
  import axios from 'axios'
  import router from 'vue-router'
  import Card from 'src/components/Cards/Card.vue'
  export default {
    components: {

    },
    data() {
      return {
        papers:[],
        input1: '',
        input2: '',
        input3: '',
        input4: '',
        select1: '',
        select2: '',
        select3: '',
        select4: '',
        yearFrom:'',
        yearTo:'',
        ok:true,
        no:false,
        listIsNull:false,
        loa:true,
        notfind:false,
        find:true,
        note:{
           backgroundImage: "url(" + require("../../public/img/fin.png") + ") ",
           backgroundPosition: "center center",
           backgroundRepeat: "no-repeat",
           backgroundSize: "cover"
        }
      }
   },
    methods:{
      show(){},
      hide(){},
      created(){
　　　　 //如果没有这句代码，select中初始化会是空白的，默认选中就无法实现
        this.select1 = "作者";
      },
      search:function(){
        var vauthor="";
        var vpublication="";
        var vaffliction="";
        var vkeyWords="";
        var vyears="";
        var _this=this;
        var isValid=true;

        vauthor=this.input1;
        vpublication=this.input2;
        vaffliction=this.input3;
        vkeyWords=this.input4;

        if (this.yearFrom==""){
          this.yearFrom="0";
        }
        if (this.yearTo==""){
          this.yearTo="0"
        }
        vyears=this.yearFrom+"-"+this.yearTo;
        if (vauthor==""){
          vauthor="null";
        }
        if (vpublication==""){
          vpublication="null";
        }
        if (vaffliction==""){
          vaffliction="null";
        }
        if (vkeyWords==""){
          vkeyWords="null";
        }
        if(vyears=="-"){
          vyears="0-0";
        }
        console.log("vaut:"+vauthor);
        console.log("vaff:"+vaffliction);
        console.log("vpublic:"+vpublication);
        console.log("vk:"+vkeyWords);
        console.log("vy:"+vyears);

        //对会议进行验证
        if ((vpublication!="ASE")&&(vpublication!="ICSE")&&(vpublication!="null")){
          isValid=false;
          this.$notify({
            title: 'input error',
            message: 'Only hav ASE / ICSE publication',
            duration: 0
          });
          return;
        }else {
          if((!isNaN(this.yearFrom))&&(!isNaN(this.yearTo))){
            //对于空数组和只有一个数值成员的数组或全是数字组成的字符串，isNaN返回false，例如：'123'、[]、[2]、['123'],isNaN返回false,
            //所以如果不需要val包含这些特殊情况，则这个判断改写为if(!isNaN(val) && typeof val === 'number' )
            isValid=true;
            this.ok=false;
            this.no=true;
          }
          else{
             isValid=false;
             this.$notify({
              title: 'input error',
              message: 'please input the valid time format! eg:2019',
              duration: 0
            });
            return;
          }
        }
        this.$axios.post('http://47.100.223.54:3180/search/item',{
          author:vauthor,
          affiliation:vaffliction,
          publication:vpublication,
          keyWords:vkeyWords,
          time:vyears
        }).then(function (response) {
            _this.papers=response.data.data;

            console.log("成功获得papers列表！")
            console.log(_this.papers);
            _this.loa=false;
            console.log(_this.papers.length);
            if (_this.papers.length==0){
              _this.notfind=true;
        _this.find=false;
            }
        }).catch(function (error) {
          console.log("获得papers列表失败！");
          console.log(error);
        })
      }
    },
  }

</script>
<style lang="stylus">
.placeholder{
  color #0e0e0e;
}
  .text-wrapper {
    word-break: break-all;
    word-wrap: break-word
  }
.news-item
  background-color #fff
  padding 20px 30px 20px 80px
  border-bottom 1px solid #0e0e0e
  margin 0 auto
  width 80%
  position relative
  line-height 20px
  .score
    color #ff6600
    font-size 1.1em
    font-weight 700
    position absolute
    top 50%
    left 0
    width 80px
    text-align center
    margin-top -10px
  .meta, .host
    font-size .85em
    color #828282
    a
      color #828282
      text-decoration underline
      &:hover
        color #ff6600
</style>

