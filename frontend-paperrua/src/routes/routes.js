import DashboardLayout from '../layout/DashboardLayout.vue'
import NotFound from '../pages/NotFoundPage.vue'
import Search from 'src/pages/Search.vue'
import Rank from 'src/pages/Rank.vue'
import About from 'src/pages/About.vue'



const routes = [
  {
    path: '/',
    component: DashboardLayout,
    redirect: '/admin/search'
  },
  {
    path: '/admin',
    component: DashboardLayout,
    redirect: '/admin/search',
    children: [
      {
        path: 'search',
        name: 'Search',
        component: Search
      },
      {
        path: 'rank',
        name: 'Rank',
        component: Rank
      },
      {
        path: 'about',
        name: 'About',
        component: About
      },
  

    ]
  },
  { path: '*', component: NotFound }
]

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes
