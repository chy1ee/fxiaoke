import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/fxiaoke',
    component: Layout,
    name: 'fxiaoke',
    meta: { title: '纷享销客', icon: 'message' },
    children: [
      {
        path: 'dept',
        name: 'dept',
        component: () => import('@/views/fxiaoke/dept'),
        meta: { title: '部门与员工', icon: 'peoples' }
      },
      {
        path: 'object',
        name: 'object',
        component: () => import('@/views/fxiaoke/object'),
        meta: { title: '对象列表', icon: 'skill' }
      },
      {
        path: 'data',
        name: 'data1',
        component: () => import('@/views/fxiaoke/data'),
        meta: { title: '对象接口', icon: 'skill' }
      },
      {
        path: 'describe/:name',
        name: 'describe',
        component: () => import('@/views/fxiaoke/describe'),
        meta: { title: '对象描述', icon: 'skill' },
        hidden: true
      }
    ]
  },

  {
    path: '/log',
    component: Layout,
    name: 'log',
    meta: { title: '日志管理', icon: 'list' },
    children: [
      {
        path: 'quartz',
        name: 'quartz',
        component: () => import('@/views/log/quartz'),
        meta: { title: '调度日志', icon: 'eye' }
      },
      {
        path: 'job/:id?',
        name: 'job',
        component: () => import('@/views/log/log'),
        meta: { title: '任务信息', icon: 'education' }
      },
      {
        path: 'detail/:id?',
        name: 'detail',
        component: () => import('@/views/log/detail'),
        meta: { title: '任务日志', icon: 'nested' }
      }
    ]
  },

  {
    path: '/quartz',
    component: Layout,
    name: 'quartz',
    meta: { title: '对接任务管理', icon: 'skill' },
    children: [
      {
        path: 'group',
        name: 'group',
        component: () => import('@/views/quartz/group'),
        meta: { title: '分组管理', icon: 'component' }
      },
      {
        path: 'trigger',
        name: 'trigger',
        component: () => import('@/views/quartz/trigger'),
        meta: { title: '触发器管理', icon: 'example' }
      },
      {
        path: 'job',
        name: 'quartz_job',
        component: () => import('@/views/quartz/job'),
        meta: { title: '执行器管理', icon: 'documentation' }
      }
    ]
  },

  {
    path: '/config',
    component: Layout,
    redirect: '/config/index',
    name: 'config',
    meta: { title: '系统配置', icon: 'edit' },
    children: [
      {
        path: 'job',
        name: 'config_job',
        component: () => import('@/views/config/job'),
        meta: { title: '任务配置', icon: 'chart' }
      },
      {
        path: 'index',
        name: 'index',
        component: () => import('@/views/config/index'),
        meta: { title: 'OpenAPI配置', icon: 'password' }
      },
      {
        path: 'touser',
        name: 'touser',
        component: () => import('@/views/config/report'),
        meta: { title: '数据报告配置', icon: 'email' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
