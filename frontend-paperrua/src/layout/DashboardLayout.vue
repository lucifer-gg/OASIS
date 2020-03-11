<template>
  <div class="wrapper">
    <side-bar>
      
      <mobile-menu slot="content"></mobile-menu>
      <div style="height=2000px"></div>
      <sidebar-link to="/admin/search">
        <i class="nc-icon nc-atom"></i>
        <p>Search</p>
      </sidebar-link>
      <sidebar-link to="/admin/rank">
        <i class="nc-icon nc-chart-pie-35"></i>
        <p>Rank</p>
      </sidebar-link>
      <sidebar-link to="/admin/about">
        <i class="nc-icon nc-notes"></i>
        <p>About</p>
      </sidebar-link>
     
  
    
    </side-bar>
    <div class="main-panel" style="margin-bottom: 1px">
<!--      <top-navbar></top-navbar>-->

      <dashboard-content @click="toggleSidebar">

      </dashboard-content>

<!--      <content-footer></content-footer>-->
    </div>
  </div>
</template>
<style lang="scss">

</style>
<script>
  import TopNavbar from './TopNavbar.vue'
  import ContentFooter from './ContentFooter.vue'
  import DashboardContent from './Content.vue'
  import MobileMenu from './MobileMenu.vue'
  export default {
    components: {
      TopNavbar,
      ContentFooter,
      DashboardContent,
      MobileMenu
    },
    methods: {
      toggleSidebar () {
        if (this.$sidebar.showSidebar) {
          this.$sidebar.displaySidebar(false)
        }
      }
    }
  }

</script>
